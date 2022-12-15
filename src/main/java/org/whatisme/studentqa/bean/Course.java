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
public class Course extends BeanBase {

  private Long cid;
  private String cname;
  private String describe;
  private Double score;
  private Long uid;
  private Integer forbidden;
}
