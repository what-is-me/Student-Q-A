package org.whatisme.studentqa.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.whatisme.studentqa.bean.HttpResult;
import org.whatisme.studentqa.bean.Teacher;
import org.whatisme.studentqa.mapper.TeacherMapper;
import org.whatisme.studentqa.tools.Transfer;
import org.whatisme.studentqa.tools.UTF8Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Slf4j
@WebServlet("/teacher")
public class TeacherServlet extends UTF8Servlet {
    TeacherMapper teacherMapper = new TeacherMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        String uid = req.getParameter("uid");
        try {
            if (uid != null)
                resp.getWriter().println(Transfer.toJson(teacherMapper.selectById(Long.valueOf(uid))));
            else resp.getWriter().println(Transfer.toJson(teacherMapper.selectAll()));
        } catch (Exception e) {
            log.error(e.getMessage());
            resp.getWriter().println("[]");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        try {
            Teacher teacher=teacherMapper.selectById(Long.valueOf(req.getParameter("uid")));
            if(teacher==null)teacher=new Teacher();
            teacher.setProperties(req.getParameterMap());
            teacherMapper.insert(teacher);
            resp.getWriter().println(HttpResult.successResult);
        } catch (Exception e) {
            log.error(e.getMessage());
            resp.getWriter().println(HttpResult.failResult);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
        Long uid = Long.valueOf(req.getParameter("uid"));
        try {
            teacherMapper.deleteById(uid);
            resp.getWriter().println(HttpResult.successResult);
        } catch (SQLException e) {
            log.error(e.getMessage());
            resp.getWriter().println(HttpResult.failResult);
        }
    }
}
