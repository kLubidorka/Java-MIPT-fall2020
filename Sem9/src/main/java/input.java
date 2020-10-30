import java.util.Scanner;

public class input {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        sc.close();

        try{
            //do smth
        } catch(Exception e){
            e.printStackTrace();
        }

        // try-with-resources
        try(Scanner sc1 = new Scanner(System.in)){
            int a = sc1.nextInt();
        }
    }
}
