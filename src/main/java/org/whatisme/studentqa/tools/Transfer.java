package org.whatisme.studentqa.tools;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class Transfer {
    public static <T> T mapToObject(Map<String, Object> map, Class<T> beanClass) {
        String jsonStr = JSONObject.toJSONString(map);
        return JSONObject.parseObject(jsonStr, beanClass);
    }

    public static <T> T stringToObject(String s, Class<T> cls) {
        return JSONObject.parseObject(s, cls);
    }

    //java对象转map
    public static Map<String, Object> objectToMap(Object obj) {
        String jsonStr = JSONObject.toJSONString(obj);
        return JSONObject.parseObject(jsonStr);
    }
}
