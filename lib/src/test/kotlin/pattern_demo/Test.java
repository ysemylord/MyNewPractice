package pattern_demo;

public class Test {
    public static void main(String[] args) {
        Car.Companion.build(builder -> {
            builder.setModel("model");
            return null;
        });
    }
}
