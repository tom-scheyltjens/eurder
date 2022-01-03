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

insert into customers(id, first_name, last_name, street_name, house_number, postal_code, city, email_address, phone_number, is_admin) values('test','Default','Admin','street','14','2300','Turnhout','default@admin.com','0123456789',true)