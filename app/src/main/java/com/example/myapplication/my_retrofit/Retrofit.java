package com.example.myapplication.my_retrofit;

import com.example.myapplication.my_retrofit.annotation.Field;
import com.example.myapplication.my_retrofit.annotation.GET;
import com.example.myapplication.my_retrofit.annotation.POST;
import com.example.myapplication.my_retrofit.annotation.Query;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Retrofit {

    public String url;
    public OkHttpClient okHttpClient;

    public Retrofit(String url, OkHttpClient okHttpClient) {
        this.url = url;
        this.okHttpClient = okHttpClient;
    }

    public <T> T create(Class<T> tClass) {
        Object proxy = Proxy.newProxyInstance(tClass.getClassLoader(), new Class[]{tClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //解析method上的注解 请求方法，请求的key
                ServiceMethod serviceMethod = new ServiceMethod.Builder(Retrofit.this, method).build();
                Call call = serviceMethod.invoke(args);
                return call;
            }
        });
        return (T) proxy;
    }
}

/**
 * 将对Method的解析放到ServiceMethod中进行
 * 这里使用Builder模式
 */
class ServiceMethod {
    private String relativeUrl;
    private String httpMethod;
    private String[] queries;
    private String[] fields;
    private Retrofit retrofit;

    public ServiceMethod(Retrofit retrofit, String relativeUrl, String httpMethod, String[] queries, String[] fields) {
        this.relativeUrl = relativeUrl;
        this.httpMethod = httpMethod;
        this.queries = queries;
        this.fields = fields;
        this.retrofit = retrofit;
    }


    public String getRelativeUrl() {
        return relativeUrl;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String[] getQueries() {
        return queries;
    }

    public String[] getFields() {
        return fields;
    }

    public Call invoke(Object[] args) {

        FormBody.Builder builder = new FormBody.Builder();
        FormBody requestBody = null;
        if (httpMethod.equals("POST")) {
            int fieldIndex = 0;
            for (String fieldKey : fields) {
                builder.add(fieldKey, (String) args[fieldIndex]);
                fieldIndex++;
            }
            requestBody = builder.build();
        }

        HttpUrl httpUrl = HttpUrl.parse(retrofit.url);
        HttpUrl.Builder httpUrlBuilder = httpUrl.newBuilder().addPathSegment(relativeUrl);

        if (httpMethod.equals("GET")) {
            int queryIndex = 0;
            for (String queryKey : queries) {
                httpUrlBuilder.addQueryParameter(queryKey, (String) args[queryIndex]);
                queryIndex++;
            }
        }

        Request request = new Request.Builder()
                .url(httpUrlBuilder.build())
                .method(getHttpMethod(), requestBody)
                .build();
        return retrofit.okHttpClient.newCall(request);
    }

    public static class Builder {
        private final Annotation[] methodAnnotations;//方法上的注解Get/Post
        private final Annotation[][] parameterAnnotations;//参数上的注解
        private String relativeUrl;
        private String httpMethod;
        private String[] queries;
        private String[] fields;
        private Retrofit retrofit;

        public Builder(Retrofit retrofit, Method method) {
            this.retrofit = retrofit;
            methodAnnotations = method.getAnnotations();
            parameterAnnotations = method.getParameterAnnotations();
        }

        public ServiceMethod build() {
            if (methodAnnotations[0] instanceof GET) {
                httpMethod = "GET";
                relativeUrl = ((GET) methodAnnotations[0]).value();
            } else if (methodAnnotations[1] instanceof POST) {
                httpMethod = "POST";
                relativeUrl = ((POST) methodAnnotations[0]).value();
            }

            queries = new String[parameterAnnotations.length];
            fields = new String[parameterAnnotations.length];
            for (Annotation[] parameterAnnotation : parameterAnnotations) {
                //parameterAnnotation一个参数上的所有注解
                int index = 0;
                for (Annotation annotationOnParameter : parameterAnnotation) {
                    //annotationOnParameter 一个参数上的所有注解中的一个
                    if (annotationOnParameter instanceof Query) {
                        String queryKey = ((Query) annotationOnParameter).value();
                        queries[index] = queryKey;
                        index++;
                    } else if (annotationOnParameter instanceof Field) {
                        String fieldKey = ((Field) annotationOnParameter).value();
                        fields[index] = fieldKey;
                        index++;
                    }
                }
            }
            return new ServiceMethod(retrofit, relativeUrl, httpMethod, queries, fields);
        }


    }
}