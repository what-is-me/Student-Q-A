package org.whatisme.studentqa.mapper;

import org.whatisme.studentqa.bean.Course;
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
                "cname='${cname}',`describe`='${describe}',score=${score},uid=${uid},forbidden=${forbidden};",map);
    }

    public void deleteById(Long cid) throws SQLException {
        Sql.execute("delete from stu_qa.course where cid=${cid};", Map.of("cid", cid.toString()));
    }
}
