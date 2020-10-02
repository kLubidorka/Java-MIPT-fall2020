import static org.junit.jupiter.api.Assertions.*;

public class InputTest {
    @org.junit.jupiter.api.Test
    public void ExampleTest(){
        assertTrue(RegExExample.validateInput("Hello."));
        assertTrue(RegExExample.validateInput("Hello!"));
        assertTrue(RegExExample.validateInput("Hello?"));

        assertFalse(RegExExample.validateInput("Hello"));
        assertFalse(RegExExample.validateInput("hello"));
        assertFalse(RegExExample.validateInput("hello."));
    }

    @org.junit.jupiter.api.Test
    public void EmailTest(){
        assertTrue(RegExExample.validateEmail("abc@gmail.com"));
        assertTrue(RegExExample.validateEmail("a_b_c@gmail.com"));
        assertTrue(RegExExample.validateEmail("A.bc@gmail.com"));

        assertFalse(RegExExample.validateEmail("abc@"));
        assertFalse(RegExExample.validateEmail("@gmail.com"));
        assertFalse(RegExExample.validateEmail("a*bc@gmail.com"));
        assertFalse(RegExExample.validateEmail("a$bc@gmail.com"));
        assertFalse(RegExExample.validateEmail("a@bc@gmail.com"));
        assertFalse(RegExExample.validateEmail("a*bc@@gmail.com"));
    }
}
