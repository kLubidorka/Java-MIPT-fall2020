SELECT DISTINCT d.from_city, a2.city as to_city, d.duration FROM airports a2
    INNER JOIN
(SELECT a.city as from_city, g.arrival_airport, g.duration FROM airports a
    INNER JOIN
(SELECT DISTINCT f.departure_airport, f.arrival_airport,
       EXTRACT(HOUR FROM f.actual_arrival - f.actual_departure) * 60 +
       EXTRACT(MINUTE FROM f.actual_arrival - f.actual_departure) AS duration
    FROM flights f INNER JOIN
    (SELECT departure_airport,
       MIN(
       EXTRACT(HOUR FROM actual_arrival - actual_departure) * 60 +
       EXTRACT(MINUTE FROM actual_arrival - actual_departure))
       AS duration FROM flights where actual_arrival IS NOT NULL GROUP BY departure_airport) s
    ON (f.departure_airport = s.departure_airport) AND
       (EXTRACT(HOUR FROM f.actual_arrival - f.actual_departure) * 60 +
        EXTRACT(MINUTE FROM f.actual_arrival - f.actual_departure) = s.duration)) g
    ON (a.airport_code = g.departure_airport)) d
    ON (a2.airport_code = d.arrival_airport) ORDER BY d.duration DESC;
