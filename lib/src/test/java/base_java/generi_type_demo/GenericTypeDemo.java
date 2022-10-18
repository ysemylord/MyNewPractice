package base_java.generi_type_demo;

import org.junit.Test;

public class GenericTypeDemo {

    class Pair<T1, T2> {
        public T1 first;
        public T2 second;

        public Pair(T1 first, T2 second) {
            this.first = first;
            this.second = second;
        }

        public T1 getFirst() {
            return first;
        }

        public void setFirst(T1 first) {
            this.first = first;
        }

        public T2 getSecond() {
            return second;
        }

        public void setSecond(T2 second) {
            this.second = second;
        }
    }

    public static <T extends Comparable> T compare(T t1, T t2) {
        if (t1.compareTo(t2) > 0) {
            return t1;
        }
        return t2;
    }

    @Test
    public void one() {
        Pair<String, Integer> pair = new Pair<String, Integer>("test", 1);
    }
}
