package base_java.generi_type_demo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GenericWildcard {
    @Test
    public void one() {
        List<? extends Comparable> comparables = new ArrayList<String>();
        List<? super Integer> integers = new ArrayList<Number>();
    }
}
