package org.whatisme.studentqa.mapper;

import org.whatisme.studentqa.tools.Sql;

import java.sql.SQLException;
import java.util.Map;

public class StudentMapper {
    public void selectCourse(Long uid, Long cid) throws SQLException {
        Sql.execute("insert into stu_qa.sc (uid, cid) VALUES (${uid},${cid});",
                Map.of("uid", uid.toString(),
                        "cid", cid.toString()));
    }
}
