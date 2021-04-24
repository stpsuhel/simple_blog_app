package tk.suhel.myblog.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import tk.suhel.myblog.R;
import tk.suhel.myblog.component.DaggerCategoryListForSpinnerComponent;
import tk.suhel.myblog.databinding.ActivityDetailsBinding;
import tk.suhel.myblog.model.Blog;
import tk.suhel.myblog.model.CategoryListForSpinner;
import tk.suhel.myblog.utils.CommonAlertDialog;
import tk.suhel.myblog.viewModel.BlogViewModel;

import static tk.suhel.myblog.utils.Constant.CURRENT_BLOG_ID;

public class DetailsActivity extends AppCompatActivity {
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
        CommonAlertDialog commonAlertDialog = new CommonAlertDialog(DetailsActivity.this, blogViewModel);
        commonAlertDialog.deleteBlogs(blogId);
    }
}