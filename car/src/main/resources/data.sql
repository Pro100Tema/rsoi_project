CREATE SCHEMA IF NOT EXISTS cars;
CREATE TABLE IF NOT EXISTS cars.cars
(
    id                  SERIAL PRIMARY KEY,
    car_uid             uuid UNIQUE NOT NULL,
    brand               VARCHAR(80) NOT NULL,
    model               VARCHAR(80) NOT NULL,
    registration_number VARCHAR(20) NOT NULL,
    power               INT,
    price               INT         NOT NULL,
    type                VARCHAR(20) CHECK (type IN ('SEDAN', 'SUV', 'MINIVAN', 'ROADSTER')),
    availability        BOOLEAN     NOT NULL
);
--GRANT ALL PRIVILEGES ON DATABASE services TO program4;
GRANT ALL PRIVILEGES ON SCHEMA cars TO program;
GRANT ALL PRIVILEGES ON TABLE cars.cars TO program;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA cars TO program;

INSERT INTO cars.cars (car_uid, brand, model, registration_number, power, type, price, availability)
VALUES ('109b42f3-198d-4c89-9276-a7520a7120ab', 'Mercedes Benz', 'GLA 250', 'ЛО777Х799', 249, 'SEDAN', 3500, true);