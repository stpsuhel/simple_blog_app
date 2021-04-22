package tk.suhel.myblog.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import tk.suhel.myblog.model.Blog;
import tk.suhel.myblog.model.Root;

public interface BlogApi {

    @GET("db")
    public Call<Root> getAllBlogFromApi();
}
