package com.sendpost.dreamsoft.NavFragment;

import static com.sendpost.dreamsoft.Classes.Constants.SUCCESS;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sendpost.dreamsoft.Classes.Functions;
import com.sendpost.dreamsoft.Classes.Variables;
import com.sendpost.dreamsoft.MyPostsActivity;
import com.sendpost.dreamsoft.R;
import com.sendpost.dreamsoft.databinding.ActivityMyWalletBinding;
import com.sendpost.dreamsoft.responses.ActiveUser;
import com.sendpost.dreamsoft.viewmodel.UserViewModel;

public class PinFragment extends Fragment {

    UserViewModel viewModel;
    ActivityMyWalletBinding activityMyWalletBinding;
    Context context;
    ImageButton back_btn;
    TextView pinbook;
    private  FragmenActivatedHistory invitedHistory = null;
    private  PinbookFragment pinfragment = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activityMyWalletBinding = ActivityMyWalletBinding.inflate(getLayoutInflater());
        return activityMyWalletBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContext();
        Functions.setLocale(Functions.getSharedPreference(context).getString(Variables.APP_LANGUAGE_CODE,Variables.DEFAULT_LANGUAGE_CODE), getActivity(), MyPostsActivity.class,false);

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        activityMyWalletBinding.submitBtn.setOnClickListener(v -> {
            if(activityMyWalletBinding.usernameEdit.getText().toString().equals("") && activityMyWalletBinding.usernameEdit.getText().toString().length() != 10){
                Toast toast= Toast.makeText(context, "Enter Valid mobile number ! ", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER ,0, 0);
                toast.show();
                return;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(getString(R.string.msg_act_user));
            builder.setPositiveButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.setNegativeButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    sendapi(Functions.getUID(context),activityMyWalletBinding.usernameEdit.getText().toString());
                    dialogInterface.dismiss();

                }
            });
            builder.create().show();
        });
        getuserhistory(Functions.getUID(context));
        invitedHistory = new FragmenActivatedHistory("Pin");
//        showBottomSheetDialogFragment(invitedHistory);
        activityMyWalletBinding.showactiveuser.setOnClickListener(view1 -> {
            showBottomSheetDialogFragment(invitedHistory);
        });

        back_btn = view.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        pinfragment = new PinbookFragment();
        activityMyWalletBinding.pinbook.setOnClickListener(view1 -> {
            showBottomSheetDialogFragment(pinfragment);
        });

//        pinbook = view.findViewById(R.id.pinbook);
//        pinbook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void showBottomSheetDialogFragment(BottomSheetDialogFragment fragment) {
        if (fragment == null || fragment.isAdded()) {
            return;
        }
        fragment.show(getChildFragmentManager(), fragment.getTag());
    }

    private void sendapi(String uid, String text) {
        viewModel.activeuser(uid,text,"PIN").observe(this, userResponse -> {
            if (userResponse != null) {
                Toast toast= Toast.makeText(context, userResponse.message, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
//                Toast.makeText(PinFragment.this, userResponse.message, Toast.LENGTH_SHORT).show();
                getuserhistory(uid);

            } else {
                Toast toast= Toast.makeText(context, userResponse.message, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
//                Toast.makeText(MyWalletActivity.this, userResponse.message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getuserhistory(String uid) {
        viewModel.activeuserhistory(Functions.getUID(context)).observe(getViewLifecycleOwner(), userResponse -> {
//            binding.refereshLay.setRefreshing(false);
//            binding.shimmerLay.stopShimmer();
//            binding.shimmerLay.setVisibility(View.GONE);
            if (userResponse != null) {
                if (userResponse.code == SUCCESS) {
                    activityMyWalletBinding.purchased.setText(": " + userResponse.purchased_pins);
                    activityMyWalletBinding.remaining.setText(": "+userResponse.available_pins);
                    activityMyWalletBinding.used.setText(": " + userResponse.used_pins);

                } else {
                    Functions.showToast(context, userResponse.message);
                }
            }
        });
    }

//        viewModel.activeuserhistory(uid).observe(this, userResponse -> {
//            if (userResponse != null) {
//
//                Toast.makeText(MyWalletActivity.this, userResponse.message, Toast.LENGTH_SHORT).show();
//
//            } else {
//                Toast.makeText(MyWalletActivity.this, userResponse.message, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

//    ActiveUserAdapter adapter;
//    private void setAdapter() {
//            adapter = new ActiveUserAdapter(context, list);
//            activityMyWalletBinding.rvNewest.setAdapter(adapter);
//    }


}