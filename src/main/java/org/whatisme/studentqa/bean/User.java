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
public class User extends BeanBase {

  private Long uid;
  private String name;
  private String password;
  private String type;

}
