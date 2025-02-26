CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE product (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    code int,
    name VARCHAR(100) NOT NULL,
    price NUMERIC(10,2)
);