package com.example.backend_scaffold.infrastructure.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期时间工具类
 * <p>
 * 提供常用的日期时间操作方法
 * </p>
 *
 * @author example
 */
public class DateTimeUtil {

    /**
     * 默认日期格式
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 默认时间格式
     */
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    /**
     * 默认日期时间格式
     */
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 将LocalDateTime转换为指定格式的字符串
     *
     * @param dateTime LocalDateTime对象
     * @param pattern  日期时间格式
     * @return 格式化后的字符串
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        if (dateTime == null) {
            return null;
        }
        return DateTimeFormatter.ofPattern(pattern).format(dateTime);
    }

    /**
     * 将LocalDateTime转换为默认格式(yyyy-MM-dd HH:mm:ss)的字符串
     *
     * @param dateTime LocalDateTime对象
     * @return 格式化后的字符串
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return format(dateTime, DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 将LocalDate转换为默认格式(yyyy-MM-dd)的字符串
     *
     * @param date LocalDate对象
     * @return 格式化后的字符串
     */
    public static String formatDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT).format(date);
    }

    /**
     * 将LocalTime转换为默认格式(HH:mm:ss)的字符串
     *
     * @param time LocalTime对象
     * @return 格式化后的字符串
     */
    public static String formatTime(LocalTime time) {
        if (time == null) {
            return null;
        }
        return DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT).format(time);
    }

    /**
     * 将字符串解析为LocalDateTime对象
     *
     * @param dateTimeStr 日期时间字符串
     * @param pattern     日期时间格式
     * @return LocalDateTime对象
     */
    public static LocalDateTime parseDateTime(String dateTimeStr, String pattern) {
        if (dateTimeStr == null || dateTimeStr.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将字符串按默认格式(yyyy-MM-dd HH:mm:ss)解析为LocalDateTime对象
     *
     * @param dateTimeStr 日期时间字符串
     * @return LocalDateTime对象
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        return parseDateTime(dateTimeStr, DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 将字符串按默认格式(yyyy-MM-dd)解析为LocalDate对象
     *
     * @param dateStr 日期字符串
     * @return LocalDate对象
     */
    public static LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
    }

    /**
     * 将字符串按默认格式(HH:mm:ss)解析为LocalTime对象
     *
     * @param timeStr 时间字符串
     * @return LocalTime对象
     */
    public static LocalTime parseTime(String timeStr) {
        if (timeStr == null || timeStr.isEmpty()) {
            return null;
        }
        return LocalTime.parse(timeStr, DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT));
    }

    /**
     * 将Date转换为LocalDateTime
     *
     * @param date Date对象
     * @return LocalDateTime对象
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 将LocalDateTime转换为Date
     *
     * @param dateTime LocalDateTime对象
     * @return Date对象
     */
    public static Date toDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取当前日期时间
     *
     * @return 当前日期时间的LocalDateTime对象
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * 获取当前日期
     *
     * @return 当前日期的LocalDate对象
     */
    public static LocalDate today() {
        return LocalDate.now();
    }

    /**
     * 获取当前时间
     *
     * @return 当前时间的LocalTime对象
     */
    public static LocalTime currentTime() {
        return LocalTime.now();
    }
}