package com.sendpost.dreamsoft.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sendpost.dreamsoft.databinding.ItemMyBussinessBinding;
import com.sendpost.dreamsoft.Classes.Functions;
import com.sendpost.dreamsoft.Classes.Variables;
import com.sendpost.dreamsoft.Interface.AdapterClickListener;
import com.sendpost.dreamsoft.model.BussinessModel;

import java.util.ArrayList;
import java.util.List;

public class BussinessAdapter extends RecyclerView.Adapter<BussinessAdapter.ViewHolder> {

    Context context ;
    List<BussinessModel> modelList = new ArrayList<>();
    AdapterClickListener adapter_click_listener;

    public BussinessAdapter(Context context, List<BussinessModel> modelList, AdapterClickListener adapter_click_listener) {
        this.context = context;
        this.modelList = modelList;
        this.adapter_click_listener=adapter_click_listener;
    }

    @NonNull
    @Override
    public BussinessAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        ItemMyBussinessBinding binding = ItemMyBussinessBinding.inflate(LayoutInflater.from(context), viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BussinessAdapter.ViewHolder holder, int position) {
        holder.binding.setBusiness(modelList.get(position));
        final BussinessModel item = modelList.get(position);
        if (Functions.getSharedPreference(context).getString(Variables.BUSSINESS_ID,"0").equals(item.getId()))
            holder.binding.activeLabel.setVisibility(View.VISIBLE);
        else
            holder.binding.activeLabel.setVisibility(View.GONE);
        holder.bind(position,item,adapter_click_listener);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemMyBussinessBinding binding;

        public ViewHolder(@NonNull ItemMyBussinessBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void bind(final int postion, final BussinessModel item, final AdapterClickListener listener) {

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v,postion,item);
                }
            });
            binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v,postion,item);
                }
            });
            binding.editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v,postion,item);
                }
            });

        }
    }
}
