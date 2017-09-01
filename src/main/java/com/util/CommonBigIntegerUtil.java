package com.util;

import java.math.BigInteger;
import java.util.Base64;

/**
 * 文件名：
 * 作者：tree
 * 时间：2017/5/3
 * 描述：
 * 版权：亚略特
 */
public class CommonBigIntegerUtil {
    /**
     * @author 作者：tree
     * @Date 时间：2017/5/5 16:20
     * 功能说明：将BigInteger类型数据转换成对应进制数据，并Base64后输出
     * @param data BigInteger数据
     * @param num 指定进制
     * @return
     */
    public static String bigIntToString(String data,int num) {
        BigInteger x = new BigInteger(data,num);
        return Base64.getEncoder().encodeToString(x.toByteArray());
    }
}
