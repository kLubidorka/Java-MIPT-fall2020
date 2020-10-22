public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }

    public static int add(int a, int b){
        if (a < 0){
            throw new IllegalArgumentException("a must be >= 0");
        }
        if (b < 0){
            throw new IllegalArgumentException("b must be >= 0");
        }
        return a + b;
    }
}
