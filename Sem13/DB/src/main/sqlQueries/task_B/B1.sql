SELECT city, GROUP_CONCAT(airport_code) as airports_in_city from airports GROUP BY city HAVING COUNT(DISTINCT airport_code) > 1;