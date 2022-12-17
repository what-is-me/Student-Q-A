package org.whatisme.studentqa.mapper;

import org.whatisme.studentqa.bean.User;
import org.whatisme.studentqa.tools.Sql;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserMapper {
    public List<User> selectAll() throws Exception {
        return Sql.select("select * from stu_qa.user;", new HashMap<>(), User.class);
    }

    public User selectById(Long uid) throws Exception {
        var map = Map.of("uid", String.valueOf(uid));
        var list = Sql.select("select * from stu_qa.user where uid=${uid};", map, User.class);
        if (list.isEmpty()) return null;
        else return list.get(0);
    }

    public void insert(User user) throws SQLException {
        var map = Map.of("uid", String.valueOf(user.getUid()), "name", user.getName(), "password", user.getPassword(), "type", user.getType());
        Sql.execute("insert into stu_qa.user(uid, name, password, type) VALUES (${uid},'${name}','${password}','${type}')", map);
    }

    public void replace(User user) throws SQLException {
        var map = Map.of("uid", String.valueOf(user.getUid()), "name", user.getName(), "password", user.getPassword(), "type", user.getType());
        Sql.execute("insert into stu_qa.user(uid, `name`, `password`, type) VALUES (${uid},'${name}','${password}','${type}') " +
                "on duplicate key update `name`='${name}', `password`='${password}', type='${type}';", map);
    }

    public void deleteById(Long uid) throws SQLException {
        Sql.execute("delete from stu_qa.user where uid=${uid}", Map.of("uid", uid.toString()));
    }
}
