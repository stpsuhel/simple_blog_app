package tk.suhel.myblog.converter;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringConverter {
    @TypeConverter
    public static List<String> fromString(String value) {
        String[] split = value.split(",");
        return new ArrayList<>(Arrays.asList(split));
    }

    @TypeConverter
    public static String toString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String s: list) {
            sb.append(s);
            sb.append(",");
        }
        sb.setLength(sb.length()-1);
        return sb.toString();
    }
}
