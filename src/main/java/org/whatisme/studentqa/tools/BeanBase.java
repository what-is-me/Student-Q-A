package org.whatisme.studentqa.tools;
import java.lang.reflect.*;
public class BeanBase {
    public void setProperty(String pro,String val) throws Exception {
        Class<?> clazz = this.getClass();
        Field field = clazz.getDeclaredField(pro);
        field.setAccessible(true);
        field.set(this, Transfer.stringToObject(val,field.getType()));
    }
}
