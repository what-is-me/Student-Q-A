package org.whatisme.studentqa.controller;

import lombok.extern.slf4j.Slf4j;
import org.whatisme.studentqa.bean.HttpResult;
import org.whatisme.studentqa.bean.User;
import org.whatisme.studentqa.mapper.UserMapper;
import org.whatisme.studentqa.tools.UTF8Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebServlet(urlPatterns = {"/Login", "/Sign-up"})
public class LogSignServlet extends UTF8Servlet {
    UserMapper userMapper = new UserMapper();

    @Override//登录
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        try {
            User nuser = User.builder()
                    .uid(Long.valueOf(req.getParameter("uid")))
                    .password(req.getParameter("password"))
                    .build();
            User user = userMapper.selectById(nuser.getUid());
            log.info(user.toString());
            if (user.getPassword().equals(nuser.getPassword())) {
                req.getSession().setAttribute("user", user);
            } else {
                resp.getWriter().println(new HttpResult(0, "password mismatch", null));
                throw new Exception("password mismatch");
            }
            resp.getWriter().println(HttpResult.successResult);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override//注册
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        try {
            User user = User.builder()
                    .uid(Long.valueOf(req.getParameter("uid")))
                    .name(req.getParameter("name"))
                    .password(req.getParameter("password"))
                    .type("student")
                    .build();
            userMapper.insert(user);
            req.getSession().setAttribute("user", user);
            resp.getWriter().println(HttpResult.successResult);
        } catch (Exception e) {
            log.error(e.getMessage());
            resp.getWriter().println(new HttpResult(0, e.getMessage(), null));
        }
    }
}
