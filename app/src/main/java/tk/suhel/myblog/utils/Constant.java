package tk.suhel.myblog.utils;

public class Constant {
    public static final String BASE_URL = "https://my-json-server.typicode.com/sohel-cse/simple-blog-api/";
    public static final String CURRENT_BLOG_ID = "current_blog_id";

    public enum DialogValue{
        ApiCall(-1),
        DeleteAll(0);

        private int value;

        DialogValue(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
