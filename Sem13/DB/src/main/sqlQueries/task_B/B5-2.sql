SELECT DAYNAME(scheduled_departure) AS day, COUNT(*) AS flights FROM flights
    WHERE departure_airport in ('DME', 'SVO', 'VKO') GROUP BY day ORDER BY flights DESC;