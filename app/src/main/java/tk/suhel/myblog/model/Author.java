package tk.suhel.myblog.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author implements Serializable {
    private int id;
    private String name;
    private String avatar;
    private String profession;
}
