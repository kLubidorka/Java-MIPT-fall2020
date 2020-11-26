CREATE TABLE aircrafts
(
    aircraft_code CHAR(3) PRIMARY KEY NOT NULL,
    model JSON NOT NULL,
    range INTEGER NOT NULL
)