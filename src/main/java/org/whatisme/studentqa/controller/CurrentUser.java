package org.whatisme.studentqa.controller;

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

@WebServlet(urlPatterns = {"/cur-user", "/alter-password"})
@Slf4j
public class CurrentUser extends UTF8Servlet {
    UserMapper userMapper=new UserMapper();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        resp.getWriter().println(Transfer.toJson(req.getSession().getAttribute("user")));
    }
    @Override//改密码
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
        User user = (User) req.getSession().getAttribute("user");
        String oldPassword = req.getParameter("old");
        if (!user.getPassword().equals(oldPassword)) {
            resp.getWriter().println(HttpResult.builder().message("密码错误").success(0).build());
            return;
        }
        String password = req.getParameter("new");
        user.setPassword(password);
        try {
            userMapper.replace(user);
            req.getSession().setAttribute("user", user);
            resp.getWriter().println(HttpResult.successResult);
        } catch (Exception e) {
            log.error(e.getMessage());
            resp.getWriter().println(HttpResult.builder().message("数据库修改失败").success(0).build());
        }
    }
}
