package com.sendpost.dreamsoft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import retrofit2.Call;
import com.sendpost.dreamsoft.Classes.Constants;
import com.sendpost.dreamsoft.adapter.UserPostsAdapter;
import com.sendpost.dreamsoft.Classes.Functions;
import com.sendpost.dreamsoft.Classes.Variables;
import com.sendpost.dreamsoft.model.UserPostModel;
import com.sendpost.dreamsoft.network.ApiClient;
import com.sendpost.dreamsoft.network.ApiService;
import com.sendpost.dreamsoft.responses.SimpleResponse;
import com.sendpost.dreamsoft.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class MyPostsActivity extends AppCompatActivity {

    RecyclerView recycler;
    UserViewModel userViewModel;
    List<UserPostModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Functions.setLocale(Functions.getSharedPreference(this).getString(Variables.APP_LANGUAGE_CODE,Variables.DEFAULT_LANGUAGE_CODE), this, MyPostsActivity.class,false);
        setContentView(R.layout.activity_my_posts);

        recycler = findViewById(R.id.recycler);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        userViewModel.getUserPosts(Functions.getUID(this))
                .observe(this, userResponse -> {
            findViewById(R.id.shimmer_lay).setVisibility(View.GONE);
            if (userResponse != null){
                if (userResponse.getUserposts().size() > 0){
                    list.clear();
                    list.addAll(userResponse.userposts);
                    setPostsAdapter();
                }else {
                    findViewById(R.id.no_data_layout).setVisibility(View.VISIBLE);
                }
            }
        });

    }
    UserPostsAdapter adapter;
    private void setPostsAdapter() {
        adapter = new UserPostsAdapter(this, list, (view, pos, object) -> {
            switch (view.getId()){
                case R.id.delete_btn:

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Are you Sure You Want to Delete This Post ?");
                    builder.setPositiveButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.setNegativeButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            deletePost(list.get(pos).getId(),pos);
                        }
                    });

                    builder.create().show();
                    break;
                default:
                    adapter.notifyDataSetChanged();
            }
            });

        recycler.setAdapter(adapter);
    }

    private void deletePost(String id, int pos) {
        ApiClient.getRetrofit().create(ApiService.class).deleteUserPost(Constants.API_KEY,id).enqueue(new retrofit2.Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, retrofit2.Response<SimpleResponse> response) {
                Log.d("dssdfdsfdsf",response.body().getMessage());
                Log.d("dssdfdsfdsf",response.body().getCode()+"");

                if (response.body().code == 200){
                    Functions.showToast(MyPostsActivity.this,getString(R.string.delete_sucsess));
                    list.remove(pos);
//                    adapter.notifyItemRemoved(pos);
                    Log.d("dssdfdsfdsf",pos+"");
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {

            }
        });
    }

    public void finish(View view) {
        finish();
    }
}