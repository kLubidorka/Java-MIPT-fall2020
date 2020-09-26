import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Java8DateTimeParse {
    public static void main(String[] args) {

        LocalDate date = LocalDate.now();
        System.out.println("Standard LocalDate format : " + date);

        // Use custom format
        System.out.println(date.format(DateTimeFormatter.ofPattern("d::MMM::uuuu")));
        System.out.println(date.format(DateTimeFormatter.BASIC_ISO_DATE));

        LocalDateTime dateTime = LocalDateTime.now();

        // Standard time format
        System.out.println("Standard LocalDateTime format : " + dateTime);

        // Custom time format
        System.out.println(dateTime.format(DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss")));
        System.out.println(dateTime.format(DateTimeFormatter.BASIC_ISO_DATE));

        DateTimeFormatter sourceFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm'+0:0000'");
        DateTimeFormatter targetFormat = DateTimeFormatter.ofPattern("MM-dd-uuuu hh:mm a", Locale.ENGLISH);

        String sourceTime = "2017-07-05T20:31+0:0000";
        String convertedTime = LocalDateTime.parse(sourceTime, sourceFormat)
                .format(targetFormat)
                .toLowerCase(Locale.ENGLISH);

        System.out.println(convertedTime);
    }
}
