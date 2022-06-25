package base;

import org.junit.Test;

import java.util.List;

public class A {
    @Test
    public void mainTest() {
        List<String> names = Tfest1Kt.returnAny(() -> "jack");
    }
}
