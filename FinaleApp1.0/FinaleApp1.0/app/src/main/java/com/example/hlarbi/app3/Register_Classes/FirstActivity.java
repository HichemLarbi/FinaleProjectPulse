package com.example.hlarbi.app3.Register_Classes;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hlarbi.app3.API.APIClient;
import com.example.hlarbi.app3.API.GetResquest;
import com.example.hlarbi.app3.API.ServiceGenerator;
import com.example.hlarbi.app3.API.objects.FitBitApi.Activities;
import com.example.hlarbi.app3.API.objects.Oauth.AccessToken;
import com.example.hlarbi.app3.API.objects.ProfileUserFitbit.GetResquestUserProfile;
import com.example.hlarbi.app3.API.objects.ProfileUserFitbit.ProfileUserFitbit;
import com.example.hlarbi.app3.BuildConfig;
import com.example.hlarbi.app3.MainClasses.MainActivity;
import com.example.hlarbi.app3.MainClasses.SQLiteHelper;
import com.example.hlarbi.app3.R;
import com.example.hlarbi.app3.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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

public class FirstActivity extends AppCompatActivity {

    public static final String useridExtra = "";
    public String dateFirst = "today";

    // TODO Replace this with your own data
    public static final String API_LOGIN_URL = "https://www.fitbit.com/oauth2/authorize?response_type=code";
    public static final String client_id = "22CT2D";
    public static final String client_secret = "1a26ad3ac2d4fb2a8cfa7410bd5847bb";
    public static final String API_OAUTH_REDIRECT = "futurestudio://callback";
    public static final String content_type = "application/x-www-form-urlencoded";
    public static AccessToken tokenUser_id;


    Bundle extras = new Bundle();
    EditText edtName, edtNumber;
    Button btnChoose, btnAdd, btnList;
    ImageView imageView;

    public static SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstactivity);

        final EditText inputEmail = (EditText) findViewById(R.id.email);
        final EditText inputPassword = (EditText) findViewById(R.id.password);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.changement_login);
        Button btnSignup = (Button) findViewById(R.id.btn_signup);
        btnAdd = (Button) findViewById(R.id.btn_login);
        Button btnReset = (Button) findViewById(R.id.btn_reset_password);

        //Animation Background
        LinearLayout mLayout = (LinearLayout) findViewById(R.id.myLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) mLayout.getBackground();
        animationDrawable.setEnterFadeDuration(4500);
        animationDrawable.setExitFadeDuration(4500);
        animationDrawable.start();
////////////////////////////DATA BASE/////////////////////////


        sqLiteHelper = new SQLiteHelper(this, "RunningDB.sqlite", null, 1);

        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS RUNNING(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, number VARCHAR)");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS OAUTHTABLE(Id INTEGER PRIMARY KEY AUTOINCREMENT, oauthname VARCHAR, oauthnumber VARCHAR)");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS POLLUTABLE(Id INTEGER PRIMARY KEY AUTOINCREMENT, fullname VARCHAR, polluname VARCHAR, pollunumber VARCHAR)");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS STATTABLE(Id INTEGER PRIMARY KEY AUTOINCREMENT, statname VARCHAR, statnumber VARCHAR)");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS GOALTABLE(Id INTEGER PRIMARY KEY AUTOINCREMENT, goalname VARCHAR, goalnumber VARCHAR)");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS AQITABLE(Id INTEGER PRIMARY KEY AUTOINCREMENT, aqiname VARCHAR, aqinumber VARCHAR)");


        ////////////////////////////DATA BASE////////////////////////


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(FirstActivity.this, SignupActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this, ResetPasswordActivity.class));
            }
        });
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(FirstActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError("Your password needs 6 caracters");
                                    } else {
                                        Toast.makeText(FirstActivity.this, "Not register yet", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    AlertDialog alertDialog = new AlertDialog.Builder(FirstActivity.this).create(); //Read Update
                                    alertDialog.setTitle("Fitbit webpage");
                                    alertDialog.setMessage("You'll be redirected a moment into Fitbit Webpage. Please register to it.");

                                    alertDialog.setButton("Continue..", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // here you can add functions
                                            Intent intent = new Intent(
                                                    Intent.ACTION_VIEW,
                                                    Uri.parse(API_LOGIN_URL + "&client_id=" + client_id + "&redirect_uri=" + API_OAUTH_REDIRECT + "&scope=activity&expired_in=604800"));
                                            // This flag is set to prevent the browser with the login form from showing in the history stack
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                            //On ferme la le navi et maintenant on va passer à la méthode OnResume
                                            startActivity(intent);
                                            finish();
                                        }
                                    });

                                    alertDialog.show();  //<-- See This

                                }
                            }
                        });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        final SQLiteDatabase db = sqLiteHelper.getWritableDatabase();


        String base2 = Credentials.basic(client_id, client_secret).substring(6);

        //Collect de données grace au lien uri
        Uri uri = getIntent().getData();
        //Si l'uri n'est pas nul on va attraper le code redonné par l'uri
        if(uri != null && uri.toString().startsWith(API_OAUTH_REDIRECT)) {
            String code = uri.getQueryParameter("code");
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

                            //ProfileCall
                            final GetResquestUserProfile clienprofile = ServiceGenerator.createService(GetResquestUserProfile.class);
                            final Map<String, String> mapProfile = new HashMap<>();
                            mapProfile.put("Authorization", headertoken);
                            final Call<ProfileUserFitbit> callProfile = clienprofile.getUserProfile(mapProfile);
                            callProfile.enqueue(new Callback<ProfileUserFitbit>() {
                                @Override
                                public void onResponse(Call<ProfileUserFitbit> call, Response<ProfileUserFitbit> response) {
                                    ProfileUserFitbit profileUserFitbit = response.body();
                                }

                                @Override
                                public void onFailure(Call<ProfileUserFitbit> call, Throwable t) {

                                }
                            });

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
                                    String steps = String.valueOf(activities.getSummary().getSteps());
                                    String calo= String.valueOf(activities.getSummary().getCaloriesOut());
                                    String distance= String.valueOf(activities.getSummary().getDistances().get(1).getDistance());
                                    String dura= String.valueOf(activities.getSummary().getSedentaryMinutes());
                                    String floors= String.valueOf(activities.getSummary().getFloors());
                                    String height= String.valueOf(activities.getSummary().getElevation());

                                    String stepsGoal = String.valueOf(activities.getGoals().getSteps());
                                    String caloGoal = String.valueOf(activities.getGoals().getCaloriesOut());
                                    String distanceGoal= String.valueOf(activities.getGoals().getDistance());
                                    String floorsGoal= String.valueOf(activities.getGoals().getFloors());

                                    AsyncHttpClient client = new AsyncHttpClient();

                                    client.get("https://api.waqi.info/feed/geo:48.856614;2.3522219/?token=c7cb1dd08fbca3cd163693d2d79efd9660a8e9a0&lat&lng&optional", null, new JsonHttpResponseHandler() {


                                        @Override
                                        public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {


                                            try {

                                                JSONObject data = response.getJSONObject("data");
                                                String aqinumber = data.get("aqi").toString();
                                                JSONObject iaqui = data.getJSONObject("iaqi");

                                                String[] number_pollu = new String[]{
                                                        iaqui.getJSONObject("co").get("v").toString(),
                                                        iaqui.getJSONObject("no2").get("v").toString(),
                                                        iaqui.getJSONObject("o3").get("v").toString(),
                                                        iaqui.getJSONObject("so2").get("v").toString(),
                                                        iaqui.getJSONObject("pm10").get("v").toString(),
                                                        iaqui.getJSONObject("pm25").get("v").toString(),
                                                        };
                                                int count = sqLiteHelper.getProfilesCountPollu();
                                                if (count==0) {
                                                    sqLiteHelper.insertDataPollu("Monoxyde de carbone", "CO", number_pollu[0]);
                                                    sqLiteHelper.insertDataPollu("Dioxyde d'azote", "NO2", number_pollu[1]);
                                                    sqLiteHelper.insertDataPollu("Ozone", "O3", number_pollu[2]);
                                                    sqLiteHelper.insertDataPollu("Dioxyde de soufre", "SO2", number_pollu[3]);
                                                    sqLiteHelper.insertDataPollu("Particulate Matter 10", "PM10", number_pollu[4]);
                                                    sqLiteHelper.insertDataPollu("Particulate Matter 2.5", "PM25", number_pollu[5]);
                                                  ///////////AQI////////////
                                                    sqLiteHelper.insertDataAqi("Air Quality Index", aqinumber);
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
                                                    cvAQI.put("aqinumber",aqinumber);
                                                    db.update("AQITABLE", cvAQI, "id = 1", null);
                                                    ////////AQI///////////

                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    });
                                    int count = sqLiteHelper.getProfilesCount();
                                    if (count==0){
                                        sqLiteHelper.insertData( "Steps",steps);
                                        sqLiteHelper.insertData( "Calo",calo);
                                        sqLiteHelper.insertData( "Distance",distance);
                                        sqLiteHelper.insertData( "Dura",dura);
                                        sqLiteHelper.insertData( "Floors",floors);
                                        sqLiteHelper.insertData( "Height",height);

                                        sqLiteHelper.insertDataOauth("Header Access token", headertoken);
                                        sqLiteHelper.insertDataOauth("User Id",usId);

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

                                        Intent intenttoken = new Intent(FirstActivity.this, UserProfile.class);
                                        startActivity(intenttoken);




                                    }
                                    if(count==6){
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

                                        ///Token and User Id///
                                        ContentValues cvToken = new ContentValues();
                                        ContentValues cvUserId = new ContentValues();

                                        cvToken.put("oauthnumber",headertoken);
                                        cvUserId.put("oauthnumber",usId);

                                        db.update("OAUTHTABLE", cvToken, "id = 1", null);
                                        db.update("OAUTHTABLE", cvUserId, "id = 2", null);






                                        ///////////////////////////STAT//////////////////////////////////////


                                        ContentValues cvStatT0 = new ContentValues();
                                        cvStatT0.put("statnumber","0");


                                        db.update("STATTABLE", cvStatT0, "id = 1", null);
                                        db.update("STATTABLE", cvStatT0, "id = 2", null);
                                        db.update("STATTABLE",cvStatT0, "id = 3", null);
                                        db.update("STATTABLE", cvStatT0, "id = 4", null);
                                        db.update("STATTABLE",cvStatT0, "id = 5", null);
                                        db.update("STATTABLE", cvStatT0, "id = 6", null);
                                        db.update("STATTABLE", cvStatT0, "id = 7", null);

                                        //////////////////////////STAT//////////////////////////////////////

                                         Intent intenttoken = new Intent(FirstActivity.this, UserProfile.class);
                                        startActivity(intenttoken);

                                    }

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


    }

}