package org.whatisme.studentqa.bean;

import lombok.*;
import org.whatisme.studentqa.tools.BeanBase;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Uc extends BeanBase {

    private Long uid;
    private Long cid;

}
