package com.sendpost.dreamsoft.ImageEditor.Video;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sendpost.dreamsoft.R;

import java.util.LinkedList;
import java.util.List;

import ja.burhanrashid52.photoeditor.Movie.widget.FilterItem;
import ja.burhanrashid52.photoeditor.Movie.widget.FilterType;


public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {

    private FilterCallback listener;
    Context context;
    public static String selectedFiltername = "";
    private final List<FilterItem> mItemList;

    public interface FilterCallback {
        void onFilterSelect(FilterItem item);
    }

    public FilterAdapter(Context context,  FilterCallback listener) {
        this.context = context;
        this.listener = listener;
        mItemList = initFilters();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_transfer_item, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageView img = holder.itemView.findViewById(R.id.transfer_img);
        TextView txt = holder.itemView.findViewById(R.id.transfer_txt);
        ImageView checkImg = holder.itemView.findViewById(R.id.transfer_check);

        final FilterItem item = mItemList.get(position);
        img.setImageResource(item.imgRes);
        txt.setText(item.name);
        checkImg.setVisibility(selectedFiltername.equals(item.name) ?View.VISIBLE:View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onFilterSelect(item);
                    selectedFiltername = item.name;
                    notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgFrame;
        RelativeLayout frame_bg_lay;
        TextView type_tv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgFrame = itemView.findViewById(R.id.imgFrame);
            frame_bg_lay = itemView.findViewById(R.id.frame_bg_lay);
            type_tv = itemView.findViewById(R.id.type_tv);

        }
    }

    private List<FilterItem> initFilters() {
        List<FilterItem> items = new LinkedList<FilterItem>();
        items.add(new FilterItem(ja.burhanrashid52.photoeditor.R.drawable.filter_default, "None", FilterType.NONE));
        items.add(new FilterItem(ja.burhanrashid52.photoeditor.R.drawable.gray, "BlackWhite", FilterType.GRAY));
        items.add(new FilterItem(ja.burhanrashid52.photoeditor.R.drawable.kuwahara, "Watercolour", FilterType.KUWAHARA));
        items.add(new FilterItem(ja.burhanrashid52.photoeditor.R.drawable.snow, "Snow", FilterType.SNOW));
        items.add(new FilterItem(ja.burhanrashid52.photoeditor.R.drawable.l1, "Lut_1", FilterType.LUT1));
        items.add(new FilterItem(ja.burhanrashid52.photoeditor.R.drawable.cameo, "Cameo", FilterType.CAMEO));
        items.add(new FilterItem(ja.burhanrashid52.photoeditor.R.drawable.l2, "Lut_2", FilterType.LUT2));
        items.add(new FilterItem(ja.burhanrashid52.photoeditor.R.drawable.l3, "Lut_3", FilterType.LUT3));
        items.add(new FilterItem(ja.burhanrashid52.photoeditor.R.drawable.l4, "Lut_4", FilterType.LUT4));
        items.add(new FilterItem(ja.burhanrashid52.photoeditor.R.drawable.l5, "Lut_5", FilterType.LUT5));
        return items;
    }

}