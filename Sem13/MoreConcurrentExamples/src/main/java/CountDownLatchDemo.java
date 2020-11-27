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
            int workersNum = 5;
            CountDownLatch doneSignal = new CountDownLatch(workersNum);
            ExecutorService executor = Executors.newCachedThreadPool();

            for (int workerNum = 0; workerNum < workersNum; ++workerNum){
                executor.execute(new WorkerRunnable(doneSignal, workerNum));
            }

            doneSignal.await(); // wait for all to finish
            Thread.sleep(50L);
            System.out.println("Task completed");
            executor.shutdownNow();
        }
    }

    static class WorkerRunnable implements Runnable {
        private final CountDownLatch doneSignal;
        private final int myNum;

        WorkerRunnable(CountDownLatch doneSignal, int workerNum) {
            this.doneSignal = doneSignal;
            this.myNum = workerNum;
        }

        public void run() {
            try {
                System.out.printf("Worker #%d launched\n", myNum);
                Thread.sleep(100L); // doWork();
            } catch (InterruptedException ignored) {
            } finally {
                doneSignal.countDown();
                System.out.printf("Worker #%d finished\n", myNum);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Driver.main();
    }
}
