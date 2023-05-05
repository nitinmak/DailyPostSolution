package com.sendpost.dreamsoft.NavFragment;

import static com.android.billingclient.api.BillingClient.SkuType.INAPP;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.sendpost.dreamsoft.Classes.Variables;
import com.sendpost.dreamsoft.adapter.SectionAdapter;
import com.sendpost.dreamsoft.adapter.SubscriptionAdapter;
import com.sendpost.dreamsoft.Classes.Constants;
import com.sendpost.dreamsoft.Classes.Functions;
import com.sendpost.dreamsoft.databinding.FragmentPremiumBinding;
import com.sendpost.dreamsoft.dialog.CallBack;
import com.sendpost.dreamsoft.dialog.CustomeDialogFragment;
import com.sendpost.dreamsoft.dialog.DialogType;
import com.sendpost.dreamsoft.dialog.PaymentTypeDialogFragment;
import com.sendpost.dreamsoft.Interface.AdapterClickListener;
import com.sendpost.dreamsoft.model.CategoryModel;
import com.sendpost.dreamsoft.model.PostsModel;
import com.sendpost.dreamsoft.model.SectionModel;
import com.sendpost.dreamsoft.model.SubscriptionModel;
import com.sendpost.dreamsoft.PosterPreviewActivity;
import com.sendpost.dreamsoft.R;
import com.sendpost.dreamsoft.RazorpayActivity;
import com.sendpost.dreamsoft.responses.HomeResponse;
import com.sendpost.dreamsoft.responses.UserResponse;
import com.sendpost.dreamsoft.ImageEditor.viewmodel.HomeViewModel;
import com.sendpost.dreamsoft.ImageEditor.viewmodel.UserViewModel;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PremiumFragment extends Fragment implements PurchasesUpdatedListener {


    public PremiumFragment() {
    }

    FragmentPremiumBinding binding;
    List<SkuDetails> storedList = new ArrayList<>();
    BillingClient billingClient;
    String selected_id = "", selected_sku = "", selected_price = "", selected_name = "",promocode = "";
    Activity context;
    private InfiniteScrollAdapter<?> infiniteAdapter;
    View view;

    UserViewModel userViewModel;
    HomeViewModel homeViewModel;
    SubscriptionAdapter adapter;
    List<SubscriptionModel> plan_list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPremiumBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();

        if (getArguments() != null && getArguments().getString("from").equals("preview")) {
            view.findViewById(R.id.back_btn).setVisibility(View.VISIBLE);
        }
        this.view = view;

        view.findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        adapter = new SubscriptionAdapter(context, plan_list, new AdapterClickListener() {
            @Override
            public void onItemClick(View view, int pos, Object object) {
                SubscriptionModel model = (SubscriptionModel) object;
                selected_price = model.price;
                selected_name = model.name;
                selected_sku = model.purchase;
                selected_id = model.id;
                startPayment(Integer.parseInt(selected_price));
            }
        });
        infiniteAdapter = InfiniteScrollAdapter.wrap(adapter);
        binding.recycler.setAdapter(infiniteAdapter);
        binding.recycler.setItemTransitionTimeMillis(150);
        binding.recycler.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());
        binding.mainLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getSubscriptions().observe(getViewLifecycleOwner(), new Observer<HomeResponse>() {
            @Override
            public void onChanged(HomeResponse homeResponse) {
                if (homeResponse != null){
                    if (homeResponse.subscriptions.size() > 0){
                        plan_list.addAll(homeResponse.subscriptions);
                        adapter.notifyDataSetChanged();
                        initalizeBill();
                    }
                }
            }
        });
        updateUI();

        binding.festivalBtn.setActivated(true);
        binding.festivalBtn.setOnClickListener(View -> {
            binding.festivalBtn.setActivated(true);
            binding.bussinessBtn.setActivated(false);
            binding.videoBtn.setActivated(false);
            binding.customBtn.setActivated(false);
            getPremiumItems("festival");
        });
        binding.bussinessBtn.setOnClickListener(View -> {
            binding.festivalBtn.setActivated(false);
            binding.bussinessBtn.setActivated(true);
            binding.videoBtn.setActivated(false);
            binding.customBtn.setActivated(false);
            getPremiumItems("business");
        });
        binding.customBtn.setOnClickListener(View -> {
            binding.festivalBtn.setActivated(false);
            binding.customBtn.setActivated(true);
            binding.bussinessBtn.setActivated(false);
            binding.videoBtn.setActivated(false);
            getPremiumItems("custom");
        });
        binding.videoBtn.setOnClickListener(View -> {
            binding.festivalBtn.setActivated(false);
            binding.bussinessBtn.setActivated(false);
            binding.customBtn.setActivated(false);
            binding.videoBtn.setActivated(true);
            getPremiumItems("video");
        });

    }

    public void initalizeBill() {
        billingClient = BillingClient.newBuilder(getContext()).enablePendingPurchases().setListener(this).build();
        Log.d(Constants.tag, "Billing : connection establish ");
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                Log.d(Constants.tag, "Billing : onBillingSetupFinished ");
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    Log.d(Constants.tag, "Billing : Load purchase query " + billingResult.getResponseCode());
                    InitPurchases();
                } else {
                    Log.d(Constants.tag, "Billing : onBillingSetupFinished " + billingResult.getResponseCode());
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                Log.d(Constants.tag, "Billing : onBillingServiceDisconnected");
            }
        });

    }

    private void InitPurchases() {
        try {
            Log.d(Constants.tag, "Billing : InitPurchases ");

            final List<String> skuList = new ArrayList<>();
            for (SubscriptionModel model : plan_list) {
                skuList.add(model.purchase);
            }
            SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
            params.setSkusList(skuList);
            params.setType(INAPP);
            billingClient.querySkuDetailsAsync(params.build()
                    , new SkuDetailsResponseListener() {
                        @Override
                        public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> list) {
                            storedList.clear();
                            storedList.addAll(list);
                            Log.d(Constants.tag, "Billing : onSkuDetailsResponse ");
                        }
                    });
        } catch (Exception e) {
            Log.d("wallet", "" + e.getMessage());
        }
    }

    private void startPayment(int price) {
        selected_price = String.valueOf(price);
        new PaymentTypeDialogFragment(new CallBack() {
            @Override
            public void getResponse(String requestType,String promo, int discount) {
                if (discount > 0){
                    int dprice = (price*discount) / 100;
                    selected_price = String.valueOf(price-dprice);
                    promocode = promo;
                }
                if (requestType.equals("google")) {
                    purchaseSubscription();
                } else {
                    Intent intent = new Intent(context, RazorpayActivity.class);
                    intent.putExtra("price", selected_price);
                    resultCallbackForPayment.launch(intent);
                }
            }
        }).show(getChildFragmentManager(), "");
    }

    ActivityResultLauncher<Intent> resultCallbackForPayment = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        updateSubscription("Google", data.getStringExtra("transaction"));
                        Toast.makeText(context, "Payment Success", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    public void purchaseSubscription() {
        try {
            if (billingClient.isReady()) {
                for (SkuDetails detail : storedList) {
                    Log.d(Constants.tag, "Billing : " + detail.getSku());
                    if (detail.getSku().equals(selected_sku)) {
                        BillingFlowParams flowParams = BillingFlowParams.newBuilder().setSkuDetails(detail).build();
                        billingClient.launchBillingFlow(getActivity(), flowParams);
                    }
                }
            } else {
                Log.d(Constants.tag, "Billing : purchaseSubscription ");
            }
        } catch (Exception e) {
            Log.d(Constants.tag, "Billing : Exception " + e.getMessage());
        }
    }

    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> purchases) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
            handlePurchases(purchases);
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            Toast.makeText(context, R.string.payment_cencelled, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "" + billingResult.getDebugMessage(), Toast.LENGTH_SHORT).show();
            ;
        }
    }

    private void handlePurchases(List<Purchase> purchases) {
        for (Purchase skuList : purchases) {
            if (!skuList.isAcknowledged()) {

                ConsumeParams consumeParams = ConsumeParams.newBuilder().setPurchaseToken(skuList.getPurchaseToken()).build();

                ConsumeResponseListener listener = new ConsumeResponseListener() {
                    @Override
                    public void onConsumeResponse(BillingResult billingResult, String purchaseToken) {
                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                            updateSubscription("Google", purchaseToken);
                        }
                    }
                };
                billingClient.consumeAsync(consumeParams, listener);
            }
        }
    }

    private void updateSubscription(String type, String tid) {
        Functions.showLoader(getContext());
        userViewModel.updateUserSubscription(Functions.getUID(context),type,selected_id,tid,promocode).observe(this, new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse userResponse) {
                Functions.cancelLoader();
                if (userResponse != null){
                    if (userResponse.code == Constants.SUCCESS){
                        Functions.saveUserData(userResponse.getUserModel(),context);
                        updateUI();
                        new CustomeDialogFragment(
                                getString(R.string.sucsess),
                                userResponse.message,
                                DialogType.SUCCESS,
                                false,
                                false,
                                false,
                                new CustomeDialogFragment.DialogCallback() {
                                    @Override
                                    public void onCencel() {

                                    }
                                    @Override
                                    public void onSubmit() {

                                    }
                                    @Override
                                    public void onDismiss() {

                                    }
                                    @Override
                                    public void onComplete(Dialog dialog) {
                                        dialog.dismiss();
                                    }
                                }
                        ).show(getChildFragmentManager(),"");
                    }else{
                        new CustomeDialogFragment(
                                getString(R.string.sucsess),
                                userResponse.message,
                                DialogType.ERROR,
                                true,
                                false,
                                true,
                                new CustomeDialogFragment.DialogCallback() {
                                    @Override
                                    public void onCencel() {

                                    }
                                    @Override
                                    public void onSubmit() {
                                        updateSubscription(type,tid);
                                    }
                                    @Override
                                    public void onDismiss() {

                                    }
                                    @Override
                                    public void onComplete(Dialog dialog) {

                                    }
                                }
                        ).show(getChildFragmentManager(),"");
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        if (Functions.IsPremiumEnable(getContext())) {
            if (sectionList.isEmpty()) {
                getPremiumItems("festival");
            }
            view.findViewById(R.id.subs_option_lay).setVisibility(View.GONE);
            view.findViewById(R.id.subs_activate_lay).setVisibility(View.VISIBLE);
            binding.nameTv.setText("Plan - "+Functions.getSharedPreference(context).getString(Variables.SUB_NAME,""));
            binding.startDate.setText(Functions.getSharedPreference(context).getString(Variables.SUB_DATE,""));
            binding.endDate.setText(Functions.getSharedPreference(context).getString(Variables.SUB_END_DATE,""));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            try {
                Date startDate = df.parse(Functions.getSharedPreference(context).getString(Variables.SUB_DATE,""));
                Date endDate = df.parse(Functions.getSharedPreference(context).getString(Variables.SUB_END_DATE,""));
                binding.durationProgress.setProgress(Functions.getSubsIntervel(startDate,endDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }


    ArrayList<SectionModel> sectionList = new ArrayList<>();
    private void getPremiumItems(String type) {
        sectionList.clear();
        binding.progressBar.setVisibility(View.VISIBLE);
        homeViewModel.getPremiumPostByCategory(type).observe(getViewLifecycleOwner(), new Observer<HomeResponse>() {
            @Override
            public void onChanged(HomeResponse homeResponse) {
                binding.progressBar.setVisibility(View.GONE);
                if (homeResponse != null){
                    if (homeResponse.categories.size() > 0){
                        for (int i=0;i < homeResponse.categories.size();i++){
                            CategoryModel categoryModel = homeResponse.categories.get(i);
                            SectionModel sectionModel = new SectionModel();
                            sectionModel.setId(categoryModel.getId());
                            sectionModel.setName(categoryModel.getName());
                            sectionModel.setPosts(categoryModel.getPremiumposts());
                            sectionModel.setStatus(categoryModel.getStatus());
                            sectionList.add(sectionModel);
                        }
                        setSectionAdapter();
                    }
                }
            }
        });

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setSectionAdapter() {
        binding.rvPremiumSection.setAdapter(new SectionAdapter(context, sectionList, new SectionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, List<PostsModel> list, int main_position, int child_position) {
                PosterPreviewActivity.postsModel = list.get(child_position);
                startActivity(new Intent(context, PosterPreviewActivity.class)
                        .putExtra("type", "Posts"));
            }
        }, false, false));
    }

}