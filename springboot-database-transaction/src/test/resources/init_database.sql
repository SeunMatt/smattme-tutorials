CREATE TABLE IF NOT EXISTS users (
    id SERIAL,
    first_name VARCHAR(250),
    last_name VARCHAR(250),
    email VARCHAR(250) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS user_notification_preference
(
    id SERIAL,
    user_id INT,
    email_notification_enabled  BOOLEAN default TRUE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS audit_trail (
    id SERIAL,
    action VARCHAR,
    actor VARCHAR,
    created_at TIMESTAMP DEFAULT  CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);


