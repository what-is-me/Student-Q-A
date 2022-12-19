import lombok.extern.slf4j.Slf4j;
import org.whatisme.studentqa.bean.User;
import org.whatisme.studentqa.mapper.QuestionMapper;

@Slf4j
public class JDBCtest {

    public static void main(String[] args) throws Exception {
        User user = User.builder().uid(2L).type("teacher").build();
        System.out.println(new QuestionMapper().listQuestionsUnAnswered(user));
    }
}