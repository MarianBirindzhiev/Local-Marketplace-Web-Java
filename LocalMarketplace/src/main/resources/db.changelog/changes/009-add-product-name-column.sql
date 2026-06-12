--liquibase formatted sql

--changeset fmi:009-add-product-name-column
ALTER TABLE products ADD COLUMN name VARCHAR(100) NOT NULL DEFAULT 'Unnamed Product';

--rollback ALTER TABLE products DROP COLUMN name;
