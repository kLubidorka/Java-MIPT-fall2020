import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * This class contains hints for tasks from "Tasks.java"
 *
 *
 * ░░░░░░░░░░░░░░░░░░░░░▄▄▄░░░░
 * ░░░░░░░░░░░░░░░░░░░▄█████▄░░
 * ░░░░░░░░░░░░░░░░░░░████████▄
 * ░░░░░░░░░░░░░░░░░░░███░░░░░░
 * ░░░░░░░░░░░░░░░░░░░███░░░░░░
 * ░░░░░░░░░░░░░░░░░░░███░░░░░░
 * ░░░░░░░░░░░░░░░░░░░███░░░░░░
 * ░░░░░░░░░░░░░░░░░░░███░░░░░░
 * ░░░░░░░░░░░░░▄▄▄▄▄████░░░░░░
 * ░░░░░░░░▄▄████████████▄░░░░░
 * ░░░░▄▄██████████████████░░░░
 * ▄▄██████████████████████░░░░
 * ░▀▀████████████████████▀░░░░
 * ░░░░▀█████████████████▀░░░░░
 * ░░░░░░▀▀███████████▀▀░░░░░░░
 * ░░░░░░░░░▀███▀▀██▀░░░░░░░░░░
 * ░░░░░░░░░░█░░░░██░░░░░░░░░░░
 * ░░░░░░░░░░█░░░░█░░░░░░░░░░░░
 * ░░░▄▄▄▄███████▄███████▄▄▄▄░░
 *
 * (goose for attracting your attention)
 *
 * IF YOU OPENED THIS FILE BY ACCIDENT AND WANT TO SOLVE TASKS WITHOUT HINTS, OPEN "Tasks.java" INSTEAD
 *
 *
 */
public class TasksWithHints<T> {

    /**
     * Return amount of elements equal to pattern
     * For example, task1(Stream.of("a", "a", "b"), "a") = 2
     *
     * Solution:
     * 1) Use 'filter' to get rid of elements that are not equal to pattern
     * 2) Use 'count' to calculate the answer
     */
    public long task1(Stream<T> stream, T pattern){
        // YOUR CODE HERE
        return 0;
    }

    /**
     * Return last element of collection or null if collection os empty
     *
     * Solution:
     * 0) Convert collection to stream with 'stream()'
     * 1) Skip max(collection.size() - 1, 0) elements
     * (we use 'max()' to cover the case when collection is empty)
     * 2) return last element (findFirst or findAny)
     * (you may use orElse(null) to avoid using Optional<T>)
     */
    public T task2(Collection<T> collection){
        // YOUR CODE HERE
        return null;
    }

    /**
     * Return list of two elements starting from the second.
     * It is guaranteed that size of the stream is greater than two
     *
     * Solution:
     * 1) Use 'skip' and 'limit' to end up with a stream of required elements
     * 2) Use 'collect' to convert stream to collection. You don't need to create your own collector,
     * since you can use standard collector 'Collectors.toList()'
     */
    public List<T> task3(Stream<T> stream){
        // YOUR CODE HERE
        return null;
    }

    /**
     * Return true if at least one element equal to "pattern" can be found in stream, else return false
     *
     * Solution:
     * 1) Use 'anyMatch'
     *
     *  Look at this experiment and try to explain the results {@link StreamExamples#speedTest()}
     */
    public boolean task4(Stream<T> stream, T pattern){
        // YOUR CODE HERE
        return false;
    }

    /**
     * Return sorted list of stream elements without duplicates
     *
     * Solution:
     * 1) Use 'distinct' to remove duplicates
     * 2) Use 'sorted'
     * 3) Use the same trick as you used in task 3
     */
    public List<T> task5(Stream<T> stream){
        // YOUR CODE HERE
        return null;
    }
}
