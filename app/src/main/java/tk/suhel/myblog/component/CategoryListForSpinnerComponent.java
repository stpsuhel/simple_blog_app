package tk.suhel.myblog.component;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;

import java.util.List;

import dagger.Component;
import tk.suhel.myblog.model.CategoryListForSpinner;
import tk.suhel.myblog.module.CategoryListForSpinnerModule;

@Component(modules = CategoryListForSpinnerModule.class)
public interface CategoryListForSpinnerComponent {
    public CategoryListForSpinner getCategoryListForSpinner();
}
