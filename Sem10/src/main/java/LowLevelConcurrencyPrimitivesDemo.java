import javafx.scene.SceneAntialiasing;

import java.util.List;
import java.util.stream.Stream;

public class LowLevelConcurrencyPrimitivesDemo {
    public static void main(String[] args) {
        raceCondition();
        raceConditionFixed();
    }

    public static void raceCondition() {
        // RACE CONDITION
        Increment inc1 = new Increment();
        Stream.iterate(0, n -> n + 1).limit(10_000).parallel().forEach(n -> inc1.increment());
        System.out.println(inc1.value);
    }

    public static void raceConditionFixed() {
        // NO RACE CONDITION
        IncrementSynch inc2 = new IncrementSynch();
        Stream.iterate(0, n -> n + 1).limit(10_000).parallel().forEach(n -> inc2.increment());
        System.out.println(inc2.value);
    }

}

class Increment {
    //int value = 0;

    // will this help?
    volatile int value = 0;

    public void increment() {
        value++;
    }
}

/**
 * Does not cause concurrent errors, since method is synchronized
 */
class IncrementSynch {

    /** Constructors cannot be synchronized. Using the synchronized keyword with a constructor is a syntax error.
     * Synchronizing constructors doesn't make sense, because only the thread that creates an object
     * should have access to it while it is being constructed.
     */
//    synchronized IncrementSynch(){
//        // do smth
//    }

    int value = 0;

    public synchronized void increment() {
        value++;
    }
}

class DataTransferObject{
    List<Object> firstList;
    List<Object> secondList;

    public synchronized void setFirstList(List<Object> list){
        firstList = list;
    }

    public synchronized void setSecondList(List<Object> list){
        secondList = list;
    }
}

class DataTransferObjectImproved{
    List<Object> firstList;
    final Object firstListLock = new Object();

    List<Object> secondList;
    final Object secondListLock = new Object();

    public void setFirstList(List<Object> list){
        synchronized (firstListLock){
            firstList = list;
        }
    }

    public void setSecondList(List<Object> list){
        synchronized (secondListLock){
            secondList = list;
        }
    }
}

class example{
    boolean condition = false;
    Object obj = new Object();

    synchronized void one(){
        try{
            while(!condition){
                obj.wait();
            }
        } catch (InterruptedException ignored) {}

        // do smth
    }

    synchronized void oneOne(){
        try{
            while(!condition){
                obj.wait();
            }
        } catch (InterruptedException ignored) {}

        // do smth
    }

    synchronized void two(){
        condition = true;
        obj.notify();
        obj.notifyAll();
    }
}