package com.sendpost.dreamsoft.adapter;

import static com.sendpost.dreamsoft.PosterPreviewActivity.postsModel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sendpost.dreamsoft.ImageEditor.EditImageActivity;
import com.sendpost.dreamsoft.R;
import com.sendpost.dreamsoft.databinding.ItemUpcomingFestivalHomeBinding;

import com.sendpost.dreamsoft.model.CategoryModel;
import com.sendpost.dreamsoft.PosterActivity;

import java.util.ArrayList;
import java.util.List;

public class UpcomingEventAdapter extends RecyclerView.Adapter<UpcomingEventAdapter.ViewHolder> {

    Context context;
    List<CategoryModel> list = new ArrayList<>();
    boolean isVideo;
    public UpcomingEventAdapter(Context context, List<CategoryModel> list, boolean isVideo) {
        this.context = context;
        this.list = list;
        this.isVideo = isVideo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUpcomingFestivalHomeBinding binding = ItemUpcomingFestivalHomeBinding.inflate(LayoutInflater.from(context), parent, false);
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
                    EditImageActivity.postModel = postsModel;

                    context.startActivity(new Intent(context, PosterActivity.class)
                            .putExtra("title",list.get(position).getName())
                            .putExtra("type","category")
                            .putExtra("itemtype",isVideo)
                            .putExtra("item_id",list.get(position).getId()));

//                  context.startActivity(new Intent(context, PosterPreviewActivity.class)
//                            .putExtra("type", "Posts")
//                            .putExtra("itemtype",isVideo));
                }
            });

        }catch (Exception e){}
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemUpcomingFestivalHomeBinding binding;

        public ViewHolder(@NonNull ItemUpcomingFestivalHomeBinding itemView) {
            super(itemView.getRoot());

            binding = itemView;

        }
    }
}
