CREATE TABLE ticket_flights
(
    ticket_no CHAR(13) NOT NULL,
    flight_id INTEGER NOT NULL,
    fare_conditions VARCHAR(10) NOT NULL,
    amount NUMERIC(10,2) NOT NULL,
    PRIMARY KEY (ticket_no, flight_id)
);

