package org.whatisme.studentqa.bean;

import lombok.*;
import org.whatisme.studentqa.tools.BeanBase;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HttpResult extends BeanBase {
    private Integer success;
    private String message;
    private Object data;
    public static HttpResult successResult = new HttpResult(1, null, null);
    public static HttpResult failResult = new HttpResult(0, null, null);
}
