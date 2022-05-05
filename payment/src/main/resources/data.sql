CREATE SCHEMA IF NOT EXISTS payments;
CREATE TABLE IF NOT EXISTS payments.payments
(
    id          SERIAL PRIMARY KEY,
    payment_uid uuid        NOT NULL,
    status      VARCHAR(20) NOT NULL CHECK (status IN ('PAID', 'CANCELED')),
    price       INT         NOT NULL
);

--GRANT ALL PRIVILEGES ON SCHEMA payments TO program4;
--GRANT ALL PRIVILEGES ON TABLE payments.payments TO program4;
--GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA payments TO program4;
