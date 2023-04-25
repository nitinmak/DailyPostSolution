package com.sendpost.dreamsoft.BusinessCard;

import static com.sendpost.dreamsoft.Classes.Functions.getSharedPreference;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.ainapage.vr.ImageEditor.FileSaveHelper;
import retrofit2.Call;
import com.sendpost.dreamsoft.Classes.Constants;
import com.sendpost.dreamsoft.Classes.Functions;
import com.sendpost.dreamsoft.Classes.PermissionUtils;
import com.sendpost.dreamsoft.Classes.Variables;
import com.sendpost.dreamsoft.Fragments.AddBussinessFragment;
import com.sendpost.dreamsoft.Interface.AdapterClickListener;
import com.sendpost.dreamsoft.R;
import com.sendpost.dreamsoft.adapter.BusinessCardDigitalAdapter;
import com.sendpost.dreamsoft.adapter.BusinessCardTamplateAdapter;

import com.sendpost.dreamsoft.databinding.ActivityBusinessCardBinding;
import com.sendpost.dreamsoft.model.BusinessCardModel;
import com.sendpost.dreamsoft.network.ApiClient;
import com.sendpost.dreamsoft.network.ApiService;
import com.sendpost.dreamsoft.responses.HomeResponse;
import com.sendpost.dreamsoft.responses.SimpleResponse;
import com.sendpost.dreamsoft.viewmodel.HomeViewModel;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BusinessCardActivity_Digital extends AppCompatActivity {

//    BusinessCardAdapter adapter;
    PermissionUtils takePermissionUtils;
    private FileSaveHelper mSaveFileHelper;
    String fileName;
    ImageView telegram,instagram;
    PermissionUtils takePermissionUtilsd;
    private FileSaveHelper mSaveFileHelperd;
    ActivityBusinessCardBinding binding;
    HomeViewModel homeViewModel;
    BusinessCardModel model;
    RecyclerView recycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBusinessCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//      setContentView(R.layout.activity_business_card);

        recycler = findViewById(R.id.digital_recycler);

//      telegram = findViewById(R.id.telegram);
//      instagram = findViewById(R.id.instagram);

//      binding.digitalRecycler.setItemTransitionTimeMillis(150);
//      binding.digitalRecycler.setItemTransformer(new ScaleTransformer.Builder()
//            .setMinScale(0.8f)
//            .build());


        GridLayoutManager manager = new GridLayoutManager(BusinessCardActivity_Digital.this,3);
        recycler.setLayoutManager(manager);
//        binding.digitalRecycler.setOffscreenItems(10);
//        binding.digitalRecycler.setAdapter(adapter);

        takePermissionUtils=new PermissionUtils(this,mPermissionResult);
        mSaveFileHelper = new FileSaveHelper(this);

//        binding.telegram.setOnClickListener(view -> {
//            createBussinessCard("telegram");
//        });
//        binding.instagram.setOnClickListener(view -> {
//            createBussinessCard("instagram");
//        });

//        findViewById(R.id.premium_tag).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showPremiumFragment();
//            }
//        });

        takePermissionUtils = new PermissionUtils(this, mPermissionResult);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getBusinessCards().observe(this, new Observer<HomeResponse>() {

            @Override
            public void onChanged(HomeResponse homeResponse) {

                if (homeResponse != null && homeResponse.getBusinesscardtamplate() != null){
                    binding.digitalRecycler.setAdapter(new BusinessCardTamplateAdapter(BusinessCardActivity_Digital.this,homeResponse.getBusinesscardtamplate()));
                }

                if (homeResponse != null && homeResponse.getBusinesscarddigital() != null){
                    binding.digitalRecycler.setAdapter(new BusinessCardDigitalAdapter(BusinessCardActivity_Digital.this, homeResponse.getBusinesscarddigital(), new AdapterClickListener() {
                        @Override
                        public void onItemClick(View view, int pos, Object object) {
                            model = (BusinessCardModel) object;

                                if (!getSharedPreference(getApplicationContext()).getString(Variables.BUSSINESS_NAME,"").equals("")){
                                    createBusinessCard(model);
                                }else{
                                    Functions.showToast(BusinessCardActivity_Digital.this,getString(R.string.please_select_bussiness));
                                }

                        }
                    }));
                }
            }
        });

//        binding.digitalRecycler.addOnItemChangedListener((viewHolder, adapterPosition) -> {
//            if (adapter.premiumlist.get(adapterPosition).equals(1)){
//                if (Functions.IsPremiumEnable(BusinessCardActivity.this)){
//                        findViewById(R.id.premium_tag).setVisibility(View.GONE);
//                }else {
//                        findViewById(R.id.premium_tag).setVisibility(View.VISIBLE);
//                }
//            }else {
//                    findViewById(R.id.premium_tag).setVisibility(View.GONE);
//            }
//        });

    }

    private void createBusinessCard(BusinessCardModel model) {
        Log.d("xxxxxx",Constants.API_KEY);
        Log.d("xxxxxx",model.getId());
        Log.d("xxxxxx",Functions.getSharedPreference(BusinessCardActivity_Digital.this)
                .getString(Variables.BUSSINESS_ID,""));
        Functions.showLoader(BusinessCardActivity_Digital.this);

        ApiClient.getRetrofit().create(ApiService.class)
                .createBusinessCard(
                        Constants.API_KEY,
                        model.getId(),
                        Functions.getSharedPreference(BusinessCardActivity_Digital.this)
                                .getString(Variables.BUSSINESS_ID,""))
                .enqueue(new retrofit2.Callback<SimpleResponse>() {

            @Override
            public void onResponse(Call<SimpleResponse> call, retrofit2.Response<SimpleResponse> response) {
                Functions.cancelLoader();
                Log.d("fvjfbnjdnbjgb",response.toString());
                assert response.body() != null;
                if (response.body().code == 200){
                    if (takePermissionUtils.isStorageCameraPermissionGranted()) {
                        DownloadPDF(response.body().message);
                    } else {
                        takePermissionUtils.takeStorageCameraPermission();
                    }
                }else {
                    Functions.showToast(BusinessCardActivity_Digital.this,response.body().message);
                }
            }
            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {

            }
        });
    }


    private void DownloadPDF(String message) {

        Functions.showLoader(BusinessCardActivity_Digital.this);

        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
        String filename = getSharedPreference(BusinessCardActivity_Digital.this).getString(Variables.BUSSINESS_NAME,"b_")+System.currentTimeMillis()+".pdf";
        AndroidNetworking.download(message, directory.getAbsolutePath(), filename).build().startDownload(new DownloadListener() {
            public void onDownloadComplete() {
                Functions.showToast(BusinessCardActivity_Digital.this,getString(R.string.download_complete));
                Functions.cancelLoader();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(message)));
//              startActivity(new Intent(context, WebviewA.class).putExtra("url","https://docs.google.com/viewer?embedded=true&url="+message).putExtra("title","Business Card"));
            }

            public void onError(ANError aNError) {
                Functions.cancelLoader();
                Functions.showToast(BusinessCardActivity_Digital.this,aNError.getErrorDetail());
            }
        });
    }

//    private void showPremiumFragment() {
//        PremiumFragment comment_f = new PremiumFragment();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.setCustomAnimations(R.anim.in_from_bottom, R.anim.out_to_top, R.anim.in_from_top, R.anim.out_from_bottom);
//        Bundle args = new Bundle();
//        args.putString("from", "preview");
//        comment_f.setArguments(args);
//        transaction.addToBackStack(null);
//        transaction.replace(android.R.id.content, comment_f).commit();
//    }

    public void finish(View view) {
        finish();
    }

    public void openBusinessActivity(View view) {
        startActivity(new Intent(this, AddBussinessFragment.class));
//        startActivity(new Intent(this, MyBussinessActivity.class));
    }

    private ActivityResultLauncher<String[]> mPermissionResult = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
                @Override
                public void onActivityResult(Map<String, Boolean> result) {

                    boolean allPermissionClear=true;
                    List<String> blockPermissionCheck=new ArrayList<>();
                    for (String key : result.keySet())
                    {
                        if (Boolean.FALSE.equals(result.get(key)))
                        {
                            allPermissionClear=false;
                            blockPermissionCheck.add(Functions.getPermissionStatus(BusinessCardActivity_Digital.this,key));
                        }
                    }
                    if (blockPermissionCheck.contains("blocked"))
                    {
                        Functions.showPermissionSetting(BusinessCardActivity_Digital.this,getString(R.string.we_need_storage_and_camera_permission_for_upload_profile_pic));
                    }

                }
            });

//    public void whatsapp(View view) {
//        createBussinessCard("whatsapp");
//    }

//    public void facebook(View view) {
//        createBussinessCard("facebook");
//    }

//    public void more(View view) {
//        createBussinessCard("more");
//    }


    public void save(View view) {
        createBusinessCard(model);
    }

    public void createBussinessCardx(String action) {

        if (takePermissionUtils.isStorageCameraPermissionGranted()) {
            Functions.showLoader(this);
            fileName = "Bcard"+System.currentTimeMillis() + ".png";
            mSaveFileHelper.createFile(fileName, new FileSaveHelper.OnFileCreateResult() {
                @Override
                public void onFileCreateResult(boolean created, @Nullable String filePath, @Nullable String error, @Nullable Uri uri) {
//                    try {
//                        savebitmap(captureView(adapter.getViewByPosition(binding.digitalRecycler.getCurrentItem())),filePath,uri,action);
//                    } catch (IOException e) {
//                        Toast.makeText(BusinessCardActivity_Digital.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
                }
            });
        } else {
            takePermissionUtils.showStorageCameraPermissionDailog(getString(R.string.we_need_storage_and_camera_permission_for_upload_profile_pic));
        }
    }

    public void createBussinessCardsx(String action) {

        if (takePermissionUtils.isStorageCameraPermissionGranted()) {
            Functions.showLoader(this);
            fileName = "Bcard"+System.currentTimeMillis() + ".png";
            mSaveFileHelper.createFile(fileName, (created, filePath, error, uri) -> {
//                try {
//                    savebitmap(captureView(adapter.getViewByPosition(binding.digitalRecycler.getCurrentItem())),filePath,uri,action);
//                } catch (IOException e) {
//                    Toast.makeText(BusinessCardActivity_Digital.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
            });
        } else {
            takePermissionUtils.showStorageCameraPermissionDailog(getString(R.string.we_need_storage_and_camera_permission_for_upload_profile_pic));
        }
    }

    private Bitmap captureView(View view) {
        if (view == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(
                view.getWidth(),
                view.getHeight(),
                Bitmap.Config.ARGB_8888
        );

        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);
        view.draw(canvas);

        return bitmap;
    }

    public void savebitmap(Bitmap bmp, String filePath,Uri uri, String action) throws IOException {
        FileOutputStream out = new FileOutputStream(filePath);
        if (bmp != null) {
            bmp.compress(Bitmap.CompressFormat.PNG, 99, out);
        }
        out.flush();
        out.close();
        Functions.cancelLoader();
        mSaveFileHelper.notifyThatFileIsNowPubliclyAvailable(getContentResolver());


        if (action.equals("save")){
            Functions.showToast(this,getString(R.string.business_card_saved));
            return;
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.putExtra(Intent.EXTRA_TEXT, getSharedPreference(this).getString("share_text",""));
        if (action.equals("whatsapp")) {
            intent.setPackage("com.whatsapp");
        } else if (action.equals("facebook")) {
            intent.setPackage("com.facebook.katana");
        }else if (action.equals("instagram")) {
            intent.setPackage("com.instagram.android");
        } else if (action.equals("telegram")) {
            intent.setPackage("org.telegram.messenger");
        }
        try {
            startActivity(Intent.createChooser(intent, "Share Image Via"));
        } catch (Exception e) {
            Toast.makeText(this, action+" Not Installed ", Toast.LENGTH_SHORT).show();
        }

    }

}
