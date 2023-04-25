package com.sendpost.dreamsoft.Fragments;

import static com.sendpost.dreamsoft.Classes.Constants.SUCCESS;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;

import com.sendpost.dreamsoft.MyBussinessActivity;
import com.sendpost.dreamsoft.binding.BindingAdaptet;
import com.sendpost.dreamsoft.databinding.ActivityMyBussinessBinding;
import com.sendpost.dreamsoft.databinding.FragmentAddBussinessBinding;
import com.sendpost.dreamsoft.dialog.CustomeDialogFragment;
import com.sendpost.dreamsoft.dialog.DialogType;
import com.sendpost.dreamsoft.dialog.PickFromFragment;
import com.sendpost.dreamsoft.dialog.PickedImageActionFragment;
import com.sendpost.dreamsoft.responses.UserResponse;
import com.sendpost.dreamsoft.viewmodel.UserViewModel;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.sendpost.dreamsoft.Classes.Functions;
import com.sendpost.dreamsoft.Classes.PermissionUtils;
import com.sendpost.dreamsoft.Classes.Variables;
import com.sendpost.dreamsoft.model.BussinessModel;
import com.sendpost.dreamsoft.R;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sendpost.dreamsoft.Classes.Constants;

public class AddBussinessFragment extends AppCompatActivity {

    String bussiness_id = "";

//    public AddBussinessFragment(String bussiness_id) {
//        this.bussiness_id = bussiness_id;
//    }

    PermissionUtils takePermissionUtils;
    SharedPreferences sharedPreferences;
    FragmentAddBussinessBinding binding;
    UserViewModel userViewModel;
    Context context;


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Functions.setLocale(Functions.getSharedPreference(this).getString(Variables.APP_LANGUAGE_CODE,Variables.DEFAULT_LANGUAGE_CODE), this, MyBussinessActivity.class,false);
//        binding = FragmentAddBussinessBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
//
//        getBussinessDetails();
//
//    }
//

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        binding = FragmentAddBussinessBinding.inflate(getLayoutInflater());
//        return binding.getRoot();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Functions.setLocale(Functions.getSharedPreference(this).getString(Variables.APP_LANGUAGE_CODE,Variables.DEFAULT_LANGUAGE_CODE), this, MyBussinessActivity.class,false);
        binding = FragmentAddBussinessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = AddBussinessFragment.this;
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        sharedPreferences = context.getSharedPreferences(Variables.PREF_NAME, MODE_PRIVATE);
        getMyBussiness();
//        if (!Objects.equals(bussiness_id, "")){
//            binding.titleTv.setText(getString(R.string.edit_bussiness));

//        }else {
//            binding.titleTv.setText(getString(R.string.add_bussiness));
//        }
//        getBussinessDetails();
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });











        takePermissionUtils=new PermissionUtils(AddBussinessFragment.this,mPermissionResult);
        binding.uploadPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (takePermissionUtils.isStorageCameraPermissionGranted()) {
                    selectImage();
                } else {
                    takePermissionUtils.showStorageCameraPermissionDailog(getString(R.string.we_need_storage_and_camera_permission_for_upload_profile_pic));
                }
            }
        });
        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitClick();
            }
        });
    }

    private void getMyBussiness() {
        userViewModel.getUserBusiness(Functions.getUID(this)).observe(this, new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse userResponse) {

                if (userResponse != null){
                    if (userResponse.code == SUCCESS){
                        if (userResponse.getBusinesses().size() > 0){
                            bussiness_id = userResponse.getBusinesses().get(0).getId();
                            getBussinessDetails();
                        }else {
                        }
                    }else {
                        Functions.showToast(AddBussinessFragment.this,userResponse.message);
                    }
                }
            }
        });
    }

    private void getBussinessDetails() {
        Functions.showLoader(AddBussinessFragment.this);
        userViewModel.getBusinessDetail(Functions.getUID(context), bussiness_id).observe(AddBussinessFragment.this,
                (Observer<UserResponse>) userResponse -> {
                    Functions.cancelLoader();
                    if (userResponse != null) {
                        if (userResponse.code == Constants.SUCCESS) {

                            setData(userResponse.getBusiness());
                        } else {
                            new CustomeDialogFragment(getString(R.string.alert), userResponse.message, DialogType.ERROR, true, false, true,
                                    new CustomeDialogFragment.DialogCallback() {
                                        @Override
                                        public void onCencel() {
                                        }

                                        @Override
                                        public void onSubmit() {
                                            getBussinessDetails();
                                        }

                                        @Override
                                        public void onDismiss() {
                                        }

                                        @Override
                                        public void onComplete(Dialog dialog) {
                                        }
                                    }
                            ).show(getSupportFragmentManager(), "");
                        }
                    }
                });
    }

    private void setData(BussinessModel business) {
        try {
            BindingAdaptet.setImageUrl(binding.bussinessPic,business.getImage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        binding.companyEdit.setText(business.getName());
        binding.numberEdit.setText(business.getNumber());
        binding.emailEdit.setText(business.getEmail());
        binding.addressEdit.setText(business.getAddress());
        binding.websiteName.setText(business.getWebsite());
        binding.youtubeEdit.setText(business.getAddress());
        binding.youtubeEdit.setText(business.getYoutube());
        binding.instaEdit.setText(business.getInstagram());
        binding.aboutEdit.setText(business.getAbout());
        binding.whatsappEdit.setText(business.getWhatsapp());
        binding.facebookEdit.setText(business.getFacebook());
    }

    private void selectImage() {
        new PickFromFragment(getString(R.string.add_photo_), new PickFromFragment.DialogCallback() {
            @Override
            public void onCencel() {
            }
            @Override
            public void onGallery() {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                resultCallbackForGallery.launch(intent);
            }
            @Override
            public void onCamera() {
                openCameraIntent();
            }
        }).show(getSupportFragmentManager(),"");
    }

//    private void selectImage() {
//        new PickFromFragment(getString(R.string.add_photo_), new PickFromFragment.DialogCallback() {
//            @Override
//            public void onCencel() {
//            }
//            @Override
//            public void onGallery() {
//                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                resultCallbackForGallery.launch(intent);
//            }
//            @Override
//            public void onCamera() {
//                openCameraIntent();
//            }
//        }).show(getChildFragmentManager(),"");
//    }

    String imageFilePath;
    private void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(context.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = Functions.createImageFile(context);
                imageFilePath = photoFile.getAbsolutePath();
            } catch (Exception ex) {
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                resultCallbackForCamera.launch(pictureIntent);
            }
        }
    }

    ActivityResultLauncher<Intent> resultCallbackForGallery = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        Uri selectedImage = data.getData();
                        beginCrop(selectedImage);

                    }
                }
            });

    ActivityResultLauncher<Intent> resultCallbackForCamera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Uri selectedImage = (Uri.fromFile(new File(imageFilePath)));
                        beginCrop(selectedImage);
                    }
                }
            });

    private void beginCrop(Uri source) {
        Intent intent= CropImage.activity(source).setCropShape(CropImageView.CropShape.RECTANGLE)
                .setAspectRatio(1,1).getIntent(AddBussinessFragment.this);
        resultCallbackForCrop.launch(intent);
    }

    ActivityResultLauncher<Intent> resultCallbackForCrop = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        CropImage.ActivityResult cropResult = CropImage.getActivityResult(data);
                        handleCrop(cropResult.getUri());
                    }
                }
            });

    String path = "";
    private void handleCrop(Uri userimageuri) {
        InputStream imageStream = null;
        try {
            imageStream = context.getContentResolver().openInputStream(userimageuri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        final Bitmap bitmp = BitmapFactory.decodeStream(imageStream);
        new PickedImageActionFragment(bitmp, new PickedImageActionFragment.OnBitmapSelect() {
            @Override
            public void output(Bitmap bitmap) {
                binding.bussinessPic.setImageBitmap(bitmap);
                path = Functions.saveImage(bitmap,context);
            }
        }).show(getSupportFragmentManager(),"");
    }


    private ActivityResultLauncher<String[]> mPermissionResult = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onActivityResult(Map<String, Boolean> result) {

                    boolean allPermissionClear=true;
                    List<String> blockPermissionCheck=new ArrayList<>();
                    for (String key : result.keySet())
                    {
                        if (!(result.get(key)))
                        {
                            allPermissionClear=false;
                            blockPermissionCheck.add(Functions.getPermissionStatus(AddBussinessFragment.this,key));
                        }
                    }
                    if (blockPermissionCheck.contains("blocked"))
                    {
                        Functions.showPermissionSetting(AddBussinessFragment.this,getString(R.string.we_need_storage_and_camera_permission_for_upload_profile_pic));
                    }
                    else
                    if (allPermissionClear)
                    {
                        selectImage();
                    }

                }
            });


    public void submitClick() {
        if (cheakValidation()){
            Functions.showLoader(AddBussinessFragment.this);
            JSONObject parameters = new JSONObject();
            try {
                parameters.put("user_id", ""+Functions.getSharedPreference(AddBussinessFragment.this).
                        getString(Variables.U_ID,""));
                parameters.put("id", ""+bussiness_id);
                parameters.put("image", ""+path);
                parameters.put("name", ""+binding.companyEdit.getText().toString());
                parameters.put("number", ""+binding.numberEdit.getText().toString());
                parameters.put("email", ""+binding.emailEdit.getText().toString());
                parameters.put("address", ""+binding.addressEdit.getText().toString());
                parameters.put("website", ""+binding.websiteName.getText().toString());
                parameters.put("youtube", ""+binding.youtubeEdit.getText().toString());
                parameters.put("instagram", ""+binding.instaEdit.getText().toString());
                parameters.put("about", ""+binding.aboutEdit.getText().toString());
                parameters.put("facebook", ""+binding.facebookEdit.getText().toString());
                parameters.put("whatsapp", ""+binding.whatsappEdit.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            userViewModel.addUserBussiness(parameters).observe(this, new Observer<UserResponse>() {
                @Override
                public void onChanged(UserResponse userResponse) {
                    Functions.cancelLoader();
                    if (userResponse != null){
                        if (userResponse.code == Constants.SUCCESS){
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
                                            getBussinessDetails();
                                        }
                                        @Override
                                        public void onDismiss() {
                                        }
                                        @Override
                                        public void onComplete(Dialog dialog) {
                                            AddBussinessFragment.this.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Functions.saveBussinessData(userResponse.business,AddBussinessFragment.this);
                                                    AddBussinessFragment.this.onBackPressed();
                                                }
                                            });
                                        }
                                    }
                            ).show(getSupportFragmentManager(),"");
                        }else{
                            new CustomeDialogFragment(
                                    getString(R.string.alert),
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
                                            getBussinessDetails();
                                        }
                                        @Override
                                        public void onDismiss() {
                                        }
                                        @Override
                                        public void onComplete(Dialog dialog) {
                                        }
                                    }
                            ).show(getSupportFragmentManager(),"");
                        }
                    }
                }
            });
        }
    }

    private boolean cheakValidation() {
        if (bussiness_id.equals("") && TextUtils.isEmpty(path)){
            Functions.showToast(context,getString(R.string.please_select_bussiness_logo));
            return false;
        }
        if (TextUtils.isEmpty(binding.companyEdit.getText().toString())){
            binding.companyEdit.setError(getString(R.string.enter_bussiness_name));
            return false;
        }
        if (TextUtils.isEmpty(binding.emailEdit.getText().toString())){
            binding.emailEdit.setError(getString(R.string.enter_bussiness_email));
            return false;
        }
        return true;
    }

}