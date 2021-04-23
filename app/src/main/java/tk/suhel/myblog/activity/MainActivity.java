package tk.suhel.myblog.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.amitshekhar.DebugDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tk.suhel.myblog.R;
import tk.suhel.myblog.adapter.BlogAdapter;
import tk.suhel.myblog.databinding.ActivityMainBinding;
import tk.suhel.myblog.model.Blog;
import tk.suhel.myblog.model.Root;
import tk.suhel.myblog.viewModel.BlogViewModel;

import static tk.suhel.myblog.utils.Constant.CURRENT_BLOG_ID;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity.TAG";
    private BlogAdapter adapter;
    private BlogViewModel blogViewModel;
    private List<Blog> blogList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new BlogAdapter(this);
        binding.blogListRecyclerView.setAdapter(adapter);
        binding.blogListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        binding.blogListRecyclerView
//                .addItemDecoration(new DividerItemDecoration(binding.blogListRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        blogViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(BlogViewModel.class);
        blogViewModel.getBlogs().observe(this, new Observer<List<Blog>>() {
            @Override
            public void onChanged(List<Blog> blogs) {
                blogList = blogs;
                adapter.setBlogs(blogs);
            }
        });

        blogViewModel.getAllBlogFromApi().enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                assert response.body() != null;
                List<Blog> blogs = response.body().getBlogs();
                setDataToRoom(blogs);
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error! " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goToAddBlogActivity(View v) {
        Intent intent = new Intent(MainActivity.this, AddBlogActivity.class);
        intent.putExtra(CURRENT_BLOG_ID, new Blog());
        startActivity(intent);
    }

    private void setDataToRoom(List<Blog> blogs) {
        for (Blog blog: blogs) {
            if (blogList != null && blogList.size() > 0){
                if (blog.ifExist(blogList, blog)){
                    blogViewModel.updateBlog(blog);
                }else {
                    blogViewModel.saveBlog(blog);
                }
            }else {
                blog.setId(0);
                blogViewModel.saveBlog(blog);
            }
        }
    }
}