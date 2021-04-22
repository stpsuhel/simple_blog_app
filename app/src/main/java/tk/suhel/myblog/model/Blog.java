package tk.suhel.myblog.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

    public String getStringFromList(List<String> categories){
        StringBuilder sb = new StringBuilder();
        for (String s: categories) {
            sb.append(s);
            sb.append(",");
        }
        sb.setLength(sb.length()-1);
        return sb.toString();
    }
}
