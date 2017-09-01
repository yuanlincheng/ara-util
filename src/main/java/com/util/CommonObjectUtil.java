package com.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * 文件名：
 * 作者：tree
 * 时间：2017/4/25
 * 描述：
 * 版权：亚略特
 */
public class CommonObjectUtil {
    /**
     * 赋值对象的值到另一个对象（一般修改对象用）
     * @param source  源对象
     *  @param dest  目标对象
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void Copy(Object source, Object dest){
        Class classS = dest.getClass();
        Class classD = source.getClass();
        Field fields[] =classD.getDeclaredFields();
        try {
            //利用java的反射机制，轮询元素
            for(int i=0;i<fields.length;i++){
                Field field = fields[i];
                Type t = field.getGenericType();
                String fieldName = field.getName();
                if(!"serialVersionUID".equals(fieldName)){
                    String firstLetter = fieldName.substring(0,1).toUpperCase();
                    String getMethodName = "get"+firstLetter+fieldName.substring(1);
                    String setMethodName = "set"+firstLetter+fieldName.substring(1);
                    Method getMethodS = classS.getMethod(getMethodName,new Class[]{});
                    Method getMethodD = classD.getMethod(getMethodName,new Class[]{});
                    Method setMethod = classD.getMethod(setMethodName,new Class[]{field.getType()});
                    Object valueS = getMethodS.invoke(source,new Object[]{});
                    Object valueD = getMethodD.invoke(dest,new Object[]{});
                    if(t.toString().equals("class java.lang.String") || t.toString().equals("class com.model.sql.Role") || t.toString().equals("class com.model.mysql.Role") || t.toString().equals("class com.model.sql.Code") || t.toString().equals("class com.model.mysql.Code")){
                        if (null==valueD){
                            setMethod.invoke(dest,new Object[]{valueS});
                        }
                    }else if(t.toString().equals("int")){
                        if (0 == (Integer)valueD){
                            setMethod.invoke(dest,new Object[]{valueS});
                        }
                    }
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
