package org.whatisme.studentqa.bean;

import lombok.*;
import org.whatisme.studentqa.tools.BeanBase;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionBody extends BeanBase {

    private Long qid;
    private String name;
    private String text;
    private Long qbid;
    private Date time;

}
