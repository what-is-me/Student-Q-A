drop database if exists stuqa;
create database if not exists stuqa;
use stuqa;
create table if not exists stuqa.teacher
(
    tid   int          not null primary key,
    tname varchar(20)  not null,
    pass  varchar(200) not null,
    dept  varchar(20),
    des   text
) collate utf8_unicode_ci;
create table if not exists stuqa.course
(
    cname varchar(20) not null primary key,
    tid   int,
    des   text,
    score float default 3,
    foreign key (tid) references stuqa.teacher (tid)
) collate utf8_unicode_ci;
create table if not exists stuqa.student
(
    sid     int         not null primary key,
    sname   varchar(20) not null,
    pass    varchar(20) not null,
    courses mediumtext
) collate utf8_unicode_ci;
create table if not exists stuqa.admin
(
    uid  int primary key not null,
    pass varchar(200)    not null
);
/*create table if not exists stuqa.users
(
    uid      int auto_increment not null primary key,
    password varchar(200)       not null,
    type     varchar(20)        not null default 'student'
) collate utf8_unicode_ci;*/
/*create function signin(pass varchar(200), t varchar(20), na varchar(20)) returns int
    reads sql data
begin
    insert into stuqa.users(password, type) VALUES (pass, t);
    select max(uid) into @uid from stuqa.users;
    if t like 'student' then
        insert into stuqa.student(sid, sname) VALUES (@uid, na);
    else
        if t like 'teacher' then
            insert into stuqa.teacher(tid, tname) values (@uid, na);
        else
            if t not like 'admin' then
                signal sqlstate 'HY000' set message_text = '种类错误';
            end if;
        end if;
    end if;
    return @uid;
end;*/