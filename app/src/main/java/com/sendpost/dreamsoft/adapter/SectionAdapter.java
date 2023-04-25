package com.sendpost.dreamsoft.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.sendpost.dreamsoft.databinding.ItemSectionHorizontalLayoutBinding;
import com.sendpost.dreamsoft.databinding.ItemSectionLayoutBinding;
import com.sendpost.dreamsoft.model.PostsModel;
import com.sendpost.dreamsoft.model.SectionModel;
import com.sendpost.dreamsoft.PosterActivity;

import java.util.List;

/**
 * Created by qboxus on 3/20/2018.
 */

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.ViewHolder> {

    public Context context;
    List<SectionModel> datalist;
    boolean isPosts;
    boolean greeting;

    public interface OnItemClickListener {
        void onItemClick(View view, List<PostsModel> postsModels, int main_position, int child_position);
    }

    public SectionAdapter.OnItemClickListener listener;

    public SectionAdapter(Context context, List<SectionModel> arrayList, OnItemClickListener listener, boolean posts, boolean greeting) {
        this.context = context;
        datalist = arrayList;
        this.listener = listener;
        this.isPosts = posts;
        this.greeting = greeting;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewtype) {
        ItemSectionLayoutBinding binding = ItemSectionLayoutBinding.inflate(LayoutInflater.from(context), viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        int position = pos;
        SectionModel item = datalist.get(position);
        holder.binding.setSection(item);
        if (item.getPost_using() != null && item.getPost_using().equals("api")){
            holder.binding.shimmerLay.setVisibility(View.VISIBLE);
        }else{
            holder.binding.shimmerLay.setVisibility(View.GONE);
            HorizontalAdapter adapter = new HorizontalAdapter(context, position, item.getPosts());
            holder.binding.horizontalRecylerview.setAdapter(adapter);
        }

        if (!isPosts) {
            holder.binding.lottieAani.setVisibility(View.GONE);
            holder.binding.countAndSave.setVisibility(View.GONE);
        }

        if (greeting) {
            holder.binding.lottieAani.setVisibility(View.GONE);
            holder.binding.countAndSave.setVisibility(View.VISIBLE);
        }
        holder.bind(position, item.getPosts());
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ItemSectionLayoutBinding binding;

        public ViewHolder(ItemSectionLayoutBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void bind(final int pos, final List<PostsModel> list) {
            binding.countAndSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (datalist.get(pos).getPost_using() != null && datalist.get(pos).getPost_using().equals("api")){
                        context.startActivity(new Intent(context, PosterActivity.class).putExtra("title", datalist.get(pos).getName()).putExtra("type", "api").putExtra("keyword", datalist.get(pos).getKeyword()));
                    }else{
                        if (greeting){
                            context.startActivity(new Intent(context, PosterActivity.class).putExtra("title", datalist.get(pos).getName()).putExtra("type", "greeting").putExtra("item_id", datalist.get(pos).getId()));
                        }else{
                            context.startActivity(new Intent(context, PosterActivity.class).putExtra("title", datalist.get(pos).getName()).putExtra("type", "section").putExtra("item_id", datalist.get(pos).getId()));
                        }
                    }
                }
            });
        }
    }


    class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.CustomViewHolder> {
        public Context context;

        List<PostsModel> datalist;
        int main_position;

        public HorizontalAdapter(Context context, int position, List<PostsModel> arrayList) {
            this.context = context;
            datalist = arrayList;
            this.main_position = position;
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewtype) {
            ItemSectionHorizontalLayoutBinding binding = ItemSectionHorizontalLayoutBinding.inflate(LayoutInflater.from(context), viewGroup, false);
            return new CustomViewHolder(binding);
        }

        @Override
        public int getItemCount() {
            return datalist.size();
        }

        class CustomViewHolder extends RecyclerView.ViewHolder {

            ItemSectionHorizontalLayoutBinding binding;

            public CustomViewHolder(ItemSectionHorizontalLayoutBinding view) {
                super(view.getRoot());
                binding = view;
            }

            public void bind(final int pos, final List<PostsModel> datalist) {
                binding.getRoot().setOnClickListener(v -> {
                    listener.onItemClick(binding.getRoot(), datalist, main_position, pos);
                });
            }
        }

        @Override
        public void onBindViewHolder(final CustomViewHolder holder, final int i) {
            PostsModel item = datalist.get(i);
            holder.binding.setPosts(item);
            holder.bind(i, datalist);
        }
    }

}