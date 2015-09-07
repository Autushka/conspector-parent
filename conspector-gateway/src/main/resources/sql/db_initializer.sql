create database gateway;

create table users(
		username varchar(100) not null primary key,
		password varchar(100) not null,
		enabled boolean not null);

create table authorities (
		username varchar(100) not null,
		authority varchar(100) not null,
		constraint fk_authorities_users foreign key(username) references users(username));

create unique index ix_auth_username on authorities (username,authority);

create table groups (
		id bigint AUTO_INCREMENT primary key,
		group_name varchar(100) not null);

create table group_authorities (
		group_id bigint not null,
		authority varchar(100) not null,
		constraint fk_group_authorities_group foreign key(group_id) references groups(id));

create table group_members (
		id bigint AUTO_INCREMENT primary key,
		username varchar(100) not null,
		group_id bigint not null,
		constraint fk_group_members_group foreign key(group_id) references groups(id));

insert into users
        values ('admin', '020aa40d02ed72bc980c05caa7506f7c791ecbd91d1210cc4ab4e830881989f06a9fdaff9a5b5bef', true);

insert into users
        values ('user', '020aa40d02ed72bc980c05caa7506f7c791ecbd91d1210cc4ab4e830881989f06a9fdaff9a5b5bef', true);

insert into authorities
        values ('admin', 'ROLE_ADMIN');

insert into authorities
        values ('user', 'ROLE_USER');