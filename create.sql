drop database if exists stu_qa;
create database stu_qa collate utf8_unicode_ci;
use stu_qa;
create table stu_qa.user
(
    uid        bigint primary key not null,
    `name`     varchar(50),
    `password` varchar(200),
    type       varchar(20) check (type in ('student', 'teacher', 'admin')) default 'student'
);
create table stu_qa.teacher
(
    uid        bigint primary key not null,
    dept       varchar(10) comment '职称',
    `describe` text
);
create table stu_qa.course
(
    cid        bigint primary key not null auto_increment,
    cname      varchar(20)        not null,
    `describe` text,
    score      double default 60,
    uid        bigint,
    forbidden  boolean default 0,
    foreign key (uid) references stu_qa.teacher (uid)
);
create table stu_qa.sc
(
    uid bigint,
    cid bigint,
    primary key (uid, cid)
);
create view stu_qa.uc as
select *
from stu_qa.sc
union
select uid, cid
from stu_qa.course;
create table stu_qa.questionHead
(
    qid     bigint auto_increment not null primary key,
    stuUid  bigint,
    teaUid  bigint,
    title   varchar(200),
    public  tinyint default 1,
    stuRead tinyint default 1,
    teaRead tinyint default 0,
    teaAns  tinyint default 0
);
create table stu_qa.questionBody
(
    qid    bigint,
    foreign key (qid) references stu_qa.questionHead (qid),
    `name` varchar(20),
    `time` datetime
);

-- trigger --
create trigger t_in
    after insert
    on stu_qa.user
    for each row
begin
    if new.type like 'teacher' then
        if new.uid not in (select uid from stu_qa.teacher) then
            insert into stu_qa.teacher(uid) values (new.uid);
        end if;
    end if;
end;
create trigger t_out
    after delete
    on stu_qa.user
    for each row
begin
    if old.type like 'teacher' then
        delete from stu_qa.teacher where uid = old.uid;
    end if;
end;