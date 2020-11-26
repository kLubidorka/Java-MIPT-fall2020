SELECT seat_no from SEATS WHERE
    (aircraft_code IN (SELECT flight_id FROM flights WHERE flight_id = ?)) AND (seat_no = ?) AND (fare_conditions = ?);

UPDATE bookings SET total_amount = total_amount + ? WHERE (book_date = ?) AND (book_ref = ?);

INSERT INTO tickets VALUES (?, ?, ?, ?, ?);

INSERT INTO ticket_flights VALUES (?, ?, ?, ?);

INSERT INTO boarding_passes VALUES (?, ?, ?, ?);
