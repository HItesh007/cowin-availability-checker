package com.hitesh.cowin.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtility {
    public static String getCurrentTimeStampWithFormatAs(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    public static String getCurrentTimeStamp() {
        return new SimpleDateFormat("dd-MM-yyyy - HH.mm.ss.SSS").format(new Date());
    }

    public static String getDateFormatted(Date date, String dateFormat) {
        return new SimpleDateFormat(dateFormat).format(date);
    }

    /**
     * To check whether the supplied <code>String</code> is of <code>Date</code> or not.
     * <br>
     * It makes use of following Date patterns for comparing. <br>
     *
     * <ul>
     *     <li>dd/MM/yyyy</li>
     *     <li>dd-MM-yyyy</li>
     *     <li>MM-dd-yyyy</li>
     *     <li>MM/dd/yyyy</li>
     *     <li>yyyy-MM-dd</li>
     *     <li>yyyy/MM/dd</li>
     * </ul>
     * <p>
     * Please add more pattern if you want to broaden date patterns.
     *
     * @param date Date as <code>String</code>
     * @return <code>true</code>, if the <code>String</code> matches Date Patterns, <code>false</code> otherwise
     */
    public static boolean isDate(String date) {
        String[] dateParsingPatterns = {"dd/MM/yyyy", "dd-MM-yyyy", "MM-dd-yyyy", "MM/dd/yyyy", "yyyy-MM-dd", "yyyy/MM/dd"};

        boolean isDate = false;

        try {
            org.apache.commons.lang3.time.DateUtils.parseDateStrictly(date.trim(), dateParsingPatterns);
            isDate = true;
        } catch (ParseException ignored) {
        }
        return isDate;
    }

    /**
     * To parse a date of type <code>String</code> as {@link Date}
     * <br>
     * It makes use of following Date patterns for comparing. <br>
     *
     * <ul>
     *     <li>dd/MM/yyyy</li>
     *     <li>dd-MM-yyyy</li>
     *     <li>MM-dd-yyyy</li>
     *     <li>MM/dd/yyyy</li>
     *     <li>yyyy-MM-dd</li>
     *     <li>yyyy/MM/dd</li>
     * </ul>
     * <p>
     * Please add more pattern if you want to broaden date patterns.
     *
     * @param dateAsString Date as <code>String</code>
     * @return {@link Date} if provided String is valid date format, <code>null</code> otherwise
     */
    public static Date toDate(String dateAsString) {
        String[] dateParsingPatterns = {"dd/MM/yyyy", "dd-MM-yyyy", "MM-dd-yyyy", "MM/dd/yyyy", "yyyy-MM-dd", "yyyy/MM/dd"};
        Date asDate = null;
        try {
            asDate = org.apache.commons.lang3.time.DateUtils.parseDateStrictly(dateAsString.trim(), dateParsingPatterns);
        } catch (ParseException ignored) {
        }
        return asDate;
    }

    /**
     * To convert Date time of string to {@link Date} object
     *
     * @param dateAsString   Date time string to be converted to date Object
     * @param dateTimeFormat Date Time format to use while conversion
     * @return {@link Date}, null otherwise
     */
    public static Date toDate(String dateAsString, String dateTimeFormat) {
        try {
            return new SimpleDateFormat(dateTimeFormat).parse(dateAsString);
        } catch (ParseException pEx) {
            return null;
        }
    }

    /**
     * To get date as a String in a particular date format.
     *
     * @param date       {@link Date} to convert into <code>String</code>
     * @param dateFormat Date format in which to convert {@link Date}
     * @return Date as a String format
     */
    public static String toString(Date date, String dateFormat) {
        return new SimpleDateFormat(dateFormat).format(date);
    }
}
