package com.sendpost.dreamsoft.dialog;

import static com.sendpost.dreamsoft.Classes.Constants.SUCCESS;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import com.sendpost.dreamsoft.Classes.Functions;
import com.sendpost.dreamsoft.Classes.Variables;
import com.sendpost.dreamsoft.R;
import com.sendpost.dreamsoft.databinding.FragmentPaymentTypeDialogBinding;
import com.sendpost.dreamsoft.responses.HomeResponse;
import com.sendpost.dreamsoft.ImageEditor.viewmodel.HomeViewModel;

public class PaymentTypeDialogFragment extends BottomSheetDialogFragment {


    String type = "";
    int discount = 0;
    CallBack callBack;
    Context context;
    FragmentPaymentTypeDialogBinding binding;
    HomeViewModel homeViewModel;

    public PaymentTypeDialogFragment(CallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPaymentTypeDialogBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        if (Functions.getSharedPreference(context).getString(Variables.GOOGLE_PURCHASE, "").equals("true")) {
            binding.googlepayRb.setVisibility(View.VISIBLE);
        }
        if (Functions.getSharedPreference(context).getString(Variables.RAZORPAY, "").equals("true")) {
            binding.razorpayRb.setVisibility(View.VISIBLE);
        }

        binding.cheakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Functions.showLoader(context);
                if (binding.codeEt.getText().toString().isEmpty()){
                    Functions.cancelLoader();
                    Toast.makeText(context, R.string.please_enter_promo, Toast.LENGTH_SHORT).show();
                }else {
                    homeViewModel.cheakPromo(binding.codeEt.getText().toString()).observe(getViewLifecycleOwner(), new Observer<HomeResponse>() {
                        @Override
                        public void onChanged(HomeResponse homeResponse) {
                            Functions.cancelLoader();
                            if (homeResponse != null){
                                if (homeResponse.code == SUCCESS){
                                    binding.successTv.setVisibility(View.VISIBLE);
                                    discount = Integer.parseInt(homeResponse.promocode.getDiscount());
                                }else {
                                    discount = 0;
                                    binding.successTv.setVisibility(View.GONE);
                                    Functions.showToast(context,homeResponse.message);
                                }
                            }else{
                                Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.googlepay_rb:
                        type = "google";
                        break;
                    case R.id.razorpay_rb:
                        type = "razorpay";
                        break;
                }
            }
        });
        binding.cencelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        binding.buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("")) {
                    Toast.makeText(getContext(), "Please select payment type", Toast.LENGTH_SHORT).show();
                } else {
                    callBack.getResponse(type,binding.codeEt.getText().toString(),discount);
                    dismiss();
                }
            }
        });
    }
}