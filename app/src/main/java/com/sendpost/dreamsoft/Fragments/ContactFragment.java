package com.sendpost.dreamsoft.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.sendpost.dreamsoft.Classes.Functions;

import com.sendpost.dreamsoft.R;
import com.sendpost.dreamsoft.dialog.CustomeDialogFragment;
import com.sendpost.dreamsoft.dialog.DialogType;
import com.sendpost.dreamsoft.responses.UserResponse;
import com.sendpost.dreamsoft.viewmodel.UserViewModel;

public class ContactFragment extends Fragment {

    public ContactFragment() {}
    EditText number_et,message_et;
    UserViewModel viewModel;
    ImageButton back_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        number_et = view.findViewById(R.id.number_et);
        message_et = view.findViewById(R.id.message_et);
        view.findViewById(R.id.submit_btn).setOnClickListener(View -> submit());
        back_btn = view.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(view1 -> {

            if (view1 != null) {
                InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
            }
            requireActivity().onBackPressed();
        });
    }

    public void submit() {
        if (number_et.getText().toString().length() < 9){
            Toast.makeText(getContext(), getString(R.string.please_enter_currect_number), Toast.LENGTH_SHORT).show();
        }else if (message_et.getText().toString().length() < 20){
            Toast.makeText(getContext(), getString(R.string.message_is_very_short), Toast.LENGTH_SHORT).show();
        }else{
            viewModel.addContact(Functions.getUID(getContext()),number_et.getText().toString(),message_et.getText().toString())
                    .observe(getViewLifecycleOwner(), new Observer<UserResponse>() {
                @Override
                public void onChanged(UserResponse userResponse) {
                    if (userResponse != null){
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
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                getActivity().onBackPressed();
                                            }
                                        });
                                    }
                                }
                        ).show(getChildFragmentManager(),"");
                    }
                }
            });
        }
    }
}