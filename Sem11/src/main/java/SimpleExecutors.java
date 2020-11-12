import java.util.concurrent.Executor;

public class SimpleExecutors {
}

class DirectExecutor implements Executor {
    public void execute(Runnable runnable) {
        runnable.run();
    }
}

class ThreadPerTaskExecutor implements Executor {
    public void execute(Runnable runnable) {
        new Thread(runnable).start();
    }
}