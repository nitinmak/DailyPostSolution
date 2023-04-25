package com.sendpost.dreamsoft.ImageEditor.Stickers;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sendpost.dreamsoft.model.StickerModel;
import com.sendpost.dreamsoft.R;

import java.util.ArrayList;
import java.util.List;

public class StickerFragment extends Fragment {

    private List<StickerModel> stickerList = new ArrayList<>();
    StickerBSFragment.StickerListener stickerListener;

    public StickerFragment(List<StickerModel> stickerList, StickerBSFragment.StickerListener stickerListener) {
        this.stickerList = stickerList;
        this.stickerListener = stickerListener;
    }

    RecyclerView rvEmoji;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sticker, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvEmoji = view.findViewById(R.id.rvEmoji);
        rvEmoji.setLayoutManager(new GridLayoutManager(getContext(),4));
        rvEmoji.setAdapter(new StickersAdapter(getContext(), stickerList, new StickerBSFragment.StickerListener() {
            @Override
            public void onStickerClick(@Nullable Bitmap bitmap) {
                stickerListener.onStickerClick(bitmap);
            }
        }));
    }

}