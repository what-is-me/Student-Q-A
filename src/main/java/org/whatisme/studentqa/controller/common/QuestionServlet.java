package org.whatisme.studentqa.controller.common;

import lombok.extern.slf4j.Slf4j;
import org.whatisme.studentqa.bean.HttpResult;
import org.whatisme.studentqa.bean.User;
import org.whatisme.studentqa.mapper.QuestionMapper;
import org.whatisme.studentqa.tools.Transfer;
import org.whatisme.studentqa.tools.UTF8Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@WebServlet("/question")
public class QuestionServlet extends UTF8Servlet {
    QuestionMapper questionMapper = new QuestionMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        Map<String, Object> ret = new HashMap<>();
        User user = (User) req.getSession().getAttribute("user");
        Long qid = Long.valueOf(req.getParameter("qid"));
        try {
            ret.put("qhead", questionMapper.findQuestionById(qid));
            ret.put("questions", questionMapper.listAll(qid));
            questionMapper.userRead(user, qid);
            resp.getWriter().println(Transfer.toJson(ret));
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        var qbid = req.getParameter("qbid");
        Long qid = Long.valueOf(req.getParameter("qid"));
        User user = (User) req.getSession().getAttribute("user");
        String text = req.getParameter("text");
        try {
            if (qbid == null || "".equals(qbid))
                questionMapper.addPartOfQuestion(user, qid, text);
            else
                questionMapper.updatePartOfQuestion(Long.valueOf(qbid), text);
            resp.getWriter().println(HttpResult.successResult);
        } catch (Exception e) {
            log.error(e.getMessage());
            resp.getWriter().println(HttpResult.failResult);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
        Long qbid = Long.valueOf(req.getParameter("qbid"));
        try {
            questionMapper.deletePartOfQuestion(qbid);
            resp.getWriter().println(HttpResult.successResult);
        } catch (SQLException e) {
            log.error(e.getMessage());
            resp.getWriter().println(HttpResult.failResult);
        }

    }
}
