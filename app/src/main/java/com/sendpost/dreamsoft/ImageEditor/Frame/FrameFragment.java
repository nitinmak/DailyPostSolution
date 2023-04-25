package com.sendpost.dreamsoft.ImageEditor.Frame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.sendpost.dreamsoft.R;
import com.sendpost.dreamsoft.model.FrameModel;

import java.util.ArrayList;
import java.util.List;

public class FrameFragment extends Fragment {

    private List<FrameModel> frame_list = new ArrayList<>();
    FrameListener frameListner;
    public FrameFragment(List<FrameModel> frame_list, FrameListener frameListner) {
        this.frame_list = frame_list;
        this.frameListner = frameListner;
    }

    RecyclerView rvFrame;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sticker, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvFrame = view.findViewById(R.id.rvEmoji);
        rvFrame.setAdapter(new FrameAdapter(getContext(), frame_list, new FrameListener() {
            @Override
            public void onFrameSelected(@Nullable FrameModel name) {
                frameListner.onFrameSelected(name);
            }
        }));
    }

}