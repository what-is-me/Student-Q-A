package org.whatisme.studentqa.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.whatisme.studentqa.tools.BeanBase;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionBody  extends BeanBase {

  private Long qid;
  private String name;
  private Date time;

}
