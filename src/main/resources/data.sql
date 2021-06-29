INSERT INTO role
VALUES (1, 'ADMIN'),
       (2, 'USER');
insert into users
values (1, 'chococom.company@gmail.com', 'ADMIN', '$2a$08$uLV71cxNaYCdc7DwEJBfCexDW37FMGE6WW4pByCnrCSBaCBOZGZH.');
insert into users
values (2, 'chococom.company@gmail.com', 'USER', '$2a$08$5lsYEu/0ebT91m7t1pOH5OJVxdiswEioARl0jW1Wz1L6onLoS3S.C');
insert into basket values ( 1, 2 );
insert into users
values (3, 'alexandra.grishchenko@gmail.com', 'SASHA', '$2a$08$H9cQqNGa/MLtCT.BYiTa1ee8ACeyBihRMdODSh7LOpbyPUcJen1aS');
insert into basket values ( 2, 3 );
insert into role_users values ( 1, 1 );
insert into role_users values ( 2, 2 );
insert into role_users values ( 2, 3 );
insert into item values ( 1, 'Dairy products', 'Milk');
insert into item_tags values ( 1,1);
insert into item_tags values ( 1,2);
insert into item_tags values ( 1,3);
insert into item values ( 2, 'Dairy products', 'Butter');
insert into item_tags values ( 2,5);
insert into item_tags values ( 2,3);
insert into item_tags values ( 2,4);
insert into item values ( 3, 'Meat products', 'Chicken');
insert into item_tags values ( 3,0);
insert into item_tags values ( 3,1);
insert into item_tags values ( 3,4);
insert into item values ( 4, 'Meat products', 'Pork');
insert into item_tags values ( 4,1);
insert into item_tags values ( 4,3);
insert into item_tags values ( 4,4);