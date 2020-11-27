import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Demo of CyclicBarrier from Oracle documentation
 * https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/CyclicBarrier.html
 */
public class CyclicBarrierDemo {
    static class Solver {
        static int ThreadsNum;
        static float[][] data;
        static CyclicBarrier barrier;

        static class Worker implements Runnable {
            int myRow;

            Worker(int row) {
                myRow = row;
            }

            public void run() {
                float sum = 0;
                for (float elem : data[myRow]) {
                    sum += elem;
                }
                data[myRow][0] = sum;
                System.out.printf("Thread #%d ready\n", myRow);
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException ignored) {
                }

            }
        }

        public static void parallelMatrixSum(float[][] matrix) {
            data = matrix;
            ThreadsNum = matrix.length;
            barrier = new CyclicBarrier(ThreadsNum, () -> {
                float sum = 0;
                for (float[] floats : matrix) {
                    sum += floats[0];
                }
                System.out.printf("The sum is %f\n", sum);
            });
            for (int i = 0; i < ThreadsNum; ++i)
                new Thread(new Worker(i)).start();
        }
    }

    public static void main(String[] args) {
        Solver.parallelMatrixSum(new float[][]{{1f, 2f, 3f}, {4f, 5f, 6f}, {7f, 8f}});
    }
}
