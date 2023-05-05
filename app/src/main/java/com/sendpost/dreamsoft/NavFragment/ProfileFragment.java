package com.sendpost.dreamsoft.NavFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sendpost.dreamsoft.Account.EditProfileFragment;
import com.sendpost.dreamsoft.Classes.Functions;
import com.sendpost.dreamsoft.Classes.Variables;
import com.sendpost.dreamsoft.ImageEditor.filters.Fragments.AddBussinessFragment;
import com.sendpost.dreamsoft.HomeActivity;
import com.sendpost.dreamsoft.MyPostsActivity;
import com.sendpost.dreamsoft.R;
import com.sendpost.dreamsoft.WebviewA;
import com.sendpost.dreamsoft.binding.BindingAdaptet;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {


    public ProfileFragment() {

    }

    TextView name_tv,email_tv,version_tv,edit_btn;
    CircleImageView user_pic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_new, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name_tv = view.findViewById(R.id.username_tv);
        email_tv = view.findViewById(R.id.email_tv);
        version_tv = view.findViewById(R.id.version_tv);
        user_pic = view.findViewById(R.id.user_pic);
        edit_btn = view.findViewById(R.id.edit_btn);

        name_tv.setText(Functions.getSharedPreference(getContext()).getString(Variables.NAME,""));
        email_tv.setText(Functions.getSharedPreference(getContext()).getString(Variables.U_EMAIL,""));

        try {
            BindingAdaptet.setImageUrl(user_pic,Functions.getSharedPreference(getContext()).getString(Variables.P_PIC,""));
        } catch (Exception e) {
            e.printStackTrace();
        }

        edit_btn.setOnClickListener(view1 -> {
            if (HomeActivity.fragmentManager.isDestroyed()){
                HomeActivity.fragmentManager = getFragmentManager();
            }
            FragmentTransaction transaction = HomeActivity.fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.in_from_bottom, R.anim.out_to_top, R.anim.in_from_top, R.anim.out_from_bottom);
            transaction.addToBackStack(null);
            transaction.replace(R.id.home_activity, new EditProfileFragment()).commit();
        });

        view.findViewById(R.id.my_post_lay).setOnClickListener(v -> startActivity(new Intent(getActivity(), MyPostsActivity.class)));

        view.findViewById(R.id.bussiness_lay).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), AddBussinessFragment.class));
//                startActivity(new Intent(getActivity(), MyBussinessActivity.class));
        });
//        view.findViewById(R.id.share_lay).setOnClickListener(v -> Functions.shareApp(getContext()));
        view.findViewById(R.id.rate_lay).setOnClickListener(v -> Functions.rateApp(getContext()));
        view.findViewById(R.id.privacy_policy_lay).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), WebviewA.class);
            intent.putExtra("title", getString(R.string.privacy_policy));
            startActivity(intent);
        });
        view.findViewById(R.id.terms_lay).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), WebviewA.class);
            intent.putExtra("title", getString(R.string.terms_service));
            startActivity(intent);
        });

    }



    @Override
    public void onResume() {
        super.onResume();
        name_tv.setText(Functions.getSharedPreference(getContext()).getString(Variables.NAME,""));
        email_tv.setText(Functions.getSharedPreference(getContext()).getString(Variables.U_EMAIL,""));
        try {
            BindingAdaptet.setImageUrl(user_pic,Functions.getSharedPreference(getContext()).getString(Variables.P_PIC,""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}