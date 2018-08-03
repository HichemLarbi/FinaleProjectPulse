package com.example.hlarbi.app3.Register_Classes;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.hlarbi.app3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword, inputname,inputnickname, input_age;
    private Button btnSignIn, btnSignUp;
    private Spinner spinner_sex, spinner_city;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    LinearLayout mLayout;
    AnimationDrawable animationDrawable;
    private String city, gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        //Animation Background
        mLayout = (LinearLayout) findViewById(R.id.myLayout);
        animationDrawable = (AnimationDrawable) mLayout.getBackground();
        animationDrawable.setEnterFadeDuration(4500);
        animationDrawable.setExitFadeDuration(4500);
        animationDrawable.start();

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputname = (EditText) findViewById(R.id.name_register);
        inputnickname = (EditText) findViewById(R.id.nickname_register);
        input_age =(EditText)findViewById(R.id.age_register);

        spinner_city = (Spinner) findViewById(R.id.spinner_city);
        ArrayAdapter<CharSequence> adapter_city =ArrayAdapter.createFromResource(this,R.array.city, android.R.layout.simple_spinner_item);
        adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_city.setAdapter(adapter_city);

        spinner_sex = (Spinner) findViewById(R.id.spinner_gender);
        ArrayAdapter<CharSequence> adapter_gender =ArrayAdapter.createFromResource(this,R.array.gender, android.R.layout.simple_spinner_item);
        adapter_gender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_sex.setAdapter(adapter_gender);



        progressBar = (ProgressBar) findViewById(R.id.changement_login);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String name = inputname.getText().toString().trim();
                final String nickname = inputnickname.getText().toString().trim();
                String age = input_age.getText().toString().trim();
                String emptyString = "";
                city = spinner_city.getSelectedItem().toString();
                gender = spinner_sex.getSelectedItem().toString();


                if (TextUtils.isEmpty(nickname)){
                    Toast.makeText(getApplicationContext(), "Enter Firstname!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(name)){
                    name = emptyString;
                }
                if (TextUtils.isEmpty(age)){
                    age = emptyString;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                final String finalName = name;
                final String finalAge = age;
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    String user_id = auth.getCurrentUser().getUid();
                                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                                    Map newPost = new HashMap();
                                    newPost.put("name", finalName);
                                    newPost.put("firstname",nickname);
                                    newPost.put("age", finalAge);
                                    newPost.put("gender", gender);
                                    newPost.put("city", city);
                                    newPost.put("email", email);
                                    current_user_db.setValue(newPost);
                                    startActivity(new Intent(SignupActivity.this, FirstActivity.class));
                                    finish();
                                }
                            }
                        });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
