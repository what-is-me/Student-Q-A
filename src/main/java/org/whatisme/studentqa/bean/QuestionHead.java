package org.whatisme.studentqa.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.whatisme.studentqa.tools.BeanBase;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionHead  extends BeanBase {

  private Long qid;
  private Long stuUid;
  private Long teaUid;
  private String title;
  private Long stuRead;
  private Long teaRead;
  private Long teaAns;

}
