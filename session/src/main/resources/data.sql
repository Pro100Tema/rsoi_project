CREATE SCHEMA IF NOT EXISTS session;
CREATE TABLE IF NOT EXISTS session.session
(
    id          SERIAL PRIMARY KEY,
    user_uid uuid        NOT NULL,
    name     VARCHAR(255) NOT NULL,
    surname     VARCHAR(255) NOT NULL,
    login     VARCHAR(255) NOT NULL,
    password     VARCHAR(255) NOT NULL,
    role      VARCHAR(20) NOT NULL CHECK (role IN ('USER', 'ADMIN'))
);

INSERT INTO session.session (user_uid, name, surname, login, password, role)
VALUES ('109b42f3-198d-4c89-9276-a7520a7120aa', 'Артём', 'Егорычев', 'user', 'test', 'USER');
GRANT ALL PRIVILEGES ON SCHEMA session TO program4;
GRANT ALL PRIVILEGES ON TABLE session.session TO program4;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA session TO program4;