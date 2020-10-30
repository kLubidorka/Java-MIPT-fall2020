public class ThreadExample {
    public static void main(String[] args) {
        // #1
        Thread thread1 = new Thread(new HelloRunnable());
        thread1.start();

        // #2
        HelloThread thread2 = new HelloThread(2);
        thread2.start();

        reordering();

        SomeThread someThread = new SomeThread();
        someThread.start();
    }

    public static void reordering(){
        for (int i = 0; i < 10; i++){
            new HelloThread(i).start();
        }
    }
}

class HelloRunnable implements Runnable {
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Hello from thread #1");
    }
}

class HelloThread extends Thread {
    int idx;

    HelloThread(int num){
        idx = num;
    }

    public void run() {
        System.out.printf("Hello from thread #%d\n", idx);
    }
}

class SomeThread extends Thread{
    public void run() {
        superImportantCalculations();
    }

    void superImportantCalculations(){
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            System.out.println("We were interrupted! Let's stop");
        }
    }
}