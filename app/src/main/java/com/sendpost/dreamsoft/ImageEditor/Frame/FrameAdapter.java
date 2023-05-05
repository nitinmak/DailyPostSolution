package com.sendpost.dreamsoft.ImageEditor.Frame;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sendpost.dreamsoft.ImageEditor.filters.Fragments.AddBussinessFragment;
import com.sendpost.dreamsoft.databinding.RowFrameViewBinding;
import com.sendpost.dreamsoft.model.FrameModel;
import com.sendpost.dreamsoft.Classes.Functions;
import com.sendpost.dreamsoft.Classes.Variables;
import com.sendpost.dreamsoft.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class FrameAdapter extends RecyclerView.Adapter<FrameAdapter.ViewHolder> {

    private List<FrameModel> list = new ArrayList<>();
    private FrameListener listener;
    Context context;
    public static String selected_frame_name = "";

    public FrameAdapter(Context context, List<FrameModel> list, FrameListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowFrameViewBinding binding = RowFrameViewBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FrameModel model = list.get(position);
        holder.binding.setFrames(model);

        if (selected_frame_name.equals(model.getTitle())) {
            holder.binding.frameBgLay.setBackgroundTintList(null);
        } else {
            holder.binding.frameBgLay.getBackground().setTint(Color.LTGRAY);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.getType().equals("business")) {
                    if (Functions.getSharedPreference(context).getString(Variables.BUSSINESS_NAME, "").equals("")) {
                        context.startActivity(new Intent(context, AddBussinessFragment.class));
//                        context.startActivity(new Intent(context, MyBussinessActivity.class));
                        Toast.makeText(context, context.getString(R.string.please_select_bussiness), Toast.LENGTH_SHORT).show();
                    } else {
                        listener.onFrameSelected(model);
                    }
                }else if (model.getType().equals("user")){
                    listener.onFrameSelected(model);
                }else {
                    listener.onFrameSelected(model);
                }
                FrameAdapter.selected_frame_name = model.getTitle();
                notifyDataSetChanged();
            }
        });

    }

    private Bitmap getBitmapFromAsset(Context context, String name) {
        AssetManager assetManager = context.getAssets();

        InputStream istr = null;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(name);
            bitmap = BitmapFactory.decodeStream(istr);
            istr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        RowFrameViewBinding binding;

        ViewHolder(@NonNull RowFrameViewBinding itemView) {
            super(itemView.getRoot());

            binding = itemView;

        }
    }

}