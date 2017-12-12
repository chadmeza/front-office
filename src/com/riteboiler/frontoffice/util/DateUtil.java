package com.riteboiler.frontoffice.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * This class is a utility class which formats
 * strings into dates, and dates into strings.
 * @author Chad Meza
 */
public class DateUtil {
	private static final String DATE_PATTERN = "M/dd/yyyy";

    private static final DateTimeFormatter DATE_FORMATTER = 
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    /**
     * Formats the passed in date to a string.
     * @param date LocalDate to convert to string
     * @return String value of date
     */
    public static String formatToString(LocalDate date) {
        if (date == null) {
            return null;
        }
        
        return DATE_FORMATTER.format(date);
    }

    /**
     * Formats the passed in string to a LocalDate date.
     * @param dateString String to convert to LocalDate
     * @return LocalDate from the dateString
     */
    public static LocalDate formatToLocalDate(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        	return null;
        }
    }

    /**
     * Checks whether the string can be properly
     * converted to a date.
     * @param dateString String to check
     * @return True if dateString can be converted to a LocalDate, False otherwise
     */
    public static boolean isValidDate(String dateString) {
        return DateUtil.formatToLocalDate(dateString) != null;
    }
}
