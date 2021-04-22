package tk.suhel.myblog.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import tk.suhel.myblog.model.Author;

public class AuthorConverter {

    @TypeConverter
    public static String fromAuthorJson(Author author) {
        Gson gson = new Gson();
        return gson.toJson(author);
    }

    @TypeConverter
    public static Author toAuthorList(String value) {
        Type type = new TypeToken<Author>() {}.getType();
        return new Gson().fromJson(value, type);
    }
}
