package com.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.exception.CommonUtilException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 文件名：
 * 作者：tree
 * 时间：2017/4/25
 * 描述：
 * 版权：亚略特
 */
public class CommonStringUtil {

    /**
     * 去除返回到前台数据中的null值
     * @param obj  对象
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void nullConvertNullString(Object obj) throws CommonUtilException {
        if(obj != null){
            try {
                //利用java的反射机制，轮询元素
                Class classz = obj.getClass();
                Field fields[] = classz.getDeclaredFields();
                for(Field field : fields){
                    Type t = field.getGenericType();
                    if(t.toString().equals("class java.lang.String")){
                        Method m = classz.getMethod("get"+change(field.getName()));
                        Object name = m.invoke(obj);
                        if(null == name){
                            Method mtd = classz.getMethod("set"+change(field.getName()),new Class[]{String.class});
                            mtd.invoke(obj, new Object[]{""});
                        }
                    }
                }
            } catch (Exception e) {
                throw new CommonUtilException("CommonStringUtil( null   to   nullString) ");
            }
        }
    }

    /**
     * 把返回到前台数据中的null值设置为指定初始值
     * @param obj  对象
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void nullConvertInitString(Object obj) throws CommonUtilException {
        if(obj != null){
            try {
                //利用java的反射机制，轮询元素
                Class classz = obj.getClass();
                Field fields[] = classz.getDeclaredFields();
                for(Field field : fields){
                    Type t = field.getGenericType();
                    if(t.toString().equals("class java.lang.String")){
                        Method m = classz.getMethod("get"+change(field.getName()));
                        Object name = m.invoke(obj);
                        if(null == name){
                            Method mtd = classz.getMethod("set"+change(field.getName()),new Class[]{String.class});
                            mtd.invoke(obj, new Object[]{"N/A"});
                        }
                    }
                }
            } catch (Exception e) {
                throw new CommonUtilException("CommonStringUtil( null   to   initString) ");
            }
        }
    }

    /**
     * 使字符串的首字母大写
     * @param str  String对象
     * @return String
     */
    public static String change(String str){
        if(null != str){
            StringBuffer sb = new StringBuffer(str);
            //获取首字母，转大写
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            return sb.toString();
        }else{
            return null;
        }
    }

    public static String mapConvertString(Map<Integer,Long> map, int start, int end){
        String result = "";
        for(int i = start; i <= end;i++){
            if(map.containsKey(i)){
                long value = map.get(i);
                result += ","+value;
            }else{
                result += ",0";
            }
        }
        if(result.length()>0){
            result = result.substring(1, result.length());
        }
        return result;
    }

    public static Map<String,Object> covertJsonStringToHashMap(String jsonParam){
        JSONArray jsonArray = JSONArray.parseArray(jsonParam);
        Map<String,Object> map = new HashMap();
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            map.put(jsonObj.getString("name"), jsonObj.get("value"));
        }
        return map;
    }

    /**
     * @author 作者：tree
     * @Date 时间：2017/6/9 10:56
     * 功能说明：照妖镜功能，将好看的妖怪变成原型
     * @param  showString 妖怪
     * @return String 妖怪原型
     */
    public static String parseShowString(Object showString){
        switch ((String)showString) {
            case "成功":
                showString = "Y";
                break;
            case "完成":
                showString = "Y";
                break;
            case "失败":
                showString = "N";
                break;
            case "执行中":
                showString = "";
                break;
            case "新增":
                showString = "I";
                break;
            case "修改":
                showString = "U";
                break;
            case "删除":
                showString = "D";
                break;
             case "紧急":
                showString = "1";
                break;
             case "中等":
                showString = "2";
                break;
             case "延后":
                showString = "3";
                break;
            case "可用":
                showString = "E";
                break;
            case "停用":
                showString = "D";
                break;
            case "是":
                showString = "T";
                break;
            case "否":
                showString = "F";
                break;
            case "比对引擎":
                showString = "Master";
                break;
            case "支撑系统":
                showString = "SUP";
                break;
            default:
                break;
        }
        return (String)showString;
    }

    public static String subStringByRule(String srcString,String start, String end) {
        int startIndex = 0;
        int endIndex = 0;
        if(Optional.ofNullable(srcString).isPresent() && srcString.length() > 0){
            StringBuffer sb = new StringBuffer();
            if(Optional.ofNullable(start).isPresent() &&  start.length() > 0){
                startIndex = srcString.indexOf(start) + start.length();
            }
            if(Optional.ofNullable(end).isPresent() &&  end.length() > 0){
                endIndex = srcString.indexOf(end) + end.length() - end.length();
            }
        }else{
            return null;
        }
        return srcString.substring(startIndex, endIndex);
    }
}