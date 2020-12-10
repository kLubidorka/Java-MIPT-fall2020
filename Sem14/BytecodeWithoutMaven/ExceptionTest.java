class ExceptionTest{
    public static void main(String[] args) {
        try{
            int a = 1 + 2;
        } catch (RuntimeException e){
            e.printStackTrace();
        } finally {
            System.out.println("Hello");
        }
    }
}

