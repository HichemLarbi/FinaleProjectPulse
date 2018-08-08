package com.example.hlarbi.app3.ViewClasses;
/*This activity : display linechart from linechart_animation_pollu package
* NB : Data has to be update but APIC Api do not provide forecast for the moment
* */
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.hlarbi.app3.R;
import com.example.hlarbi.app3.ViewClasses.linechart_animation_pollu.ChartView;
import com.example.hlarbi.app3.ViewClasses.linechart_animation_pollu.InputData;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.hlarbi.app3.Register_Classes.LoginActivity.sqLiteHelper;

public class PopLineChartPollu extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_linechart);
        RelativeLayout relativeLayout;
               ViewGroup viewGroup = (ViewGroup) ((ViewGroup) (findViewById(android.R.id.content))).getChildAt(0);
        initViews(viewGroup);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width*.8),(int) (height*.6));
    }
    private void initViews(View v) {
        ChartView chartView = findViewById(R.id.charView);
        List<InputData> dataList = createChartData();
        chartView.setData(dataList);
    }
    @NonNull
    private List<InputData> createChartData() {
        final SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
        List<InputData> dataList = new ArrayList<>();
        dataList.add(new InputData(28));
        dataList.add(new InputData(25));
        dataList.add(new InputData(31));
        dataList.add(new InputData(29));
        dataList.add(new InputData(32));
        dataList.add(new InputData(20));
        dataList.add(new InputData(28));
//X axe => add date number
        long currMillis = System.currentTimeMillis();
        currMillis -= currMillis % TimeUnit.DAYS.toMillis(1);
        for (int i = 0; i < dataList.size(); i++) {
            long position = dataList.size() - 1 - i;
            long offsetMillis = TimeUnit.DAYS.toMillis(position);
            long millis = currMillis - offsetMillis;
            dataList.get(i).setMillis(millis);
        }
        return dataList;
    }
}
