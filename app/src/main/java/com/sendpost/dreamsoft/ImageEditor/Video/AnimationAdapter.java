package com.sendpost.dreamsoft.ImageEditor.Video;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sendpost.dreamsoft.R;

import java.util.List;

import ja.burhanrashid52.photoeditor.Movie.widget.TransferItem;


public class AnimationAdapter extends RecyclerView.Adapter<AnimationAdapter.ViewHolder> {

    private AnimatinCallback listener;
    Context context;
    public static String selectedAnimationname = "";
    private final List<TransferItem> mItemList;

    public interface AnimatinCallback {
        void onAnimationSelect(TransferItem item);
    }

    public AnimationAdapter(Context context,List<TransferItem> mItemList, AnimatinCallback listener) {
        this.context = context;
        this.listener = listener;
        this.mItemList = mItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_transfer_item, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageView img = holder.itemView.findViewById(R.id.transfer_img);
        TextView txt = holder.itemView.findViewById(R.id.transfer_txt);
        ImageView checkImg = holder.itemView.findViewById(R.id.transfer_check);

        final TransferItem item = mItemList.get(position);
        img.setImageResource(item.imgRes);
        txt.setText(item.name);
        checkImg.setVisibility(selectedAnimationname.equals(item.name) ?View.VISIBLE:View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onAnimationSelect(item);
                    selectedAnimationname = item.name;
                    notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgFrame;
        RelativeLayout frame_bg_lay;
        TextView type_tv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgFrame = itemView.findViewById(R.id.imgFrame);
            frame_bg_lay = itemView.findViewById(R.id.frame_bg_lay);
            type_tv = itemView.findViewById(R.id.type_tv);

        }
    }

}