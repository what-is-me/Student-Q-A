package org.whatisme.studentqa.tools;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

public class Transfer {
    private static final Gson gson = new GsonBuilder()
            .setNumberToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE).setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T mapToObject(Map<String, Object> map, Class<T> beanClass) {
        String jsonStr = gson.toJson(map);
        return gson.fromJson(jsonStr, beanClass);
    }

    public static <T> T stringToObject(String s, Class<T> cls) {
        return gson.fromJson(s, cls);
    }

    //java对象转map
    public static Map<String, Object> objectToMap(Object obj) {
        String jsonStr = gson.toJson(obj);
        return gson.fromJson(jsonStr,new TypeToken<Map<String, Object>>() {}.getType());
    }
}
