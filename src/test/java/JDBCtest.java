import lombok.extern.slf4j.Slf4j;
import org.whatisme.studentqa.bean.User;
import org.whatisme.studentqa.tools.BeanBase;

@Slf4j
public class JDBCtest {

    public static void main(String[] args) throws Exception {
        User user=new User();
        user.setProperty("name","admin");
        System.out.println(Long.valueOf(null));
    }
}