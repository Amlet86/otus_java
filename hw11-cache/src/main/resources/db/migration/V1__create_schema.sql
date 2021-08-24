CREATE SEQUENCE hibernate_sequence start with 1 increment by 1;

CREATE TABLE client
(
    id bigint not null primary key,
    name varchar(50)
);