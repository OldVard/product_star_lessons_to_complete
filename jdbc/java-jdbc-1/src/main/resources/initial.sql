create table if not exists task (
    task_id serial primary key,
    title varchar(255),
    finished boolean,
    creation_date timestamp
);