/*
 * 文件名：${KeyTools}
 * 作者：${Tree}
 * 版本：
 * 时间：${2014.4.29}
 * 修改：
 * 描述：加解密工具类
 *
 *
 * 版权：亚略特
 */
package com.jce;

import java.security.MessageDigest;

public final class KeyTools
{
    //算法类型
	static String MessageDigestString[] ={"MD5","SHA-1","SHA-256","SHA-384","SHA-512"};

    public KeyTools(){}

    /**
	 * 获取秘钥
	 * @param key
	 * @param iv
	 * @return byte[]
	 */
    public static byte[] GetKey(byte[] key,byte[] iv)
    {
        byte[] ret = new byte[key.length];
        for(int i=0;i<key.length;i++)
        {
            ret[i] =(byte)(key[i] ^ iv[i % iv.length]) ;
        }
        return ret;
    }

    /**
   	 * 获取秘钥
   	 * @param inputString 初始字符串
   	 * @param keyLength 秘钥长度
   	 * @param iv iV值
   	 * @return byte[]
   	 */
    public static byte[] GetKey(String inputString, int keyLength, byte[] iv) throws Exception
    {
        byte[] key = GetKey(inputString,keyLength);
        return GetKey(key,iv);
    }

    /**
   	 * 获取秘钥
   	 * @param inputString 初始字符串
   	 * @param keyLength 秘钥长度
   	 * @return byte[]
   	 */
    public static byte[] GetKey(String inputString, int keyLength) throws Exception
    {
        String realInputString = "key_of_" + inputString;
        byte[] temp ;
        if(keyLength <= 16)
        {
            temp = KeyTools.MessageDigest("MD5",realInputString.getBytes());
        }
        else if(keyLength <= 20)
        {
            temp = KeyTools.MessageDigest("SHA-1",realInputString.getBytes());
        }
        else if(keyLength <= 32)
        {
            temp = KeyTools.MessageDigest("SHA-256",realInputString.getBytes());
        }
        else if(keyLength <= 48)
        {
            temp = KeyTools.MessageDigest("SHA-384",realInputString.getBytes());
        }
        else if(keyLength <= 64)
        {
            temp = KeyTools.MessageDigest("SHA-512",realInputString.getBytes());
        }
        else
        {
            throw new Exception("KeyLength is too big.Must be <= 64.");
        }
        byte[] ret = new byte[keyLength];
        System.arraycopy(temp, 0, ret, 0, keyLength);
        return ret;
    }

    /**
   	 * 获取IV值
   	 * @param inputString 初始字符串
   	 * @param ivLength 秘钥长度
   	 * @return byte[] IV
   	 */
    public static byte[] GetIV(String inputString, int ivLength) throws Exception
    {
        String realInputString = "iv_of_" + inputString;
        byte[] temp ;
        if(ivLength <= 16)
        {
            temp = KeyTools.MessageDigest("MD5",realInputString.getBytes());
        }
        else if(ivLength <= 20)
        {
            temp = KeyTools.MessageDigest("SHA-1",realInputString.getBytes());
        }
        else if(ivLength <= 32)
        {
            temp = KeyTools.MessageDigest("SHA-256",realInputString.getBytes());
        }
        else if(ivLength <= 48)
        {
            temp = KeyTools.MessageDigest("SHA-384",realInputString.getBytes());
        }
        else if(ivLength <= 64)
        {
            temp = KeyTools.MessageDigest("SHA-512",realInputString.getBytes());
        }
        else
        {
            throw new Exception("ivLength is too big.Must be <= 64.");
        }
        byte[] ret = new byte[ivLength];
        System.arraycopy(temp, 0, ret, 0, ivLength);
        return ret;
    }

    /**
   	 * 获取MD5加密后的密文
   	 * @param inputString 加密字符串
   	 * @return String
   	 */
    public final static String GetPasswordMD5(String inputString)
    {
        return MessageDigest("MD5",inputString) ;
    }

    /**
   	 * byte[]加密函数
   	 * @param name 算法
   	 * @param strTemp byte[]值
   	 * @return String
   	 */
    public final static byte[] MessageDigest(String name, byte[] strTemp) throws Exception
    {
        MessageDigest mdTemp = MessageDigest.getInstance(name);
        mdTemp.update(strTemp);
        byte[] md = mdTemp.digest();
        return md;
    }

    /**
   	 * String加密函数
   	 * @param name 算法
   	 * @param s 加密字符串
   	 * @return String
   	 */
    public final static String MessageDigest(String name, String s)
    {
        char hexDigits[] =
        {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'
        };
        try
        {
            byte[] strTemp = s.getBytes();
            byte[] md = MessageDigest(name,strTemp);
            //使用MD5创建MessageDigest对象
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++)
            {
                byte b = md[i];
                //System.out.println((int)b);
                //将没个数(int)b进行双字节加密
                str[k++] = hexDigits[b >> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            return new String(str);
        } catch (Exception e)
        {
            return null;
        }
    }

    //测试
    public static void main(String[] args)
    {

    	String str = "123.com";
    	String a = KeyTools.GetPasswordMD5(str);

//        String str = "81479E137F967703077CF83FDA3FABEE";
//        KeyTools.

    	System.out.println(a);

//    	for(int i=0;i<MessageDigestString.length;i++)
//        {
//            try
//            {
//                System.out.println(MessageDigestString[i] +"加密后长度：" + KeyTools.MessageDigest(MessageDigestString[i], new byte[]{1}).length);
//            }
//            catch(Exception e)
//            {
//                System.out.println(MessageDigestString[i] +"算法出现异常");
//            }
//        }
        System.out.println("");
    }
}