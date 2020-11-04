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
        IncrementSynch inc1 = new IncrementSynch();
        Stream.iterate(0, n -> n + 1).limit(10_000).parallel().forEach(n -> inc1.increment());
        System.out.println(inc1.value);
    }

}

class Increment {
    int value = 0;

    public void increment() {
        value++;
    }
}

/**
 * Does not cause concurrent errors, since method is synchronized
 */
class IncrementSynch {
    int value = 0;

    public synchronized void increment() {
        value++;
    }
}
