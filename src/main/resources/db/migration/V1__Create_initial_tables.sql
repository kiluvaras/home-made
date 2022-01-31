create table product
(
    id            serial primary key,
    name          varchar(50),
    price         numeric(19, 2),
    quantity      integer,
    quantity_type varchar(3),
    seller        varchar
)