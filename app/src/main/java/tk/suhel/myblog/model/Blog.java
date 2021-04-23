package tk.suhel.myblog.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "blog")
public class Blog implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private String cover_photo;
    private List<String> categories;
    private Author author;

    public Blog(String title, String description, String cover_photo, List<String> categories, Author author) {
        this.title = title;
        this.description = description;
        this.cover_photo = cover_photo;
        this.categories = categories;
        this.author = author;
    }

    @BindingAdapter("android:loadImage")
    public static void loadImage(ImageView imageView, String cover_photo){
        Picasso.get().load(cover_photo).into(imageView);
    }

    public Boolean ifExist(List<Blog> blogList, Blog blog){
        for (Blog item : blogList) {
            if (item.getId() == blog.getId()){
                return true;
            }
        }
        return false;
    }
}
