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
    score      double  default 60,
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
    cid     bigint,
    title   varchar(200),
    pub     tinyint  default 1,
    stuRead tinyint  default 1,
    teaRead tinyint  default 0,
    teaAns  tinyint  default 0,
    `time`  DATETIME default current_timestamp
);
create table stu_qa.questionBody
(
    qid    bigint,
    foreign key (qid) references stu_qa.questionHead (qid),
    qbid   bigint primary key auto_increment,
    `name` varchar(20),
    `text` longtext,
    `time` datetime default current_timestamp
);
create view course_view as
select course.*, user.name as tname
from course
         left join user on user.uid = course.uid;
create view question_head_view as
select questionhead.*, c.cname, c.tname, c.uid as teaUid, c.forbidden
from stu_qa.questionhead
         left join course_view c on questionhead.cid = c.cid;

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