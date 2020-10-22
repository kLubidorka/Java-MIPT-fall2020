import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class StreamExamples {
    public static void main(String[] args) {
        IntStream.of(1, 2, 3, 4)
                .filter(s -> s % 2 == 0)
                .sorted()
                .forEach(System.out::println);
    }

    /**
     * Our goal is to find all the products that are cheaper than 14 standard units, sort them by id and print them.
     * This method solves the problem without streams
     */
    public static void beforeJava8Approach() {
        List<Product> products = prepareProducts();

        // Find all the products that are cheaper than 14 standard units
        List<Product> cheapProducts = new ArrayList<>();
        for (Product elem : products) {
            if (elem.getPrice() < 14) {
                cheapProducts.add(elem);
            }
        }

        // Sort them
        Collections.sort(cheapProducts);

        // Print them
        for (Product elem : cheapProducts) {
            System.out.println(elem);
        }
    }

    /**
     * This method solves the problem using stream
     */
    public static void streamApproach() {
        List<Product> products = prepareProducts();

        products.stream()
                .filter(s -> s.getPrice() < 14)
                .sorted()
                .forEach(System.out::println);
    }


    private static List<Product> prepareProducts() {
        List<Product> products = new ArrayList<>(5);
        products.add(new Product(1, 20, "Banana"));
        products.add(new Product(2, 10, "Candy"));
        products.add(new Product(3, 12, "Juice"));
        products.add(new Product(4, 15, "Apple"));

        return products;
    }
}
