package com.sendpost.dreamsoft.NavFragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sendpost.dreamsoft.Classes.Functions;
import com.sendpost.dreamsoft.viewmodel.UserViewModel;
import com.sendpost.dreamsoft.Interface.AdapterClickListener;
import com.sendpost.dreamsoft.UsersAdapter;
import com.sendpost.dreamsoft.databinding.FragmentInvitedHistoryBinding;
import com.sendpost.dreamsoft.responses.UserResponse;

public class FragmentInvitedHistory extends BottomSheetDialogFragment {

    public Object tag;
    String tagginng;

    public FragmentInvitedHistory(String pin) {
        tagginng = pin;
    }

    FragmentInvitedHistoryBinding binding;
    Activity context;

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    UserViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInvitedHistoryBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        Log.d("mfkmkf", tagginng);

        viewModel.getInvitedUser(Functions.getUID(context)).observe(getViewLifecycleOwner(),
            userResponse -> {
//          Log.d("dmdkvskvns",userResponse.getUserslist().toString());
            if (userResponse != null && userResponse.getUserslist() != null) {
                if (userResponse.getUserslist().size() > 0) {
                    binding.userList.setAdapter(new UsersAdapter(context, userResponse.getUserslist(), new AdapterClickListener() {
                        @Override
                        public void onItemClick(View view1, int pos, Object object) {
                        }
                    }));
                }
                else {
                    binding.noDataLayout.setVisibility(View.VISIBLE);
                }
            } else {
                binding.noDataLayout.setVisibility(View.VISIBLE);
            }
        });

    }
}


