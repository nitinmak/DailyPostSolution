package com.sendpost.dreamsoft.ImageEditor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sendpost.dreamsoft.Classes.Callback;
import com.sendpost.dreamsoft.R;

public class FramItemChangeDialogFragment extends DialogFragment {

    Callback callback;
    String txt;

    public FramItemChangeDialogFragment(String t,Callback callback) {
        this.callback = callback;
        txt = t;
    }

    TextView cancel_btn,submit_btn;
    EditText edit_tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fram_item_change_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edit_tv = view.findViewById(R.id.edit_tv);
        cancel_btn = view.findViewById(R.id.cencel_btn);
        submit_btn = view.findViewById(R.id.submit_btn);

        edit_tv.setText(txt);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit_tv.getText().toString().equals("")){
                    callback.Responce(edit_tv.getText().toString());
                    dismiss();
                }else {
                    Toast.makeText(getContext(), R.string.please_fill_field, Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}