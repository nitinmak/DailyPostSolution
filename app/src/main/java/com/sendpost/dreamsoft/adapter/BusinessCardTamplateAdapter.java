package com.sendpost.dreamsoft.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.sendpost.dreamsoft.databinding.ItemVisitingcardTamplateBinding;
import com.sendpost.dreamsoft.responses.TamplateModel;
import com.sendpost.dreamsoft.utils.BCardTamplateUtils;

public class BusinessCardTamplateAdapter extends RecyclerView.Adapter<BusinessCardTamplateAdapter.ViewHolder> {

    Context context;
    List<TamplateModel> list = new ArrayList<>();

    public BusinessCardTamplateAdapter(Context context, List<TamplateModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemVisitingcardTamplateBinding binding = ItemVisitingcardTamplateBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        int position = pos;
        holder.binding.setBusinessCard(list.get(position));
        try {
            holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BCardTamplateUtils bCardTamplateUtils = new BCardTamplateUtils(context);
                    bCardTamplateUtils.openEditorActivity(list.get(position).getJson());
                }
            });
        }catch (Exception e){}
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemVisitingcardTamplateBinding binding;

        public ViewHolder(@NonNull ItemVisitingcardTamplateBinding itemView) {
            super(itemView.getRoot());

            binding = itemView;

        }
    }
}
