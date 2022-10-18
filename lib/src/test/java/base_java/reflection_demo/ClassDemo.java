package base_java.reflection_demo;

import org.junit.Test;

public class ClassDemo {
    class User {
        private String name;
        private int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    /**
     * String 是类型， new String()是String类型的实例是它的实例对象
     * Class<> 是类型， String.class是它的实例对象
     *
     * String.class 是描述String类型的数据
     */
    @Test
    public void one() {
        Class<User> userClass = User.class;
        User user = new User("1",1);
        user.getClass();
    }
}
