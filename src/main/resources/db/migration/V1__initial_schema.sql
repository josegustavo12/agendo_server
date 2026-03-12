-- Professions
CREATE TABLE IF NOT EXISTS professions (
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Users
CREATE TABLE IF NOT EXISTS users (
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL UNIQUE,
    phone         VARCHAR(255) NOT NULL,
    role          VARCHAR(50)  NOT NULL,
    token         VARCHAR(255) UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at    TIMESTAMP    NOT NULL
);

-- Professionals
CREATE TABLE IF NOT EXISTS professionals (
    id            BIGSERIAL PRIMARY KEY,
    user_id       BIGINT       NOT NULL UNIQUE REFERENCES users(id),
    profession_id BIGINT       REFERENCES professions(id),
    bio           TEXT,
    is_available  BOOLEAN      NOT NULL DEFAULT TRUE
);

-- Clients
CREATE TABLE IF NOT EXISTS clients (
    id                       BIGSERIAL PRIMARY KEY,
    user_id                  BIGINT       NOT NULL UNIQUE REFERENCES users(id),
    tax_id                   VARCHAR(255),
    preferred_payment_method VARCHAR(255)
);

-- Service Types
CREATE TABLE IF NOT EXISTS service_types (
    id          BIGSERIAL      PRIMARY KEY,
    owner_id    BIGINT         NOT NULL REFERENCES users(id),
    name        VARCHAR(255)   NOT NULL,
    description VARCHAR(255),
    price       NUMERIC(10, 2) NOT NULL
);

-- Appointments
CREATE TABLE IF NOT EXISTS appointments (
    id              BIGSERIAL      PRIMARY KEY,
    professional_id BIGINT         NOT NULL REFERENCES users(id),
    client_id       BIGINT         NOT NULL REFERENCES users(id),
    total_amount    NUMERIC(10, 2) NOT NULL,
    schedule_date   TIMESTAMP      NOT NULL,
    request_date    TIMESTAMP      NOT NULL
);

-- Appointment Services (join table)
CREATE TABLE IF NOT EXISTS appointment_services (
    id               BIGSERIAL PRIMARY KEY,
    appointment_id   BIGINT NOT NULL REFERENCES appointments(id),
    service_type_id  BIGINT NOT NULL REFERENCES service_types(id)
);

-- Ratings
CREATE TABLE IF NOT EXISTS ratings (
    id              BIGSERIAL PRIMARY KEY,
    professional_id BIGINT  NOT NULL REFERENCES users(id),
    client_id       BIGINT  NOT NULL REFERENCES users(id),
    score           INTEGER NOT NULL,
    comment         TEXT,
    created_at      TIMESTAMP NOT NULL
);
