
package com.xml;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 文件名：
 * 作者：tree
 * 时间：2016/6/17
 * 描述：解析XML数据工具类
 * 此类用于解析morpho飞指设备采集到的指纹图像报文
 *
 * 版权：亚略特
 */
public class XmlUtil {

    static String filePath = "C:\\Users\\tree\\Desktop\\指纹样本数据_20170601\\20170601指纹数据\\Morpho";
    static String desFilePath = "C:\\Users\\tree\\Desktop\\指纹样本数据_20170601\\20170601指纹数据\\Morpho\\image\\";

    // 报文解析
    public static void parseXml(String xmlContent)  {
        try {
            // 通过输入的XML数据构造一个Document
            Document doc = DocumentHelper.parseText(xmlContent);
            // 取的根元素
            Element root = doc.getRootElement();
            Element lastName = root.element("personnalInfo").element("lastName");
            String name = "";
            int count = 0;
            if(lastName.getTextTrim().indexOf("_") != -1){
                name = lastName.getTextTrim().split("_")[0];
                count = Integer.parseInt(lastName.getTextTrim().split("_")[1]);
            }else{
                name = lastName.getTextTrim();
            }

            List<Element> nodes = root.element("fingerImages").elements("FingerImageType");
            for (Element node : nodes) {
                String fpIndex = node.attributeValue("id");
//                String fileName = desFilePath + lastName.getTextTrim() + "_" + fpIndex + ".wsq";
                String fileName = desFilePath + name + "_" + fpIndex + "_" + count +".wsq";
                System.out.println(fileName);
                File file = new File(fileName);
                if(!file.exists()) {
                    file.createNewFile();
                }
                Files.write(Paths.get(fileName), Base64.getDecoder().decode(node.getTextTrim()));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void traverseFolder2(String path) {
        // 定义一个xml解析对象
        File file = new File(filePath);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                System.out.println("文件夹是空的!");
                return;
            } else {
                File dir = new File(desFilePath);
                if (dir.exists()) {
                    deleteDir(dir);
                }
                dir.mkdirs();
                for (File file2 : files) {
                    if (file2.isDirectory()) {
//                        traverseFolder2(file2.getAbsolutePath());
                    } else {
                        System.out.println("文件:" + file2.getAbsolutePath());
                        // Java8用流的方式读文件，更加高效
                        try {
                            //按行读取文件
                            List<String> contentLines = Files.lines(Paths.get(file2.getAbsolutePath())).collect(java.util.stream.Collectors.toList());
                            XmlUtil.parseXml(contentLines.get(0));
//                            Files.lines(Paths.get(file2.getAbsolutePath()), StandardCharsets.UTF_8).forEach(System.out::println);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    public static void main(String[] args) {
        XmlUtil.traverseFolder2(filePath);
    }
}
