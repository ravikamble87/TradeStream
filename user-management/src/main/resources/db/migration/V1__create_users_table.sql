-- V1__create_users_table.sql
CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       username VARCHAR(100) NOT NULL UNIQUE,
                       email VARCHAR(150) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       first_name VARCHAR(100),
                       last_name VARCHAR(100),
                       status VARCHAR(20) NOT NULL DEFAULT 'PENDING_VERIFICATION',
                       email_verified BOOLEAN NOT NULL DEFAULT FALSE,
                       failed_login_attempts INT NOT NULL DEFAULT 0,
                       account_locked_until TIMESTAMPTZ,
                       last_login_at TIMESTAMPTZ,
                       created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                       updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                       version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE user_roles (
                            user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                            role VARCHAR(30) NOT NULL,
                            PRIMARY KEY (user_id, role)
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_status ON users(status);