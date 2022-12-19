package org.whatisme.studentqa.mapper;

import org.whatisme.studentqa.bean.Course;
import org.whatisme.studentqa.bean.QuestionHead;
import org.whatisme.studentqa.bean.User;
import org.whatisme.studentqa.tools.Sql;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseMapper {
    public List<Course> selectAll() throws Exception {
        return Sql.select("select * from stu_qa.course;", new HashMap<>(), Course.class);
    }

    public Course selectById(Long cid) {
        try {
            return Sql.select("select * from stu_qa.course where cid='${cid}';", Map.of("cid", cid.toString()), Course.class).get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public void insert(Course course) throws SQLException {
        Map<String, String> map = Map.of("cid", course.getCid().toString(),
                "cname", course.getCname(),
                "describe", course.getDescribe(),
                "uid", course.getUid().toString(),
                "score", course.getScore().toString(),
                "forbidden", course.getForbidden().toString());
        Sql.execute("insert into stu_qa.course(cid, cname, `describe`, score, uid, forbidden) VALUES " +
                "(${cid},'${cname}','${describe}',${score},${uid},${forbidden}) on duplicate key update " +
                "cname='${cname}',`describe`='${describe}',score=${score},uid=${uid},forbidden=${forbidden};", map);
    }

    public void deleteById(Long cid) throws SQLException {
        Sql.execute("delete from stu_qa.course where cid=${cid};", Map.of("cid", cid.toString()));
    }

    public List<QuestionHead> listQuestions(User user) throws Exception {
        if ("student".equals(user.getType())) {
            return Sql.select("select * from stu_qa.question_head_view where stuUid=${uid};", Map.of("uid", user.getUid().toString()), QuestionHead.class);
        } else {
            return Sql.select("select * from stu_qa.question_head_view where cid in (select cid from stu_qa.course where uid = ${uid})", Map.of("uid", user.getUid().toString()), QuestionHead.class);
        }
    }

    public List<QuestionHead> listQuestions(User user, Long cid) throws Exception {
        if ("student".equals(user.getType())) {
            return Sql.select("select * from stu_qa.question_head_view where (stuUid = ${uid} or pub = 1) and cid = ${cid};", Map.of("uid", user.getUid().toString(), "cid", cid.toString()), QuestionHead.class);
        } else {
            return Sql.select("select * from stu_qa.question_head_view where cid = ${cid}", Map.of("cid", cid.toString()), QuestionHead.class);
        }
    }

    public void alterForbidden(Long cid) throws SQLException {
        Sql.execute("update stu_qa.course set forbidden=forbidden^1 where cid=${cid};", Map.of("cid", cid));
    }
}
