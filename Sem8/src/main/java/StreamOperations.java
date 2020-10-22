import java.util.Optional;
import java.util.stream.Stream;

public class StreamOperations {
    public static void main(String[] args) {
        String str =  Stream.of("a1", "b2").findFirst().orElse("nothing");
    }
}
