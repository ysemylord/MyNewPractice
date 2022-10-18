package base_java.reflection_demo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.lang.reflect.Type;


class MyTypeToken extends TypeToken<ReflectAndGeneric.Response<ReflectAndGeneric.User>> {

}

public class ReflectAndGeneric {


    class Response<T> {
        int code;
        String msg;
        T data;

        public Response(int code, String msg, T data) {
            this.code = code;
            this.msg = msg;
            this.data = data;
        }
    }


    class User {
        int age;
        String name;

        public User(int age, String name) {
            this.age = age;
            this.name = name;
        }
    }

    class Response0 {
        int code;
        String msg;

        public Response0(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }


    @Test
    public void one() {
        Gson gson = new Gson();
        String jsonStr = "{\"code\":12,\"msg\":\"success\"}";
        Response0 response0 = gson.fromJson(jsonStr, Response0.class);
        System.out.println(response0.msg + " : " + response0.code);
    }

    @Test
    public void two() {

        Gson gson = new Gson();

        String jsonStr = "{\"code\":12,\"msg\":\"success\",\"data\":{\"age\":12,\"name\":\"jack\"}}";

        Response<User> userResponse = gson.fromJson(jsonStr, Response.class);
        System.out.println(userResponse.code);
        System.out.println(userResponse.msg);
        System.out.println(userResponse.data.age);

        // Response<User> userResponse2 = gson.fromJson(jsonStr2, Response<User>.class);
    }

    @Test
    public void three() {
        Gson gson = new Gson();
        String jsonStr = "{\"code\":12,\"msg\":\"success\",\"data\":{\"age\":12,\"name\":\"jack\"}}";
        Type type = new TypeToken<Response<User>>() {
        }.getType();
        Response<User> userResponse = gson.fromJson(jsonStr, type);
        //Response<User> userResponse1 = gson.fromJson(jsonStr, new MyTpeToken().getType());
        System.out.println(userResponse.data.age);
    }
}
