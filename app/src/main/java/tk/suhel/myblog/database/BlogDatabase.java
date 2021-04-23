package tk.suhel.myblog.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import javax.inject.Inject;

import tk.suhel.myblog.converter.AuthorConverter;
import tk.suhel.myblog.converter.StringConverter;
import tk.suhel.myblog.dao.BlogDAO;
import tk.suhel.myblog.model.Blog;

@Database(entities = {Blog.class}, version = 1)
@TypeConverters({StringConverter.class, AuthorConverter.class})
public abstract class BlogDatabase extends RoomDatabase {
    private static volatile BlogDatabase INSTANCE;

    public abstract BlogDAO getBlogDAO();

    public static BlogDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (BlogDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext(), BlogDatabase.class, "simple_blog_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
