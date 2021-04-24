package tk.suhel.myblog.module;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import tk.suhel.myblog.model.CategoryListForSpinner;

@Module
public class CategoryListForSpinnerModule {
    private List<KeyPairBoolData> categoryList;
    private List<KeyPairBoolData> alreadySelectedList;
    private List<String> selectedCategoryList;

    public CategoryListForSpinnerModule() {
        this.categoryList = new ArrayList<>();
        this.alreadySelectedList = new ArrayList<>();
        this.selectedCategoryList = new ArrayList<>();
    }

    @Provides
    public CategoryListForSpinner provideCategoryListForSpinner(){
        return new CategoryListForSpinner(categoryList, alreadySelectedList, selectedCategoryList);
    }

}
