package org.whatisme.studentqa.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.whatisme.studentqa.bean.HttpResult;
import org.whatisme.studentqa.bean.User;
import org.whatisme.studentqa.mapper.UserMapper;
import org.whatisme.studentqa.tools.Transfer;
import org.whatisme.studentqa.tools.UTF8Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@Slf4j
@WebServlet(urlPatterns = {"/user"})
public class UserServlet extends UTF8Servlet {
    UserMapper userMapper = new UserMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        try {
            resp.getWriter().println(Transfer.toJson(userMapper.selectAll()));
        } catch (Exception e) {
            log.error(e.getMessage());
            resp.getWriter().println("[]");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        try {
            User user=userMapper.selectById(Long.valueOf(req.getParameter("uid")));
            if(user==null)user=new User();
            user.setProperties(req.getParameterMap());
            userMapper.replace(user);
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
            userMapper.deleteById(uid);
            resp.getWriter().println(HttpResult.successResult);
        } catch (SQLException e) {
            log.error(e.getMessage());
            resp.getWriter().println(HttpResult.failResult);
        }
    }
}
