import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceDemo {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.printf("Hello from %s\n", Thread.currentThread().getName());
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    System.out.printf("Thread %s was interrupted\n", Thread.currentThread().getName());
                    return;
                }
                System.out.printf("Thread %s finished\n", Thread.currentThread().getName());
            }
        };

        executorService.submit(runnable);
        //executorService.shutdown();
        //executorService.shutdownNow();
        executorService.submit(runnable);
    }
}
