public class Frames {
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

    }
}