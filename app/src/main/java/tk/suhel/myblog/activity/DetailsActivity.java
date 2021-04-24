package tk.suhel.myblog.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import tk.suhel.myblog.R;
import tk.suhel.myblog.component.DaggerCategoryListForSpinnerComponent;
import tk.suhel.myblog.databinding.ActivityDetailsBinding;
import tk.suhel.myblog.model.Blog;
import tk.suhel.myblog.model.CategoryListForSpinner;
import tk.suhel.myblog.viewModel.BlogViewModel;

import static tk.suhel.myblog.utils.Constant.CURRENT_BLOG_ID;

public class DetailsActivity extends AppCompatActivity {
    private static final String TAG = "DetailsActivity.TAG";
    private BlogViewModel blogViewModel;
    private int blogId;
    private Blog oldBlog;

    private ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        blogViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(BlogViewModel.class);
        setTitle("Blog Details");

        blogId = getIntent().getIntExtra(CURRENT_BLOG_ID, 0);

        if (blogId != 0){
            blogViewModel.getBlogs(blogId).observe(this, new Observer<Blog>() {
                @Override
                public void onChanged(Blog blog) {
                    oldBlog = blog;
                    if (blog != null){
                        binding.setBlog(blog);
                        CategoryListForSpinner categoryListForSpinner = DaggerCategoryListForSpinnerComponent.create().getCategoryListForSpinner();
                        binding.setCategories(categoryListForSpinner.listToString(blog.getCategories()));
                    }
                }
            });
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
            intent.putExtra(CURRENT_BLOG_ID, oldBlog);
            startActivity(intent);
            return true;
        }else if (item.getItemId() == R.id.delete_blog) {
            deleteBlogItemAlert();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void deleteBlogItemAlert(){
        new AlertDialog.Builder(DetailsActivity.this)
                .setTitle("Delete Blog")
                .setMessage("Are you sure you want to delete this Blog?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            if (blogViewModel.deleteBlogs(blogId).get() != 0){
                                Toast.makeText(DetailsActivity.this, "Blog Successfully Deleted!", Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(DetailsActivity.this, "Blog Not Deleted. Something Wrong...!", Toast.LENGTH_SHORT).show();
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
}