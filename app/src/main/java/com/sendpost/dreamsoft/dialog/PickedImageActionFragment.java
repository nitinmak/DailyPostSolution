package com.sendpost.dreamsoft.dialog;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
//import androidx.palette.graphics.Palette;

import com.airbnb.lottie.LottieAnimationView;
import com.sendpost.dreamsoft.BgEraser.BGConfig;
import com.sendpost.dreamsoft.BgEraser.EraserActivity;
import com.sendpost.dreamsoft.BgEraser.MLCropAsyncTask;
import com.sendpost.dreamsoft.BgEraser.MLOnCropTaskCompleted;
import com.sendpost.dreamsoft.R;
import com.sendpost.dreamsoft.View.ShapesImage;


public class PickedImageActionFragment extends DialogFragment {

    public interface OnBitmapSelect{
        void output(Bitmap bitmap);
    }

    Bitmap selectedBit,cutBit;
    OnBitmapSelect onBitmapSelect;
    public PickedImageActionFragment(Bitmap bitmap,OnBitmapSelect onBitmapSelect) {
        selectedBit = bitmap;
        this.onBitmapSelect = onBitmapSelect;
    }

    TextView remove_bg,use_original,use_this;
    ShapesImage normalPreviewImage,ovalPreviewImage,diamondPreviewImage,squarePreviewImage;
    LottieAnimationView animation_view;
    ShapesImage normal_img,oval_img,diamond_img,square_img;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_picked_image_action, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setCancelable(false);
        remove_bg = view.findViewById(R.id.remove_bg);
        use_original = view.findViewById(R.id.use_original);
        use_this = view.findViewById(R.id.use_this);

        normalPreviewImage = view.findViewById(R.id.normalPreviewImage);
        ovalPreviewImage = view.findViewById(R.id.ovalPreviewImage);
        diamondPreviewImage = view.findViewById(R.id.diamondPreviewImage);
        squarePreviewImage = view.findViewById(R.id.squarePreviewImage);
        animation_view = view.findViewById(R.id.animation_view);

        BGConfig.currentBit = selectedBit;
        normalPreviewImage.setImageBitmap(selectedBit);
        ovalPreviewImage.setImageBitmap(selectedBit);
        diamondPreviewImage.setImageBitmap(selectedBit);
        squarePreviewImage.setImageBitmap(selectedBit);

        normal_img = view.findViewById(R.id.normal_img);
        oval_img = view.findViewById(R.id.oval_img);
        diamond_img = view.findViewById(R.id.diamond_img);
        square_img = view.findViewById(R.id.square_img);

        selectedShapesImage = normalPreviewImage;
        normal_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedShapesImage = normalPreviewImage;
                selectedShapesImage(selectedShapesImage);
                use_this.setVisibility(View.VISIBLE);
            }
        });
        oval_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedShapesImage = ovalPreviewImage;
                selectedShapesImage(selectedShapesImage);
                use_this.setVisibility(View.VISIBLE);
            }
        });
        diamond_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedShapesImage = diamondPreviewImage;
                selectedShapesImage(selectedShapesImage);
                use_this.setVisibility(View.VISIBLE);
            }
        });
        square_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedShapesImage = squarePreviewImage;
                selectedShapesImage(selectedShapesImage);
                use_this.setVisibility(View.VISIBLE);
            }
        });

        remove_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (remove_bg.getText().toString().equals(getString(R.string.remove_bg))){
                    removeBgAuto();
                }else{
                    EraserActivity.b = selectedBit;
                    resultCallbackForEraser.launch(new Intent(getContext(),EraserActivity.class));
                }
            }
        });
        view.findViewById(R.id.close_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        view.findViewById(R.id.use_this).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBitmapSelect.output(selectedShapesImage.getBitmap());
                dismiss();
            }
        });
        use_original.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBitmapSelect.output(selectedBit);
                dismiss();
            }
        });
    }

    ActivityResultLauncher<Intent> resultCallbackForEraser = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        normalPreviewImage.setImageBitmap(EraserActivity.eraserResultBmp);
                        ovalPreviewImage.setImageBitmap(EraserActivity.eraserResultBmp);
                        diamondPreviewImage.setImageBitmap(EraserActivity.eraserResultBmp);
                        squarePreviewImage.setImageBitmap(EraserActivity.eraserResultBmp);
                        selectedShapesImage.setImageBitmap(EraserActivity.eraserResultBmp);
                    }
                }
            });

    private void selectedShapesImage(ShapesImage img) {
        switch (img.getId()){
            case R.id.normalPreviewImage:
                normalPreviewImage.setVisibility(View.VISIBLE);
                ovalPreviewImage.setVisibility(View.GONE);
                diamondPreviewImage.setVisibility(View.GONE);
                squarePreviewImage.setVisibility(View.GONE);
                break;
            case R.id.ovalPreviewImage:
                normalPreviewImage.setVisibility(View.GONE);
                ovalPreviewImage.setVisibility(View.VISIBLE);
                diamondPreviewImage.setVisibility(View.GONE);
                squarePreviewImage.setVisibility(View.GONE);
                break;
            case R.id.diamondPreviewImage:
                normalPreviewImage.setVisibility(View.GONE);
                ovalPreviewImage.setVisibility(View.GONE);
                diamondPreviewImage.setVisibility(View.VISIBLE);
                squarePreviewImage.setVisibility(View.GONE);
                break;
            case R.id.squarePreviewImage:
                normalPreviewImage.setVisibility(View.GONE);
                ovalPreviewImage.setVisibility(View.GONE);
                diamondPreviewImage.setVisibility(View.GONE);
                squarePreviewImage.setVisibility(View.VISIBLE);
                break;
        }
    }

    ShapesImage selectedShapesImage;
    private void removeBgAuto() {
        animation_view.setVisibility(View.VISIBLE);
        remove_bg.setEnabled(false);
        use_original.setEnabled(false);

        new MLCropAsyncTask(new MLOnCropTaskCompleted() {
            public void onTaskCompleted(Bitmap bitmap, Bitmap bitmap2, int left, int top) {
                        /*int[] iArr = {0, 0, selectedBit.getWidth(), selectedBit.getHeight()};
                        int width = selectedBit.getWidth();
                        int height = selectedBit.getHeight();
                        int i = width * height;
                        selectedBit.getPixels(new int[i], 0, width, 0, 0, width, height);
                        int[] iArr2 = new int[i];
                        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                        createBitmap.setPixels(iArr2, 0, width, 0, 0, width, height);
                        cutBit = ImageUtils.getMask(CreateVCardActivity.this, selectedBit, createBitmap, width, height);*/
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                        bitmap, selectedBit.getWidth(), selectedBit.getHeight(), false);
                cutBit = resizedBitmap;

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
//                        Palette p = Palette.from(cutBit).generate();
//                        if (p.getDominantSwatch() == null) {
//                            Toast.makeText(getActivity(), "OKK", Toast.LENGTH_SHORT).show();
//                        }
                        normalPreviewImage.setImageBitmap(resizedBitmap);
                        ovalPreviewImage.setImageBitmap(resizedBitmap);
                        diamondPreviewImage.setImageBitmap(resizedBitmap);
                        squarePreviewImage.setImageBitmap(resizedBitmap);
                        selectedShapesImage.setImageBitmap(resizedBitmap);

                        use_this.setVisibility(View.VISIBLE);
                        remove_bg.setVisibility(View.VISIBLE);
                        remove_bg.setEnabled(true);
                        use_original.setEnabled(true);
                        remove_bg.setText(getString(R.string.manual));
                        animation_view.setVisibility(View.GONE);
                    }
                });


            }
        }, getActivity()).execute(new Void[0]);
    }
}