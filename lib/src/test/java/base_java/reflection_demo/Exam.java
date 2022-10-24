package base_java.reflection_demo;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Exam {

    static class User {
        private String name;

        public User() {
        }

        public User(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }


    @Test
    public void newInstance() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        Class<User> userClass = User.class;
        Constructor<User> userConstructor1 = userClass.getConstructor();
        Constructor<User> userConstructor2 = userClass.getConstructor(String.class);
        User user1 = userConstructor1.newInstance();
        User user2 = userConstructor2.newInstance("2");
        System.out.println(user1);
        System.out.println(user2.name);
        Method method = userClass.getMethod("getName");
        method.invoke(user1);
    }
}
