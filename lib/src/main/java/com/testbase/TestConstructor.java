package com.testbase;

public class TestConstructor {
    private static class Parent {
        public Parent() {
            System.out.println("无参构造函数");
        }

        public Parent(String p1) {
            System.out.println("造函数1个");
        }

        public Parent(String p1, String p2) {
            System.out.println("造函数2个");
        }
    }

    private static class Child extends Parent {
        public Child() {

        }

        public Child(String p1) {

        }

        public Child(String p1,String p2) {
            super(p1,p2);
        }

    }

    public static void main(String[] args) {
        System.out.println(new Child("111"));
        System.out.println(new Child("111","2222"));
    }
}
