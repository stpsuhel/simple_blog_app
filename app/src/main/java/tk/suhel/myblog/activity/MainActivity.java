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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tk.suhel.myblog.R;
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
            getDataFromApi();
        }
    }

    public void goToAddBlogActivity(View v) {
        Blog blog = DaggerBlogComponent.create().getBlog();

        Intent intent = new Intent(MainActivity.this, AddBlogActivity.class);
        intent.putExtra(CURRENT_BLOG_ID, blog);
        startActivity(intent);
    }

    public void getDataFromApi(){
        blogViewModel.getAllBlogFromApi().enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                assert response.body() != null;
                blogViewModel.saveAllBlogs(response.body().getBlogs());
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error! " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
            getAlartDialogForDataFromApi();
            return true;
        }else if (item.getItemId() == R.id.delete_all_blog) {
            deleteBlogItemAlert();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void deleteBlogItemAlert(){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Delete All Blog!!")
                .setMessage("Are you sure you want to delete All Blog?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            if (blogViewModel.deleteAllBlogs().get() > 0){
                                Toast.makeText(MainActivity.this, "All Blog Successfully Deleted!", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(MainActivity.this, "All Blog Not Deleted. Something Wrong...!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void getAlartDialogForDataFromApi(){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Refresh Blog!!")
                .setMessage("Are you sure you want to Reload All Blog?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getDataFromApi();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }
}