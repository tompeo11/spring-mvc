# database spring-mvc

create database c2202l_spring_crm; use c2202l_spring_crm;

set foreign_key_checks = 0;

drop table if exists authorities;

drop table if exists users; create table users ( id int(11) NOT NULL AUTO_INCREMENT, username varchar(50) NOT NULL, password varchar(68) NOT NULL, email varchar(250) NULL, enabled tinyint(1) NOT NULL, PRIMARY KEY (id) ) ENGINE=InnoDB CHARSET=utf8;

insert into users(username,password,email,enabled) values ('ray','$2a$12$yrqpXaFZTUXIHkriRkWEu.DiCGPMPj1JKjYEhN3Iqf4mrVRjgJxNK','ray@email.com',1), ('tommy','$2a$12$MGOF3pyaPnHMhDGzPL/zzONLYM2Ui6oRvVVXtbPaBnIh1XSB/0b/2','tommy@email.com',1);

drop table if exists roles; create table roles ( id int(11) NOT NULL AUTO_INCREMENT, name varchar(50) NOT NULL, PRIMARY KEY (id) ) ENGINE=InnoDB CHARSET=utf8;

insert into roles(name) values ('ROLE_EMPLOYEE'),('ROLE_ADMIN');

drop table if exists users_roles; CREATE TABLE users_roles ( user_id int(11) not null, role_id int(11) not null, PRIMARY KEY (user_id, role_id), KEY FK_USER_idx (user_id), CONSTRAINT FK_user_role_01 FOREIGN KEY (user_id) REFERENCES users (id) on delete no action on update no action, CONSTRAINT FK_role_user_01 FOREIGN KEY (role_id) REFERENCES roles (id) on delete no action on update no action ) engine=InnoDB auto_increment=1 CHARSET=utf8;

insert into users_roles(user_id,role_id) values (1,1),(1,2),(2,1);

set foreign_key_checks = 1;