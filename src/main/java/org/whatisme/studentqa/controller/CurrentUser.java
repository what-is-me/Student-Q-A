package org.whatisme.studentqa.controller;

import com.alibaba.fastjson.JSONObject;
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

@WebServlet(urlPatterns = "/cur-user")
@Slf4j
public class CurrentUser extends UTF8Servlet {
    UserMapper userMapper=new UserMapper();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        resp.getWriter().println(JSONObject.toJSONString(req.getSession().getAttribute("user")));
    }
    @Override//改密码
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
        User user = (User) req.getSession().getAttribute("user");
        String password = req.getParameter("password");
        user.setPassword(password);
        try {
            userMapper.replace(user);
            req.getSession().setAttribute("user", user);
            resp.getWriter().println(HttpResult.successResult);
        }catch (Exception e){
            log.error(e.getMessage());
            resp.getWriter().println(HttpResult.failResult);
        }
    }
}
