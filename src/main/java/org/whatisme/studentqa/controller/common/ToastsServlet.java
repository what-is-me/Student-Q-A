package org.whatisme.studentqa.controller.common;

import lombok.extern.slf4j.Slf4j;
import org.whatisme.studentqa.bean.User;
import org.whatisme.studentqa.mapper.QuestionMapper;
import org.whatisme.studentqa.tools.Transfer;
import org.whatisme.studentqa.tools.UTF8Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@WebServlet("/toasts")
public class ToastsServlet extends UTF8Servlet {
    QuestionMapper questionMapper = new QuestionMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        User user = (User) req.getSession().getAttribute("user");
        Map<String, Object> ret = new HashMap<>();
        try {
            ret.put("unread", questionMapper.listQuestionsUnread(user));
            if ("teacher".equals(user.getType()))
                ret.put("unans", questionMapper.listQuestionsUnAnswered(user));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        resp.getWriter().println(Transfer.toJson(ret));
    }
}
