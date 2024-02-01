package com.reflect_test.generic;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class Test {
    static class Data {
        private String result;

        public Data(String result) {
            this.result = result;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }

    public static void main(String[] args) {
        Response<Data> response = new Response<>(new Data("hello"), "success", 0);
        Gson gson = new Gson();
        //序列化
        String responseStr = gson.toJson(response);
        System.out.println(responseStr);
        //反序列化
       /* Response<Data> dataResponse = gson.fromJson(responseStr,Response.class);
        Data data = dataResponse.getData();//因为没有传入Data的类型信息，所以报错
        */

        /*Type type = new TypeToken<Response<Data>>() {
        }.getType();*/
        TestTypeToken typeToken = new TestTypeToken();
        Response<Data> dataResponse = gson.fromJson(responseStr, new TypeToken<Response<Data>>(){}.getType());
        Data data = dataResponse.getData();
    }
}
