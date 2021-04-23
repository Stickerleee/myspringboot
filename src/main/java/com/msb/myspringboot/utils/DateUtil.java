package com.msb.myspringboot.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * 日期工具类
 * 
 * @author Stickerleee
 * @since 2021年4月15日 下午4:55:58
 */
public class DateUtil {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
			.withLocale(Locale.CHINA).withZone(ZoneId.systemDefault());

	/**
	 * 返回当前时间
	 *
	 * @return 字符串表示的时间
	 */
	public static String now() {
		return FORMATTER.format(Instant.now());
	}

}
