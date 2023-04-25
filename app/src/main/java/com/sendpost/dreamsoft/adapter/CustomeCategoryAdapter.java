package com.sendpost.dreamsoft.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sendpost.dreamsoft.databinding.ItemCustomeCategoryHomeBinding;
import com.sendpost.dreamsoft.model.CategoryModel;
import com.sendpost.dreamsoft.PosterActivity;

import java.util.ArrayList;
import java.util.List;

public class CustomeCategoryAdapter extends RecyclerView.Adapter<CustomeCategoryAdapter.ViewHolder> {

    Context context;
    List<CategoryModel> list = new ArrayList<>();

    public CustomeCategoryAdapter(Context context, List<CategoryModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCustomeCategoryHomeBinding binding = ItemCustomeCategoryHomeBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int pos = position;
        holder.binding.setCategory(list.get(pos));
        try {
            holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, PosterActivity.class).putExtra("title",list.get(pos).getName()).putExtra("type","category").putExtra("item_id",list.get(pos).getId()));
                }
            });
        }catch (Exception e){}
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemCustomeCategoryHomeBinding binding;

        public ViewHolder(@NonNull ItemCustomeCategoryHomeBinding itemView) {
            super(itemView.getRoot());

            binding = itemView;
        }
    }
}
