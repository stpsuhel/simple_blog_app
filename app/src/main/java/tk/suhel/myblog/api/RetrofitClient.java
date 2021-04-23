package tk.suhel.myblog.api;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static tk.suhel.myblog.utils.Constant.BASE_URL;

public class RetrofitClient {
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
