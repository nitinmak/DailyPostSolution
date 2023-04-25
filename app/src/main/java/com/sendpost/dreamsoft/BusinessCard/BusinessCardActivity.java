package com.sendpost.dreamsoft.BusinessCard;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ainapage.vr.ImageEditor.FileSaveHelper;

import com.sendpost.dreamsoft.Classes.Functions;
import com.sendpost.dreamsoft.Classes.PermissionUtils;
import com.sendpost.dreamsoft.Fragments.AddBussinessFragment;
import com.sendpost.dreamsoft.NavFragment.PremiumFragment;
import com.sendpost.dreamsoft.R;

import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BusinessCardActivity extends AppCompatActivity {

    DiscreteScrollView recycler;
    LinearLayout mAdView;
    BusinessCardAdapter adapter;
    PermissionUtils takePermissionUtils;
    private FileSaveHelper mSaveFileHelper;
    String fileName;
    ImageView telegram,instagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visting_card);

        recycler = findViewById(R.id.recycler);
        telegram = findViewById(R.id.telegram);
        instagram = findViewById(R.id.instagram);

        recycler.setItemTransitionTimeMillis(150);
        recycler.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());
        adapter = new BusinessCardAdapter(this);

        recycler.setOffscreenItems(10);
        recycler.setAdapter(adapter);
        takePermissionUtils=new PermissionUtils(this,mPermissionResult);
        mSaveFileHelper = new FileSaveHelper(this);

        telegram.setOnClickListener(view -> {
            createBussinessCard("telegram");
        });
        instagram.setOnClickListener(view -> {
            createBussinessCard("instagram");
        });

//        findViewById(R.id.premium_tag).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showPremiumFragment();
//            }
//        });

        recycler.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            @Override
            public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {
                if (adapter.premiumlist.get(adapterPosition).equals(1)){
                    if (Functions.IsPremiumEnable(BusinessCardActivity.this)){
//                        findViewById(R.id.premium_tag).setVisibility(View.GONE);
                    }else {
//                        findViewById(R.id.premium_tag).setVisibility(View.VISIBLE);
                    }
                }else {
//                    findViewById(R.id.premium_tag).setVisibility(View.GONE);
                }
            }
        });
    }

    private void showPremiumFragment() {
        PremiumFragment comment_f = new PremiumFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.in_from_bottom, R.anim.out_to_top, R.anim.in_from_top, R.anim.out_from_bottom);
        Bundle args = new Bundle();
        args.putString("from", "preview");
        comment_f.setArguments(args);
        transaction.addToBackStack(null);
        transaction.replace(android.R.id.content, comment_f).commit();
    }

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
                            blockPermissionCheck.add(Functions.getPermissionStatus(BusinessCardActivity.this,key));
                        }
                    }
                    if (blockPermissionCheck.contains("blocked"))
                    {
                        Functions.showPermissionSetting(BusinessCardActivity.this,getString(R.string.we_need_storage_and_camera_permission_for_upload_profile_pic));
                    }

                }
            });

    public void whatsapp(View view) {
        createBussinessCard("whatsapp");
    }

    public void facebook(View view) {
        createBussinessCard("facebook");
    }

    public void more(View view) {
        createBussinessCard("more");
    }

    public void save(View view) {
        createBussinessCard("save");
    }

    public void createBussinessCard(String action) {

        if (takePermissionUtils.isStorageCameraPermissionGranted()) {
            Functions.showLoader(this);
            fileName = "Bcard"+System.currentTimeMillis() + ".png";
            mSaveFileHelper.createFile(fileName, new FileSaveHelper.OnFileCreateResult() {
                @Override
                public void onFileCreateResult(boolean created, @Nullable String filePath, @Nullable String error, @Nullable Uri uri) {
                    try {
                        savebitmap(captureView(adapter.getViewByPosition(recycler.getCurrentItem())),filePath,uri,action);
                    } catch (IOException e) {
                        Toast.makeText(BusinessCardActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
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
//        intent.putExtra(Intent.EXTRA_TEXT, Functions.getSharedPreference(this).getString("share_text",""));
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