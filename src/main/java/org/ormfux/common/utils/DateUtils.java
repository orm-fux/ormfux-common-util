package org.ormfux.common.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.ormfux.common.datatype.TimeUnit;

/**
 * Utilities for date date (day) values.
 */
public final class DateUtils {
    
    private DateUtils() {
    }

    /**
     * Returns a date, representing today 0 o'clock.
     *
     * @return today
     */
    public static Date today() {
        return today(null);
    }

    /**
     * Returns a date, representing today 0 o'clock in the given timezone.
     *
     * @param timeZone timezone to use
     * @return today
     */
    public static Date today(final TimeZone timeZone) {
        return getCalendar(null, timeZone).getTime();
    }

    /**
     * Return a date representing yesterday 0 o'clock.
     *
     * @return yesterday.
     */
    public static Date yesterday() {
        return yesterday(null);
    }

    /**
     * Return a date representing yesterday 0 o'clock of the parameter date.
     *
     * @param date date
     * @return yesterday.
     */
    public static Date yesterday(final Date date) {
        final Calendar calendar = getCalendar(date, null);
        calendar.add(Calendar.DATE, -1);
        
        return calendar.getTime();
    }

    /**
     * Return a date representing tomorrow 0 o'clock.
     *
     * @return tomorrow
     */
    public static Date tomorrow() {
        return tomorrow(null);
    }

    /**
     * Return a date representing tomorrow 0 o'clock.
     *
     * @param date date
     * @return yesterday.
     */
    public static Date tomorrow(final Date date) {
        final Calendar calendar = getCalendar(date, null);
        calendar.add(Calendar.DATE, 1);
        
        return calendar.getTime();
    }

    /**
     * Returns a calendar object for the given date and timezone. Both
     * parameters may be null (then the current date and default timezone
     * are used).
     *
     * @param date     date to use for the calendar time
     * @param timeZone time zone to use for the calendar
     * @return a calendar object
     */
    public static Calendar getCalendar(final Date date, final TimeZone timeZone) {
        Calendar calendar = null;
        
        if (timeZone != null) {
            calendar = Calendar.getInstance(timeZone);
        } else {
            calendar = Calendar.getInstance();
        }

        if (date != null) {
            calendar.setTime(date);
        }

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }

    /**
     * Creates a date.
     *
     * @param year  year
     * @param month month
     * @param date  date
     * @return date
     */
    public static Date getDate(final int year, final int month, final int date) {
        final Calendar calendar = getCalendar(null, null);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, date);
        
        return calendar.getTime();
    }

    /**
     * Checks if two date objects are on the same day ignoring time.
     *
     * @param date1 the first date, not altered, not null
     * @param date2 the second date, not altered, not null
     * @return true if they represent the same day
     */
    public static boolean isSameDay(final Date date1, final Date date2) {
        final Calendar calendar1 = getCalendar(date1, null);
        final Calendar calendar2 = getCalendar(date2, null);
        
        return calendar1.get(Calendar.ERA) == calendar2.get(Calendar.ERA)
                && calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR);
    }
    
    /**
     * Checks if a date is before the other date ignoring the time.
     *
     * @param date1 the first date, not altered, not null
     * @param date2 the second date, not altered, not null
     * @return true if date1 is before or at the same date as date2.
     */
    public static boolean isBeforeDay(final Date date1, final Date date2) {
        return !isSameOrAfterDay(date1, date2);
    }
    
    /**
     * Checks if a date is before or at the same date ignoring the time.
     *
     * @param date1 the first date, not altered, not null
     * @param date2 the second date, not altered, not null
     * @return true if date1 is before or at the same date as date2.
     */
    public static boolean isSameOrBeforeDay(final Date date1, final Date date2) {
        return getCalendar(date1, null).compareTo(getCalendar(date2, null)) <= 0;
    }

    /**
     * Checks if a date is after the other date ignoring the time.
     *
     * @param date1 the first date, not altered, not null
     * @param date2 the second date, not altered, not null
     * @return true if date1 is after or at the same date as date2.
     */
    public static boolean isAfterDay(final Date date1, final Date date2) {
        return !isSameOrBeforeDay(date1, date2);
    }
    
    /**
     * Checks if a date is after or at the same date ignoring the time.
     *
     * @param date1 the first date, not altered, not null
     * @param date2 the second date, not altered, not null
     * @return true if date1 is after or at the same date as date2.
     */
    public static boolean isSameOrAfterDay(final Date date1, final Date date2) {
        return getCalendar(date1, null).compareTo(getCalendar(date2, null)) >= 0;
    }

    /**
     * Current time.
     *
     * @return current time.
     */
    public static Date now() {
        return getCalendar(null, null).getTime();
    }
    
    /**
     * Shifts the "now" date by the provided values.
     */
    public static Date shift(final int quantity, final TimeUnit unit) {
        return shift(now(), quantity, unit);
    }
    
    /**
     * Shifts the date by the provided quantity and time unit.
     * 
     * @param date The date to shift.
     * @param quantity The quantity to shift by.
     * @param unit The unit of the quantity.
     */
    public static Date shift(final Date date, final int quantity, final TimeUnit unit) {
        final int calendarCode;
        
        switch (unit) {
            case DAY:
                calendarCode = Calendar.DAY_OF_MONTH;
                break;
            case MONTH:
                calendarCode = Calendar.MONTH;
                break;
            case WEEK:
                calendarCode = Calendar.WEEK_OF_YEAR;
                break;
            case YEAR:
                calendarCode = Calendar.YEAR;
                break;
            default:
                throw new IllegalArgumentException("Unsupported time unit: " + unit);
        }
        
        final Calendar calendar = getCalendar(null, null);
        calendar.setTime(date);
        calendar.add(calendarCode, quantity);
        
        return calendar.getTime();
    }
    
}
