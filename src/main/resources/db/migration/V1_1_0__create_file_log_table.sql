CREATE SCHEMA IF NOT EXISTS giftandgo;

CREATE TABLE giftandgo.log_entry (
    uuid VARCHAR(255) PRIMARY KEY,
    uri VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    response_code INTEGER NOT NULL,
    ip_address VARCHAR(255) NOT NULL,
    ip_country VARCHAR(255),
    ip_provider VARCHAR(255),
    duration INTEGER NOT NULL
);