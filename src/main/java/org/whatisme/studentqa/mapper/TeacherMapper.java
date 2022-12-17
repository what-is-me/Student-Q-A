package org.whatisme.studentqa.mapper;

import org.whatisme.studentqa.bean.Teacher;
import org.whatisme.studentqa.tools.Sql;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherMapper {
    public List<Teacher> selectAll() throws Exception {
        return Sql.select("select * from stu_qa.teacher;", new HashMap<>(), Teacher.class);

    }

    public Teacher selectById(Long uid) {
        try {
            return Sql.select("select * from stu_qa.teacher where uid='${uid}';", Map.of("uid", uid.toString()), Teacher.class).get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public void insert(Teacher teacher) throws SQLException {
        Map<String, String> map = Map.of("uid", teacher.getUid().toString(), "dept", teacher.getDept(), "describe", teacher.getDescribe());
        Sql.execute("insert into stu_qa.teacher(uid, dept,`describe`) VALUES (${uid},'${dept}','${describe}') " +
                "on duplicate key update dept='${dept}', `describe`='${describe}';", map);
    }

    public void deleteById(Long uid) throws SQLException {
        Sql.execute("delete from stu_qa.teacher where uid=${uid};", Map.of("uid", uid.toString()));
    }
}
