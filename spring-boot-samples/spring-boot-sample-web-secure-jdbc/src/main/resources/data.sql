insert into user (username, password, enabled) values ('user', '{noop}user', true);

insert into authority (username, authority) values ('user', 'ROLE_ADMIN');
