package tk.suhel.myblog.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import tk.suhel.myblog.model.Blog;

@Dao
public interface BlogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void saveBlog(Blog blog);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void saveAllBlog(List<Blog> blog);

    @Update
    public void updateBlog(Blog blog);

    @Query("select * from blog")
    public LiveData<List<Blog>> getBlogs();

    @Query("select * from blog where id = :id")
    public Blog getBlogs(int id);

    @Query("delete from blog where id = :id")
    public int deleteBlogs(int id);
}
