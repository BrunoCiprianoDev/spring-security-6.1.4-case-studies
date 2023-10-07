CREATE TYPE user_role AS ENUM ('admin', 'employee', 'client');

CREATE TABLE IF NOT EXISTS users (
    id bigserial PRIMARY KEY,
    login TEXT NOT NULL UNIQUE,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    password TEXT NOT NULL,
    role TEXT NOT NULL,
    account_non_expired boolean,
    account_non_locked boolean,
    credentials_non_expired boolean,
    enabled boolean
);