package com.sendpost.dreamsoft.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.sendpost.dreamsoft.Interface.AdapterClickListener;
import com.sendpost.dreamsoft.databinding.ItemBusinesscardTamplateBinding;
import com.sendpost.dreamsoft.model.BusinessCardModel;

public class BusinessCardDigitalAdapter extends RecyclerView.Adapter<BusinessCardDigitalAdapter.ViewHolder> {

    Context context;
    List<BusinessCardModel> list = new ArrayList<>();
    AdapterClickListener listener;

    public BusinessCardDigitalAdapter(Context context, List<BusinessCardModel> list, AdapterClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBusinesscardTamplateBinding binding = ItemBusinesscardTamplateBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        int position = pos;
        Log.d("nitintiitn",list.get(position).getThumb_url());
        holder.binding.setBusinessCard(list.get(position));
        try {
            holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(view,pos,list.get(pos));
                }
            });
        }catch (Exception e){}
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemBusinesscardTamplateBinding binding;

        public ViewHolder(@NonNull ItemBusinesscardTamplateBinding itemView) {
            super(itemView.getRoot());

            binding = itemView;

        }
    }
}
