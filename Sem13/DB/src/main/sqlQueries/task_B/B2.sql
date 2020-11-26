SELECT TOP(?) airports.city as city, SUM(cancelled.cancelled_flights) as cancelled_flights FROM airports INNER JOIN
    (SELECT departure_airport as departure, COUNT(*) as cancelled_flights FROM flights WHERE
    status = 'Cancelled'
    GROUP BY departure
    ) as cancelled ON airports.airport_code = cancelled.departure GROUP BY city ORDER BY cancelled_flights DESC;