import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamOperations {
    public static void main(String[] args) {
        //Stream.of("a", "b", "c").map(n -> n + "_1").forEach(System.out::println);

        //Stream.of("a", "b", "c").flatMap(n -> Stream.of(n , n + "_1")).forEach(System.out::println);

        //Stream.of("-1", "1", "1234").mapToInt(Integer::parseInt).forEach(System.out::println);

        //String result = Stream.of("-1", "1", "1234").findFirst().orElse("");

        //String result = Stream.of("-1", "1", "1234").findAny().orElse("");

        //List<String> list = Stream.of("-1", "1", "1234").collect(Collectors.toList());

        //long cnt = Stream.of("-1", "1", "1234").count();

        // boolean result = Stream.of(-1, 1, 1234).anyMatch(n -> n == 1);

        //int result = Stream.of(-1, 1, 1234).min(Integer::compareTo).orElse(0);

        //String[] array = Stream.of("-1", "1", "1234").toArray(String[]::new);

        String result = Stream.of("-1", "1", "1234").reduce((a, b) -> a + b).orElse("");

        // (a + b) + c = a + (b + c) = a + b + c

        System.out.println(result);
    }
}
