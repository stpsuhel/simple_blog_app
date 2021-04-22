package tk.suhel.myblog.viewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import retrofit2.Call;
import tk.suhel.myblog.model.Root;
import tk.suhel.myblog.repository.BlogRepository;
import tk.suhel.myblog.model.Blog;

public class BlogViewModel extends AndroidViewModel {
    private BlogRepository repository;
    private LiveData<List<Blog>> blogList;

    public BlogViewModel(@NonNull Application application) {
        super(application);
        repository = new BlogRepository(application);
        blogList = repository.getBlogs();
    }

    public LiveData<List<Blog>> getBlogs(){
        return blogList;
    }

    public AsyncTask<Integer, Void, Blog> getBlogs(int id){
        return repository.getBlogs(id);
    }

    public void saveBlog(Blog blog){
        repository.saveBlog(blog);
    }

    public void updateBlog(Blog blog){
        repository.updateBlog(blog);
    }

    public Call<Root> getAllBlogFromApi(){
        return repository.getAllBlogFromApi();
    }
}
