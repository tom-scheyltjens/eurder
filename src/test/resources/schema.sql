create table items
(
    ID          varchar not null primary key,
    NAME        varchar not null,
    DESCRIPTION varchar not null,
    PRICE       float   not null,
    AMOUNT      float   not null
);

create table customers
(
    ID            varchar not null primary key,
    FIRST_NAME    varchar not null,
    LAST_NAME     varchar not null,
    STREET_NAME   varchar not null,
    HOUSE_NUMBER  varchar not null,
    POSTAL_CODE   varchar not null,
    CITY          varchar not null,
    EMAIL_ADDRESS varchar not null,
    PHONE_NUMBER  varchar not null,
    IS_ADMIN      bool default false
);

create table orders
(
    ID             varchar not null primary key,
    FK_CUSTOMER_ID varchar not null,
    TOTAL_PRICE    float   not null,
    constraint FK_ORDERS_CUSTOMER foreign key (FK_CUSTOMER_ID) references customers (ID)
);

create table item_groups
(
    ID            varchar not null primary key,
    FK_ITEM_ID    varchar not null,
    FK_ORDER_ID   varchar not null,
    ITEM_PRICE    float   not null,
    AMOUNT        int     not null,
    SHIPPING_DATE date    not null,
    constraint FK_ITEM_GROUPS_ITEM foreign key (FK_ITEM_ID) references items (ID),
    constraint FK_ITEM_GROUPS_ORDER foreign key (FK_ORDER_ID) references orders (ID)
);

insert into customers(id, first_name, last_name, street_name, house_number, postal_code, city, email_address, phone_number, is_admin) values('test','Default','Admin','street','14','2300','Turnhout','default@admin.com','0123456789',true)