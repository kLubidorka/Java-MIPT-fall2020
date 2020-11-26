UPDATE flights SET status = 'Cancelled' WHERE flight_id IN
    (SELECT flight_id AS id FROM flights WHERE scheduled_departure
    BETWEEN
    PARSEDATETIME(?, 'dd.MM.yyyy', 'en', 'GMT')
    AND
    PARSEDATETIME(?, 'dd.MM.yyyy', 'en', 'GMT')
    AND (
        departure_airport IN ('DME', 'SVO', 'VKO')
        OR
        arrival_airport IN ('DME', 'SVO', 'VKO')
            ));

SELECT FORMATDATETIME(f.scheduled_departure, 'dd.MM.yyyy') as d, SUM(sums.sum) as sum FROM (flights f INNER JOIN
(SELECT flight_id as id, SUM(amount) as sum FROM ticket_flights WHERE flight_id IN
     (SELECT flight_id AS id FROM flights WHERE scheduled_departure
     BETWEEN
     PARSEDATETIME(?, 'dd.MM.yyyy', 'en', 'GMT')
     AND
     PARSEDATETIME(?, 'dd.MM.yyyy', 'en', 'GMT')
     AND (
        departure_airport IN ('DME', 'SVO', 'VKO')
        OR
        arrival_airport IN ('DME', 'SVO', 'VKO')
        )) GROUP BY flight_id) sums
     ON f.flight_id = sums.id) GROUP BY d;