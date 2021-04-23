package tk.suhel.myblog.component;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;

import java.util.List;

import dagger.Component;
import tk.suhel.myblog.model.CategoryListForSpinner;

@Component
public interface CategoryListForSpinnerComponent {
    public CategoryListForSpinner getCategoryListForSpinner();
}
