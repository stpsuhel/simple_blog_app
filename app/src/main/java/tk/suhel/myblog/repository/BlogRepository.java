package tk.suhel.myblog.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import tk.suhel.myblog.api.RetrofitClient;
import tk.suhel.myblog.dao.BlogDAO;
import tk.suhel.myblog.database.BlogDatabase;
import tk.suhel.myblog.model.Blog;
import tk.suhel.myblog.model.Root;

public class BlogRepository {
    private static final String TAG = "BlogRepository.TAG";
    private final BlogDAO dao;
    private final LiveData<List<Blog>> blogList;

    private final RetrofitClient client;

    public BlogRepository(Application application) {
        BlogDatabase db = BlogDatabase.getDatabase(application);
        this.dao = db.getBlogDAO();
        this.blogList = dao.getBlogs();

        client = RetrofitClient.getInstanse();
    }

    public LiveData<List<Blog>> getBlogs(){
        return blogList;
    }

    public AsyncTask<Integer, Void, Blog> getBlogs(int id){
        return new findByIdAsync(dao).execute(id);
    }

    private static class findByIdAsync extends AsyncTask<Integer, Void, Blog> {
        private final BlogDAO blogDAO;
        public findByIdAsync(BlogDAO dao) {
            this.blogDAO = dao;
        }

        @Override
        protected Blog doInBackground(Integer... ids) {
            return blogDAO.getBlogs(ids[0]);
        }
    }

    public void saveBlog(Blog blog){
        new saveUpdateBlogsAsync(dao).execute(blog);
    }

    public void updateBlog(Blog blog){
        new saveUpdateBlogsAsync(dao).execute(blog);
    }

    private static class saveUpdateBlogsAsync extends AsyncTask<Blog, Void, Void> {
        private final BlogDAO asyncDAO;
        public saveUpdateBlogsAsync(BlogDAO dao) {
            this.asyncDAO = dao;
        }

        @Override
        protected Void doInBackground(Blog... blogs) {
            Blog blog = blogs[0];
            if (blog.getId() == 0){
                asyncDAO.saveBlog(blog);
            }else {
                asyncDAO.updateBlog(blog);
            }
            return null;
        }
    }

    public AsyncTask<Integer, Void, Integer> deleteBlogs(int id){
        return new deleteBlogsAsyncTask(dao).execute(id);
    }

    private static class deleteBlogsAsyncTask extends AsyncTask<Integer, Void, Integer> {
        private BlogDAO blogDAO;
        public deleteBlogsAsyncTask(BlogDAO dao) {
            this.blogDAO = dao;
        }

        @Override
        protected Integer doInBackground(Integer... ids) {
            return blogDAO.deleteBlogs(ids[0]);
        }
    }

    public Call<Root> getAllBlogFromApi(){
        return client.getApi().getAllBlogFromApi();
    }

    public void saveAllBlogs(List<Blog> blogs){
        new saveAllBlogsAsync(dao).execute(blogs);
    }

    private static class saveAllBlogsAsync extends AsyncTask<List<Blog>, Void, Void> {
        private final BlogDAO asyncDAO;
        public saveAllBlogsAsync(BlogDAO dao) {
            this.asyncDAO = dao;
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(List<Blog>... blogs) {
            asyncDAO.saveAllBlog(blogs[0]);
            return null;
        }
    }
}
