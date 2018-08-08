package com.example.hlarbi.app3.ViewClasses;
/*DialogCity class : Activity where the user can change to see pollution data from other cities
* Once he select a city the Pollution Table is updated and the adapter is notified from a change, so gridview is updated too.*/
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.hlarbi.app3.MainClasses.MainActi.MainActivity;
import com.example.hlarbi.app3.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.hlarbi.app3.Register_Classes.LoginActivity.sqLiteHelper;
public class DialogueCitybis extends AppCompatActivity {
    Button barca;
    Button birmin;
    Button NYC;
    Button paris;
    Button singapore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogue_city);
        barca = (Button)findViewById(R.id.Barcelonabtn);
        birmin = (Button)findViewById(R.id.Birminghambtn);
        NYC = (Button)findViewById(R.id.NewYorkCitybtn);
        paris = (Button)findViewById(R.id.Parisbtn);
        singapore = (Button)findViewById(R.id.Singaporebtn);
        final Intent i = new Intent(DialogueCitybis.this, MainActivity.class);
        barca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] abrev = {"so2","no2","o3","pm10","p","t"};
                String[] name = {"SO2","NO2","O3","PM10","P","C°"};
                String[] fullname = {"Sulfur Dioxyde","Azote Dioxyde","Ozone","Particulate Matter 10","Pressure","Temperature"};
                updateall("41.390205;2.154007",fullname,name,abrev);
                startActivity(i);
            }
        });
        birmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] abrev = {"o3","no2","pm10","pm25","p","t"};
                String[] name = {"CO","NO2","O3","PM25","P","H"};
                String[] fullname = {"Carbon Monoxyde","Azote Dioxyde","Ozone","Particulate Matter 2,5","Pressure","Humidity"};
                updateall("52.489471;-1.898575",fullname,name,abrev);
                startActivity(i);

            }
        });
        NYC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] abrev = {"co","no2","o3","pm25","p","h"};
                String[] name = {"CO","NO2","O3","PM25","P","H"};
                String[] fullname = {"Carbon Monoxyde","Azote Dioxyde","Ozone","Particulate Matter 2,5","Pressure","Humidity"};
                updateall("40.730610;-73.935242",fullname,name,abrev);
                startActivity(i);
            }
        });
        paris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] abrev = {"co","no2","o3","so2","pm10","pm25"};
                String[] name = {"CO","NO2","O3","SO2","PM10","PM25"};
                String[] fullname = {"Carbon Monoxyde","Azote Dioxyde","Ozone","Sulfur Dioxyde","Particulate Mattere 10","Particulate Matter 2,5"};
                updateall("48.856614;2.3522219",fullname,name,abrev);
                startActivity(i);
            }
        });
        singapore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] abrev = {"co","no2","o3","pm10","pm25","p"};
                String[] name = {"CO","NO2","O3","PM10","PM25","P"};
                String[] fullname = {"Carbon Monoxyde","Azote Dioxyde","Ozone","Particulate Matter 10","Particulate Mattere 25","Pressure"};
                updateall("1.296568;103.852119",fullname,name,abrev);
                startActivity(i);
            }
        });
    }

 public void  updateall (String latlog, String[] fullname, final String [] name, final String [] abrev){
        final SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        final String[] abrestr = abrev;
        final String[] namestr = name;
        final String[]fullnamestr =fullname;
        for (int i = 0; i <2 ; i++) {
            AsyncHttpClient client = new AsyncHttpClient();

            client.get("https://api.waqi.info/feed/geo:"+latlog+"/?token=c7cb1dd08fbca3cd163693d2d79efd9660a8e9a0&lat&lng&optional", null, new JsonHttpResponseHandler() {


         @Override
         public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
             try {
                 JSONObject data = response.getJSONObject("data");
                 String aqinumber = data.get("aqi").toString();
                 JSONObject iaqui = data.getJSONObject("iaqi");
                 String[] number_pollu = new String[]{
                         iaqui.getJSONObject(abrestr[0]).get("v").toString(),
                         iaqui.getJSONObject(abrestr[1]).get("v").toString(),
                         iaqui.getJSONObject(abrestr[2]).get("v").toString(),
                         iaqui.getJSONObject(abrestr[3]).get("v").toString(),
                         iaqui.getJSONObject(abrestr[4]).get("v").toString(),
                         iaqui.getJSONObject(abrestr[5]).get("v").toString(),
                 };
///////////////////////////////number//////////////////////////////////////
                 ContentValues cvCO = new ContentValues();
                 ContentValues cvNO2 = new ContentValues();
                 ContentValues cvO3 = new ContentValues();
                 ContentValues cvSO2 = new ContentValues();
                 ContentValues cvPM10 = new ContentValues();
                 ContentValues cvPM25 = new ContentValues();


                 cvCO.put("pollunumber",number_pollu[0]);
                 cvNO2.put("pollunumber",number_pollu[1]);
                 cvO3.put("pollunumber",number_pollu[2]);
                 cvSO2.put("pollunumber",number_pollu[3]);
                 cvPM10.put("pollunumber",number_pollu[4]);
                 cvPM25.put("pollunumber",number_pollu[5]);


                 db.update("POLLUTABLE", cvCO, "id = 1", null);
                 db.update("POLLUTABLE", cvNO2, "id = 2", null);
                 db.update("POLLUTABLE", cvO3, "id = 3", null);
                 db.update("POLLUTABLE", cvSO2, "id = 4", null);
                 db.update("POLLUTABLE", cvPM10, "id = 5", null);
                 db.update("POLLUTABLE", cvPM25, "id = 6", null);


                 ///////AQI///////////
                 ContentValues cvAQI = new ContentValues();
                 ContentValues cvCity = new ContentValues();
                 ContentValues cvLatLog = new ContentValues();
                 cvAQI.put("aqinumber",aqinumber);
                 cvCity.put("aqinumber","");
                 cvLatLog.put("aqinumber","");
                 db.update("AQITABLE", cvAQI, "id = 1", null);
                 db.update("AQITABLE", cvCity, "id = 2", null);
                 db.update("AQITABLE",cvLatLog,"id = 3",null);
                 ////////AQI///////////
                 /////////////////////////////////name/////////////////////////////////////////////
                 ContentValues cvCOn = new ContentValues();
                 ContentValues cvNO2n = new ContentValues();
                 ContentValues cvO3n = new ContentValues();
                 ContentValues cvSO2n = new ContentValues();
                 ContentValues cvPM10n = new ContentValues();
                 ContentValues cvPM25n = new ContentValues();


                 cvCOn.put("polluname",namestr[0]);
                 cvNO2n.put("polluname",namestr[1]);
                 cvO3n.put("polluname",namestr[2]);
                 cvSO2n.put("polluname",namestr[3]);
                 cvPM10n.put("polluname",namestr[4]);
                 cvPM25n.put("polluname",namestr[5]);


                 db.update("POLLUTABLE", cvCOn, "id = 1", null);
                 db.update("POLLUTABLE", cvNO2n, "id = 2", null);
                 db.update("POLLUTABLE", cvO3n, "id = 3", null);
                 db.update("POLLUTABLE", cvSO2n, "id = 4", null);
                 db.update("POLLUTABLE", cvPM10n, "id = 5", null);
                 db.update("POLLUTABLE", cvPM25n, "id = 6", null);
                 //////////////////////////////name/////////////////////////////////////////////

                 //////////////////////////////fullname/////////////////////////////////////////////
                 ContentValues cvCOnf = new ContentValues();
                 ContentValues cvNO2nf = new ContentValues();
                 ContentValues cvO3nf = new ContentValues();
                 ContentValues cvSO2nf = new ContentValues();
                 ContentValues cvPM10nf = new ContentValues();
                 ContentValues cvPM25nf = new ContentValues();


                 cvCOnf.put("fullname",fullnamestr[0]);
                 cvNO2nf.put("fullname",fullnamestr[1]);
                 cvO3nf.put("fullname",fullnamestr[2]);
                 cvSO2nf.put("fullname",fullnamestr[3]);
                 cvPM10nf.put("fullname",fullnamestr[4]);
                 cvPM25nf.put("fullname",fullnamestr[5]);


                 db.update("POLLUTABLE", cvCOnf, "id = 1", null);
                 db.update("POLLUTABLE", cvNO2nf, "id = 2", null);
                 db.update("POLLUTABLE", cvO3nf, "id = 3", null);
                 db.update("POLLUTABLE", cvSO2nf, "id = 4", null);
                 db.update("POLLUTABLE", cvPM10nf, "id = 5", null);
                 db.update("POLLUTABLE", cvPM25nf, "id = 6", null);
                 //////////////////////////////fullname/////////////////////////////////////////////
             } catch (JSONException e) {
             }
         }
     });
 i+=1;
     }
 }
}
