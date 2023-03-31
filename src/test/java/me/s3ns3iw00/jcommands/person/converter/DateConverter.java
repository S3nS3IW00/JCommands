package me.s3ns3iw00.jcommands.person.converter;

import me.s3ns3iw00.jcommands.argument.converter.ArgumentResultConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * An {@link ArgumentResultConverter} that converts {@link String} value formatted by {@link DateConverter#DATE_FORMAT} into {@link java.util.Date}
 */
public class DateConverter implements ArgumentResultConverter<String, Date> {

    // The date format
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Date convertTo(String value) {
        /* Don't need to check anything, since
           type and formatting is already validated by the argument itself
         */
        try {
            return DATE_FORMAT.parse(value);
        } catch (ParseException e) {
            return null;
        }
    }

}
