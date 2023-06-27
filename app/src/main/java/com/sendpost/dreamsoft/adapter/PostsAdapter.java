package com.sendpost.dreamsoft.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.sendpost.dreamsoft.databinding.ItemMostSendedBinding;
import com.sendpost.dreamsoft.databinding.ItemPosterVerticalBinding;
import com.sendpost.dreamsoft.model.PostsModel;
import com.sendpost.dreamsoft.responses.HomeResponse;

import java.util.ArrayList;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<PostsModel> list = new ArrayList<>();
    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, PostsModel postsModels, int main_position);
    }

    public PostsAdapter(Context context, List<PostsModel> list,OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
        this.list = list;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        ItemPosterVerticalBinding binding = ItemPosterVerticalBinding.inflate(LayoutInflater.from(context), viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holde, int pos) {
        ViewHolder holder = (ViewHolder) holde;int position = pos;
        holder.binding.setPosts(list.get(pos));
        holder.binding.viewTv.setText(list.get(position).views);
        Log.d("dfnvjfv",list.get(pos).item_url);
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v,list.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("onChanged___",""+list.size());
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemPosterVerticalBinding binding;

        public ViewHolder(@NonNull ItemPosterVerticalBinding itemView) {
            super(itemView.getRoot());

            binding = itemView;
        }
    }
}
