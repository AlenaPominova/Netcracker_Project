package ru.vsu.netcracker.parking.frontend.utils;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomTimestampConverter extends PropertyEditorSupport {

    public static final String DEFAULT_BATCH_PATTERN = "yyyy-MM-dd";

    private final SimpleDateFormat sdf;

    /**
     * uses default pattern yyyy-MM-dd for date parsing.
     */
    public CustomTimestampConverter() {
        this.sdf = new SimpleDateFormat(CustomTimestampConverter.DEFAULT_BATCH_PATTERN);
    }

    /**
     * Uses the given pattern for dateparsing, see {@link SimpleDateFormat} for allowed patterns.
     *
     * @param pattern
     *            the pattern describing the date and time format
     * @see SimpleDateFormat#SimpleDateFormat(String)
     */
    public CustomTimestampConverter(String pattern) {
        this.sdf = new SimpleDateFormat(pattern);
    }

    /**
     * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        try {
            setValue(new Timestamp(this.sdf.parse(text).getTime()));
        } catch (ParseException ex) {
            throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
        }
    }

    /**
     * Format the Timestamp as String, using the specified DateFormat.
     */
    @Override
    public String getAsText() {
        Timestamp value = (Timestamp) getValue();
        return (value != null ? this.sdf.format(value) : "");
    }
}
