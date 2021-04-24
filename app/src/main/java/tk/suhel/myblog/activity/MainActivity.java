package tk.suhel.myblog.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tk.suhel.myblog.R;
import tk.suhel.myblog.adapter.BlogAdapter;
import tk.suhel.myblog.component.BlogViewModelComponent;
import tk.suhel.myblog.component.DaggerBlogComponent;
import tk.suhel.myblog.databinding.ActivityMainBinding;
import tk.suhel.myblog.model.Blog;
import tk.suhel.myblog.model.Root;
import tk.suhel.myblog.utils.CommonAlertDialog;
import tk.suhel.myblog.viewModel.BlogViewModel;

import static tk.suhel.myblog.utils.Constant.CURRENT_BLOG_ID;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity.TAG";
    private BlogAdapter adapter;
    private BlogViewModel blogViewModel;
    private List<Blog> blogList;

    CommonAlertDialog commonAlertDialog;

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

        commonAlertDialog = new CommonAlertDialog(MainActivity.this, blogViewModel);
        if (blogList == null && adapter.getItemCount() <= 0){
            commonAlertDialog.getDataFromApi();
        }
    }

    public void goToAddBlogActivity(View v) {
        Blog blog = DaggerBlogComponent.create().getBlog();

        Intent intent = new Intent(MainActivity.this, AddBlogActivity.class);
        intent.putExtra(CURRENT_BLOG_ID, blog);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.api_call_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.refresh_api_call) {
            commonAlertDialog.getDialogForDataFromApi();
            return true;
        }else if (item.getItemId() == R.id.delete_all_blog) {
            commonAlertDialog.deleteAllBlogs();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}