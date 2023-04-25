package com.sendpost.dreamsoft.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sendpost.dreamsoft.Classes.Functions;
import com.sendpost.dreamsoft.ImageEditor.PreviewActivity;
import com.sendpost.dreamsoft.Interface.PostDeleteInterface;
import com.sendpost.dreamsoft.databinding.ItemUserPosterBinding;
import com.sendpost.dreamsoft.model.UserPostModel;

import java.util.List;

public class UserPostsAdapter extends RecyclerView.Adapter<UserPostsAdapter.ViewHolder> {

    Context context;
    List<UserPostModel> list;

    PostDeleteInterface postDeleteInterface;

    public UserPostsAdapter(Context context, List<UserPostModel> list ,PostDeleteInterface deleteInterface) {
        this.context = context;
        this.list = list;
        this.postDeleteInterface = deleteInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        ItemUserPosterBinding binding = ItemUserPosterBinding.inflate(LayoutInflater.from(context),viewGroup,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        int position = pos;
        holder.binding.setPosts(list.get(pos));
        holder.itemView.setOnClickListener(v -> context.startActivity(new Intent(context, PreviewActivity.class).putExtra("url", Functions.getItemBaseUrl(list.get(position).post_url))));
        holder.binding(pos,list,postDeleteInterface);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemUserPosterBinding binding;

        public ViewHolder(@NonNull ItemUserPosterBinding itemView) {
            super(itemView.getRoot());

            binding = itemView;
        }

        public void binding(int position, List<UserPostModel> list, PostDeleteInterface postDeleteInterface) {
            binding.deleteBtn.setOnClickListener(v -> postDeleteInterface.onItemClick(v,position,list));
        }
    }

}
