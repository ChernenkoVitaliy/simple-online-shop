INSERT INTO account(email, password, created_at, account_status) VALUES('email1@mail.com', 'password1', '2022-12-01T00:21:37', 'ACTIVE');
INSERT INTO account(email, password, created_at, account_status) VALUES('email2@mail.com', 'password2', '2019-10-10T13:10:01', 'BLOCKED');
INSERT INTO account(email, password, created_at, account_status) VALUES('email3@mail.com', 'password3', '2022-04-21T00:00:00', 'BANNED');
INSERT INTO account(email, password, created_at, account_status) VALUES('email4@mail.com', 'password4', '2022-12-15T20:59:40', 'ACTIVE');
INSERT INTO account(email, password, created_at, account_status) VALUES('email5@mail.com', 'password5', '2021-05-07T01:22:20', 'ACTIVE');
INSERT INTO account(email, password, created_at, account_status) VALUES('email6@mail.com', 'password6', '2022-03-30T21:21:21', 'ACTIVE');

INSERT INTO address(country, city, street) VALUES('Ukraine', 'Kyiv', '1st avenue');
INSERT INTO address(country, city, street) VALUES('USA', 'New York', '5th avenue');

INSERT INTO seller(account_id, company_name, company_description, company_site) VALUES (4, 'Company1', 'Company description1', 'site1.com.ua');
INSERT INTO seller(account_id, company_name, company_description, company_site) VALUES (5, 'Company2', 'Company description2', 'site2.com.ua');
INSERT INTO seller(account_id, company_name, company_description, company_site) VALUES (6, 'Company3', 'Company description3', 'site3.com.ua');

INSERT INTO seller_phones(phone, seller_id) VALUES('123-45-67', 3);
INSERT INTO seller_phones(phone, seller_id) VALUES('234-56-78', 2);
INSERT INTO seller_phones(phone, seller_id) VALUES('345-67-89', 1);
INSERT INTO seller_phones(phone, seller_id) VALUES('456-78-90', 1);

INSERT INTO customer(account_id, name, surname, phone, address_id) VALUES(3, 'Name1', 'Surname1', '012-345-67', 2);
INSERT INTO customer(account_id, name, surname, phone, address_id) VALUES(2, 'Name2', 'Surname2', '123-65-21', 1);
INSERT INTO customer(account_id, name, surname, phone, address_id) VALUES(1, 'Name3', 'Surname3', '765-43-21', null);

INSERT INTO tag(name, description) VALUES('tag_name1', 'tag_description1');
INSERT INTO tag(name, description) VALUES('tag_name2', 'tag_description2');
INSERT INTO tag(name, description) VALUES('tag_name3', 'tag_description3');

INSERT INTO product(name, description, price, seller_id) VALUES('product_name1', 'product_description1' ,1000.2 ,3);
INSERT INTO product(name, description, price, seller_id) VALUES('product_name2', 'product_description2' ,2000.3 ,2);
INSERT INTO product(name, description, price, seller_id) VALUES('product_name3', 'product_description3' ,3000.1 ,1);

INSERT INTO feedback(account_id, feedback_text, created_at, product_id) VALUES(3, 'Feedback_text1', '2022-01-01T00:21:37',1);
INSERT INTO feedback(account_id, feedback_text, created_at, product_id) VALUES(2, 'Feedback_text2', '2019-12-13T17:20:01',2);
INSERT INTO feedback(account_id, feedback_text, created_at, product_id) VALUES(1, 'Feedback_text3', '2022-07-09T12:03:03',3);

INSERT INTO cart(created_at, customer_id) VALUES('2022-10-10T00:02:27', 2);
INSERT INTO cart(created_at, customer_id) VALUES('2021-12-07T13:21:10', 1);
INSERT INTO cart(created_at, customer_id) VALUES('2022-04-28T07:07:07', 3);

INSERT INTO orders(created_at, order_status, customer_id) VALUES('2022-01-01T01:21:21', 'NEW',2);
INSERT INTO orders(created_at, order_status, customer_id) VALUES('2022-11-11T11:03:11', 'PENDING',1);
INSERT INTO orders(created_at, order_status, customer_id) VALUES('2022-12-15T08:36:55', 'AWAITING_PAYMENT',3);

INSERT INTO delivery(order_id, customer_id, created_at, delivery_date, delivered_at) VALUES(2, 3, '2022-12-15T01:21:21', null, null);
INSERT INTO delivery(order_id, customer_id, created_at, delivery_date, delivered_at) VALUES(3, 2, '2022-12-14T10:17:35', '2022-12-15T00:00:00', null);
INSERT INTO delivery(order_id, customer_id, created_at, delivery_date, delivered_at) VALUES(1, 1, '2022-12-12T17:04:59', '2022-12-13T17:00:00', '2022-12-13T17:00:00');

INSERT INTO orders_products(order_id, product_id) VALUES(2, 1);
INSERT INTO orders_products(order_id, product_id) VALUES(1, 2);
INSERT INTO orders_products(order_id, product_id) VALUES(3, 2);
INSERT INTO orders_products(order_id, product_id) VALUES(2, 3);
INSERT INTO orders_products(order_id, product_id) VALUES(2, 2);

INSERT INTO wish_list_product_customer(product_id, customer_id) VALUES(1, 1);
INSERT INTO wish_list_product_customer(product_id, customer_id) VALUES(1, 2);
INSERT INTO wish_list_product_customer(product_id, customer_id) VALUES(3, 1);
INSERT INTO wish_list_product_customer(product_id, customer_id) VALUES(3, 3);
INSERT INTO wish_list_product_customer(product_id, customer_id) VALUES(2, 1);

INSERT INTO products_tags(product_id, tag_id) VALUES(1, 1);
INSERT INTO products_tags(product_id, tag_id) VALUES(1, 2);
INSERT INTO products_tags(product_id, tag_id) VALUES(2, 1);
INSERT INTO products_tags(product_id, tag_id) VALUES(3, 1);

INSERT INTO carts_products(cart_id, product_id) VALUES(1, 2);
INSERT INTO carts_products(cart_id, product_id) VALUES(1, 3);
INSERT INTO carts_products(cart_id, product_id) VALUES(2, 2);