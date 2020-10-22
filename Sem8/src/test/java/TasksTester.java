import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TasksTester {
    @Test
    void task1(){
        Tasks<String> stringTask = new Tasks<>();
        assertEquals(stringTask.task1(Stream.of("a", "a", "b"), "a"), 2);
        assertEquals(stringTask.task1(Stream.of("a", "a", "b"), "c"), 0);

        Tasks<Integer> intTask = new Tasks<>();
        assertEquals(intTask.task1(Stream.of(1, 2, 3, 2, 2), 2), 3);
        assertEquals(intTask.task1(Stream.of(1, 2, 3), 4), 0);
    }

    @Test
    void task2(){
        Tasks<Double> doubleTask = new Tasks<>();
        assertEquals(doubleTask.task2(Arrays.asList(1D, 2D, 3D, 15D)), 15);
        assertNull(doubleTask.task2(Collections.emptyList()));
    }

    @Test
    void task3(){
        Tasks<Integer> intTask = new Tasks<>();
        assertEquals(intTask.task3(Stream.of(1, 2, 3, 15)), Arrays.asList(2, 3));
        assertEquals(intTask.task3(Stream.of(12, 4, 7, 15, 123, 12, 6, 345)), Arrays.asList(4, 7));
    }
}
