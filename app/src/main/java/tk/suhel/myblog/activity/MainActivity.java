package tk.suhel.myblog.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tk.suhel.myblog.adapter.BlogAdapter;
import tk.suhel.myblog.component.DaggerBlogComponent;
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

        blogViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(BlogViewModel.class);
        blogViewModel.getBlogs().observe(this, new Observer<List<Blog>>() {
            @Override
            public void onChanged(List<Blog> blogs) {
                blogList = blogs;
                adapter.setBlogs(blogs);
            }
        });

        if (blogList == null){
            showProgressDialog().show();
        }

        blogViewModel.getAllBlogFromApi().enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                assert response.body() != null;
                blogViewModel.saveAllBlogs(response.body().getBlogs());
                showProgressDialog().dismiss();
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                showProgressDialog().dismiss();
                Toast.makeText(MainActivity.this, "Error! " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goToAddBlogActivity(View v) {
        Blog blog = DaggerBlogComponent.create().getBlog();

        Intent intent = new Intent(MainActivity.this, AddBlogActivity.class);
        intent.putExtra(CURRENT_BLOG_ID, blog);
        startActivity(intent);
    }

    public AlertDialog showProgressDialog(){
        final ProgressBar progressBar = new ProgressBar(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        progressBar.setPadding(5, 5, 5, 50);
        progressBar.setLayoutParams(lp);

        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Loading...Blog...")
                .setMessage("Please wait for a moment...")
                .create();

//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7dffffff")));
        dialog.setView(progressBar);
        return dialog;
    }
}