package org.whatisme.studentqa.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.whatisme.studentqa.bean.Course;
import org.whatisme.studentqa.bean.HttpResult;
import org.whatisme.studentqa.mapper.CourseMapper;
import org.whatisme.studentqa.tools.Transfer;
import org.whatisme.studentqa.tools.UTF8Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Slf4j
@WebServlet("/course")
public class CourseServlet extends UTF8Servlet {
    CourseMapper courseMapper = new CourseMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        String cid = req.getParameter("cid");
        try {
            if (cid != null)
                resp.getWriter().println(Transfer.toJson(courseMapper.selectById(Long.valueOf(cid))));
            else resp.getWriter().println(Transfer.toJson(courseMapper.selectAll()));
        } catch (Exception e) {
            log.error(e.getMessage());
            resp.getWriter().println("[]");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        try {
            Course course=courseMapper.selectById(Long.valueOf(req.getParameter("cid")));
            if(course==null)course=new Course();
            course.setProperties(req.getParameterMap());
            courseMapper.insert(course);
            resp.getWriter().println(HttpResult.successResult);
        } catch (Exception e) {
            log.error(e.getMessage());
            resp.getWriter().println(HttpResult.failResult);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
        Long cid = Long.valueOf(req.getParameter("cid"));
        try {
            courseMapper.deleteById(cid);
            resp.getWriter().println(HttpResult.successResult);
        } catch (SQLException e) {
            log.error(e.getMessage());
            resp.getWriter().println(HttpResult.failResult);
        }
    }
}
