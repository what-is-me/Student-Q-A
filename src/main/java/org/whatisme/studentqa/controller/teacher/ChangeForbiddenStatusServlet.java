package org.whatisme.studentqa.controller.teacher;

import lombok.extern.slf4j.Slf4j;
import org.whatisme.studentqa.bean.HttpResult;
import org.whatisme.studentqa.mapper.CourseMapper;
import org.whatisme.studentqa.tools.UTF8Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Slf4j
@WebServlet("/teacher/alter-forbid")
public class ChangeForbiddenStatusServlet extends UTF8Servlet {
    CourseMapper courseMapper = new CourseMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        Long cid = Long.valueOf(req.getParameter("cid"));
        try {
            courseMapper.alterForbidden(cid);
            resp.getWriter().println(HttpResult.successResult);
        } catch (SQLException e) {
            log.error(e.getMessage());
            resp.getWriter().println(HttpResult.failResult);
        }
    }
}
