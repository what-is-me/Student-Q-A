package org.whatisme.studentqa.bean;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.whatisme.studentqa.tools.BeanBase;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HttpResult extends BeanBase {
    private Integer success;
    private String message;
    private Object data;
    public static HttpResult successResult = new HttpResult(1, null, null);
    public static HttpResult failResult = new HttpResult(0, null, null);

    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
