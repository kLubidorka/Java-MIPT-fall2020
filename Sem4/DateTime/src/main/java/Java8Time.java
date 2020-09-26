import java.time.LocalTime;

public class Java8Time {
    public static void main(String[] args) {

        // Get current time
        LocalTime time = LocalTime.now();
        System.out.println("Current time : " + time);

        // Create LocalTime with parameters
        LocalTime specificTime = LocalTime.of(23, 15, 11, 22);
        System.out.println("Time : " + specificTime);

        // 100 seconds after 01.01.1970
        LocalTime sec100 = LocalTime.ofSecondOfDay(100);
        System.out.println("100 seconds after 01.01.1970 : " + sec100);
    }
}
