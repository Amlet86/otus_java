ALTER TABLE client ADD COLUMN address_id bigint;

ALTER TABLE client ADD FOREIGN KEY (address_id)
REFERENCES address;