package com.util;

/**
 * 文件名：
 * 作者：tree
 * 时间：2017/5/2
 * 描述：
 * 版权：亚略特
 */
public class CommonByteUtil {

   /**
    * @author 作者：tree
    * @Date 时间：2017/5/5 16:17
    * 功能说明：进行多个byte截位组装    暂时无用  备用
    * @param srcByte 原byte数组
    * @return 目标byte数组
    */
    public static byte[] bytePlainMerger(byte[] srcByte){
        byte[] desByte = null;
        int num = 32;
        int x = 5;
        int y = 40;
        if(srcByte[1] == 121){
            desByte = new byte[srcByte.length+1];
            if (srcByte[3] == 32) {
                x = x - 1;
                y = y - 1;
            }else{
                y = y - 1;
            }
        }else{
            desByte = new byte[srcByte.length+2];
            if(srcByte[3] == 32){
                srcByte[3] = 33;
            }
            if(srcByte[37] == 32){
                srcByte[37] = 33;
            }
        }
        System.arraycopy(srcByte, 0, desByte, 0, 4);
        System.arraycopy(srcByte, 4, desByte, x, 34);
        System.arraycopy(srcByte,38, desByte, y, srcByte.length - 38);
        return desByte;
    }


    /**
     * @author 作者：tree
     * @Date 时间：2017/5/5 16:17
     * 功能说明：进行多个byte截位组装    暂时无用  备用
     * @param srcByte 原byte数组
     * @return 目标byte数组
     */
    public static byte[] changeJceByte(byte[] srcByte,int type){
        if(srcByte[0] == 48){
            byte[] desByte = null;
            if(1==type){
                desByte = new byte[srcByte.length+1];
                System.arraycopy(srcByte, 0, desByte, 0, 38);
                desByte[1] = 122;
                desByte[38] = 33;
                desByte[39] = 0;
                System.arraycopy(srcByte, 39, desByte, 40, srcByte.length - 39);
            }else if (2 == type){
                desByte = new byte[srcByte.length-1];
                System.arraycopy(srcByte, 0, desByte, 0, 4);
                desByte[1] = 120;
                desByte[3] = 32;
                System.arraycopy(srcByte, 5, desByte, 4, srcByte.length - 5);
            }else{
                desByte = new byte[srcByte.length];
                System.arraycopy(srcByte, 0, desByte, 0, 4);
                desByte[3] = 32;
                System.arraycopy(srcByte, 5, desByte, 4, 34);
                desByte[37] = 33;
                desByte[38] = 0;
                System.arraycopy(srcByte, 39, desByte, 39, srcByte.length - 39);
            }
            return desByte;
        }else{
            return srcByte;
        }
    }
}
