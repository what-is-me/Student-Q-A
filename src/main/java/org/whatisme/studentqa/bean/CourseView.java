package org.whatisme.studentqa.bean;

import lombok.*;
import org.whatisme.studentqa.tools.BeanBase;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseView extends BeanBase {

    private Long cid;
    private String cname;
    private String describe;
    private Double score;
    private Long uid;
    private String tname;
    private Integer forbidden;
}