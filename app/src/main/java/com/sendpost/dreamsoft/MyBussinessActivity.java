package com.sendpost.dreamsoft;

import static com.sendpost.dreamsoft.Classes.Constants.SUCCESS;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sendpost.dreamsoft.Classes.Constants;

import com.sendpost.dreamsoft.R;

import com.sendpost.dreamsoft.adapter.BussinessAdapter;
import com.sendpost.dreamsoft.Classes.Functions;
import com.sendpost.dreamsoft.Classes.Variables;
import com.sendpost.dreamsoft.databinding.ActivityMyBussinessBinding;
import com.sendpost.dreamsoft.model.BussinessModel;
import com.sendpost.dreamsoft.network.ApiClient;
import com.sendpost.dreamsoft.network.ApiService;
import com.sendpost.dreamsoft.responses.SimpleResponse;
import com.sendpost.dreamsoft.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class MyBussinessActivity extends AppCompatActivity {

    List<BussinessModel> list = new ArrayList<>();
    ActivityMyBussinessBinding binding;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Functions.setLocale(Functions.getSharedPreference(this).getString(Variables.APP_LANGUAGE_CODE,Variables.DEFAULT_LANGUAGE_CODE), this, MyBussinessActivity.class,false);
        binding = ActivityMyBussinessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        binding.refereshLay.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMyBussiness();
            }
        });
    }

    private void getMyBussiness() {
        userViewModel.getUserBusiness(Functions.getUID(this)).observe(this, userResponse -> {
            binding.refereshLay.setRefreshing(false);
            binding.shimmerLay.stopShimmer();
            binding.shimmerLay.setVisibility(View.GONE);
            if (userResponse != null){
                if (userResponse.code == SUCCESS){
                    if (userResponse.getBusinesses().size() > 0){
                        list.clear();
                        list.addAll(userResponse.getBusinesses());
//                            setAdapter();
//                            editBussiness(list.get(0).getId());
                        binding.noDataLayout.setVisibility(View.GONE);
                    }else {
                        binding.noDataLayout.setVisibility(View.VISIBLE);
                    }
                }else {
                    Functions.showToast(MyBussinessActivity.this,userResponse.message);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyBussiness();
    }

    BussinessAdapter adapter;
    private void setAdapter() {
        adapter = new BussinessAdapter(this, list, (view, pos, object) -> {
            BussinessModel model = (BussinessModel) object;
            switch (view.getId()){
                case R.id.edit_btn:
//                        editBussiness(model.getId());
                    break;
                case R.id.delete_btn:
                    deleteBussiness(model.getId(),pos);
                    break;
                default:
                    Functions.saveBussinessData(model,MyBussinessActivity.this);
                    adapter.notifyDataSetChanged();
            }
        });
        binding.recyclerView.setAdapter(adapter);
    }

    private void deleteBussiness(String id, int pos) {
        ApiClient.getRetrofit().create(ApiService.class).deleteUserBusiness(Constants.API_KEY,id).enqueue(new retrofit2.Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, retrofit2.Response<SimpleResponse> response) {
                if (response.body().code == 200){
                    Functions.showToast(MyBussinessActivity.this,getString(R.string.delete_sucsess));
                    list.remove(pos);
                    adapter.notifyItemRemoved(pos);
                }
            }
            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {

            }
        });
    }
//
//    private void editBussiness(String id) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.setCustomAnimations(R.anim.in_from_bottom, R.anim.out_to_top, R.anim.in_from_top, R.anim.out_from_bottom);
//        transaction.addToBackStack(null);
//        transaction.replace(R.id.main_lay, new AddBussinessFragment(id)).commit();
//    }
//
//    public void addNew(View view) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.setCustomAnimations(R.anim.in_from_bottom, R.anim.out_to_top, R.anim.in_from_top, R.anim.out_from_bottom);
//        transaction.addToBackStack(null);
//        transaction.replace(R.id.main_lay, new AddBussinessFragment("")).commit();
//    }

    public void finish(View view) {
        finish();
    }

}