package com.example.hlarbi.app3.MainClasses.FragOne;
/*Here is the GridView Adapter that display Running data provided from SQLite Running Table*/
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.hlarbi.app3.R;
import java.util.ArrayList;
public class RunningListAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<Running> runningsList;
    private Integer [] images = { R.drawable.icon_steps,
            R.drawable.icon_calo,
            R.drawable.icon_distance,
            R.drawable.icon_duration,
            R.drawable.icon_floors,
            R.drawable.icon_height};
    public RunningListAdapter(Context context, int layout, ArrayList<Running> runningsList) {
        this.context = context;
        this.layout = layout;
        this.runningsList = runningsList;
    }
    @Override
    public int getCount() {
        return runningsList.size();
    }
    @Override
    public Object getItem(int position) {
        return runningsList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder{
        TextView txtName, txtPrice;
    }
    @Override
    public View getView(int position,
                        View view,
                        ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtName = (TextView) row.findViewById(R.id.textView_subject_item_grid);
            holder.txtPrice = (TextView) row.findViewById(R.id.textView_number_item);

            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Running running = runningsList.get(position);

        holder.txtName.setText(running.getName());
        holder.txtPrice.setText(running.getNumbers());


        ImageView imageView = (ImageView) row.findViewById(R.id.imageView_item_grid);
        imageView.setImageResource(images[position]);

        return row;
    }
}
