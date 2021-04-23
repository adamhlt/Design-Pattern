package utils;

import javafx.util.converter.LocalDateTimeStringConverter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeConverter {

    private static final String pattern = "dd/MM/yyyy à HH:mm:ss";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

    public static LocalDateTime getLocalDateTimeFromString( String dateTime ){
        return new LocalDateTimeStringConverter(formatter, formatter).fromString(dateTime);
    }
}
