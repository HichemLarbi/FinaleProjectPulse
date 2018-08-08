package com.example.hlarbi.app3.ViewClasses;
/*FragmentTwo : display data from Pollution Table in a GridView and has a button to open linechart activity.
* NB : linechart operates but API AQIC to not provide a forecast of the aqi*/
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import com.example.hlarbi.app3.MainClasses.FragTwo.PolluListAdapter;
import com.example.hlarbi.app3.MainClasses.FragTwo.Pollution;
import com.example.hlarbi.app3.R;
import java.util.ArrayList;
import static com.example.hlarbi.app3.Register_Classes.LoginActivity.sqLiteHelper;
public class FragmentTwo extends Fragment{
    final SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
    ArrayList<Pollution> list;
    PolluListAdapter adapter;
    TextView aqiTextView;
    public FragmentTwo()  {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        // Inflate the layout for this fragment
        final View pollu_view = inflater.inflate(R.layout.activity_data_pollution, container, false);
        final Button spinner_city = (Button) pollu_view.findViewById(R.id.spinner_choose_city);
        spinner_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), DialogueCitybis.class);
                startActivity(i);
                adapter.notifyDataSetChanged();
            }
        });
        ////////////////////AQI//////////////////////
/////////////////////////////////SET AQI//////////////////////////////////////////
        Cursor caqi = db.rawQuery("SELECT * FROM AQITABLE WHERE id = 1", null);
        if( caqi == null){

        }
        caqi.moveToFirst();
        final String aqi = String.valueOf(caqi.getString(caqi.getColumnIndex("aqinumber")));
        aqiTextView = pollu_view.findViewById(R.id.textAqiView);
        aqiTextView.setText(aqi);
/////////////////////////////////SET AQI//////////////////////////////////////////
        Button b = (Button) pollu_view.findViewById(R.id.popup);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity((new Intent(getContext(), PopLineChartPollu.class)));
            }
        });
        GridView ls = (GridView) pollu_view.findViewById(R.id.grid_view_data_pollu);
        list = new ArrayList<>();
        adapter = new PolluListAdapter(getContext(), R.layout.item_sample_gridview_pollu, list);
        ls.setAdapter(adapter);
        Cursor cursor = sqLiteHelper.getData("SELECT * FROM POLLUTABLE");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String fullname = cursor.getString(1);
            String polluname = cursor.getString(2);
            String pollunumber = cursor.getString(3);
            list.add(new Pollution(fullname, polluname, pollunumber, id));
        }
        adapter.notifyDataSetChanged();

        // Inflate the layout for this fragment

        return pollu_view;
    }
}