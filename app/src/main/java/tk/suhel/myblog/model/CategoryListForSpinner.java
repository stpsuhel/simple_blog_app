package tk.suhel.myblog.model;

import android.util.Log;

import androidx.databinding.BindingAdapter;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lombok.Data;

public class CategoryListForSpinner{
    private static final String TAG = "CategoryListForSpinner";
    private final List<KeyPairBoolData> categoryList;
    private static List<KeyPairBoolData> alreadySelectedList;
    private static List<String> selectedCategoryList;

    @Inject
    public CategoryListForSpinner() {
        this.categoryList = new ArrayList<>();
        alreadySelectedList = new ArrayList<>();
        selectedCategoryList = new ArrayList<>();
        setCategoryList();
    }

//    public CategoryListForSpinner(List<KeyPairBoolData> categoryList) {
//        this.categoryList = categoryList;
//    }

    public void setCategoryList(){
        categoryList.add(new KeyPairBoolData("Business", false));
        categoryList.add(new KeyPairBoolData("Lifestyle", false));
        categoryList.add(new KeyPairBoolData("Entertainment", false));
        categoryList.add(new KeyPairBoolData("Productivity", false));
    }

    public List<KeyPairBoolData> getCategoryList(){
        return categoryList;
    }

    public void setAlreadySelectedList(List<String> list){
        if (list != null && list.size() > 0) {
            for (String s : list) {
                Log.d(TAG, "setAlreadySelectedList: " + s);
                alreadySelectedList.add(new KeyPairBoolData(s, true));
            }
        }
    }

    public List<String> getSelectedCategoryList(){
        return selectedCategoryList;
    }

    public static Boolean ifExist(List<KeyPairBoolData> alreadySelectedList, String data){
        for (KeyPairBoolData item : alreadySelectedList) {
            if (item.getName().equals(data)){
                return true;
            }
        }
        return false;
    }

    @BindingAdapter("android:setCategoryToSpinner")
    public static void setCategoryToSpinner(MultiSpinnerSearch spinner, List<KeyPairBoolData> list) {
        if(alreadySelectedList.size() > 0){
            for (KeyPairBoolData item: list) {
                if (ifExist(alreadySelectedList, item.getName())){
                    Log.d(TAG, "setCategoryToSpinner: " + item.getName());
                    item.setSelected(true);
                }
            }
        }
        spinner.setItems(list, items -> {
            selectedCategoryList.clear();
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).isSelected()) {
                    Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    selectedCategoryList.add(items.get(i).getName());
                }
            }
        });
        if (selectedCategoryList.isEmpty()){
            for (KeyPairBoolData item : alreadySelectedList) {
                Log.d(TAG, "setCategoryToSpinner: " + item.getName());
                selectedCategoryList.add(item.getName());
            }
        }
    }

    public String listToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String s: list) {
            sb.append(s);
            sb.append(", ");
        }
        sb.setLength(sb.length()-2);
        return sb.toString();
    }
}
