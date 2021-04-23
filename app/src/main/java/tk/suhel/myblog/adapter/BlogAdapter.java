package tk.suhel.myblog.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import tk.suhel.myblog.R;
import tk.suhel.myblog.activity.DetailsActivity;
import tk.suhel.myblog.databinding.RowForBlogViewBinding;
import tk.suhel.myblog.model.Blog;
import tk.suhel.myblog.model.CategoryListForSpinner;

import static tk.suhel.myblog.utils.Constant.CURRENT_BLOG_ID;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.BlogViewHolder> {
    private static final String TAG = "BlogAdapter.TAG";
    private LayoutInflater mInflater;
    private List<Blog> blogList;

    public BlogAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowForBlogViewBinding rowForBlogViewBinding = RowForBlogViewBinding.inflate(mInflater, parent, false);
        return new BlogViewHolder(rowForBlogViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogViewHolder holder, int position) {
        Blog blog = blogList.get(position);
        holder.rowForBlogViewBinding.setBlog(blog);

        CategoryListForSpinner categoryListForSpinner = new CategoryListForSpinner();
        holder.rowForBlogViewBinding.setCategories(categoryListForSpinner.listToString(blog.getCategories()));

        holder.rowForBlogViewBinding.ivCoverPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                intent.putExtra(CURRENT_BLOG_ID, blog.getId());
                view.getContext().startActivity(intent);
            }
        });
    }

    public void setBlogs(List<Blog> blogs){
        Log.d(TAG, "setBlogs: " + blogs);
        this.blogList = blogs;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (blogList != null) return blogList.size();
        return 0;
    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder{
        private RowForBlogViewBinding rowForBlogViewBinding;

        public BlogViewHolder(@NonNull RowForBlogViewBinding rowForBlogViewBinding) {
            super(rowForBlogViewBinding.getRoot());
            this.rowForBlogViewBinding = rowForBlogViewBinding;
        }
    }
}
