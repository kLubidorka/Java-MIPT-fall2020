DELETE FROM flights WHERE aircraft_code IN (
    SELECT aircraft_code FROM aircrafts WHERE model = ?
    );