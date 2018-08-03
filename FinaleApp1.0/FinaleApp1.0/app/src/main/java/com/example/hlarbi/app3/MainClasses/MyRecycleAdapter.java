package com.example.hlarbi.app3.MainClasses;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hlarbi.app3.R;
import com.example.hlarbi.app3.Register_Classes.ResetPasswordActivity;
import com.example.hlarbi.app3.UserProfile;

import static android.support.v4.content.ContextCompat.startActivity;

public class MyRecycleAdapter extends RecyclerView.Adapter{
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_setting_recyclev,parent,false);
        return new MyRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MyRecyclerViewHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return Setting_Recycle_Data.title_setting.length;
    }

    private class MyRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView mItemTile;

        private ImageView imageViewIconsetting;

        private RelativeLayout mRlayout_item;

        public MyRecyclerViewHolder(View itemView) {
            super(itemView);
            mItemTile = (TextView) itemView.findViewById(R.id.text_setting);
            imageViewIconsetting = (ImageView) itemView.findViewById(R.id.icon_setting);
            itemView.setOnClickListener(this);
        }

        public void bindView(int position) {
            mItemTile.setText(Setting_Recycle_Data.title_setting[position]);
            imageViewIconsetting.setImageResource(Setting_Recycle_Data.images_icon_setting[position]);


        }

        public void onClick(View view) {
            if (getAdapterPosition() == 0) {
                Intent i0 = new Intent(itemView.getContext(), UserProfile.class);
                startActivityInAdapter(itemView.getContext(), i0);
            }
            if (getAdapterPosition() == 1) {
                    String url = "https://goo.gl/forms/ApB3PGxWk6kFRSQx2";
                    Intent i1 = new Intent(Intent.ACTION_VIEW);
                    i1.setData(Uri.parse(url));
                    startActivityInAdapter(itemView.getContext(), i1);
                }

                if (getAdapterPosition() == 3) {
                    Intent i3 = new Intent(itemView.getContext(), ResetPasswordActivity.class);
                    startActivityInAdapter(itemView.getContext(), i3);
                }
                if (getAdapterPosition() == 4) {
                    String url = "http://www.project-pulse.eu/";
                    Intent i4 = new Intent(Intent.ACTION_VIEW);
                    i4.setData(Uri.parse(url));
                    startActivityInAdapter(itemView.getContext(), i4);
                }
            }
        }


        private void startActivityInAdapter(Context context, Intent intent) {
            startActivity(context, intent, null);
        }
    }