create table if not exists books (
    id serial primary key,
    name varchar(255) not null,
    author varchar(255) not null,
    pages int not null
);

-- insert into books (name, author, pages) values ('Американские боги', 'Нил Гейман', 702);
-- insert into books (name, author, pages) values ('Благие Знамения', 'Терри Пратчетт', 360);
-- insert into books (name, author, pages) values ('Ведьмак: Башня Ласточки', 'Анджей Сапковский', 358);
-- insert into books (name, author, pages) values ('Властелин Колец: Братство Кольца', 'Джон Рональд Руэл Толкин', 442);
-- insert into books (name, author, pages) values ('Гарри Поттер и Дары Смерти', 'Джоан Роулинг', 450);