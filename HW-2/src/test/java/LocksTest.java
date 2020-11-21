import ProblemA.Locks;
import org.junit.jupiter.api.Test;

public class LocksTest {
    /**
     * It's not 100%-sure deadlock indicator!
     */
    @Test
    void deadlockTest(){
        Locks.deadlock();
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
        Locks.livelock();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException ignored) {
        }

        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            assert !thread.getState().equals(Thread.State.BLOCKED);
        }
    }

}
