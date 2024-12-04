use schedule;
drop table user;
drop table schedule;

create table user
(
    user_id    bigint auto_increment primary key,
    email      varchar(255)                       not null UNIQUE,
    password   varchar(255)                       not null,
    name       varchar(255)                       not null,
    created_at datetime default CURRENT_TIMESTAMP not null,
    updated_at datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP
);

create table schedule
(
    schedule_id bigint auto_increment primary key,
    title       varchar(255)                       not null,
    todo        varchar(255)                       not null,
    created_at  datetime default current_timestamp not null,
    updated_at  datetime default current_timestamp not null on update current_timestamp,
    user_id     bigint                             not null,
    constraint fk_schedule_user foreign key (user_id) references user (user_id) on delete cascade
);