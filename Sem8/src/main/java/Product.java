public class Product implements Comparable<Product> {
    private int id;
    private int price;
    private String name;

    Product(int id, int price, String name) {
        this.id = id;
        this.price = price;
        this.name = name;
    }

    int getId() {
        return id;
    }

    int getPrice() {
        return price;
    }

    String getName() {
        return name;
    }

    @Override
    public int compareTo(Product product) {
        return Integer.compare(this.id, product.id);

        // Or the same:

//        if (this.id > product.id){
//            return 1;
//        }
//        if (this.id < product.id){
//            return -1;
//        }
//        return 0;
    }

    @Override
    public String toString() {
        return String.format("Product %s with id %d costs %d standard units", name, id, price);
    }
}
