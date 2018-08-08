package com.example.hlarbi.app3.MainClasses.FragThree;
/*Here is the RecycleView Adapter that is used in FragmentThree*/
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.hlarbi.app3.ViewClasses.Profile_user_info;
import com.example.hlarbi.app3.R;
import com.example.hlarbi.app3.Register_Classes.LoginActivity;
import com.example.hlarbi.app3.Register_Classes.ResetPasswordActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;
import static com.example.hlarbi.app3.Register_Classes.LoginActivity.sqLiteHelper;

public class SettingRecycleAdapter extends RecyclerView.Adapter{
    final SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
    FirebaseAuth mAuth;
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
                Intent i0 = new Intent(itemView.getContext(), Profile_user_info.class);
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
        if (getAdapterPosition()==5){
            mAuth = FirebaseAuth.getInstance();
db.execSQL("drop table if exists RUNNING ");
            db.execSQL("drop table if exists OAUTHTABLE ");
            db.execSQL("drop table if exists POLLUTABLE ");
            db.execSQL("drop table if exists STATTABLE ");
            db.execSQL("drop table if exists GOALTABLE ");
            db.execSQL("drop table if exists AQITABLE ");
               mAuth.signOut();
               Intent i5 = new Intent(itemView.getContext(), LoginActivity.class);
               startActivityInAdapter(itemView.getContext(),i5);/*RUNNING(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, number VARCHAR)");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS OAUTHTABLE(Id INTEGER PRIMARY KEY AUTOINCREMENT, oauthname VARCHAR, oauthnumber VARCHAR)");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS POLLUTABLE(Id INTEGER PRIMARY KEY AUTOINCREMENT, fullname VARCHAR, polluname VARCHAR, pollunumber VARCHAR)");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS STATTABLE(Id INTEGER PRIMARY KEY AUTOINCREMENT, statname VARCHAR, statnumber VARCHAR)");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS GOALTABLE(Id INTEGER PRIMARY KEY AUTOINCREMENT, goalname VARCHAR, goalnumber VARCHAR)");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS AQITABLE(Id INTEGER PRIMARY KEY AUTOINCREMENT, aqiname VARCHAR, aqinumber VARCHAR)");*/
        }
        }
        }
        private void startActivityInAdapter(Context context, Intent intent) {
            startActivity(context, intent, null);
        }
    }