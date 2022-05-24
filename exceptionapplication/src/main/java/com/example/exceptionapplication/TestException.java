package com.example.exceptionapplication;

public class TestException {

    public void method() {
        try {
            int i = 0;
            new A();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   static class A {
       public static B b = new B();
    }
}



class B {
    public B() {
        int i = 1 / 0;
    }
}
