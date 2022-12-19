package org.whatisme.studentqa.bean;

import lombok.*;
import org.whatisme.studentqa.tools.BeanBase;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BeanBase {

  private Long uid;
  private String name;
  private String password;
  private String type;

}
