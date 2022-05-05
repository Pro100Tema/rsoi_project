-- file: 10-create-user-and-db.sql
CREATE DATABASE services;
CREATE ROLE program4 WITH PASSWORD 'test';
GRANT ALL PRIVILEGES ON DATABASE services TO program4;
ALTER ROLE program4 WITH LOGIN;