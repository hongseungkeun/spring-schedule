create table schedule
(
    schedule_id bigint auto_increment
        primary key,
    title       varchar(255)                       not null,
    todo        varchar(255)                       null,
    user_name   varchar(255)                       null,
    password    varchar(255)                       null,
    createdAt   datetime default CURRENT_TIMESTAMP null,
    updatedAt   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);