import java.util.regex.*;

public class RegExExample {
    public static void main(String[] args) {
        regEx();
    }

    public static void regEx(){
        Pattern p = Pattern.compile("a*b");
        Matcher m = p.matcher("aaaaab");
        boolean b = m.matches();
        System.out.println(b);
    }
}
