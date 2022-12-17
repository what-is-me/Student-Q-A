package org.whatisme.studentqa.tools;

import java.lang.reflect.Field;
import java.util.Map;

public class BeanBase{
    public void setProperty(String pro, String val) throws Exception{
        Class<?> clazz = this.getClass();
        Field field = clazz.getDeclaredField(pro);
        field.setAccessible(true);
        Object v;
        if (field.getType() == String.class)
            v = val;
        else
            v = Transfer.stringToObject(val, field.getType());
        field.set(this, v);
    }

    public String toString() {
        return Transfer.toJson(this);
    }

    public void setProperties(Map<String, String[]> map) throws Exception {
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            String k = entry.getKey();
            String[] v = entry.getValue();
            this.setProperty(k, v[0]);
        }
    }
}
