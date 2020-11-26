CREATE TABLE tickets
(
    ticket_no CHAR(13) PRIMARY KEY NOT NULL,
    book_ref CHAR(6) NOT NULL,
    passenger_id VARCHAR(20) NOT NULL,
    passenger_name TEXT NOT NULL,
    contact_data JSON NULL
);