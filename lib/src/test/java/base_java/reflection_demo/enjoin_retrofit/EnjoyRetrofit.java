package base_java.reflection_demo.enjoin_retrofit;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * 建筑这模式，将复杂对象的构建和表示过程分离开，其实就是构造函数的参数过多就用建造者模式
 */
public class EnjoyRetrofit {
    private HttpUrl baseUrl;
    private okhttp3.Call.Factory callFactory;//实现类时Okhttpclient,用于创建Call
    private Map<Method, ServiceMethod> serviceMethodMap = new HashMap<>();//缓存MethodService


    public EnjoyRetrofit(HttpUrl baseUrl, Call.Factory callFactory) {
        this.baseUrl = baseUrl;
        this.callFactory = callFactory;
    }

    public Call.Factory getCallFactory() {
        return callFactory;
    }

    public HttpUrl getBaseUrl() {
        return baseUrl;
    }

    /**
     * Class<T> service带来了泛型的类型信息
     *
     * @return T 返回一个T的实例
     */
    @SuppressWarnings("unchecked") // Single-interface proxy creation guarded by parameter safety.
    public <T> T create(Class<T> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                //收集信息
                ServiceMethod serviceMethod = new ServiceMethod.Builder(EnjoyRetrofit.this, method).build();
                return serviceMethod.invoke(objects);
            }
        });
    }

    private ServiceMethod loadServiceMethod(Method method) {

        //因为serviceMethodMap中的元素是不会删除的，如果某个元素存在，它就一直存在，不会从存在变成不存在
        //所以一个元素存在如果存在时，我们去获取它是线程安全的

        //但是如果一个元素不存在，map可能受其他线程影响，从不存在变为存在，此时实现不安全的

        ServiceMethod serviceMethod = serviceMethodMap.get(method);
        if (serviceMethod != null) return serviceMethod;

        synchronized (this) {
            ServiceMethod targetServiceMethod = serviceMethodMap.get(method);
            if (targetServiceMethod == null) {
                serviceMethodMap.put(method, new ServiceMethod.Builder(EnjoyRetrofit.this, method).build());
            }
            return targetServiceMethod;
        }
    }

    public static class Builder {
        private String baseUrl;
        private okhttp3.Call.Factory callFactory;

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder setCallFactory(Call.Factory callFactory) {
            this.callFactory = callFactory;
            return this;
        }

        public EnjoyRetrofit build() {
            HttpUrl baseUrl = HttpUrl.get(this.baseUrl);
            okhttp3.Call.Factory callFactory = this.callFactory;

            if (callFactory == null) {
                callFactory = (Call.Factory) new OkHttpClient.Builder().build();
            }
            return new EnjoyRetrofit(baseUrl, callFactory);
        }
    }
}
