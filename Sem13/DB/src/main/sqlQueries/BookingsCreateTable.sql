CREATE TABLE bookings
(
    book_ref CHAR(6) PRIMARY KEY NOT NULL,
    book_date TIMESTAMP WITH TIME ZONE  NOT NULL,
    total_amount NUMERIC(10,2)  NOT NULL
);