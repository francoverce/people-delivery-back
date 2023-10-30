create table if not exists users(
                                    id bigserial primary key,
                                    email varchar unique,
                                    name varchar,
                                    last_name varchar,
                                    password varchar not null,
                                    rol varchar,
                                    code UUID unique,
                                    icon varchar,
                                    created_at TIMESTAMP,
                                    updated_at TIMESTAMP,
                                    favorites text[]
);

ALTER TABLE users ADD constraint pk_id_email UNIQUE (id,email);