import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Demo of CountDownLatch from Oracle documentation.
 * In this example it is demonstrated that CountDownLatch can be used as an instrument for managing the execution
 * of a task that was divided into independent parts. 'Driver' waits for all that parts performed by
 * 'WorkerRunnable' to be completed
 * https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/CountDownLatch.html
 */
public class CountDownLatchDemo {
    static class Driver {
        static void main() throws InterruptedException {
            int N = 5;
            CountDownLatch doneSignal = new CountDownLatch(N);
            ExecutorService e = Executors.newCachedThreadPool();

            for (int i = 0; i < N; ++i){
                e.execute(new WorkerRunnable(doneSignal, i));
                System.out.printf("Worker #%d launched\n", i);
            }

            doneSignal.await(); // wait for all to finish
            Thread.sleep(50L);
            System.out.println("Task completed");
            e.shutdownNow();
        }
    }

    static class WorkerRunnable implements Runnable {
        private final CountDownLatch doneSignal;
        private final int i;
        WorkerRunnable(CountDownLatch doneSignal, int i) {
            this.doneSignal = doneSignal;
            this.i = i;
        }
        public void run() {
            try {
                Thread.sleep(100L); // doWork();
            } catch (InterruptedException ignored) {
            }
            doneSignal.countDown();
            System.out.printf("Worker #%d finished\n", i);
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Driver.main();
    }
}
