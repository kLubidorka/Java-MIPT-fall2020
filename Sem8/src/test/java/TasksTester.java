import org.junit.jupiter.api.*;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TasksTester {
    @Test
    void task1(){
        Tasks<String> stringTask = new Tasks<>();
        assertEquals(stringTask.task1(Stream.of("a", "a", "b"), "a"), 2);
        assertEquals(stringTask.task1(Stream.of("a", "a", "b"), "b"), 1);
        assertEquals(stringTask.task1(Stream.of("a", "a", "b"), "c"), 0);

        Tasks<Integer> intTask = new Tasks<>();
        assertEquals(intTask.task1(Stream.of(1, 2, 3), 2), 1);
        assertEquals(intTask.task1(Stream.of(1, 2, 3, 2, 2), 2), 3);
        assertEquals(intTask.task1(Stream.of(1, 2, 3), 4), 0);
    }
}
