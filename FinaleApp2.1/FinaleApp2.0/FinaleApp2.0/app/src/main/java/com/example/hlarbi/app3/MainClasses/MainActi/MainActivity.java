package com.example.hlarbi.app3.MainClasses.MainActi;
/*After user signup, he comes here. This is the main activity. Thanks to the BottomBar Menu user can navigate between FragmentOne,FragmentTwo,FragmentThree
 * BN : in the onCreateView we make sure that the token is always refreshed since we don't want any crash while communicating with Fitbit WebApi.
 * So every time the user comes/is redirected to the MainActivity he gets a new access token using refreshtoken.
  * */
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.hlarbi.app3.API.objects.ClassRequest.APIClient;
import com.example.hlarbi.app3.API.objects.ClassRequest.GetResquest;
import com.example.hlarbi.app3.API.objects.ClassRequest.ServiceGenerator;
import com.example.hlarbi.app3.API.objects.FitBitApi.Activities;
import com.example.hlarbi.app3.API.objects.Oauth.AccessToken;
import com.example.hlarbi.app3.BuildConfig;
import com.example.hlarbi.app3.Register_Classes.LoginActivity;
import com.example.hlarbi.app3.ViewClasses.FragmentOne;
import com.example.hlarbi.app3.ViewClasses.FragmentThree;
import com.example.hlarbi.app3.ViewClasses.FragmentTwo;
import com.example.hlarbi.app3.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.hlarbi.app3.Register_Classes.LoginActivity.API_LOGIN_URL;
import static com.example.hlarbi.app3.Register_Classes.LoginActivity.sqLiteHelper;
public class MainActivity extends AppCompatActivity {
    public String dateFirst = "today";
    public static AccessToken tokenUser_id;
    public static String code;
    public static String base2;
    private DatabaseReference mCustomerDatabase;
    final SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
    public static final String client_id = "22CT2D";
    public static final String client_secret = "1a26ad3ac2d4fb2a8cfa7410bd5847bb";
    public static final String API_OAUTH_REDIRECT = "futurestudio://callback";
    String grant_type = "refresh_token";
    private BottomNavigationView mMainnav;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            mMainnav = (BottomNavigationView) findViewById(R.id.navigation);
            switch (item.getItemId()) {
                case R.id.navigation_run:
                    FragmentOne fragmentOne = new FragmentOne();
                    android.support.v4.app.FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.fram, fragmentOne, "FragmentName");
                    fragmentTransaction1.commit();
                    mMainnav.setItemBackgroundResource(R.color.june4);
                    return true;
                case R.id.navigation_pollu:
                    FragmentTwo fragmentTwo = new FragmentTwo();
                    android.support.v4.app.FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.fram, fragmentTwo, "FragmentName");
                    fragmentTransaction2.commit();
                    mMainnav.setItemBackgroundResource(R.color.june4);
                    return true;
                case R.id.navigation_profil:
                    FragmentThree fragmentThree = new FragmentThree();
                    android.support.v4.app.FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction3.replace(R.id.fram, fragmentThree, "FragmentName");
                    fragmentTransaction3.commit();
                    mMainnav.setItemBackgroundResource(R.color.june4);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentThree fragmentThree = new FragmentThree();
        android.support.v4.app.FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction3.replace(R.id.fram, fragmentThree, "FragmentName");
        fragmentTransaction3.commit();
    }
    @Override
    protected void onResume() {
        super.onResume();
        final SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        String userID;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        base2 = Credentials.basic(client_id, client_secret).substring(6);
        //Collect de données grace au lien uri
        mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

        //Si l'uri n'est pas nul on va attraper le code redonné par l'uri
        int count = sqLiteHelper.getProfilesCount();
       String code = LoginActivity.code;
       if(count==0){
            if(code != null) {
                // TODO We can probably do something with this code! Show the user that we are logging them in

                final SharedPreferences prefs = this.getSharedPreferences(
                        BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
                //Nous appelons l'intercepteur (ServiceGenerator) qui va communiquer avec le serveur
                APIClient client = ServiceGenerator.createService(APIClient.class);
                //Pour avoir le token d'acces nous devons envoyer certains parametre définis dans la document pas FitBit
                Call<AccessToken> call = client.getNewAccessToken("authorization_code",
                        client_id,
                        API_OAUTH_REDIRECT,
                        code,
                        base2);
                //Nous demandons la réponse par la commande suivante :
                call.enqueue(new Callback<AccessToken>() {
                    @Override
                    public void onResponse(final Call<AccessToken> call, Response<AccessToken> response) {
                        int statusCode = response.code();
                        if (statusCode == 200) {


                            //Nous obtenons le tokenUser_id d'acces ainsi que d'autres paramètres
                            tokenUser_id = response.body();
                            prefs.edit().putBoolean("oauth.loggedin", true).apply();
                            prefs.edit().putString("oauth.accesstoken", tokenUser_id.getAccessToken()).apply();
                            prefs.edit().putString("oauth.refreshtoken", tokenUser_id.getRefreshToken()).apply();
                            prefs.edit().putString("oauth.tokentype", tokenUser_id.getTokenType()).apply();
                            prefs.edit().putString("oauth.tokentype", tokenUser_id.getUser_ID()).apply();

                            //Dès à présent nous commencons à construire le Header qui nous permettra de demander les Datas

                            final String headertoken = " " + String.valueOf(tokenUser_id.getTokenType()) + " " + String.valueOf(tokenUser_id.getAccessToken());
                            // TODO Show the user they are logged in

                            final String usId = String.valueOf(tokenUser_id.getUser_ID());

                            final String RefreshToken = String.valueOf(tokenUser_id.getRefreshToken());

                            final String secret = String.valueOf(tokenUser_id.getClient_secret());



                            //ActivitiesCall
                            final GetResquest clientg = ServiceGenerator.createService(GetResquest.class);

                            final Map<String, String> map = new HashMap<>();

                            map.put("Authorization", headertoken);
                            final Call<Activities> callg = clientg.getActivitiesData(map,tokenUser_id.getUser_ID(),dateFirst);
                            callg.enqueue(new Callback<Activities>() {

                                @Override
                                public void onResponse(Call<Activities> call, Response<Activities> response) {
                                    Activities activities =response.body();
                                    /////////////////////////////////////////////////////////OAUTH/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////RUNNING/////////////////////////////////////////////////////////////////////////
                                    final String steps = String.valueOf(activities.getSummary().getSteps());
                                    final String calo= String.valueOf(activities.getSummary().getCaloriesOut());
                                    final String distance= String.valueOf(activities.getSummary().getDistances().get(1).getDistance());
                                    final String dura= String.valueOf(activities.getSummary().getSedentaryMinutes());
                                    final String floors= String.valueOf(activities.getSummary().getFloors());
                                    final String height= String.valueOf(activities.getSummary().getElevation());

                                    final String stepsGoal = String.valueOf(activities.getGoals().getSteps());
                                    final String caloGoal = String.valueOf(activities.getGoals().getCaloriesOut());
                                    final String distanceGoal= String.valueOf(activities.getGoals().getDistance());
                                    final String floorsGoal= String.valueOf(activities.getGoals().getFloors());
///////////////////////////////////////////////////////////////////POLLUTION//////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                    mCustomerDatabase.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                                                Map<String, Object> mapphoto = (Map<String, Object>) dataSnapshot.getValue();
                                                String city = mapphoto.get("city").toString();
                                                if(city.equals("Paris")){
                                                    String[] abrev = {"co","no2","o3","so2","pm10","pm25"};
                                                    String[] name = {"CO","NO2","O3","SO2","PM10","PM25"};
                                                    String[] fullname = {"Carbon Monoxyde","Azote Dioxyde","Ozone","Sulfur Dioxyde","Particulate Mattere 10","Particulate Matter 2,5"};
                                                    databasesetup("48.856614;2.3522219",fullname,name,abrev,"Paris");}
                                                if(city.equals("Barcelona")){
                                                    String[] abrev = {"so2","no2","o3","pm10","p","t"};
                                                    String[] name = {"SO2","NO2","O3","PM10","P","C°"};
                                                    String[] fullname = {"Sulfur Dioxyde","Azote Dioxyde","Ozone","Particulate Matter 10","Pressure","Temperature"};
                                                    databasesetup("41.390205;2.154007",fullname,name,abrev,"Barcelona");
                                                }
                                                if(city.equals("Singapore")){
                                                    String[] abrev = {"co","no2","o3","pm10","pm25","p"};
                                                    String[] name = {"CO","NO2","O3","PM10","PM25","P"};
                                                    String[] fullname = {"Carbon Monoxyde","Azote Dioxyde","Ozone","Particulate Matter 10","Particulate Mattere 25","Pressure"};
                                                    databasesetup("1.296568;103.852119",fullname,name,abrev,"Singapore");
                                                }
                                                if(city.equals("New York City")){
                                                    String[] abrev = {"co","no2","o3","pm25","p","h"};
                                                    String[] name = {"CO","NO2","O3","PM25","P","H"};
                                                    String[] fullname = {"Carbon Monoxyde","Azote Dioxyde","Ozone","Particulate Matter 2,5","Pressure","Humidity"};
                                                    databasesetup("40.730610;-73.935242",fullname,name,abrev,"New York City");

                                                }
                                                if(city.equals("Birmingham")){
                                                    String[] abrev = {"o3","no2","pm10","pm25","p","t"};
                                                    String[] name = {"CO","NO2","O3","PM25","P","H"};
                                                    String[] fullname = {"Carbon Monoxyde","Azote Dioxyde","Ozone","Particulate Matter 2,5","Pressure","Humidity"};
                                                    databasesetup("52.489471;-1.898575",fullname,name,abrev,"Birmingham");
                                                }
                                                int count = sqLiteHelper.getProfilesCount();

                                                    sqLiteHelper.insertData( "Steps",steps);
                                                    sqLiteHelper.insertData( "Calo",calo);
                                                    sqLiteHelper.insertData( "Distance",distance);
                                                    sqLiteHelper.insertData( "Dura",dura);
                                                    sqLiteHelper.insertData( "Floors",floors);
                                                    sqLiteHelper.insertData( "Height",height);

                                                    sqLiteHelper.insertDataOauth("Header Access token", headertoken);
                                                    sqLiteHelper.insertDataOauth("User Id",usId);
                                                    sqLiteHelper.insertDataOauth("Refresh Token",RefreshToken);
                                                    sqLiteHelper.insertDataOauth("Client Secret",secret);

                                                    sqLiteHelper.insertDataGoal("StepsGoal",stepsGoal);
                                                    sqLiteHelper.insertDataGoal("CaloGoal",caloGoal);
                                                    sqLiteHelper.insertDataGoal("DistanceGoal",distanceGoal);
                                                    sqLiteHelper.insertDataGoal("FloorsGoal",floorsGoal);
                                                    /////////////////////Stat//////////////////////////
                                                    sqLiteHelper.insertDataStat("TodayStat","0");
                                                    sqLiteHelper.insertDataStat("TodayStat-1","0");
                                                    sqLiteHelper.insertDataStat("TodayStat-2","0");
                                                    sqLiteHelper.insertDataStat("TodayStat-3","0");
                                                    sqLiteHelper.insertDataStat("TodayStat-4","0");
                                                    sqLiteHelper.insertDataStat("TodayStat-5","0");
                                                    sqLiteHelper.insertDataStat("TodayStat-6","0");
                                                    ///////////////////Stat///////////////////////////

                                                if(count==6){

                                                }
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });
                                }
                                @Override
                                public void onFailure(Call<Activities> call, Throwable t) {
                                }
                            });
                        } else {
                            // TODO Handle a missing code in the redirect URI
                        }
                    }
                    @Override
                    public void onFailure(Call<AccessToken> call, Throwable t) {
                    }
                });

        }
       }
       if (count==6){



      Cursor cheadertoken = db.rawQuery("SELECT * FROM OAUTHTABLE WHERE id = 1", null);
         cheadertoken.moveToFirst();
         final String headertoken  = String.valueOf(cheadertoken.getString(cheadertoken.getColumnIndex("oauthnumber")));
         Cursor cuserID = db.rawQuery("SELECT * FROM OAUTHTABLE WHERE id = 2", null);
         cuserID.moveToFirst();
         final String userIDUp  = String.valueOf(cuserID.getString(cuserID.getColumnIndex("oauthnumber")));
         Cursor crefresh = db.rawQuery("SELECT * FROM OAUTHTABLE WHERE id = 3", null);
         crefresh.moveToFirst();
         final String refresh  = String.valueOf(crefresh.getString(crefresh.getColumnIndex("oauthnumber")));
         APIClient client = ServiceGenerator.createService(APIClient.class,refresh,userIDUp,headertoken,this);
         Call<AccessToken> callrefresh = client.getRefreshAccessToken(refresh,
                 client_id,
                 client_secret,
                 API_OAUTH_REDIRECT,
                 grant_type);
         callrefresh.enqueue(new Callback<AccessToken>() {
             @Override
             public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                 int statusCode = response.code();
                 if (statusCode == 200) {
                     AccessToken tokenUser_id1 = response.body();
                     String newheadertoken ="Bearer " + String.valueOf(tokenUser_id1.getAccessToken());
                     String newusId = String.valueOf(tokenUser_id1.getUser_ID());
                     String newrefresh= String.valueOf(tokenUser_id1.getRefreshToken());

                     ContentValues cvToken = new ContentValues();
                     ContentValues cvUserId = new ContentValues();
                     ContentValues cvRefresh = new ContentValues();
                     cvToken.put("oauthnumber",newheadertoken);
                     cvUserId.put("oauthnumber",newusId);
                     cvRefresh.put("oauthnumber",newrefresh);
                     db.update("OAUTHTABLE", cvToken, "id = 1", null);
                     db.update("OAUTHTABLE", cvUserId, "id = 2", null);
                     db.update("OAUTHTABLE", cvRefresh, "id = 3", null);

                     final GetResquest clientg = ServiceGenerator.createService(GetResquest.class);

                     final Map<String, String> map = new HashMap<>();

                     map.put("Authorization", newheadertoken);
                     final Call<Activities> callg = clientg.getActivitiesData(map,tokenUser_id1.getUser_ID(),dateFirst);
                     callg.enqueue(new Callback<Activities>() {

                         @Override
                         public void onResponse(Call<Activities> call, Response<Activities> response) {
                             Activities activities =response.body();
                             final String steps = String.valueOf(activities.getSummary().getSteps());
                             final String calo= String.valueOf(activities.getSummary().getCaloriesOut());
                             final String distance= String.valueOf(activities.getSummary().getDistances().get(1).getDistance());
                             final String dura= String.valueOf(activities.getSummary().getSedentaryMinutes());
                             final String floors= String.valueOf(activities.getSummary().getFloors());
                             final String height= String.valueOf(activities.getSummary().getElevation());

                             final String stepsGoal = String.valueOf(activities.getGoals().getSteps());
                             final String caloGoal = String.valueOf(activities.getGoals().getCaloriesOut());
                             final String distanceGoal= String.valueOf(activities.getGoals().getDistance());
                             final String floorsGoal= String.valueOf(activities.getGoals().getFloors());
                             ///Running////
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

                             ///Goals///
                             ContentValues cvgoalSteps = new ContentValues();
                             ContentValues cvgoalCalo = new ContentValues();
                             ContentValues cvgoalDis = new ContentValues();
                             ContentValues cvgoalFloors = new ContentValues();

                             cvgoalSteps.put("goalnumber",stepsGoal);
                             cvgoalCalo.put("goalnumber",caloGoal);
                             cvgoalDis.put("goalnumber",distanceGoal);
                             cvgoalFloors.put("goalnumber",floorsGoal);

                             db.update("GOALTABLE", cvgoalSteps, "id = 1", null);
                             db.update("GOALTABLE", cvgoalCalo, "id = 2", null);
                             db.update("GOALTABLE", cvgoalDis, "id = 3", null);
                             db.update("GOALTABLE", cvgoalFloors, "id = 4", null);


                         }

                         @Override
                         public void onFailure(Call<Activities> call, Throwable t) {

                         }
                     });


/*
                    */

                 }
             }
             @Override
             public void onFailure(Call<AccessToken> call, Throwable t) {
             }
         });}}
    public void databasesetup (final String latlog,
                               String[] fullname,
                               String[] name ,
                               String[] abrev,
                               final String city){
        final SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        final String[] abrestr = abrev;
        final String[] namestr = name;
        final String[]fullnamestr =fullname;
        AsyncHttpClient client = new AsyncHttpClient();
        for (int i = 0; i <2 ; i++) {


        client.get("https://api.waqi.info/feed/geo:"+latlog+"/?token=c7cb1dd08fbca3cd163693d2d79efd9660a8e9a0&lat&lng&optional",
                null, new JsonHttpResponseHandler() {


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
                            int count = sqLiteHelper.getProfilesCountPollu();
                            if (count==0) {
                                sqLiteHelper.insertDataPollu(fullnamestr[0], namestr[0], number_pollu[0]);
                                sqLiteHelper.insertDataPollu(fullnamestr[1], namestr[1], number_pollu[1]);
                                sqLiteHelper.insertDataPollu(fullnamestr[2], namestr[2], number_pollu[2]);
                                sqLiteHelper.insertDataPollu(fullnamestr[3], namestr[3], number_pollu[3]);
                                sqLiteHelper.insertDataPollu(fullnamestr[4], namestr[4], number_pollu[4]);
                                sqLiteHelper.insertDataPollu(fullnamestr[5], namestr[5], number_pollu[5]);
                                ///////////AQI////////////
                                sqLiteHelper.insertDataAqi("Air Quality Index", aqinumber);
                                sqLiteHelper.insertDataAqi("City",city);
                                sqLiteHelper.insertDataAqi("LatLog",latlog);
                                //////////////AQI///////////////
                            }
                            if (count==6){
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
                                cvCity.put("aqinumber",city);
                                cvLatLog.put("aqinumber","52.489471;-1.898575");
                                db.update("AQITABLE", cvAQI, "id = 1", null);
                                db.update("AQITABLE", cvCity, "id = 2", null);
                                db.update("AQITABLE",cvLatLog,"id = 3",null);
                                ////////AQI///////////
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    } }
}