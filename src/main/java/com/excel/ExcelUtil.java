package com.excel;


import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 文件名：
 * 作者：tree
 * 时间：2017/6/12
 * 描述：
 * 版权：亚略特
 */
public class ExcelUtil {

    /**
     * 添加列表信息
     * sheet excelSheet
     * list 导出主要信息
     * fieldName 属性名称>数组对于表头 扩展属性格式extra.key
     * contextStyle 内容样式
     * isHaveSerial 是否添加序号
     */
    public static <T> void addContextByList(XSSFSheet sheet, List<T> list, String[] fieldName, XSSFCellStyle contextStyle,boolean isHaveSerial) {
        try {
            XSSFRow row = null;
            XSSFCell cell = null;
            if (list != null) {
                List<T> tList = (List<T>) list;
                T t = null;
                String value = "";
                for (int i = 0; i < list.size(); i++) {
                    row = sheet.createRow(i + 2);
                    for (int j = 0; j < fieldName.length; j++) {
                        t = tList.get(i);
                        value = objectToString(getFieldValueByName(fieldName[j], t));
                        if(isHaveSerial){
                            //首列加序号
                            if(row.getCell(0)!=null && row.getCell(0).getStringCellValue()!=null){
                                cell = row.createCell(0);
                                cell.setCellValue(""+i);
                            }
                            cell = row.createCell(j+1);
                            cell.setCellValue(value);
                        }else{
                            cell = row.createCell(j);
                            cell.setCellValue(value);
                        }
                        cell.setCellStyle(contextStyle);
                    }
                }
                for (int j = 1; j < fieldName.length; j++) {
                    sheet.autoSizeColumn(j); // 单元格宽度 以最大的为准
                }
            } else {
                row = sheet.createRow(2);
                cell = row.createCell(0);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * <P>Object转成String类型，便于填充单元格</P>
     * */
    public static String objectToString(Object object){
        String str = "";
        if(object==null){
        }else if(object instanceof Date){
            DateFormat from_type = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = (Date)object;
            str = from_type.format(date);
        }else if(object instanceof String){
            str = (String)object;
        }else if(object instanceof Integer){
            str = ((Integer)object).intValue()+"";
        }else if(object instanceof Double){
            str = ((Double)object).doubleValue()+"";
        }else if(object instanceof Long){
            str = Long.toString(((Long)object).longValue());
        }else if(object instanceof Float){
            str = Float.toHexString(((Float)object).floatValue());
        }else if(object instanceof Boolean){
            str = Boolean.toString((Boolean)object);
        }else if(object instanceof Short){
            str = Short.toString((Short)object);
        }
        return str;
    }

    /**
     * @author 作者：tree
     * @Date 时间：2017/6/12 12:56
     * 功能说明：添加标题(第一行)与表头(第二行)
     * @param sheet 工作簿
     * @param assettitle 表头>数组
     * @param titleName 标题
     * @param headerStyle 标题样式
     * @param contextStyle 表头样式
     * @return
     */
    public static void addTitle(XSSFSheet sheet, String[] assettitle, String titleName,
                                XSSFCellStyle headerStyle, XSSFCellStyle contextStyle) {
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, assettitle.length - 1));
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue(titleName);
        cell.setCellStyle(headerStyle);
        row = sheet.createRow(1);
        for (int i = 0; i < assettitle.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(assettitle[i]);
            cell.setCellStyle(contextStyle);
        }
    }

    /**
     * <p>
     * 根据属性名获取属性值
     * </p>
     * fieldName 属性名 object 属性所属对象
     * 支持Map扩展属性, 不支持List类型属性，
     * return 属性值
     */
    @SuppressWarnings("unchecked")
    public static Object getFieldValueByName(String fieldName, Object object) {
        try {
            Object fieldValue = null;
            if (fieldName.length() > 0 && object != null) {
                String firstLetter = ""; // 首字母
                String getter = ""; // get方法
                Method method = null; // 方法
                String extraKey = null;
                // 处理扩展属性 extraData.key
                if (fieldName.indexOf(".") > 0) {
                    String[] extra = fieldName.split("\\.");
                    fieldName = extra[0];
                    extraKey = extra[1];
                }
                firstLetter = fieldName.substring(0, 1).toUpperCase();
                getter = "get" + firstLetter + fieldName.substring(1);
                method = object.getClass().getMethod(getter, new Class[] {});
                fieldValue = method.invoke(object, new Object[] {});
                if (extraKey != null) {
                    Map<String, Object> map = (Map<String, Object>) fieldValue;
                    fieldValue = map==null ? "":map.get(extraKey);
                }
            }
            return fieldValue;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @author 作者：tree
     * @Date 时间：2017/6/12 12:38
     * 功能说明：创建excel字体样式
     * @param contentFlag  内容内心   表头 1   内容 2
     * @return
     */
    public static XSSFCellStyle createFont(XSSFWorkbook workbook,int contentFlag) {
        XSSFCellStyle fontStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        switch (contentFlag){
            case 1: //表头
                font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
                font.setFontName("黑体");
                font.setFontHeightInPoints((short) 16);// 设置字体大小
                break;
            default: //内容
                font.setFontName("宋体");
                font.setFontHeightInPoints((short) 10);// 设置字体大小
                break;
        }
        fontStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        fontStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        fontStyle.setFont(font);
        return fontStyle;
    }
}
