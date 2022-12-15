package org.whatisme.studentqa.tools;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Sql {
    static Statement stmt;

    static {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            String url = "jdbc:mysql://127.0.0.1:3306/stu_qa?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8";
            String username = "root";
            String password = "root";
            Connection conn = null;
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public static void execute(String sql) throws SQLException {
        log.info(sql);
        stmt.execute(sql);
    }

    public static List<HashMap<String, Object>> select(String sql) throws SQLException {
        log.info(sql);
        ResultSet res = stmt.executeQuery(sql);
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

    public static <T> List<T> select(String sql, Map<String, String> mp, Class<T> cls) throws Exception {
        sql = parse(sql, mp);
        var preRet = select(sql);
        List<T> ret = new ArrayList<>();
        for (HashMap<String, Object> p : preRet) {
            ret.add(Transfer.mapToObject(p, cls));
        }
        return ret;
    }

    private static String parse(String content, Map<String, String> map) {
        Map<String, String> kvs = new HashMap<>();
        map.forEach((k, v) -> {
            kvs.put("${" + k + "}", v);
        });
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

    public static void execute(String sql, Map<String, String> mp) throws SQLException {
        execute(parse(sql, mp));
    }
}
