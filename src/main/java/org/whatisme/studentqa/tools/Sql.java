package org.whatisme.studentqa.tools;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Sql {
    static Connection conn = null;
    static {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            String url = "jdbc:mysql://127.0.0.1:3306/stu_qa?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8";
            String username = "root";
            String password = "root";
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public static void execute(String sql) throws SQLException {
        log.info(sql);
        conn.createStatement().execute(sql);
    }

    public static List<HashMap<String, Object>> select(String sql) throws SQLException {
        log.info(sql);
        ResultSet res = conn.createStatement().executeQuery(sql);
        List<HashMap<String, Object>> ret = new ArrayList<>();
        var rsmd = res.getMetaData();
        int colCount = rsmd.getColumnCount();
        while (res.next()) {
            HashMap<String, Object> row = new HashMap<>();
            for (int i = 1; i <= colCount; i++) {
                row.put(rsmd.getColumnName(i), res.getString(i));
            }
            ret.add(row);
        }
        return ret;
    }

    public static <T, U> List<T> select(String sql, Map<String, U> mp, Class<T> cls) throws Exception {
        sql = parse(sql, mp);
        var preRet = select(sql);
        List<T> ret = new ArrayList<>();
        for (HashMap<String, Object> p : preRet) {
            ret.add(Transfer.mapToObject(p, cls));
        }
        return ret;
    }

    private static <T> String parse(String content, Map<String, T> map) {
        Map<String, String> kvs = new HashMap<>();
        map.forEach((k, v) -> kvs.put("${" + k + "}", v.toString()));
        String pattern = "\\$\\{(.*?)}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(content);
        StringBuilder sr = new StringBuilder();
        while (m.find()) {
            String group = m.group();
            m.appendReplacement(sr, kvs.get(group));
        }
        m.appendTail(sr);
        return sr.toString();
    }

    public static <T> void execute(String sql, Map<String, T> mp) throws SQLException {
        execute(parse(sql, mp));
    }

    public static void execute(String sql, BeanBase mp) throws SQLException {
        execute(parse(sql, Transfer.objectToMap(mp)));
    }
}
