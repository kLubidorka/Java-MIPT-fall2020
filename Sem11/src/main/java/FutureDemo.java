import java.util.concurrent.*;

public class FutureDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Callable<Integer> task = () -> {
            // calculate something super important
            TimeUnit.SECONDS.sleep(10);
            return 3;
        };

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(task);

        tryToGetResultImmediately(future);
        tryToGetResultWithDelay(future, 10);
    }

    static void tryToGetResultImmediately(Future<Integer> future) throws ExecutionException, InterruptedException {
        if (future.isDone()){
            System.out.println("It's ready");
            System.out.printf("The result is %d", future.get());
        } else{
            System.out.println("It's not ready yet");
        }
    }

    static void tryToGetResultWithDelay(Future<Integer> future, int delay) throws ExecutionException, InterruptedException {
        int result;
        try {
            result = future.get(delay, TimeUnit.SECONDS);
            System.out.println("It's ready");
            System.out.printf("The result is %d", result);
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            System.out.println("It's not ready yet");
        }
    }
}
