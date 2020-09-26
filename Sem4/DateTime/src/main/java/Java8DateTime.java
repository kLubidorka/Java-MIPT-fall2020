import java.time.*;

public class Java8DateTime {
    public static void main(String[] args) {

        // Current date and time
        LocalDateTime today = LocalDateTime.now();
        System.out.println("Current date and time : " + today);

        // Create dateTime with LocalDate и LocalTime
        today = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        System.out.println("DateTime : " + today);

        // Create LocalDateTime with parameters
        LocalDateTime randDate = LocalDateTime.of(2020, Month.JULY, 9, 11, 6, 22);
        System.out.println("LocalDateTime : " + randDate);

        // 100 seconds after 01.01.1970
        LocalDateTime dateFromBase = LocalDateTime.ofEpochSecond(100, 0, ZoneOffset.UTC);
        System.out.println("100 seconds after 01.01.1970 : " + dateFromBase);

    }
}
