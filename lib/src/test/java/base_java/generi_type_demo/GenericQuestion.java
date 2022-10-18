package base_java.generi_type_demo;

/**
 * 泛型遗留的问题
 */
public class GenericQuestion {

    static class Parent {

    }

    static class Child extends Parent {

    }

    public static void main(String[] args) {

        Parent parent = new Parent();

        Child child = (Child) parent;

    }

}
