package tk.suhel.myblog.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://my-json-server.typicode.com/sohel-cse/simple-blog-api/";
    private static volatile RetrofitClient INSTANCE;
    private Retrofit retrofit;

    public RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitClient getInstanse(){
        if (INSTANCE == null){
            synchronized (RetrofitClient.class){
                if (INSTANCE == null){
                    INSTANCE = new RetrofitClient();
                }
            }
        }
        return INSTANCE;
    }

    public BlogApi getApi(){
        return retrofit.create(BlogApi.class);
    }
}
