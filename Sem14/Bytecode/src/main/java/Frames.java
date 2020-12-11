public class Frames {
    private static int a;

    static void setA(int a){
        Frames.a = a;
    }

    private String field;

    public String getField(){
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public static void recursion(int cnt){
        if (cnt > 0){
            recursion(--cnt);
        } else{
            System.out.println("End");
        }
    }

    public static void main(String[] args) {
        // This was to demonstrate frames
//        int b = 13;
//        Frames.setA(10);

        // VERY BAD PRACTICE
        // you should never handle errors
        try{
            recursion(1000000);
        } catch (StackOverflowError e){
            System.out.println("Stack overflow");
        }

    }
}