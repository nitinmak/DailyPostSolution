package com.sendpost.dreamsoft.Classes;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.content.ContextCompat;

import com.sendpost.dreamsoft.R;
import com.sendpost.dreamsoft.dialog.CustomeDialogFragment;
import com.sendpost.dreamsoft.dialog.DialogType;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtils {
    Activity activity;
    ActivityResultLauncher<String[]> mPermissionResult;

    public PermissionUtils(Activity activity, ActivityResultLauncher<String[]> mPermissionResult) {
        this.activity = activity;
        this.mPermissionResult=mPermissionResult;
    }

    public void takeStorageCameraPermission()
    {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
        mPermissionResult.launch(permissions);
    }

    public void takeContactPermission() {
        String[] permissions = {Manifest.permission.READ_CONTACTS};
        mPermissionResult.launch(permissions);
    }

    public void showStorageCameraPermissionDailog(String message)
    {
        List<String> permissionStatusList=new ArrayList<>();
        String[] permissions;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        {
            permissions =new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.MANAGE_EXTERNAL_STORAGE};
        }
        else
        {
            permissions =new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.MANAGE_EXTERNAL_STORAGE,};
        }

        for (String keyStr:permissions)
        {
            permissionStatusList.add(Functions.getPermissionStatus(activity,keyStr));
        }

        if (permissionStatusList.contains("denied"))
        {
            new CustomeDialogFragment(activity.getString(R.string.permission_alert),
                    activity.getString(R.string.we_need_storage_and_camera_permission_for_upload_profile_pic),
                    DialogType.WARNING,
                    true,
                    false,
                    true,
                    new CustomeDialogFragment.DialogCallback() {
                        @Override
                        public void onCencel() {
                        }
                        @Override
                        public void onSubmit() {
                            takeStorageCameraPermission();
                        }
                        @Override
                        public void onDismiss() {
                        }
                        @Override
                        public void onComplete(Dialog dialog) {
                        }
                    });
            return;
        }
        takeStorageCameraPermission();
    }

    public boolean isStorageCameraPermissionGranted()
    {
        int readExternalStoragePermission= ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeExternalStoragePermission= ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission= ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        return (readExternalStoragePermission== PackageManager.PERMISSION_GRANTED && writeExternalStoragePermission== PackageManager.PERMISSION_GRANTED && cameraPermission== PackageManager.PERMISSION_GRANTED);

    }

}
