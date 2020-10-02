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

    //@org.junit.jupiter.api.Test
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

    //@org.junit.jupiter.api.Test
    public void phoneNumTest(){
        assertTrue(RegExExample.validatePhoneNumber("+7 (923) 123-23-56"));
        assertTrue(RegExExample.validatePhoneNumber("+73 (923) 123-23-56"));
        assertTrue(RegExExample.validatePhoneNumber("+724 (923) 120-33-56"));

        assertFalse(RegExExample.validatePhoneNumber("7 (923) 123-20-56"));
        assertFalse(RegExExample.validatePhoneNumber("+7 (1923) 123-28-66"));
        assertFalse(RegExExample.validatePhoneNumber("+7 (923)  923-53-56"));
        assertFalse(RegExExample.validatePhoneNumber("+7 (993) 72-203-26"));
        assertFalse(RegExExample.validatePhoneNumber("+7 (963) 123-23-76 "));
        assertFalse(RegExExample.validatePhoneNumber("+7(923)123-23-56"));
    }
}
