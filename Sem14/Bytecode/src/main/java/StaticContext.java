public class StaticContext {
    public static void main(String[] args) {
        Context context = new Context();
        Context context2 = new Context();
        Context context3 = new Context();
    }
}


class Context{
    private static String field;

    static{
        field = "abc";
        System.out.println("Static block");
    }
    Context(){
        System.out.println("Constructor");
    }
}