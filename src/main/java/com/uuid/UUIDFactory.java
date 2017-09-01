package com.uuid;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 文件名：UUIDFactory
 * 作者：tree
 * 时间：2016/4/15 12:16
 * 描述：生成UUID标识ID
 *
 * 版权：亚略特
 */
public class UUIDFactory {
    public static void main(String[] args) {
        String result = UUIDFactory.randomShortUUID();
        System.out.println(result);
        System.out.println(result.length());
    }

    final static char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z'};

    final static Map<Character, Integer> digitMap = new HashMap<Character, Integer>();

    static {
        for (int i = 0; i < digits.length; i++) {
            digitMap.put(digits[i], i);
        }
    }

    /**
     * 支持的最大进制数
     */
    public static final int MAX_RADIX = digits.length;

    /**
     * 支持的最小进制数
     */
    public static final int MIN_RADIX = 2;

    /**
     * @author 作者：tree
     * @Date 时间：2016/4/15 13:39
     * 功能说明：随机生成19位的UUID随机码
     * 处理流程：
     *
     * @return String
     */
    public static String randomShortUUID() {
        UUID uuid = UUID.randomUUID();
        StringBuilder sb = new StringBuilder();
        sb.append(digits(uuid.getMostSignificantBits() >> 32, 8));
        sb.append(digits(uuid.getMostSignificantBits() >> 16, 4));
        sb.append(digits(uuid.getMostSignificantBits(), 4));
        sb.append(digits(uuid.getLeastSignificantBits() >> 48, 4));
        sb.append(digits(uuid.getLeastSignificantBits(), 12));
        return sb.toString();
    }

    /**
     * @author 作者：tree
     * @Date 时间：2016/4/15 12:16
     * 功能说明：
     * 处理流程：
     *
     * @param val
     * @param digits
     *
     * @return String
     */
    private static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return longToString(hi | (val & (hi - 1)), MAX_RADIX)
                .substring(1);
    }

    /**
     * 将long型值转换为62进制的工具类
     *
     * @param i
     * @param radix
     * @return
     */
    public static String longToString(long i, int radix) {
        if (radix < MIN_RADIX || radix > MAX_RADIX)
            radix = 10;
        if (radix == 10)
            return Long.toString(i);

        final int size = 65;
        int charPos = 64;

        char[] buf = new char[size];
        boolean negative = (i < 0);

        if (!negative) {
            i = -i;
        }

        while (i <= -radix) {
            buf[charPos--] = digits[(int) (-(i % radix))];
            i = i / radix;
        }
        buf[charPos] = digits[(int) (-i)];

        if (negative) {
            buf[--charPos] = '-';
        }

        return new String(buf, charPos, (size - charPos));
    }

}