package com.michealwang.mqmail.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @DESCRIPTION: DateUtils
 *
 */
public class DateUtils {

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * yyyyMMddHHmmss
     */
    public static final DateTimeFormatter DATE_TIME_SIMPLE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    /**
     * yyyy-MM-dd
     */
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /**
     * yyyyMMdd
     */
    private static final DateTimeFormatter DATE_SIMPLE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");


    /**
     * 获取当前日期开始时间
     */
    public static LocalDateTime begin(LocalDateTime localDateTime) {
        return LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MIN);
    }

    /**
     * 获取当前日期结束时间
     */
    public static LocalDateTime end(LocalDateTime localDateTime) {
        return LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MAX);
    }


    /**
     * yyyy-MM-dd
     */
    public static String getDate() {
        return getDate(LocalDate.now(), true);
    }

    /**
     * yyyyMMdd
     */
    public static String getDateSimple() {
        return getDate(LocalDate.now(), false);
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static String getDateTime() {
        return getDateTime(LocalDateTime.now(), true);
    }

    /**
     * yyyyMMddHHmmss
     */
    public static String getDateTimeSimple() {
        return getDateTime(LocalDateTime.now(), false);
    }


    /**
     * 格式化日期加时间 by LocalDate
     *
     * @param localDate LocalDate
     * @param delimiter 是否带分隔符
     *                  delimiter == true ? 'yyyy-MM-dd' : 'yyyyMMdd'
     * @return 格式化后的日期
     */
    private static String getDate(LocalDate localDate, boolean delimiter) {
        if (delimiter) {
            return localDate.format(DATE_FORMAT);
        }
        return localDate.format(DATE_SIMPLE_FORMAT);
    }

    /**
     * 格式化日期加时间 by LocalDateTime
     *
     * @param localDateTime LocalDateTime
     * @param delimiter     是否带分隔符
     *                      delimiter == true ? 'yyyy-MM-dd HH:mm:ss' : 'yyyyMMddHHmmss'
     * @return 格式化后的日期
     */
    private static String getDateTime(LocalDateTime localDateTime, boolean delimiter) {
        if (delimiter) {
            return localDateTime.format(DATE_TIME_FORMAT);
        }
        return localDateTime.format(DATE_TIME_SIMPLE_FORMAT);
    }


}
