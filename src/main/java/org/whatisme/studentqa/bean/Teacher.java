package org.whatisme.studentqa.bean;

import lombok.*;
import org.whatisme.studentqa.tools.BeanBase;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Teacher  extends BeanBase {

  private Long uid;
  private String dept;
  private String describe;

}
