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

import com.sendpost.dreamsoft.databinding.ItemMyWalltePinBinding;
import com.sendpost.dreamsoft.responses.PointHistoey;

public class PointAdapter extends RecyclerView.Adapter<PointAdapter.ViewHolder> {

    Context context ;
    List<PointHistoey> modelList = new ArrayList<>();


    public PointAdapter(Context context, List<PointHistoey> modelList) {
        this.context = context;
        this.modelList = modelList;

    }

    @NonNull
    @Override
    public PointAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        ItemMyWalltePinBinding binding = ItemMyWalltePinBinding.inflate(LayoutInflater.from(context), viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PointAdapter.ViewHolder holder, int position) {

        final PointHistoey item = modelList.get(position);
        int i = position+1;
         holder.binding.nob.setText(""+i);
         holder.binding.mobile.setText(item.getRemark());
         holder.binding.points.setText(item.getMember_point());
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
