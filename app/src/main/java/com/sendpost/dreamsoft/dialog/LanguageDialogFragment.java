package com.sendpost.dreamsoft.dialog;

import static com.sendpost.dreamsoft.Classes.Constants.languageList;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sendpost.dreamsoft.adapter.LanguageAdapter;
import com.sendpost.dreamsoft.Classes.Functions;
import com.sendpost.dreamsoft.Classes.Variables;
import com.sendpost.dreamsoft.Interface.AdapterClickListener;
import com.sendpost.dreamsoft.MainActivity;
import com.sendpost.dreamsoft.model.LanguageModel;
import com.sendpost.dreamsoft.R;

public class LanguageDialogFragment extends BottomSheetDialogFragment {

    public LanguageDialogFragment() {
    }

    RecyclerView recyclerview;
    LanguageAdapter adapter;
    LanguageModel selectedLanguage = new LanguageModel();
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_language_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();

        recyclerview = view.findViewById(R.id.recyclerview);
        adapter=new LanguageAdapter(getContext(),languageList, new AdapterClickListener() {
            @Override
            public void onItemClick(View view, int pos, Object object) {
                selectedLanguage = (LanguageModel) object;
                SharedPreferences.Editor editor2 = Functions.getSharedPreference(getActivity()).edit();
                editor2.putString(Variables.TEST_APP_LANGUAGE_CODE, selectedLanguage.language_code);
                editor2.apply();
                adapter.notifyDataSetChanged();
            }
        });

        recyclerview.setAdapter(adapter);
        view.findViewById(R.id.main_lay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        view.findViewById(R.id.cencel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        view.findViewById(R.id.save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor2 = Functions.getSharedPreference(getActivity()).edit();
                if (selectedLanguage.language_name != null){
                    editor2.putString(Variables.APP_LANGUAGE, selectedLanguage.language_name);
                    editor2.putString(Variables.APP_LANGUAGE_CODE, selectedLanguage.language_code);
                    editor2.apply();
                    Functions.setLocale(Functions.getSharedPreference(getActivity()).getString(Variables.APP_LANGUAGE_CODE,Variables.DEFAULT_LANGUAGE_CODE), getActivity(), MainActivity.class,true);
                }else {
                    editor2.putString(Variables.TEST_APP_LANGUAGE_CODE, "en");
                    editor2.apply();
                    getActivity().onBackPressed();
                }
            }
        });
    }

}