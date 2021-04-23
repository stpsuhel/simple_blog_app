package tk.suhel.myblog.component;

import dagger.Component;
import tk.suhel.myblog.model.Blog;

@Component
public interface BlogComponent {
    public Blog getBlog();
}
