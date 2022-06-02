package function_demo;

import org.jetbrains.annotations.Nullable;
import org.junit.Test;

public class JavaTest {
    class My implements function_demo.Test.TestC {


        @Override
        public void test1(@Nullable Long num) {

        }

        @Override
        public void test2(long num) {

        }
    }
    @Test
    void test(){

    }
}
