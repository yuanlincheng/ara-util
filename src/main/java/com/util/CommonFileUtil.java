package com.util;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.util.Base64;

/**
 * 文件名：
 * 作者：tree
 * 时间：2017/4/25
 * 描述：
 * 版权：亚略特
 */
public class CommonFileUtil {


    /**
     * @author 作者：tree
     * @Date 时间：2017/4/25 15:50
     * 功能说明：读取txt
     * 处理流程：
     * @param filePath 文件的绝对路径
     * @return  String 文件内容
     */
    public static String readFile(String filePath){
        StringBuffer sb = new StringBuffer();
        try{
            BufferedReader br = new BufferedReader(new FileReader(new File(filePath))); //构造一个BufferedReader类来读取文件
            String s;
            while((s = br.readLine())!=null){ //使用readLine方法，一次读一行
                sb.append(s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * @author 作者：tree
     * @Date 时间：2017/4/25 15:50
     * 功能说明：读取txt
     * 处理流程：
     * @param imagePath 文件的绝对路径
     * @return  String 图片的Base64串
     */
    public static String readImage(String imagePath){
        byte[] imgData = null; //对其进行Base64编码处理
        try {
            // 读取图片文件
            File file = new File(imagePath);
            InputStream inStream = new FileInputStream(file);
            imgData = new byte[(int) file.length()];
            inStream.read(imgData);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(imgData); // 返回Base64编码过的字节数组字符串
    }

    /**
     * @author 作者：tree
     * @Date 时间：2016/10/26 15:50
     * 功能说明：将内容写入指定文件，包含图片
     * 处理流程：
     *
     * @param filePath 文件的绝对路径
     * @param fileContent  文件的内容
     * @param isString 是否需要组装成字符串形式的文件内容
     *
     * @return  boolean 写文件是否成功
     */
    public static boolean writeFile(String filePath,byte[] fileContent,boolean isString){
        boolean result = false;
        try{
            File file = new File(filePath);
            file.createNewFile();
            if(isString){
                FileUtils.write(file,new String(fileContent));
            }else{
                FileUtils.writeByteArrayToFile(file,fileContent);
            }
            result = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
