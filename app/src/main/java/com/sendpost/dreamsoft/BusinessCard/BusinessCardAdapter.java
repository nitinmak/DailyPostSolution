package com.sendpost.dreamsoft.BusinessCard;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sendpost.dreamsoft.Classes.Functions;
import com.sendpost.dreamsoft.Classes.Variables;
import com.sendpost.dreamsoft.R;
import com.sendpost.dreamsoft.View.ShapesImage;
import com.sendpost.dreamsoft.binding.BindingAdaptet;

import java.util.ArrayList;
import java.util.List;

public class BusinessCardAdapter extends RecyclerView.Adapter<BusinessCardAdapter.ViewHolder> {

    private Context mContext;
    SharedPreferences sharedPreferences;
    List<CardView> list = new ArrayList<>();
    List<Integer> premiumlist = new ArrayList<>();

    public BusinessCardAdapter(Context context) {
        mContext = context;
        sharedPreferences = context.getSharedPreferences(Variables.PREF_NAME,Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType){
            case 0:
                view = LayoutInflater.from(mContext).inflate(R.layout.business_card1,parent,false);
                break;
            case 1:
                view = LayoutInflater.from(mContext).inflate(R.layout.business_card2,parent,false);
                break;
            case 2:
                view = LayoutInflater.from(mContext).inflate(R.layout.business_card3,parent,false);
                break;
            case 3:
                view = LayoutInflater.from(mContext).inflate(R.layout.business_card4,parent,false);
                break;

        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public CardView getViewByPosition(int position) {
        return list.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView main_lay;
        ShapesImage bussiness_pic;
        TextView name_tv,person_name,about_tv,number_tv,email_tv,website_tv,whatsapp_tv,facebook_tv,instagram_tv,address_tv,premium_tv;
        LinearLayout whatsappvis;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            main_lay = itemView.findViewById(R.id.main_lay);
            bussiness_pic = itemView.findViewById(R.id.bussiness_pic);
            person_name = itemView.findViewById(R.id.person_name);
//          visecard = itemView.findViewById(R.id.visecard);
            name_tv = itemView.findViewById(R.id.bussiness_name);
            about_tv = itemView.findViewById(R.id.about_bussiness);
            number_tv = itemView.findViewById(R.id.number_tv);
            email_tv = itemView.findViewById(R.id.email_tv);
            website_tv = itemView.findViewById(R.id.website_tv);
            whatsapp_tv = itemView.findViewById(R.id.whatsapp_tv);
            whatsappvis = itemView.findViewById(R.id.whatsappvis);
            facebook_tv = itemView.findViewById(R.id.facebook_tv);
            instagram_tv = itemView.findViewById(R.id.instagram_tv);
            address_tv = itemView.findViewById(R.id.address_tv);
            premium_tv = itemView.findViewById(R.id.premium_tv);

            try {
                if(Functions.getSharedPreference(mContext).getString(Variables.BUSSINESS_LOGO,"").equals(""))
                    bussiness_pic.setVisibility(View.GONE);
                else
                    BindingAdaptet.setImageUrl(bussiness_pic,Functions.getSharedPreference(mContext).getString(Variables.BUSSINESS_LOGO,""));

            }catch (Exception e){}

            try {
                if(Functions.getSharedPreference(mContext).getString(Variables.BUSSINESS_NAME,"").equals(""))
                    name_tv.setVisibility(View.GONE);
                else
                    name_tv.setText(Functions.getSharedPreference(mContext).getString(Variables.BUSSINESS_NAME,""));
            }catch (Exception e){}

            try {
                if (Functions.getSharedPreference(mContext).getString(Variables.NAME, "").equals(""))
                    person_name.setVisibility(View.GONE);
                else
                    person_name.setText(Functions.getSharedPreference(mContext).getString(Variables.NAME, ""));
            } catch (Exception e) {}

            try {
                if(Functions.getSharedPreference(mContext).getString(Variables.BUSSINESS_ABOUT,"").equals("")) {
                    about_tv.setVisibility(View.GONE);
//                    visecard.setVisibility(View.GONE);
                }else
                    about_tv.setText(Functions.getSharedPreference(mContext).getString(Variables.BUSSINESS_ABOUT,""));
            }catch (Exception e){}

            try {
                if(Functions.getSharedPreference(mContext).getString(Variables.BUSSINESS_NUMBER,"").equals(""))
                    number_tv.setVisibility(View.GONE);
                else
                    number_tv.setText(Functions.getSharedPreference(mContext).getString(Variables.BUSSINESS_NUMBER,""));
            }catch (Exception e){}

            try {
                if(Functions.getSharedPreference(mContext).getString(Variables.BUSSINESS_EMAIL,"").equals(""))
                    email_tv.setVisibility(View.GONE);
                else
                    email_tv.setText(Functions.getSharedPreference(mContext).getString(Variables.BUSSINESS_EMAIL,""));
            }catch (Exception e){}

            try {
                if(Functions.getSharedPreference(mContext).getString(Variables.WEBSITE,"").equals(""))
                    website_tv.setVisibility(View.GONE);
                else
                    website_tv.setText(Functions.getSharedPreference(mContext).getString(Variables.WEBSITE,"").trim());
            }catch (Exception e){}

            try {
                if(Functions.getSharedPreference(mContext).getString(Variables.WHATSAPP,"").equals("")) {
                    whatsapp_tv.setVisibility(View.GONE);
                    whatsappvis.setVisibility(View.GONE);
                }else{
                    whatsapp_tv.setText(Functions.getSharedPreference(mContext).getString(Variables.WHATSAPP,""));
                }
            }catch (Exception e){}

            try {
                if(Functions.getSharedPreference(mContext).getString(Variables.FACEBOOK,"").equals(""))
                    facebook_tv.setVisibility(View.GONE);
                else
                    facebook_tv.setText(Functions.getSharedPreference(mContext).getString(Variables.FACEBOOK,""));
            }catch (Exception e){}

            try {
                if(Functions.getSharedPreference(mContext).getString(Variables.INSTAGRAM,"").equals(""))
                    instagram_tv.setVisibility(View.GONE);
                else
                    instagram_tv.setText(Functions.getSharedPreference(mContext).getString(Variables.INSTAGRAM,""));
            }catch (Exception e){}

            try {
                if(Functions.getSharedPreference(mContext).getString(Variables.BUSSINESS_ADDRESS,"").equals(""))
                    address_tv.setVisibility(View.GONE);
                else
                    address_tv.setText(Functions.getSharedPreference(mContext).getString(Variables.BUSSINESS_ADDRESS,""));
            }catch (Exception e){}

            if (getItemCount() != list.size()){
                try {
                    if(Functions.getSharedPreference(mContext).getString(Variables.BUSSINESS_ADDRESS,"").equals(""))
                        premium_tv.setVisibility(View.GONE);
                    else
                        premium_tv.setText(Functions.getSharedPreference(mContext).getString(Variables.BUSSINESS_ADDRESS,""));
                        premiumlist.add(1);
                }catch (Exception e){
                    premiumlist.add(0);
                }
                list.add(main_lay);
            }
        }
    }
}

