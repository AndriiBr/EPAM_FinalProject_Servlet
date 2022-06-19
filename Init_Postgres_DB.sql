drop table if exists usr_edition cascade;
drop table if exists usr cascade;
drop table if exists usr_role;
drop table if exists edition cascade;
drop table if exists genre;

create table genre
(
    id      SERIAL,
    name_en varchar(255),
    name_ua varchar(255),
    primary key (id)
);

create table edition
(
    id          SERIAL,
    title_en    varchar(255),
    title_ua    varchar(255),
    text_en     varchar(8196),
    text_ua     varchar(8196),
    title_image varchar(2048),
    genre_id    INT references genre (id),
    price       INT,
    primary key (id)
);

create table user_role
(
    id      SERIAL,
    role_En varchar(25),
    role_Ua varchar(25),
    primary key (id)
);

create table usr
(
    id           SERIAL,
    username     varchar(255),
    pass         varchar(64),
    email        varchar(128),
    first_name   varchar(64),
    user_image   varchar(2048),
    balance      int,
    user_role_id int references user_role (id),
    primary key (id)
);

create table user_edition
(
    user_id    INT references usr (id),
    edition_id INT references edition (id)
);