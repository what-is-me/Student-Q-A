package org.whatisme.studentqa.mapper;

import org.whatisme.studentqa.bean.QuestionBody;
import org.whatisme.studentqa.bean.QuestionHead;
import org.whatisme.studentqa.bean.User;
import org.whatisme.studentqa.tools.Sql;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class QuestionMapper {
    synchronized public Long addQuestion(QuestionHead question) throws SQLException {
        Sql.execute("insert into stu_qa.questionhead(stuUid, cid, title, pub)values (${stuUid},${cid},'${title}',${pub})", question);
        return Long.valueOf(Sql.select("select max(qid) as qid from stu_qa.questionhead;").get(0).get("qid").toString());
    }

    public void deleteQuestion(Long qid) throws SQLException {
        var map = Map.of("qid", qid);
        Sql.execute("delete from stu_qa.questionbody where qid=${qid};", map);
        Sql.execute("delete from stu_qa.questionhead where qid=${qid};", map);
    }

    public void deletePartOfQuestion(Long qbid) throws SQLException {
        var map = Map.of("qbid", qbid);
        Sql.execute("delete from stu_qa.questionbody where qbid=${qbid};", map);
    }

    public void addPartOfQuestion(User user, Long qid, String text) throws SQLException {
        Sql.execute("insert into stu_qa.questionbody(qid, name, text) values (${qid},'${name}','${text}');",
                Map.of("qid", qid.toString(), "name", user.getName(), "text", text));
        var map = Map.of("qid", qid);
        if ("student".equals(user.getType()))
            Sql.execute("update stu_qa.questionhead set teaRead = 0, teaAns = 0 where qid=${qid};", map);
        else Sql.execute("update stu_qa.questionhead set stuRead = 0, teaAns = 1 where qid=${qid}", map);
    }

    public void userRead(User user, Long qid) throws SQLException {
        var map = Map.of("qid", qid, "uid", user.getUid());
        Sql.execute("update stu_qa.questionhead set stuRead=1 where qid=${qid} and stuUid=${uid};", map);
        Sql.execute("update stu_qa.questionhead set teaRead=1 where qid=${qid} and cid in (select cid from stu_qa.course where uid=${uid})", map);
    }

    public void updatePartOfQuestion(Long qbid, String text) throws Exception {
        Sql.execute("update stu_qa.questionbody set `text` = '${text}' where qbid=${qbid};",
                Map.of("text", text, "qbid", qbid));
    }

    public List<QuestionBody> listAll(Long qid) throws Exception {
        return Sql.select("select * from stu_qa.questionbody where qid=${qid} order by `time` desc;", Map.of("qid", qid), QuestionBody.class);
    }

    public QuestionHead findQuestionById(Long qid) throws Exception {
        return Sql.select("select * from stu_qa.question_head_view where qid=${qid}", Map.of("qid", qid), QuestionHead.class).get(0);
    }

    public List<QuestionHead> listQuestionsUnread(User user) throws Exception {
        if ("student".equals(user.getType())) {
            return Sql.select("select * from stu_qa.question_head_view where stuUid=${uid} and stuRead = 0;", Map.of("uid", user.getUid().toString()), QuestionHead.class);
        } else {
            return Sql.select("select * from stu_qa.question_head_view where cid in (select cid from stu_qa.course where uid = ${uid}) and teaRead = 0", Map.of("uid", user.getUid().toString()), QuestionHead.class);
        }
    }

    public List<QuestionHead> listQuestionsUnAnswered(User user) throws Exception {
        return Sql.select("select * from stu_qa.question_head_view where cid in (select cid from stu_qa.course where uid = ${uid}) and teaAns = 0", Map.of("uid", user.getUid().toString()), QuestionHead.class);
    }
}
