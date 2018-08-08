package com.example.hlarbi.app3.Register_Classes;
/*This is the LoginActivity : it follows this plan :
////////onCreate Method///////
1/Firebase Authentication : e-mail and password are send to Firebase and if the permission is positive a dialog appears
2/Dialog is display to warn that the User has to register into Fitbit Web Page
3/Redirection to FitBit Web Page
4/Following Oauth2 Code flow, a code is given and will be used to make AccessToken Call and get CallBack response thanks to retrofit classes
///////onResume Method/////////
5/If the code!=0, we make the post request with specific params and we get callback response with AccessToken, UserId, RefreshToken...
6/Store main callback variables to a Table we created thanks to SQLite Database. Those oauth data will be used to later to refresh the access token which last only 8 hours
7/We use AccessToken to get Fitbit data an store to a Table.
8/With AsyncHttpClient we get pollution data from AQIC Api and store values to a SQLite table.
9/For all Api calls : if the user is the first to enter into the app : database lines are created. Else we just update database.
10/Intent to MainActivity

BN : if the user is already log into the firebase : Intent to MainActivity without all this : because it has already been done*/
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hlarbi.app3.API.objects.ClassRequest.APIClient;
import com.example.hlarbi.app3.API.objects.ClassRequest.GetResquest;
import com.example.hlarbi.app3.API.objects.ClassRequest.ServiceGenerator;
import com.example.hlarbi.app3.API.objects.FitBitApi.Activities;
import com.example.hlarbi.app3.API.objects.Oauth.AccessToken;
import com.example.hlarbi.app3.BuildConfig;
import com.example.hlarbi.app3.MainClasses.MainActi.MainActivity;
import com.example.hlarbi.app3.MainClasses.MainActi.SQLiteHelper;
import com.example.hlarbi.app3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
public class LoginActivity extends AppCompatActivity {
    public static final String API_LOGIN_URL = "https://www.fitbit.com/oauth2/authorize?response_type=code";
    public static final String client_id = "22CT2D";
    public static final String client_secret = "1a26ad3ac2d4fb2a8cfa7410bd5847bb";
    public static final String API_OAUTH_REDIRECT = "futurestudio://callback";
    public static AccessToken tokenUser_id;
    public static String code;
    public static String base2;
    public static SQLiteHelper sqLiteHelper;
    public String dateFirst = "today";
    private DatabaseReference mCustomerDatabase;
    FirebaseAuth mAuth; Button btnAdd;
int i = 0;
    @Override
    protected void onStart() {
        super.onStart();
        int count = sqLiteHelper.getProfilesCount();
       if (count>0){
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }}}
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
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
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
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError("Your password needs 6 caracters");
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Not register yet", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create(); //Read Update
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
                                            Uri uri = getIntent().getData();
                                            if(uri != null && uri.toString().startsWith(API_OAUTH_REDIRECT)) {
                                                code = uri.getQueryParameter("code");
                                                Intent intenttoken = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intenttoken);
                                            i=i+1;}
                                        }
                                    });

                                    alertDialog.show();
                                }
                            }
                        });
            }
        });
    }
 @Override
 protected void onResume() {
     super.onResume();
     Uri uri = getIntent().getData();
     if(uri != null && uri.toString().startsWith(API_OAUTH_REDIRECT)) {
         code = uri.getQueryParameter("code");
         Intent intenttoken = new Intent(LoginActivity.this, MainActivity.class);
         startActivity(intenttoken);
     }
 }
}