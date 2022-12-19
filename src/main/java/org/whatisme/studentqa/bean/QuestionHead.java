package org.whatisme.studentqa.bean;

import lombok.*;
import org.whatisme.studentqa.tools.BeanBase;

import java.sql.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionHead extends BeanBase {

    private String cname;
    private String tname;
    private Long qid;
    private Long stuUid;
    private Long teaUid;
    private Long cid;
    private String title;
    private Long pub;
    private Long stuRead;
    private Long teaRead;
    private Long teaAns;
    private Date time;
    private Long forbidden;
}
