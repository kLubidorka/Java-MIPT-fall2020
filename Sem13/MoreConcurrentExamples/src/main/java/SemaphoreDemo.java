import java.util.concurrent.Semaphore;

/**
 * Semaphore demo from Oracle documentation
 * https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/Semaphore.html
 */
public class SemaphoreDemo {

    static class Pool {
        private static final int MAX_AVAILABLE = 10;
        private final Semaphore available = new Semaphore(MAX_AVAILABLE, true);

        public Object getItem() throws InterruptedException {
            available.acquire();
            return getNextAvailableItem();
        }

        public void putItem(Object x) {
            if (markAsUnused(x))
                available.release();
        }

        // Not a particularly efficient data structure; just for demo
        protected Object[] items = new Object[3];
        protected boolean[] used = new boolean[MAX_AVAILABLE];

        protected synchronized Object getNextAvailableItem() {
            for (int i = 0; i < MAX_AVAILABLE; ++i) {
                if (!used[i]) {
                    used[i] = true;
                    return items[i];
                }
            }
            return null; // not reached
        }

        protected synchronized boolean markAsUnused(Object item) {
            for (int i = 0; i < MAX_AVAILABLE; ++i) {
                if (item == items[i]) {
                    if (used[i]) {
                        used[i] = false;
                        return true;
                    } else
                        return false;
                }
            }
            return false;
        }
    }
}
