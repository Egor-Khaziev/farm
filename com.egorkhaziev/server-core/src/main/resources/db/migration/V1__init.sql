create table users
(
    id         bigserial primary key not null,
    username   varchar(255)          not null,
    password   varchar(255)          not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);


insert into users (username, password)
values ('bob',  '100'),
       ('max', '100');

create table tasks
(
    id         bigserial primary key not null,
    title      varchar(255)          not null,
    user_id    bigint                not null references users (id),
    status     ENUM ('RENDERING', 'ERROR', 'COMPLETE') not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into tasks (title, user_id, status)
values ('task1', 1,  'COMPLETE'),
       ('task4', 2, 'COMPLETE'),
       ('task11', 1, 'COMPLETE');

create table status_history
(
    id         bigserial primary key,
    task_id    bigint not null references tasks (id),
    status     ENUM ('RENDERING', 'ERROR', 'COMPLETE') not null,
    created_at timestamp default current_timestamp
);

insert into status_history (task_id, status)
values (1, 'RENDERING'),
       (2, 'RENDERING'),
       (3, 'RENDERING'),
       (1, 'COMPLETE'),
       (2, 'COMPLETE'),
       (3, 'COMPLETE');








