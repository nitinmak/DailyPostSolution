package com.sendpost.dreamsoft.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.sendpost.dreamsoft.databinding.ItemSubscriptionBinding;

import com.sendpost.dreamsoft.Interface.AdapterClickListener;
import com.sendpost.dreamsoft.model.SubscriptionModel;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.ViewHolder> {

    Context context ;
    List<SubscriptionModel> wallet_modelArrayList = new ArrayList<>();
    AdapterClickListener adapter_click_listener;

    public SubscriptionAdapter(Context context, List<SubscriptionModel> wallet_modelArrayList, AdapterClickListener adapter_click_listener) {
        this.context = context;
        this.wallet_modelArrayList = wallet_modelArrayList;
        this.adapter_click_listener=adapter_click_listener;
    }

    @NonNull
    @Override
    public SubscriptionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        ItemSubscriptionBinding binding = ItemSubscriptionBinding.inflate(LayoutInflater.from(context), viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionAdapter.ViewHolder holder, int position) {
        holder.binding.setSubscription(wallet_modelArrayList.get(position));
        holder.bind(position,wallet_modelArrayList.get(position),adapter_click_listener);
    }

    @Override
    public int getItemCount() {
        return wallet_modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemSubscriptionBinding binding;

        public ViewHolder(@NonNull ItemSubscriptionBinding itemView) {
            super(itemView.getRoot());

            binding = itemView;
        }

        public void bind(final int postion, final SubscriptionModel item, final AdapterClickListener listener) {

            binding.oneMonthBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v,postion,item);
                }
            });

        }
    }
}
