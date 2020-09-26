import java.time.Duration;
import java.time.Instant;

public class Java8Timestamp {
    public static void main(String[] args) {
        // Current timestamp
        Instant timestamp = Instant.now();
        System.out.println("Current timestamp : " + timestamp);

        // Example of Duration
        Duration sixtyDay = Duration.ofDays(60);
        System.out.println(sixtyDay.getSeconds());
    }
}
