create table public.tbl_user
(
    id          bigserial not null
        constraint tbl_user_pkey
            primary key,
    create_date timestamp,
    email       varchar(255),
    first_name  varchar(255),
    last_name   varchar(255),
    password    varchar(255),
    role        varchar(255),
    user_name   varchar(255)
);