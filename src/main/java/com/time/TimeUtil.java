/*
 * 文件名：${TimeUtil}
 * 作者：${Tree}
 * 版本：
 * 时间：${2014.4.16}
 * 修改：
 * 描述：时间工具类
 *
 *
 * 版权：亚略特
 */
package com.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

	//定义时间格式
	static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	static DateTimeFormatter timeNumFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

	/**
	 * 以 yy-MM-dd HH:mm:ss 格式輸出當前時間
	 * @param
	 * @return String
	 */
	public static String getFormatDate(){
		LocalDateTime localDateTime = LocalDateTime.now();
//		System.out.println(localDateTime.format(dateTimeFormatter));
		return localDateTime.format(dateTimeFormatter);
	}

	/**
	 * 获取当前时间的天数或小时数
	 * @param
	 * @return String
	 */
	public static String getMinusDateTime(String timeType,int num){
		LocalDateTime localDateTime = null;
		switch (timeType) {
			case "H":
				localDateTime = LocalDateTime.now().minusHours(num);
				break;
			case "D":
				localDateTime = LocalDateTime.now().minusDays(num);
				break;
			default:
				break;
		}
//		System.out.println(localDateTime.format(dateTimeFormatter));
		return localDateTime.format(dateTimeFormatter);
	}

	/**
	 * 以 yyyyMMddHHmmss 格式輸出當前時間
	 * @param
	 * @return String
	 */
	public static String getOnlyNumberDate(){
		LocalDateTime localDateTime = LocalDateTime.now();
//		System.out.println(localDateTime.format(timeNumFormatter));
		return localDateTime.format(timeNumFormatter);
	}

    /**
     * 现在是否超过了指定时间
     * @param date     * @param minute
     * @return
     */
    public static boolean checkOverTime(LocalDateTime date,int minute){
        return Instant.now().toEpochMilli() > date
                .withMinute(date.getMinute()+minute)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }

    /**
     * 获取今天的时间区间
     * @return
     */
    public static String getTodayDateArea(){
        LocalDate localDate = LocalDate.now();
        localDate.format(dateFormatter);
        return localDate.format(dateFormatter) + " 00:00:00 - " + getFormatDate();
    }
}