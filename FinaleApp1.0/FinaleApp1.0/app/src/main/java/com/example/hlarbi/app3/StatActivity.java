package com.example.hlarbi.app3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.example.hlarbi.app3.Register_Classes.FirstActivity.sqLiteHelper;

public class StatActivity extends AppCompatActivity {
    final SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
    BarChart barChart;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.chart_bar_);
///////////////////////////////////////////TODAY STAT////////////////////////////////////////////////////////////
        Cursor cstat0 = db.rawQuery("SELECT * FROM STATTABLE WHERE id = 1", null);
        cstat0.moveToFirst();
        final Float stat0  = Float.valueOf(String.valueOf(cstat0.getString(cstat0.getColumnIndex("statnumber"))));
///////////////////////////////////////////TODAY STAT////////////////////////////////////////////////////////////

///////////////////////////////////////////TODAY STAT-1////////////////////////////////////////////////////////////
        Cursor cstat1 = db.rawQuery("SELECT * FROM STATTABLE WHERE id = 2", null);
        cstat1.moveToFirst();
        final Float stat1  = Float.valueOf(String.valueOf(cstat1.getString(cstat1.getColumnIndex("statnumber"))));
///////////////////////////////////////////TODAY STAT-1////////////////////////////////////////////////////////////

///////////////////////////////////////////TODAY STAT-2////////////////////////////////////////////////////////////
        Cursor cstat2 = db.rawQuery("SELECT * FROM STATTABLE WHERE id = 3", null);
        cstat2.moveToFirst();
        final Float stat2  = Float.valueOf(String.valueOf(cstat2.getString(cstat2.getColumnIndex("statnumber"))));
///////////////////////////////////////////TODAY STAT-2////////////////////////////////////////////////////////////


///////////////////////////////////////////TODAY STAT-3////////////////////////////////////////////////////////////
        Cursor cstat3 = db.rawQuery("SELECT * FROM STATTABLE WHERE id = 4", null);
        cstat3.moveToFirst();
        final Float stat3  = Float.valueOf(String.valueOf(cstat3.getString(cstat3.getColumnIndex("statnumber"))));
///////////////////////////////////////////TODAY STAT-3////////////////////////////////////////////////////////////

///////////////////////////////////////////TODAY STAT-4////////////////////////////////////////////////////////////
        Cursor cstat4 = db.rawQuery("SELECT * FROM STATTABLE WHERE id = 5", null);
        cstat4.moveToFirst();
        final Float stat4  = Float.valueOf(String.valueOf(cstat4.getString(cstat4.getColumnIndex("statnumber"))));
///////////////////////////////////////////TODAY STAT-4////////////////////////////////////////////////////////////

///////////////////////////////////////////TODAY STAT-5////////////////////////////////////////////////////////////
        Cursor cstat5 = db.rawQuery("SELECT * FROM STATTABLE WHERE id = 6", null);
        cstat5.moveToFirst();
        final Float stat5  = Float.valueOf(String.valueOf(cstat5.getString(cstat5.getColumnIndex("statnumber"))));
///////////////////////////////////////////TODAY STAT-5////////////////////////////////////////////////////////////

///////////////////////////////////////////TODAY STAT-6////////////////////////////////////////////////////////////
        Cursor cstat6 = db.rawQuery("SELECT * FROM STATTABLE WHERE id = 7", null);
        cstat6.moveToFirst();
        final Float stat6  = Float.valueOf(String.valueOf(cstat6.getString(cstat6.getColumnIndex("statnumber"))));
///////////////////////////////////////////TODAY STAT-6////////////////////////////////////////////////////////////




        barChart =(BarChart)findViewById(R.id.barchart);
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1,stat0));
        barEntries.add(new BarEntry(2,stat1));
        barEntries.add(new BarEntry(3,stat2));
        barEntries.add(new BarEntry(4,stat3));
        barEntries.add(new BarEntry(5,stat4));
        barEntries.add(new BarEntry(6,stat5));
        barEntries.add(new BarEntry(7,stat6));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Days");

        barChart.setDrawGridBackground(true);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData theData = new BarData(barDataSet);

        barChart.setData(theData);
        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);

    }


}
