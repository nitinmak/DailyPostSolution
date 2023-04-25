package com.sendpost.dreamsoft.Fragments;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.sendpost.dreamsoft.Classes.Functions;
import com.sendpost.dreamsoft.Classes.Variables;
import com.sendpost.dreamsoft.Interface.AdapterClickListener;
import com.sendpost.dreamsoft.R;
import com.sendpost.dreamsoft.UsersAdapter;
import com.sendpost.dreamsoft.databinding.FragmentReferBinding;
import com.sendpost.dreamsoft.responses.UserResponse;
import com.sendpost.dreamsoft.viewmodel.UserViewModel;

public class ReferFragment extends Fragment {

    public ReferFragment() {}

    FragmentReferBinding binding;
    Activity context;
    UserViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReferBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        binding.referCode.setText(Functions.getSharedPreference(context).getString(Variables.REFER_ID,""));
        binding.copyBtn.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("ReferCode", binding.referCode.getText());
            clipboard.setPrimaryClip(clip);
            clipboard.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
                @Override
                public void onPrimaryClipChanged() {
                    Functions.showToast(context,getString(R.string.copied));
                }
            });
        });
        binding.inviteBtn.setOnClickListener(v ->{
            Functions.shareApp(context);
        });

        binding.backBtn.setOnClickListener(view1 -> {
            getActivity().onBackPressed();
        });

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        viewModel.getInvitedUser(Functions.getUID(context)).observe(getViewLifecycleOwner(), new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse userResponse) {
                if (userResponse != null && userResponse.getUserslist() != null){
                    if (userResponse.getUserslist().size() > 0){
                        binding.userList.setAdapter(new UsersAdapter(context, userResponse.getUserslist(), new AdapterClickListener() {
                            @Override
                            public void onItemClick(View view, int pos, Object object) {

                            }
                        }));
                    }else{
                        binding.noDataLayout.setVisibility(View.VISIBLE);
                    }
                }else {
                    binding.noDataLayout.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}