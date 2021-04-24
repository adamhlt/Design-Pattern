package utils;

import javafx.util.converter.LocalDateTimeStringConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class to convert DateTime
 *
 * @version 1.0
 */
public class DateTimeConverter {
    private static final String pattern = "dd/MM/yyyy à HH:mm:ss";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern( pattern );

    /**
     * Convert String to LocalDateTime using this pattern : "dd/MM/yyyy à HH:mm:ss"
     *
     * @param dateTime A string to convert
     * @return A localDateTime from the strign
     */
    public static LocalDateTime getLocalDateTimeFromString( String dateTime ) {
        return new LocalDateTimeStringConverter( formatter, formatter ).fromString( dateTime );
    }
}
