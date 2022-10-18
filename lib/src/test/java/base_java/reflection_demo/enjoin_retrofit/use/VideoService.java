package base_java.reflection_demo.enjoin_retrofit.use;

import java.io.IOException;

import base_java.reflection_demo.enjoin_retrofit.EnjoyRetrofit;
import base_java.reflection_demo.enjoin_retrofit.annotation.GET;
import base_java.reflection_demo.enjoin_retrofit.annotation.Query;
import okhttp3.Call;
import okhttp3.Response;


public interface VideoService {
    @GET("getHaoKanVideo")
    Call getUserInfo(@Query("page") int page, @Query("size") int size);
}

class Test {
    public static void main(String[] args) throws IOException, InterruptedException {
        EnjoyRetrofit enjoyRetrofit = new EnjoyRetrofit.Builder()
                .setBaseUrl("https://api.apiopen.top/api")
                .build();
        VideoService videoService = enjoyRetrofit.create(VideoService.class);
        Call call = videoService.getUserInfo(0, 2);
        Response stringResponse = call.execute();
        System.out.println(stringResponse.body().string());
        Thread.sleep(50000);
    }
}
