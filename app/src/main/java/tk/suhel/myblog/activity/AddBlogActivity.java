package tk.suhel.myblog.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import tk.suhel.myblog.component.DaggerCategoryListForSpinnerComponent;
import tk.suhel.myblog.databinding.ActivityAddBlogBinding;
import tk.suhel.myblog.model.Author;
import tk.suhel.myblog.model.Blog;
import tk.suhel.myblog.model.CategoryListForSpinner;
import tk.suhel.myblog.viewModel.BlogViewModel;

import static tk.suhel.myblog.utils.Constant.CURRENT_BLOG_ID;

public class AddBlogActivity extends AppCompatActivity {
    private static final String TAG = "AddBlogActivity.TAG";

    private Blog oldBlog;
    private Boolean isUpdate = false;

    private CategoryListForSpinner categoryListObject;
    private ActivityAddBlogBinding binding;
    private BlogViewModel blogViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBlogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        blogViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(BlogViewModel.class);

        oldBlog = (Blog) getIntent().getSerializableExtra(CURRENT_BLOG_ID);
        assert oldBlog != null;

        categoryListObject = DaggerCategoryListForSpinnerComponent.create().getCategoryListForSpinner();
        categoryListObject.setAlreadySelectedList(oldBlog.getCategories());

        binding.setBlog(oldBlog);
        binding.setCategories(categoryListObject);
        binding.setHandler(this);

        if (oldBlog.getId() != 0){
            setTitle("Update Blog");
            isUpdate = true;

            binding.tvAddNewBlog.setText("Update Blog");
            binding.btnSaveBlog.setText("Update");
        }else {
            setTitle("Add New Blog");
        }
    }

    public void saveUpdateBlogData(View view){
        String title = binding.etTitle.getText().toString().trim();
        String des = binding.etDescription.getText().toString().trim();

        if (title.isEmpty() || des.isEmpty() || categoryListObject.getSelectedCategoryList().size() <= 0){
            Toast.makeText(this, "Enter All the field!!", Toast.LENGTH_SHORT).show();
        }
        else {
            String imageUrl = oldBlog.getCover_photo() == null?
                    "https://i.picsum.photos/id/579/200/300.jpg?hmac=9MD8EV4Jl9EqKLkTj5kyNdBUKQWyHk2m4pE4UCBGc8Q" : oldBlog.getCover_photo();

            Author author = oldBlog.getAuthor() == null?
                    new Author(1, "John Doe", "https://i.pravatar.cc/250", "Content Writer") : oldBlog.getAuthor();

            Log.d(TAG, "saveUpdateBlogData: " + categoryListObject.getSelectedCategoryList());
            Blog blog = new Blog(oldBlog.getId(), title, des, imageUrl, categoryListObject.getSelectedCategoryList(), author);
            if (isUpdate){
                blogViewModel.updateBlog(blog);
            }else {
                blogViewModel.saveBlog(blog);
            }
            finish();
        }
    }
}