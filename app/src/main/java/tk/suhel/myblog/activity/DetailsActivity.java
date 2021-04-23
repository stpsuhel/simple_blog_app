package tk.suhel.myblog.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import lombok.SneakyThrows;
import tk.suhel.myblog.R;
import tk.suhel.myblog.databinding.ActivityDetailsBinding;
import tk.suhel.myblog.model.Blog;
import tk.suhel.myblog.model.CategoryListForSpinner;
import tk.suhel.myblog.viewModel.BlogViewModel;

import static tk.suhel.myblog.utils.Constant.CURRENT_BLOG_ID;

public class DetailsActivity extends AppCompatActivity {
    private static final String TAG = "DetailsActivity.TAG";
    private BlogViewModel blogViewModel;
    private int blogId;
    private Blog blog;

    private ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        blogViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(BlogViewModel.class);
        setTitle("Blog Details");

        blogId = getIntent().getIntExtra(CURRENT_BLOG_ID, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            if (blogId != 0){
                blog = blogViewModel.getBlogs(blogId).get();
                binding.setBlog(blog);

                CategoryListForSpinner categoryListForSpinner = new CategoryListForSpinner();
                binding.setCategories(categoryListForSpinner.listToString(blog.getCategories()));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Toast.makeText(DetailsActivity.this, "Error! " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_page_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.edit_blog_activity) {
            Intent intent = new Intent(DetailsActivity.this, AddBlogActivity.class);
            intent.putExtra(CURRENT_BLOG_ID, blog);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}