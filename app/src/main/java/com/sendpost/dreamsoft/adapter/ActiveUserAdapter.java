package com.sendpost.dreamsoft.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sendpost.dreamsoft.databinding.ItemMyBussinessBinding;
import com.sendpost.dreamsoft.databinding.ItemMyWalltePinBinding;
import com.sendpost.dreamsoft.responses.ActiveUser;

public class ActiveUserAdapter extends RecyclerView.Adapter<ActiveUserAdapter.ViewHolder> {

    Context context ;
    List<ActiveUser> modelList = new ArrayList<>();


    public ActiveUserAdapter(Context context, List<ActiveUser> modelList) {
        this.context = context;
        this.modelList = modelList;

    }

    @NonNull
    @Override
    public ActiveUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        ItemMyWalltePinBinding binding = ItemMyWalltePinBinding.inflate(LayoutInflater.from(context), viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveUserAdapter.ViewHolder holder, int position) {

        final ActiveUser item = modelList.get(position);
        int i = position+1;
         holder.binding.nob.setText(""+i);
         holder.binding.mobile.setText(item.getRemarkmobile());
        String dtStart = item.getCreated_at();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        try {
            Date date = format.parse(dtStart);
            holder.binding.createddate.setText(date+"");
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemMyWalltePinBinding binding;

        public ViewHolder(@NonNull ItemMyWalltePinBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
