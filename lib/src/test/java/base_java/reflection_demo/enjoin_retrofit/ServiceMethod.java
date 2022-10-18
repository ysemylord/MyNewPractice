package base_java.reflection_demo.enjoin_retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import base_java.reflection_demo.enjoin_retrofit.annotation.Field;
import base_java.reflection_demo.enjoin_retrofit.annotation.GET;
import base_java.reflection_demo.enjoin_retrofit.annotation.POST;
import base_java.reflection_demo.enjoin_retrofit.annotation.Query;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * 存储各个请求方法的信息,
 * 每个请求方法的实现也是ServiceMethod.invoke(）实现的
 */
public class ServiceMethod {
    private EnjoyRetrofit retrofit;
    private String requestMethod;
    private boolean hasBody;
    private HttpUrl url;
    private ParameterHandler[] parameterHandlers;//存放请求参数的Key

    private ServiceMethod(EnjoyRetrofit enjoyRetrofit, String requestMethod, boolean hasBody, HttpUrl url, ParameterHandler[] parameterHandlers) {
        this.retrofit = enjoyRetrofit;
        this.requestMethod = requestMethod;
        this.hasBody = hasBody;
        this.url = url;
        this.parameterHandlers = parameterHandlers;
    }


    /**
     * 返回一个Call
     *
     * @param args
     * @return
     */
    public Call invoke(Object[] args) {
        Request.Builder requestBuilder = new Request.Builder();

        //这里的请求参数实现简单写下就行
        if ("GET".equals(requestMethod)) {
            for (int i = 0; i < parameterHandlers.length; i++) {
                ParameterHandler parameterHandler = parameterHandlers[i];
               url = url.newBuilder().addQueryParameter(parameterHandler.getKey(), args[i].toString()).build();
            }
            requestBuilder.url(url).get();
        } else if ("POST".equals(requestMethod)) {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            for (int i = 0; i < parameterHandlers.length; i++) {
                ParameterHandler parameterHandler = parameterHandlers[i];
                formBodyBuilder.add(parameterHandler.getKey(), args[i].toString());
            }
            requestBuilder.url(url).post(formBodyBuilder.build());
        }

        return retrofit.getCallFactory().newCall(requestBuilder.build());
    }

    public static class Builder {
        Annotation[] methodAnnotations;
        Annotation[][] parameterAnnotations;//一个方法多个参数，一个参数可能有多个注解
        EnjoyRetrofit retrofit;


        public Builder(EnjoyRetrofit retrofit, Method method) {
            this.retrofit = retrofit;
            methodAnnotations = method.getAnnotations();
            parameterAnnotations = method.getParameterAnnotations();
        }

        public ServiceMethod build() {

            String requestMethod = "";
            boolean hasBody = false;
            HttpUrl url = null;
            ParameterHandler[] parameterHandlers;//存放请求参数的Key

            for (int i = 0; i < methodAnnotations.length; i++) {
                Annotation annotation = methodAnnotations[i];
                if (annotation instanceof GET) {
                    String relativeUrl = ((GET) annotation).value();
                    url = retrofit.getBaseUrl().newBuilder().addPathSegments(relativeUrl).build();
                    hasBody = false;
                    requestMethod = "GET";
                } else if (annotation instanceof POST) {
                    String relativeUrl = ((POST) annotation).value();
                    url = retrofit.getBaseUrl().newBuilder(relativeUrl).build();
                    hasBody = true;
                    requestMethod = "POST";
                }
            }

            parameterHandlers = new ParameterHandler[parameterAnnotations.length];
            for (int i = 0; i < parameterAnnotations.length; i++) {
                Annotation[] annotationsOnOneParameter = parameterAnnotations[i];
                for (int j = 0; j < annotationsOnOneParameter.length; j++) {
                    Annotation annotation = annotationsOnOneParameter[j];
                    if (annotation instanceof Query) {
                        parameterHandlers[i] = new ParameterHandler.
                                QueryParameterHandler(((Query) annotation).value());
                    } else if (annotation instanceof Field) {
                        parameterHandlers[i] = new ParameterHandler.
                                PostParameterHandler(((Query) annotation).value());
                    }
                }
            }

            return new ServiceMethod(retrofit, requestMethod, hasBody, url, parameterHandlers);

        }
    }
}

