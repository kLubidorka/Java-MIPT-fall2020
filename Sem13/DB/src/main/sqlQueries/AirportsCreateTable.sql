CREATE TABLE airports
(
    airport_code CHAR(3) PRIMARY KEY NOT NULL,
    airport_name JSON NOT NULL,
    city JSON NOT NULL,
    coordinates GEOMETRY(POINT) NOT NULL,
    timezone TEXT NOT NULL
);