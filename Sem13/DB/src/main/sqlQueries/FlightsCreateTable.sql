CREATE TABLE flights
(
    flight_id VARCHAR(10) PRIMARY KEY NOT NULL,
    flight_no CHAR(6) NOT NULL,
    scheduled_departure TIMESTAMP WITH TIME ZONE NOT NULL,
    scheduled_arrival TIMESTAMP WITH TIME ZONE NOT NULL,
    departure_airport CHAR(3) NOT NULL,
    arrival_airport CHAR(3) NOT NULL,
    status VARCHAR(20) NOT NULL,
    aircraft_code CHAR(3) NOT NULL,
    actual_departure TIMESTAMP WITH TIME ZONE NULL,
    actual_arrival TIMESTAMP WITH TIME ZONE NULL
);
