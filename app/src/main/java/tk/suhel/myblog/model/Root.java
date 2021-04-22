package tk.suhel.myblog.model;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Root {
    private List<Blog> blogs;
}


