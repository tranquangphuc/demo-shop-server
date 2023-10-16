CREATE TABLE IF NOT EXISTS customer (
	id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	name varchar NOT NULL,
	email varchar NOT NULL,
	birthday date,
	deleted boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS shop (
	id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	name varchar NOT NULL,
	address varchar NOT NULL,
	deleted boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS product (
	id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	shop_id bigint NOT NULL,
	name varchar NOT NULL,
	image_url varchar,
	price float8 NOT NULL,
	deleted boolean NOT NULL DEFAULT FALSE,
	CONSTRAINT product_fk_shop_id FOREIGN KEY (shop_id) REFERENCES shop(id)
);

CREATE TABLE IF NOT EXISTS order_item (
	customer_id bigint NOT NULL,
	product_id bigint NOT NULL,
	ordered_date timestamp WITH time ZONE NOT NULL,
	PRIMARY KEY (customer_id, product_id, ordered_date),
	CONSTRAINT order_item_fk_customer_id FOREIGN KEY (customer_id) REFERENCES customer(id),
	CONSTRAINT order_item_fk_product_id FOREIGN KEY (product_id) REFERENCES product(id)
);
