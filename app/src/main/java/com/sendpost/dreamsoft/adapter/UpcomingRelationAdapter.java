package com.sendpost.dreamsoft.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.sendpost.dreamsoft.PosterActivity;
import com.sendpost.dreamsoft.databinding.ItemRelationHomeBinding;
import com.sendpost.dreamsoft.databinding.ItemUpcomingGodsdayHomeBinding;
import com.sendpost.dreamsoft.model.CategoryModel;

public class UpcomingRelationAdapter extends RecyclerView.Adapter<UpcomingRelationAdapter.ViewHolder> {

    Context context;
    List<CategoryModel> list = new ArrayList<>();
    boolean isVideo;
    public UpcomingRelationAdapter(Context context, List<CategoryModel> list, boolean isVideo) {
        this.context = context;
        this.list = list;
        this.isVideo = isVideo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRelationHomeBinding binding = ItemRelationHomeBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        int position = pos;
        try {
            holder.binding.setCategory(list.get(position));
            holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, PosterActivity.class)
                            .putExtra("title",list.get(position).getName())
                            .putExtra("type","category")
                            .putExtra("itemtype",isVideo)
                            .putExtra("item_id",list.get(position).getId()));
                }
            });
        }catch (Exception e){}
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemRelationHomeBinding binding;

        public ViewHolder(@NonNull ItemRelationHomeBinding itemView) {
            super(itemView.getRoot());

            binding = itemView;

        }
    }
}
