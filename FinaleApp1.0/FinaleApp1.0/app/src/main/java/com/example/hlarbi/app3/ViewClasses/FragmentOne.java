package com.example.hlarbi.app3.ViewClasses;


import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.hlarbi.app3.API.APIClient;
import com.example.hlarbi.app3.API.GetResquest;
import com.example.hlarbi.app3.API.ServiceGenerator;
import com.example.hlarbi.app3.API.objects.FitBitApi.Activities;
import com.example.hlarbi.app3.API.objects.Oauth.AccessToken;
import com.example.hlarbi.app3.MainClasses.OauthDataBase;
import com.example.hlarbi.app3.Register_Classes.FirstActivity;
import com.example.hlarbi.app3.R;
import com.example.hlarbi.app3.MainClasses.Running;
import com.example.hlarbi.app3.MainClasses.RunningListAdapter;
import com.example.hlarbi.app3.StatActivity;
import com.google.android.gms.common.data.TextFilterable;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.hlarbi.app3.Register_Classes.FirstActivity.sqLiteHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOne extends Fragment implements View.OnClickListener{
    public static final String client_id = "22CT2D";
    public static final String client_secret = "1a26ad3ac2d4fb2a8cfa7410bd5847bb";
    public static final String API_OAUTH_REDIRECT = "futurestudio://callback";
    String grant_type = "refresh_token";

    final SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
    private ViewFlipper viewFlipper;
    GridView gridView;
    ArrayList<Running> list;
    ArrayList<OauthDataBase> OauthDBList;
    RunningListAdapter adapter = null;
    OauthDataBase oauthDataBase;

    ///UPDATE BUTTON///
    private Button mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    public TextView chosenDate;
    public String dateFirst = "today";
    private Button refresh_fitbit;

    public ViewFlipper getViewFlipper(ViewFlipper vf) {
        vf = getActivity().findViewById(R.id.view_flipper);

        return vf;
    }



    public FragmentOne() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View v = inflater.inflate(R.layout.activity_data_resume, container, false);
        GridView gridView = (GridView) v.findViewById(R.id.grid_view_data_resume);






/////////////////////////////////////////////////////////////////////////////////////////TOKEN///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////Headertoken//////
        Cursor cheadertoken = db.rawQuery("SELECT * FROM OAUTHTABLE WHERE id = 1", null);
        cheadertoken.moveToFirst();
        final String headertoken  = String.valueOf(cheadertoken.getString(cheadertoken.getColumnIndex("oauthnumber")));
        /////User id//////
        Cursor cuserID = db.rawQuery("SELECT * FROM OAUTHTABLE WHERE id = 2", null);
        cuserID.moveToFirst();
        final String userID  = String.valueOf(cuserID.getString(cuserID.getColumnIndex("oauthnumber")));
/////////////////////////////////////////////////////////////////////////////////////////TOKEN///////////////////////////////////////////////////////////////////////////////////////////////////////////////



        //////////////////////STATS////////////////////////
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.addHeader("Authorization", headertoken);
                    client.get("https://api.fitbit.com/1/user/"+userID+"/activities/steps/date/today/7d.json", null, new JsonHttpResponseHandler() {


                        @Override
                        public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                            try {
                                JSONObject jsonobject = new JSONObject(String.valueOf(response));
                                //dht11JSONbject = jsonobject.getJSONObject("result");


                                List<String> allNames = new ArrayList<String>();
                                JSONArray cast = jsonobject.getJSONArray("activities-steps");
                                for (int i=0; i<cast.length(); i++) {
                                    ContentValues cvStat = new ContentValues();
                                    String idStat = new String();
                                    idStat = "id = " + String.valueOf(i);
                                    JSONObject parametrosdht11 = cast.getJSONObject(i);
                                    String datavalue = parametrosdht11.getString("value");
                                    cvStat.put("statnumber",datavalue);
                                    db.update("STATTABLE", cvStat, idStat, null);

                                }






                            } catch (JSONException e) {
                                e.printStackTrace();
                            }




                            Intent i1 = new Intent(v.getContext(), StatActivity.class);
                            startActivity(i1);
                        }});}
                if (position==1){
                    AsyncHttpClient client1 = new AsyncHttpClient();
                    client1.addHeader("Authorization", headertoken);
                    client1.get("https://api.fitbit.com/1/user/"+userID+"/activities/calories/date/today/7d.json", null, new JsonHttpResponseHandler() {


                        @Override
                        public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                            try {
                                JSONObject jsonobject = new JSONObject(String.valueOf(response));
                                //dht11JSONbject = jsonobject.getJSONObject("result");


                                List<String> allNames = new ArrayList<String>();
                                JSONArray cast = jsonobject.getJSONArray("activities-calories");
                                for (int i=0; i<cast.length(); i++) {
                                    ContentValues cvStat = new ContentValues();
                                    String idStat = new String();
                                    idStat = "id = " + String.valueOf(i);
                                    JSONObject parametrosdht11 = cast.getJSONObject(i);
                                    String datavalue = parametrosdht11.getString("value");
                                    cvStat.put("statnumber",datavalue);
                                    db.update("STATTABLE", cvStat, idStat, null);

                                }






                            } catch (JSONException e) {
                                e.printStackTrace();
                            }




                            Intent i1 = new Intent(v.getContext(), StatActivity.class);
                            startActivity(i1);
                        }});
                }
                if (position==2){Toast.makeText(v.getContext(),"Dis",Toast.LENGTH_SHORT).show();
                    AsyncHttpClient client2 = new AsyncHttpClient();
                    client2.addHeader("Authorization", headertoken);
                    client2.get("https://api.fitbit.com/1/user/"+userID+"/activities/distance/date/today/7d.json", null, new JsonHttpResponseHandler() {


                        @Override
                        public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                            try {
                                JSONObject jsonobject = new JSONObject(String.valueOf(response));
                                //dht11JSONbject = jsonobject.getJSONObject("result");


                                List<String> allNames = new ArrayList<String>();
                                JSONArray cast = jsonobject.getJSONArray("activities-distance");
                                for (int i=0; i<cast.length(); i++) {
                                    ContentValues cvStat = new ContentValues();
                                    String idStat = new String();
                                    idStat = "id = " + String.valueOf(i);
                                    JSONObject parametrosdht11 = cast.getJSONObject(i);
                                    String datavalue = parametrosdht11.getString("value");
                                    cvStat.put("statnumber",datavalue);
                                    db.update("STATTABLE", cvStat, idStat, null);

                                }






                            } catch (JSONException e) {
                                e.printStackTrace();
                            }




                            Intent i1 = new Intent(v.getContext(), StatActivity.class);
                            startActivity(i1);
                        }});}
                if (position==3){Toast.makeText(v.getContext(),"Dura",Toast.LENGTH_SHORT).show();
                    AsyncHttpClient client3 = new AsyncHttpClient();
                    client3.addHeader("Authorization", headertoken);
                    client3.get("https://api.fitbit.com/1/user/"+userID+"/activities/minutesSedentary/date/today/7d.json", null, new JsonHttpResponseHandler() {


                        @Override
                        public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                            try {
                                JSONObject jsonobject = new JSONObject(String.valueOf(response));
                                //dht11JSONbject = jsonobject.getJSONObject("result");


                                List<String> allNames = new ArrayList<String>();
                                JSONArray cast = jsonobject.getJSONArray("activities-minutesSedentary");
                                for (int i=0; i<cast.length(); i++) {
                                    ContentValues cvStat = new ContentValues();
                                    String idStat = new String();
                                    idStat = "id = " + String.valueOf(i);
                                    JSONObject parametrosdht11 = cast.getJSONObject(i);
                                    String datavalue = parametrosdht11.getString("value");
                                    cvStat.put("statnumber",datavalue);
                                    db.update("STATTABLE", cvStat, idStat, null);

                                }






                            } catch (JSONException e) {
                                e.printStackTrace();
                            }




                            Intent i1 = new Intent(v.getContext(), StatActivity.class);
                            startActivity(i1);
                        }});}
                if (position==4){Toast.makeText(v.getContext(),"Floor",Toast.LENGTH_SHORT).show();
                    AsyncHttpClient client4 = new AsyncHttpClient();
                    client4.addHeader("Authorization", headertoken);
                    client4.get("https://api.fitbit.com/1/user/"+userID+"/activities/floors/date/today/7d.json", null, new JsonHttpResponseHandler() {


                        @Override
                        public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                            try {
                                JSONObject jsonobject = new JSONObject(String.valueOf(response));
                                //dht11JSONbject = jsonobject.getJSONObject("result");


                                List<String> allNames = new ArrayList<String>();
                                JSONArray cast = jsonobject.getJSONArray("activities-floors");
                                for (int i=0; i<cast.length(); i++) {
                                    ContentValues cvStat = new ContentValues();
                                    String idStat = new String();
                                    idStat = "id = " + String.valueOf(i);
                                    JSONObject parametrosdht11 = cast.getJSONObject(i);
                                    String datavalue = parametrosdht11.getString("value");
                                    cvStat.put("statnumber",datavalue);
                                    db.update("STATTABLE", cvStat, idStat, null);

                                }






                            } catch (JSONException e) {
                                e.printStackTrace();
                            }




                            Intent i1 = new Intent(v.getContext(), StatActivity.class);
                            startActivity(i1);
                        }});}
                if (position==5){Toast.makeText(v.getContext(),"Height",Toast.LENGTH_SHORT).show();
                    AsyncHttpClient client5 = new AsyncHttpClient();
                    client5.addHeader("Authorization", headertoken);
                    client5.get("https://api.fitbit.com/1/user/"+userID+"/activities/elevation/date/today/7d.json", null, new JsonHttpResponseHandler() {


                        @Override
                        public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                            try {
                                JSONObject jsonobject = new JSONObject(String.valueOf(response));
                                //dht11JSONbject = jsonobject.getJSONObject("result");


                                List<String> allNames = new ArrayList<String>();
                                JSONArray cast = jsonobject.getJSONArray("activities-elevation");
                                for (int i=0; i<cast.length(); i++) {
                                    ContentValues cvStat = new ContentValues();
                                    String idStat = new String();
                                    idStat = "id = " + String.valueOf(i);
                                    JSONObject parametrosdht11 = cast.getJSONObject(i);
                                    String datavalue = parametrosdht11.getString("value");
                                    cvStat.put("statnumber",datavalue);
                                    db.update("STATTABLE", cvStat, idStat, null);

                                }






                            } catch (JSONException e) {
                                e.printStackTrace();
                            }




                            Intent i1 = new Intent(v.getContext(), StatActivity.class);
                            startActivity(i1);
                        }});

            }
        }});
        ////////////////////STATS////////////////////////




        mDisplayDate =(Button) v.findViewById(R.id.buttondate);
        refresh_fitbit = (Button)v.findViewById(R.id.refresh_fitbit);


        ////////////////////FlipperView////////////////////////
        Button bL = (Button) v.findViewById(R.id.bvflipperL);
        Button bR = (Button) v.findViewById(R.id.bvflipperR);
        bL.setOnClickListener(this);
        bR.setOnClickListener(this);
        ////////////////////////FlipperView////////////////////////////////

///////////////////////////////////////////////////////////////////////////PogressBar//////////////////////////////////////////////////////////////////////////////////////////////////////////






/////////////////////////////Steps//////////////////////////////
        final ProgressBar progressBarSteps = (ProgressBar) v.findViewById(R.id.progress_steps_id);
        final TextView PercenttextSteps = (TextView) v.findViewById(R.id.steps_goal_text_id);
        Cursor csteps = db.rawQuery("SELECT * FROM RUNNING WHERE id = 1", null);
        csteps.moveToFirst();
        final String steps  = String.valueOf(csteps.getString(csteps.getColumnIndex("number")));
        Cursor cstepsG = db.rawQuery("SELECT * FROM GOALTABLE WHERE id = 1", null);
        cstepsG.moveToFirst();
        final String stepsG  = String.valueOf(cstepsG.getString(cstepsG.getColumnIndex("goalnumber")));
        int percentsteps = Math.round(100 * Float.valueOf(steps) / (Float.valueOf(stepsG)));
        progressBarSteps.setProgress((int) percentsteps);
        PercenttextSteps.setText(String.valueOf(percentsteps));

        /////////////////////////////////Steps//////////////////////////////////////////

        ///////////////////////////////////////Calo////////////////////////////////////////////
        final ProgressBar progressBarCalo = (ProgressBar) v.findViewById(R.id.progress_calo_id);
        final TextView PercenttextCalo = (TextView) v.findViewById(R.id.calo_goal_text_id);
        Cursor ccalo = db.rawQuery("SELECT * FROM RUNNING WHERE id = 2", null);
        ccalo.moveToFirst();
        final String calo  = String.valueOf(ccalo.getString(ccalo.getColumnIndex("number")));
        Cursor ccaloG = db.rawQuery("SELECT * FROM GOALTABLE WHERE id = 2", null);
        ccaloG.moveToFirst();
        final String caloG  = String.valueOf(ccaloG.getString(ccaloG.getColumnIndex("goalnumber")));
        int percentcalo = Math.round(100 * Float.valueOf(calo) / (Float.valueOf(caloG)));
        progressBarCalo.setProgress((int) percentcalo);
        PercenttextCalo.setText(String.valueOf(percentcalo));
        ///////////////////////////////////////Calo////////////////////////////////////////////


        ///////////////////////////////////////Distance////////////////////////////////////////////
        final ProgressBar progressBarDis = (ProgressBar) v.findViewById(R.id.progress_distance_id);
        final TextView PercenttextDis = (TextView) v.findViewById(R.id.distance_goal_text_id);
        Cursor cdis = db.rawQuery("SELECT * FROM RUNNING WHERE id = 3", null);
        cdis.moveToFirst();
        final String dis  = String.valueOf(cdis.getString(cdis.getColumnIndex("number")));
        Cursor cdisG = db.rawQuery("SELECT * FROM GOALTABLE WHERE id = 3", null);
        cdisG.moveToFirst();
        final String disG  = String.valueOf(cdisG.getString(cdisG.getColumnIndex("goalnumber")));
        int percentdis = Math.round(100 * Float.valueOf(dis) / (Float.valueOf(disG)));
        progressBarDis.setProgress((int) percentdis);
        PercenttextDis.setText(String.valueOf(percentdis));
        ///////////////////////////////////////Distance////////////////////////////////////////////

        ///////////////////////////////////////Floors////////////////////////////////////////////
        final ProgressBar progressBarflo = (ProgressBar) v.findViewById(R.id.progress_floors_id);
        final TextView Percenttextflo = (TextView) v.findViewById(R.id.floors_goal_text_id);
        Cursor cflo = db.rawQuery("SELECT * FROM RUNNING WHERE id = 5", null);
        cflo.moveToFirst();
        final String flo  = String.valueOf(cflo.getString(cflo.getColumnIndex("number")));
        Cursor cfloG = db.rawQuery("SELECT * FROM GOALTABLE WHERE id = 4", null);
        cfloG.moveToFirst();
        final String floG  = String.valueOf(cfloG.getString(cfloG.getColumnIndex("goalnumber")));
        final int percentflo = Math.round(100 * Float.valueOf(flo) / (Float.valueOf(floG)));
        progressBarflo.setProgress((int) percentflo);
        Percenttextflo.setText(String.valueOf(percentflo));
        ///////////////////////////////////////Floors////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////PogressBar////////////////////////////////////////////////////////////////////////////////////////////////////////
        list = new ArrayList<>();
        adapter = new RunningListAdapter(getContext(), R.layout.item_sample_gridview_run, list);
        gridView.setAdapter(adapter);

        Cursor cursor = sqLiteHelper.getData("SELECT * FROM RUNNING");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String number = cursor.getString(2);
            list.add(new Running(name, number, id));
        }
        adapter.notifyDataSetChanged();

        refresh_fitbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                APIClient clientrefresh = ServiceGenerator.createService(APIClient.class, FirstActivity.tokenUser_id,getActivity());
                //Pour avoir le token d'acces nous devons envoyer certains parametre définis dans la document pas FitBit
                Call<AccessToken> callrefresh = clientrefresh.getRefreshAccessToken(String.valueOf(FirstActivity.tokenUser_id.getRefreshToken()),
                        client_id,
                        client_secret,
                        API_OAUTH_REDIRECT,
                        grant_type);
                //Nous demandons la réponse par la commande suivante :
                callrefresh.enqueue(new Callback<AccessToken>() {
                    @Override
                    public void onResponse(final Call<AccessToken> call, Response<AccessToken> response) {
                        int statusCode = response.code();
                        if (statusCode == 200) {

                            //Nous obtenons le tokenUser_id d'acces ainsi que d'autres paramètres
                            AccessToken tokenUser_id1 = response.body();
                            String accesstokenrefresh = String.valueOf(tokenUser_id1.getAccessToken());
                            String useridrefresh = String.valueOf(tokenUser_id1.getClientID());
                            ///Token and User Id///
                            ContentValues cvToken = new ContentValues();
                            ContentValues cvUserId = new ContentValues();

                            cvToken.put("oauthnumber",accesstokenrefresh);
                            cvUserId.put("oauthnumber",useridrefresh);

                            db.update("OAUTHTABLE", cvToken, "id = 2", null);
                            db.update("OAUTHTABLE", cvUserId, "id = 3", null);


                            Toast.makeText(getActivity(),"Refreshed",Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<AccessToken> call, Throwable t) {
                        Toast.makeText(getActivity(),"Failed to refresh. Please try to relog.",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                month = month + 1;


                String date = year + "-" + month + "-" + day;
                //      chosenDate.setText(date);

                final GetResquest clientg = ServiceGenerator.createService(GetResquest.class);

                final Map<String, String> map = new HashMap<>();

                map.put("Authorization", headertoken);
                final Call<Activities> calld = clientg.getActivitiesData(map, userID, String.valueOf(date));
                calld.enqueue(new Callback<Activities>() {
                    @Override
                    public void onResponse(Call<Activities> call, Response<Activities> response) {

                        // textView.setText(String.valueOf(activities.getSummary().getSteps()));
                        Activities actvi = response.body();
                        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();

                        String steps = String.valueOf(actvi.getSummary().getSteps());
                        String calo= String.valueOf(actvi.getSummary().getCaloriesOut());
                        String distance= String.valueOf(actvi.getSummary().getDistances().get(1).getDistance());
                        String dura= String.valueOf(actvi.getSummary().getSedentaryMinutes());
                        String floors= String.valueOf(actvi.getSummary().getFloors());
                        String height= String.valueOf(actvi.getSummary().getElevation());

                        String goalsteps = String.valueOf(actvi.getGoals().getSteps());
                        String goalfloor  = String.valueOf(actvi.getGoals().getFloors());
                        String goalcalo = String.valueOf(actvi.getGoals().getCaloriesOut());
                        String goaldis = String.valueOf(actvi.getGoals().getDistance());

                        ///////////Progress Steps///////////
                        int percentsteps = Math.round(100 * Float.valueOf(steps) / (Float.valueOf(goalsteps)));
                        progressBarSteps.setProgress((percentsteps));
                        PercenttextSteps.setText(String.valueOf(percentsteps));
                        ////////////Progress Calo/////////////
                        int percentcalo = Math.round(100 * Float.valueOf(calo) / (Float.valueOf(goalcalo)));
                        progressBarCalo.setProgress((percentcalo));
                        PercenttextCalo.setText(String.valueOf(percentcalo));
                        //////////Progress Dis/////////////
                        int percentdis = Math.round(100 * Float.valueOf(distance) / (Float.valueOf(goaldis)));
                        progressBarDis.setProgress((percentdis));
                        PercenttextDis.setText(String.valueOf(percentdis));
                        /////////Progress floo////////
                        int percentfloor = Math.round(100 * Float.valueOf(floors) / (Float.valueOf(goalfloor)));
                        progressBarflo.setProgress((percentfloor));
                        Percenttextflo.setText(String.valueOf(percentfloor));


                        ////////////////////////Data value//////////////////////
                        ContentValues cvSteps = new ContentValues();
                        ContentValues cvCalo = new ContentValues();
                        ContentValues cvDis = new ContentValues();
                        ContentValues cvDura = new ContentValues();
                        ContentValues cvFloors = new ContentValues();
                        ContentValues cvHeight = new ContentValues();

                        cvSteps.put("number",steps);
                        cvCalo.put("number",calo);
                        cvDis.put("number",distance);
                        cvDura.put("number",dura);
                        cvFloors.put("number",floors);
                        cvHeight.put("number",height);

                        db.update("RUNNING", cvSteps, "id = 1", null);
                        db.update("RUNNING", cvCalo, "id = 2", null);
                        db.update("RUNNING", cvDis, "id = 3", null);
                        db.update("RUNNING", cvDura, "id = 4", null);
                        db.update("RUNNING", cvFloors, "id = 5", null);
                        db.update("RUNNING", cvHeight, "id = 6", null);

                        updateRunningList();
                    }

                    @Override
                    public void onFailure(Call<Activities> call, Throwable t) {
                        Toast.makeText(getActivity(),"Please try to refresh",Toast.LENGTH_SHORT).show();
                    }
                });

            }

        };

        return v;
    }



    private void updateRunningList(){
        // get all data from sqlite
        Cursor cursor = sqLiteHelper.getData("SELECT * FROM RUNNING");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String number = cursor.getString(2);


            list.add(new Running(name, number,id));
        }
        adapter.notifyDataSetChanged();




    }

    public void previousView(View v) {
        getViewFlipper(viewFlipper).setInAnimation(getContext(), R.anim.slide_in_right);

        getViewFlipper(viewFlipper).showPrevious();
    }
    public void nextView(View v) {
        getViewFlipper(viewFlipper).setInAnimation(getContext(), R.anim.slide_out_right);

        getViewFlipper(viewFlipper).setOutAnimation(getContext(), R.anim.slide_in_left);

        getViewFlipper(viewFlipper).showNext();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bvflipperL:
                previousView(v);
                break;
            case R.id.bvflipperR:
                nextView(v);
                break;



        }

            }


}













































/*       //Calendar

        mDisplayDate = (Button) v.findViewById(R.id.buttondate);


        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                month = month + 1;


                String date = year + "-" + month + "-" + day;

                final GetResquest clientg = ServiceGenerator.createService(GetResquest.class);

                final Map<String, String> map = new HashMap<>();

                map.put("Authorization", headertoken);
                final Call<Activities> calld = clientg.getActivitiesData(map,token,String.valueOf(date));
                calld.enqueue(new Callback<Activities>() {
                    @Override
                    public void onResponse(Call<Activities> call, Response<Activities> response) {
                        Activities activities1 =response.body();


                    }

                    @Override
                    public void onFailure(Call<Activities> call, Throwable t) {

                    }
                });

            }

        };*/