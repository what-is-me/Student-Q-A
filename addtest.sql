use stu_qa;
insert into stu_qa.user(uid, name, password, type)
VALUES (1, 'admin', 'password', 'admin');
insert into stu_qa.user(uid, name, password, type)
VALUES (2, 't1', 'password', 'teacher');
insert into stu_qa.user(uid, name, password, type)
VALUES (3, 't2', 'password', 'teacher');
insert into stu_qa.user(uid, name, password, type)
VALUES (4, 't3', 'password', 'teacher');
insert into stu_qa.user(uid, name, password, type)
VALUES (5, 't4', 'password', 'teacher');
insert into stu_qa.user(uid, name, password, type)
VALUES (6, 't5', 'password', 'teacher');
insert into stu_qa.user(uid, name, password, type)
VALUES (11, 's1', 'password', 'student');
insert into stu_qa.user(uid, name, password, type)
VALUES (12, 's2', 'password', 'student');
insert into stu_qa.user(uid, name, password, type)
VALUES (13, 's3', 'password', 'student');
insert into stu_qa.user(uid, name, password, type)
VALUES (14, 's4', 'password', 'student');
insert into stu_qa.user(uid, name, password, type)
VALUES (15, 's5', 'password', 'student');
insert into stu_qa.course(cid, cname, `describe`, uid)
VALUES (1, '数据结构', '高级数据结构', 2);
insert into stu_qa.course(cid, cname, `describe`, uid)
VALUES (2, '算法设计', '算法', 3);
insert into stu_qa.course(cid, cname, `describe`, uid)
VALUES (3, '人工智能', '尖端学科', 4);
insert into stu_qa.sc(uid, cid)
VALUES (11, 1);
insert into stu_qa.sc(uid, cid)
VALUES (11, 2);
insert into stu_qa.sc(uid, cid)
VALUES (11, 3);
insert into stu_qa.questionhead(stuUid, cid, title, pub)
values (11, 1, 'hello world', 0);
insert into stu_qa.questionbody(qid, name, text)
VALUES (1, 's1', '### hello world
\n\n
- this is the first test question body.\n
- hihihi\n\n

$\\sum_{i=1}^n i^2 = ?$
\n\n
```python\n
print("hello world")\n
```
')