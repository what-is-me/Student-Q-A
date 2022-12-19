package org.whatisme.studentqa.controller.student;

import lombok.extern.slf4j.Slf4j;
import org.whatisme.studentqa.bean.User;
import org.whatisme.studentqa.mapper.StudentMapper;
import org.whatisme.studentqa.mapper.UserMapper;
import org.whatisme.studentqa.tools.Transfer;
import org.whatisme.studentqa.tools.UTF8Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebServlet("/select-course")
public class SelectCourseServlet extends UTF8Servlet {
    StudentMapper studentMapper = new StudentMapper();
    UserMapper userMapper = new UserMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        try {
            resp.getWriter().println(Transfer.toJson(userMapper.listAllCourses()));
        } catch (Exception e) {
            log.error(e.getMessage());
            resp.getWriter().println("[]");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        User user = (User) req.getSession().getAttribute("user");
        String[] cidS = req.getParameterMap().getOrDefault("cid", new String[0]);
        for (String cid : cidS) {
            try {
                studentMapper.selectCourse(user.getUid(), Long.valueOf(cid));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        resp.sendRedirect("/student/index.html");
    }
}
