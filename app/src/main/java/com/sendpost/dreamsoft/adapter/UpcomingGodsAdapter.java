package com.sendpost.dreamsoft.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.sendpost.dreamsoft.PosterActivity;
import com.sendpost.dreamsoft.databinding.ItemUpcomingFestivalHomeBinding;
import com.sendpost.dreamsoft.databinding.ItemUpcomingGodsdayHomeBinding;
import com.sendpost.dreamsoft.model.CategoryModel;

public class UpcomingGodsAdapter extends RecyclerView.Adapter<UpcomingGodsAdapter.ViewHolder> {

    Context context;
    List<CategoryModel> list;
    boolean isVideo;
    public UpcomingGodsAdapter(Context context, List<CategoryModel> list, boolean isVideo) {
        this.context = context;
        this.list = list;
        this.isVideo = isVideo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUpcomingGodsdayHomeBinding binding = ItemUpcomingGodsdayHomeBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        int position = pos;
        try {
            holder.binding.setCategory(list.get(position));
            holder.binding.getRoot().setOnClickListener(view -> context.startActivity(new Intent(context, PosterActivity.class)
                    .putExtra("title",list.get(position).getName())
                    .putExtra("type","category")
                    .putExtra("itemtype",isVideo)
                    .putExtra("item_id",list.get(position).getId())));
        }catch (Exception e){}
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemUpcomingGodsdayHomeBinding binding;

        public ViewHolder(@NonNull ItemUpcomingGodsdayHomeBinding itemView) {
            super(itemView.getRoot());

            binding = itemView;

        }
    }
}
