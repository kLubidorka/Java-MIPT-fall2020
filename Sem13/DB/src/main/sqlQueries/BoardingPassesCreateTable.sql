CREATE TABLE boarding_passes
(
    ticket_no CHAR(13) NOT NULL,
    flight_id INTEGER  NOT NULL,
    boarding_no INTEGER  NOT NULL,
    seat_no VARCHAR(4) NOT NULL,
    PRIMARY KEY (ticket_no, flight_id)
);
