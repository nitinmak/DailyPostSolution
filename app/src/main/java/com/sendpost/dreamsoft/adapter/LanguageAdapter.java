package com.sendpost.dreamsoft.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sendpost.dreamsoft.databinding.ItemLanguageBinding;
import com.sendpost.dreamsoft.Classes.Functions;
import com.sendpost.dreamsoft.Classes.Variables;
import com.sendpost.dreamsoft.Interface.AdapterClickListener;
import com.sendpost.dreamsoft.model.LanguageModel;

import java.util.ArrayList;


public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder> {

    private ArrayList<LanguageModel> list;
    private AdapterClickListener listener;
    Context context;

    public LanguageAdapter(Context context,ArrayList<LanguageModel> list, AdapterClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLanguageBinding binding = ItemLanguageBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setLanguage(list.get(position));
        Log.d("NITIN",list.get(position).language_code);
        Log.d("NITIN----",Functions.getSharedPreference(context).getString(Variables.TEST_APP_LANGUAGE_CODE,Variables.DEFAULT_LANGUAGE_CODE));
        if (Functions.getSharedPreference(context).getString(Variables.TEST_APP_LANGUAGE_CODE,Variables.DEFAULT_LANGUAGE_CODE).equals(list.get(position).language_code)){
            holder.binding.ivTick.setVisibility(View.VISIBLE);
        }else {
            holder.binding.ivTick.setVisibility(View.GONE);
        }
        holder.bind(position, list.get(position) , listener);
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ItemLanguageBinding binding;

        ViewHolder(@NonNull ItemLanguageBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void bind(final int pos, final LanguageModel model, final AdapterClickListener listener) {
            binding.getRoot().setOnClickListener(v -> listener.onItemClick(v,pos,model));
        }

    }

}
