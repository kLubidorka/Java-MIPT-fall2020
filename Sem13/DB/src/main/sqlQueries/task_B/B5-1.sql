SELECT DAYNAME(scheduled_departure) AS day, COUNT(*) AS flights FROM flights
    WHERE arrival_airport IN ('DME', 'SVO', 'VKO') GROUP BY day ORDER BY flights DESC;