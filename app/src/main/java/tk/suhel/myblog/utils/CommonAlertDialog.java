package tk.suhel.myblog.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import lombok.Data;
import lombok.NoArgsConstructor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tk.suhel.myblog.activity.MainActivity;
import tk.suhel.myblog.model.Root;
import tk.suhel.myblog.viewModel.BlogViewModel;

@Data
@NoArgsConstructor
public class CommonAlertDialog {
    private Context context;
    private BlogViewModel blogViewModel;
    private String title;
    private String message;

    public CommonAlertDialog(Context context, BlogViewModel blogViewModel) {
        this.blogViewModel = blogViewModel;
        this.context = context;
    }

    public void getDialogForDataFromApi(){
        title = "Refresh Blog!!";
        message = "Are you sure you want to Reload All Blog?";
        showAlertForDelete(Constant.DialogValue.ApiCall.getValue());
    }

    public void deleteAllBlogs(){
        title = "Delete All Blog!!";
        message = "Are you sure you want to delete All Blog Success Message?";
        showAlertForDelete(Constant.DialogValue.DeleteAll.getValue());
    }

    public void deleteBlogs(int id){
        title = "Delete Blog!!";
        message = "Are you sure you want to delete this Blog?";
        showAlertForDelete(id);
    }

    private void showAlertForDelete(int id){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            if(id == -1){
                                getDataFromApi();
                            } else {
                                if (id == 0 && blogViewModel.deleteAllBlogs().get() > 0) {
                                    Toast.makeText(context, "All Blog Successfully Deleted!", Toast.LENGTH_SHORT).show();
                                } else if (id > 0 && blogViewModel.deleteBlogs(id).get() > 0) {
                                    Toast.makeText(context, "Blog Successfully Deleted!", Toast.LENGTH_SHORT).show();
                                    ((Activity)context).finish();
                                } else {
                                    Toast.makeText(context, "Something Wrong...!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void getDataFromApi(){
        blogViewModel.getAllBlogFromApi().enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                assert response.body() != null;
                blogViewModel.saveAllBlogs(response.body().getBlogs());
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(context, "Error! " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
