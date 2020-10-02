import java.util.regex.*;

public class RegExExample {
    public static void main(String[] args) {

    }

    public static void regEx(){
        Pattern p = Pattern.compile("a*b");
        Matcher m = p.matcher("aaaaab");
        boolean b = m.matches();
        System.out.println(b);
    }

    /**
     * Validates a sentence. Checks if first letter is capital and it ends with dot, question mark or exclamation mark.
     * @param input String with the sentence to check
     * @return true if sentence is correct, false otherwise
     */
    public static boolean validateInput(String input){
        return Pattern.matches("[A-Z].*[.?!]", input);
    }

    public static void regExAsSplitter(){
        String test = "car;bike;train:plane";
        String[] testSplitted = test.split("[;:]");
        for (String el : testSplitted){
            System.out.println(el);
        }
    }



    /* /////////////////////////////// TASKS /////////////////////////////// */



    /**
     * Validates email address. Email should contain one at-symbol (@) and at least one symbol to the left and to the right
     * from at-symbol. It can contain letters, numbers and symbols '.', '-', '_'
     * @param email String with email to validate
     * @return true if email is correct, false otherwise
     */
    public static boolean validateEmail(String email){
        Pattern p = Pattern.compile("YOUR PATTERN HERE");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * Validates phone number. Phone number format should be '+COUNTRY_CODE (xxx) xxx-xx-xx'. COUNTRY_CODE is 1-3
     * digit number.
     * @param number String with email to validate
     * @return true if email is correct, false otherwise
     */
    public static boolean validatePhoneNumber(String number){
        Pattern p = Pattern.compile("YOUR PATTERN HERE");
        Matcher m = p.matcher(number);
        return m.matches();
    }
}
