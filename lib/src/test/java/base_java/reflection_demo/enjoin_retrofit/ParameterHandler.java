package base_java.reflection_demo.enjoin_retrofit;

/**
 * 因为每种请求方式对参数的处理方式不一样，所以参数使用单独的一个类
 */
public abstract class ParameterHandler {
    private String key;

    public ParameterHandler(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    /**
     * 这里和真实的retrofit的框架实现有点不一样，retrofit这里是 apply(RequestBuilder builder, @Nullable T value)
     * RequestBuilder是retroft自定义的类，用于包装需要的信息
     * @param serviceMethod
     * @param value
     */
    abstract void apply(ServiceMethod serviceMethod, Object value);

    public static class QueryParameterHandler extends ParameterHandler {

        public QueryParameterHandler(String key) {
            super(key);
        }

        @Override
        void apply(ServiceMethod serviceMethod, Object value) {

        }


    }

    public static class PostParameterHandler extends ParameterHandler {

        public PostParameterHandler(String key) {
            super(key);
        }

        @Override
        void apply(ServiceMethod serviceMethod, Object value) {

        }


    }
}
