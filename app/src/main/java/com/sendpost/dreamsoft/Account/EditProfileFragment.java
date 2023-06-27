package com.sendpost.dreamsoft.Account;

import static com.sendpost.dreamsoft.Classes.Constants.FAILD;
import static com.sendpost.dreamsoft.Classes.Constants.SUCCESS;
import static com.sendpost.dreamsoft.Classes.Functions.getSharedPreference;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sendpost.dreamsoft.R;
import com.sendpost.dreamsoft.databinding.FragmentEditProfileBinding;
import com.sendpost.dreamsoft.dialog.PickedImageActionFragment;
import com.sendpost.dreamsoft.model.UserModel;
import com.sendpost.dreamsoft.binding.BindingAdaptet;
import com.sendpost.dreamsoft.dialog.CustomeDialogFragment;
import com.sendpost.dreamsoft.dialog.DialogType;
import com.sendpost.dreamsoft.dialog.PickFromFragment;
import com.sendpost.dreamsoft.responses.UserResponse;
import com.sendpost.dreamsoft.viewmodel.UserViewModel;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.sendpost.dreamsoft.Classes.Functions;
import com.sendpost.dreamsoft.Classes.PermissionUtils;
import com.sendpost.dreamsoft.Classes.Variables;
import com.sendpost.dreamsoft.IntroActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EditProfileFragment extends Fragment {

    public EditProfileFragment() {}

    FragmentEditProfileBinding binding;
    PermissionUtils takePermissionUtils;
    UserViewModel userViewModel;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContext();
        Log.d("nitinmakwana", getSharedPreference(getContext()).getString(Variables.P_PIC,""));
        binding.usernameEdit.setText(getSharedPreference(getContext()).getString(Variables.NAME,""));
        binding.emailEdit.setText(getSharedPreference(getContext()).getString(Variables.U_EMAIL,""));
        binding.ivUseraddress.setText(getSharedPreference(getContext()).getString(Variables.ADDRESSP,""));
        binding.webEdit.setText(getSharedPreference(getContext()).getString(Variables.WEBSITEP,""));

        binding.referralEdit.setText(getSharedPreference(getContext()).getString(Variables.REFERRAL,""));

        if (!getSharedPreference(getContext()).getString(Variables.NUMBER,"").equals("null")){
            binding.numberEdit.setText(getSharedPreference(getContext()).getString(Variables.NUMBER,""));
        }

        if (!getSharedPreference(getContext()).getString(Variables.ADDRESSP,"").equals("null")){
            binding.ivUseraddress.setText(getSharedPreference(getContext()).getString(Variables.ADDRESSP,""));
        }

        if (!getSharedPreference(getContext()).getString(Variables.WEBSITEP,"").equals("null")){
            binding.webEdit.setText(getSharedPreference(getContext()).getString(Variables.WEBSITEP,""));
        }

        if (getSharedPreference(getContext()).getString(Variables.SOCIAL,"").equals("phone")){
            binding.numberEdit.setEnabled(false);
        }

        try {
            BindingAdaptet.setEditImageUrl(binding.userPic,Functions.getItemBaseUrl(getSharedPreference(getContext()).getString(Variables.P_PIC,"")));

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (getSharedPreference(getContext()).getString(Variables.NAME,"").equals("")){
            binding.backBtn.setVisibility(View.GONE);
        }

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        takePermissionUtils=new PermissionUtils(getActivity(),mPermissionResult);

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

        binding.submitBtn.setOnClickListener(view1 -> submitClick());
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        binding.emailEdit.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

    }

    private void selectImage() {
        new PickFromFragment(getString(R.string.select_image), new PickFromFragment.DialogCallback() {
            @Override
            public void onCencel() {}

            @Override
            public void onGallery() {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                resultCallbackForGallery.launch(intent);
            }

            @Override
            public void onCamera() {
                openCameraIntent();
            }

        }).show(getChildFragmentManager(),"");
    }

    String imageFilePath;
    private void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = Functions.createImageFile(getContext());
                imageFilePath = photoFile.getAbsolutePath();
            } catch (Exception ex) {
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".fileprovider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                resultCallbackForCamera.launch(pictureIntent);
            }
        }
    }

    ActivityResultLauncher<Intent> resultCallbackForGallery = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
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
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Uri selectedImage = (Uri.fromFile(new File(imageFilePath)));
                        beginCrop(selectedImage);
                    }
                }
            });

    private void beginCrop(Uri source) {
        Intent intent= CropImage.activity(source).setCropShape(CropImageView.CropShape.OVAL)
                .setAspectRatio(1,1).getIntent(getActivity());
        resultCallbackForCrop.launch(intent);
    }

    ActivityResultLauncher<Intent> resultCallbackForCrop = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
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
        new PickedImageActionFragment(bitmp, bitmap -> {
            binding.userPic.setImageBitmap(bitmap);
            path = Functions.saveImage(bitmap,context);

            userViewModel.updateProfilePic(Functions.getUID(context),path).observe(this, new Observer<UserResponse>() {
                @Override
                public void onChanged(UserResponse userResponse) {
                    Functions.cancelLoader();
                    if(userResponse != null){
                        if (userResponse.code == SUCCESS){
                            BindingAdaptet.setEditImageUrl(binding.userPic,Functions.getItemBaseUrl(userResponse.getUserModel().getProfile_pic()));
                            Log.d("IMAGEURL",Functions.getItemBaseUrl(userResponse.getUserModel().getProfile_pic()));
                            getSharedPreference(context).edit().putString(Variables.P_PIC,Functions.getItemBaseUrl(userResponse.getUserModel().getProfile_pic())).apply();

                        }else {
                            Functions.showToast(context,userResponse.message);
                        }
                    }
                }
            });
        }).show(getChildFragmentManager(),"");
    }

    private void handleCropx(Uri userimageuri) {
        String path = userimageuri.getPath();
        Functions.showLoader(context);

        userViewModel.updateProfilePic(Functions.getUID(context),path).observe(this, new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse userResponse) {
                Functions.cancelLoader();
                if(userResponse != null){
                    if (userResponse.code == SUCCESS){
                        BindingAdaptet.setEditImageUrl(binding.userPic,Functions.getItemBaseUrl(userResponse.getUserModel().getProfile_pic()));
                    }else {
                        Functions.showToast(context,userResponse.message);
                    }
                }
            }
        });
    }

    private final ActivityResultLauncher<String[]> mPermissionResult = registerForActivityResult(

            new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
                @Override
                public void onActivityResult(Map<String, Boolean> result) {
                    boolean allPermissionClear=true;
                    List<String> blockPermissionCheck=new ArrayList<>();
                    for (String key : result.keySet()) {
                        if (Boolean.FALSE.equals(result.get(key))) {
                            allPermissionClear=false;
                            blockPermissionCheck.add(Functions.getPermissionStatus(getActivity(),key));
                        }
                    }if (blockPermissionCheck.contains("blocked")) {
                        Functions.showPermissionSetting(requireActivity(),getString(R.string.we_need_storage_and_camera_permission_for_upload_profile_pic));
                    } else if (allPermissionClear) {
                        selectImage();
                    }
                }
            }
    );

    public void submitClick() {

        if (cheakValidation()){
            Functions.showLoader(context);
            userViewModel.updateProfile(
                    Functions.getUID(context),
                    binding.usernameEdit.getText().toString(),
                    binding.emailEdit.getText().toString(),
                    binding.numberEdit.getText().toString(),
                    binding.ivUseraddress.getText().toString(),
                    binding.webEdit.getText().toString(),
                    binding.referralEdit.getText().toString()
                    ).observe(getViewLifecycleOwner(), userResponse -> {
                        Functions.cancelLoader();
                        if (userResponse != null){
                            if(userResponse.code == SUCCESS){
                                new CustomeDialogFragment(
                                        getString(R.string.sucsess),
                                        getString(R.string.profile_update_successfully),
                                        DialogType.SUCCESS,
                                        false,
                                        false,
                                        false,
                                        new CustomeDialogFragment.DialogCallback() {
                                            @Override
                                            public void onCencel() {}
                                            @Override
                                            public void onSubmit() {}
                                            @Override
                                            public void onDismiss() {}
                                            @Override
                                            public void onComplete(Dialog dialog) {
                                                getActivity().runOnUiThread(() -> saveData(userResponse.getUserModel()));
                                            }
                                        }
                                ).show(getChildFragmentManager(),"");

                            } else if (userResponse.code == FAILD) {
                                Toast.makeText(getContext(),userResponse.message,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    private void saveData(UserModel userModel) {
        if (getSharedPreference(getContext()).getString(Variables.NAME,"").equals("")
                || getSharedPreference(getContext()).getString(Variables.NAME,"").equals("null")){
            Functions.saveUserData(userModel,context);
            Intent intent=new Intent(getContext(), IntroActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else{
            Functions.saveUserData(userModel,context);
            getActivity().onBackPressed();
        }
    }

    private boolean cheakValidation() {

        if (TextUtils.isEmpty(binding.usernameEdit.getText().toString())){
            binding.usernameEdit.setError(getString(R.string.enter_name));
            return false;
        }

        if (TextUtils.isEmpty(binding.referralEdit.getText().toString())){
            binding.referralEdit.setError(getString(R.string.referral));
            return false;
        }

        if (TextUtils.isEmpty(binding.emailEdit.getText().toString())){
            binding.emailEdit.setError(getString(R.string.enter_email));
            return false;
        }

        if (TextUtils.isEmpty(binding.numberEdit.getText().toString())){
            binding.numberEdit.setError(getString(R.string.enter_number));
            return false;
        }

        if (TextUtils.isEmpty(binding.ivUseraddress.getText().toString())){
            binding.ivUseraddress.setError(getString(R.string.address));
            return false;
        }


        return true;
    }
}