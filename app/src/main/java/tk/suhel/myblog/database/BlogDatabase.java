package tk.suhel.myblog.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
//                            .addCallback(getCallBack)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

//    private static RoomDatabase.Callback getCallBack = new RoomDatabase.Callback(){
//        @Override
//        public void onOpen(@NonNull SupportSQLiteDatabase db) {
//            super.onOpen(db);
//            new GeneratesDbData(INSTANCE).execute();
//        }
//    };

//    private static class GeneratesDbData extends AsyncTask<Void, Void, Void> {
//        private final BlogDAO dao;
//        public GeneratesDbData(BlogDatabase instance) {
//            dao = instance.getBlogDAO();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
////            Blog blog = new Blog(0, "Blog 1", "Description 1", "https://i.picsum.photos/id/579/200/300.jpg?hmac=9MD8EV4Jl9EqKLkTj5kyNdBUKQWyHk2m4pE4UCBGc8Q");
////            dao.deleteBlogs();
//            return null;
//        }
//    }
}
