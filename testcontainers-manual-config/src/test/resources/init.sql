DROP TABLE IF EXISTS pets;
CREATE TABLE pets (
                      id BIGSERIAL NOT NULL PRIMARY KEY,
                      id_string VARCHAR NOT NULL,
                      name VARCHAR NOT NULL,
                      colour VARCHAR NOT NULL,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      UNIQUE (id_string),
                      UNIQUE (name)
)