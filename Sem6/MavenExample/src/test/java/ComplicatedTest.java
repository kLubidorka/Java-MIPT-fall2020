import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ComplicatedTest {
    @BeforeAll
    void setUp(){
        System.out.println("Testing started");
    }

    @BeforeEach
    void before(){
        System.out.println("This method is called before each test");
    }

    @Test
    void addition() {
        System.out.println("Testing in progress");
        assertEquals(2, HelloWorld.add(1, 1));
    }

    @Test
    void addition2(){
        System.out.println("Testing in progress");
        assertEquals(3, HelloWorld.add(2, 1));
    }

    @Test
    void coolFeatures(){
        Assertions.assertLinesMatch(
                asList("You can compare strings", "or use regex: \\d{2}\\.\\d{2}\\.\\d{4}"),
                asList("You can compare strings", "or use regex: 12.09.2017")
        );

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("Something went wrong");
        });

        assertEquals("Something went wrong", exception.getMessage());
    }

    @Test
    void sumtest(){
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            HelloWorld.add(-1, 2);
        });

        assertEquals("a must be >= 0", exception.getMessage());
    }

    @RepeatedTest(5)
    void repeatedTest() {
        System.out.println("You will see this message five times");
    }

    @AfterEach
    void after(){
        System.out.println("This method is called after each test");
    }

    @AfterAll
    static void Finalize(){
        System.out.println("Testing finished");
    }
}
