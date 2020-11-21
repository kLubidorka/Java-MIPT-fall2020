import ProblemA.Locks;
import org.junit.jupiter.api.Test;

public class LocksTest {
    /**
     * It's not 100%-sure deadlock indicator!
     */
    @Test
    void deadlockTest(){
        Thread runner = new Thread(Locks::deadlock);
        runner.start();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException ignored) {
        }
        int blockedThreads = 0;
        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            if (thread.getState().equals(Thread.State.BLOCKED)) {
                blockedThreads++;
            }
        }
        assert blockedThreads >= 2;
    }

    /**
     * It's not 100%-sure livelock indicator!
     */
    @Test
    void livelockTest(){
        Thread runner = new Thread(Locks::livelock);
        runner.start();
        long start = System.currentTimeMillis();
        try{
            long timeToWait = 2000;
            runner.join(timeToWait);
            long duration = System.currentTimeMillis() - start;
            assert !(duration < timeToWait * 0.9);
        } catch (InterruptedException ignored){

        }
        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            assert !thread.getState().equals(Thread.State.BLOCKED);
        }
    }

}
