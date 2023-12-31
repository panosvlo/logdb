-- Users Table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    username VARCHAR(100) UNIQUE,
    password VARCHAR(100),
    address VARCHAR(255),
    email VARCHAR(100)
);

-- Log Types Table
CREATE TABLE log_types (
    id SERIAL PRIMARY KEY,
    type_name VARCHAR(100) UNIQUE
);

-- Logs Table
CREATE TABLE logs (
    id SERIAL PRIMARY KEY,
    log_type_id BIGINT,
    timestamp TIMESTAMP,
    source_ip VARCHAR(15),
    destination_ip VARCHAR(15),
    FOREIGN KEY (log_type_id) REFERENCES log_types (id)
);

-- Log Details Table
CREATE TABLE log_details (
    id SERIAL PRIMARY KEY,
    log_id INT,
    key VARCHAR(255),
    value TEXT,
    FOREIGN KEY (log_id) REFERENCES logs (id)
);

INSERT INTO log_types (type_name) VALUES ('access_log');
INSERT INTO log_types (type_name) VALUES ('hdfs_dataxceiver_log');
INSERT INTO log_types (type_name) VALUES ('hdfs_fs_namesystem_log');

