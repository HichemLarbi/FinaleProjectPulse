package com.example.hlarbi.app3.MainClasses.FragTwo;
/*Here is the GridView Adapter that display Pollution data provided from SQLite Pollution Table*/
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hlarbi.app3.R;

import java.util.ArrayList;


public class PolluListAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<Pollution> pollutionList;


    public PolluListAdapter(Context context, int layout, ArrayList<Pollution> pollutionList) {
        this.context = context;
        this.layout = layout;
        this.pollutionList = pollutionList;
    }

    @Override
    public int getCount() {
        return pollutionList.size();
    }

    @Override
    public Object getItem(int position) {
        return pollutionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView txtFullName, txtName, txtNumber;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtFullName = (TextView) row.findViewById(R.id.textView_fullname_pollu);
            holder.txtName = (TextView) row.findViewById(R.id.textView_subject_item_grid_pollu);
            holder.txtNumber = (TextView) row.findViewById(R.id.textView_number_item_pollu);

            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Pollution pollution = pollutionList.get(position);

        holder.txtFullName.setText(pollution.getFullname());
        holder.txtName.setText(pollution.getPolluname());
        holder.txtNumber.setText(pollution.getPollunumber());

        return row;
    }
}
