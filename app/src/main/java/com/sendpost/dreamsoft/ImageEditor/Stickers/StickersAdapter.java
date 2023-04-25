package com.sendpost.dreamsoft.ImageEditor.Stickers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sendpost.dreamsoft.binding.BindingAdaptet;
import com.sendpost.dreamsoft.Classes.Functions;
import com.sendpost.dreamsoft.model.StickerModel;
import com.sendpost.dreamsoft.R;

import java.util.List;


public class StickersAdapter extends RecyclerView.Adapter<StickersAdapter.ViewHolder> {

    private List<StickerModel> list;
    private StickerBSFragment.StickerListener listener;
    Context context;

    public StickersAdapter(Context context, List<StickerModel> list, StickerBSFragment.StickerListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_sticker, parent,false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StickerModel model = list.get(position);

        try {
            BindingAdaptet.setImageUrl(holder.imgSticker,model.item_url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Functions.showLoader(context);
                Glide.with(context)
                        .asBitmap()
                        .load(Functions.getItemBaseUrl(model.item_url))
                        .into(new CustomTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                Functions.cancelLoader();
                                listener.onStickerClick(resource);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

                            }
                        });
            }
        });

    }



    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgSticker;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgSticker = itemView.findViewById(R.id.imgSticker);

        }
    }

}