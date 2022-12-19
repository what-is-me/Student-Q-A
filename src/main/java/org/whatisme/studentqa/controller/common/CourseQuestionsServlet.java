package org.whatisme.studentqa.controller.common;

import lombok.extern.slf4j.Slf4j;
import org.whatisme.studentqa.bean.HttpResult;
import org.whatisme.studentqa.bean.QuestionHead;
import org.whatisme.studentqa.bean.User;
import org.whatisme.studentqa.mapper.CourseMapper;
import org.whatisme.studentqa.mapper.QuestionMapper;
import org.whatisme.studentqa.mapper.UserMapper;
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
@WebServlet("/course/questions")
public class CourseQuestionsServlet extends UTF8Servlet {
    CourseMapper courseMapper = new CourseMapper();
    UserMapper userMapper = new UserMapper();
    QuestionMapper questionMapper = new QuestionMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        String cid = req.getParameter("cid");
        User user = (User) req.getSession().getAttribute("user");
        Map<String, Object> ret = new HashMap<>();
        try {
            if (cid == null) {
                ret.put("questions", courseMapper.listQuestions(user));
            } else {
                ret.put("course", userMapper.listAllCourses(user.getUid()));
                ret.put("questions", courseMapper.listQuestions(user, Long.valueOf(cid)));
            }
            resp.getWriter().println(Transfer.toJson(ret));
        } catch (Exception e) {
            log.error(e.getMessage());
            resp.getWriter().println("{\"questions\":[]}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        User user = (User) req.getSession().getAttribute("user");
        QuestionHead questionHead = new QuestionHead();
        try {
            questionHead.setProperties(req.getParameterMap());
            questionHead.setStuUid(user.getUid());
            Long qid = questionMapper.addQuestion(questionHead);
            resp.getWriter().println(HttpResult.builder().success(1).data(qid).build());
        } catch (Exception e) {
            log.error(e.getMessage());
            resp.getWriter().println(HttpResult.failResult);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
        Long qid = Long.valueOf(req.getParameter("qid"));
        try {
            questionMapper.deleteQuestion(qid);
            resp.getWriter().println(HttpResult.successResult);
        } catch (Exception e) {
            log.error(e.getMessage());
            resp.getWriter().println(HttpResult.failResult);
        }
    }
}
