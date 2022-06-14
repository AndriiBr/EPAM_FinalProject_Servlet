insert into user_role (id, role_en, role_ua)
VALUES (0, 'banned', 'заблокований');
insert into user_role (id, role_en, role_ua)
VALUES (1, 'admin', 'адміністратор');
insert into user_role (id, role_en, role_ua)
VALUES (2, 'user', 'користувач');

insert into genre (name_en, name_ua)
values ('fantasy', 'фентезі');
insert into genre (name_en, name_ua)
values ('economy', 'економіка');
insert into genre (name_en, name_ua)
values ('news', 'новини');
insert into genre (name_en, name_ua)
values ('science', 'наука');
insert into genre (name_en, name_ua)
values ('nature', 'природа');
insert into genre (name_en, name_ua)
values ('technology', 'технології');

insert into edition (title_en, title_ua, title_image, genre_id, price)
values ('National Geographic - The Space', 'Національна географія - Космос',
        'http://localhost:8081/edition_titles/National Geographic - The Space.jpg', 5, 500);
insert into edition (title_en, title_ua, title_image, genre_id, price)
values ('Forbes', 'Форбс', 'http://localhost:8081/edition_titles/Forbes.jpg', 2, 500);
insert into edition (title_en, title_ua, title_image, genre_id, price)
values ('National Geographic - Wild', 'Національна географія - Дика природа',
        'http://localhost:8081/edition_titles/National Geographic - Wild.jpg', 5, 450);
insert into edition (title_en, title_ua, title_image, genre_id, price)
values ('Time', 'Час', 'http://localhost:8081/edition_titles/Time.jpg', 3, 300);
insert into edition (title_en, title_ua, title_image, genre_id, price)
values ('Fantasy Magazine', 'Журнал фантастики', 'http://localhost:8081/edition_titles/Fantasy magazine.jpg', 1, 400);
insert into edition (title_en, title_ua, title_image, genre_id, price)
values ('Science News', 'Новини науки', 'http://localhost:8081/edition_titles/Science News.jpg', 4, 420);
insert into edition (title_en, title_ua, title_image, genre_id, price)
values ('Wired', 'На проводі', 'http://localhost:8081/edition_titles/Wired.jpg', 6, 370);
insert into edition (title_en, title_ua, title_image, genre_id, price)
values ('Digital Innovation', 'Цифрові інновації', 'http://localhost:8081/edition_titles/Digital Innovation.jpg', 6,
        430);
insert into edition (title_en, title_ua, title_image, genre_id, price)
values ('Data Economy', 'Дані економіки', 'http://localhost:8081/edition_titles/Data Economy.jpg', 2, 280);
insert into edition (title_en, title_ua, title_image, genre_id, price)
values ('Economics', 'Економіка', 'http://localhost:8081/edition_titles/Economics.jpg', 2, 310);
insert into edition (title_en, title_ua, title_image, genre_id, price)
values ('Recycling', 'Переробка', 'http://localhost:8081/edition_titles/Recycling.jpg', 2, 280);
insert into edition (title_en, title_ua, title_image, genre_id, price)
values ('Science', 'Наука', 'http://localhost:8081/edition_titles/Science.jpg', 4, 315);
insert into edition (title_en, title_ua, title_image, genre_id, price)
values ('Popular science', 'Популярна наука', 'http://localhost:8081/edition_titles/Popular science.jpg', 4, 340);
insert into edition (title_en, title_ua, title_image, genre_id, price)
values ('Nature', 'Природа', 'http://localhost:8081/edition_titles/Nature.jpg', 5, 280);

insert into usr (username, pass, email, first_name, user_image, balance, user_role_id)
VALUES ('admin', 'Pass1234', 'admin@gmail.com', 'Andrii', 'no image', 1100, 1);
insert into usr (username, pass, email, first_name, user_image, balance, user_role_id)
VALUES ('user1', 'Pass1234', 'user1@gmail.com', 'Diana', 'no image', 1200, 2);
insert into usr (username, pass, email, first_name, user_image, balance, user_role_id)
VALUES ('user2', 'Pass1234', 'user2@gmail.com', 'Artem', 'no image', 1300, 2);
insert into usr (username, pass, email, first_name, user_image, balance, user_role_id)
VALUES ('user3', 'Pass1234', 'user3@gmail.com', 'Alexandra', 'no image', 1400, 2);
insert into usr (username, pass, email, user_image, balance, user_role_id)
VALUES ('user4', 'Pass1234', 'user4@gmail.com', 'no image', 1500, 2);