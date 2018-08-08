package com.example.hlarbi.app3.ViewClasses;
/*A simple listview which retrieves User data from Firebase*/
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.bumptech.glide.Glide;
import com.example.hlarbi.app3.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import de.hdodenhof.circleimageview.CircleImageView;
public class Profile_user_info extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference mCustomerDatabase;
    String userID;
    String name;
    String firstname;
    String age;
    String city;
    String gender;
    ListView listView;
    de.hdodenhof.circleimageview.CircleImageView circleImageView;
    ImageView imageView;
    String mProfileImageUrl;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_information_user);
        listView = (ListView) findViewById(R.id.listview_user_profile_information);
        circleImageView = (CircleImageView) findViewById(R.id.photo_profile_info);
        relativeLayout = (RelativeLayout) findViewById(R.id.edit_profile_relative_layout);
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Profile_user_info.this,UserProfileSettings.class);
                startActivity(in);
            }
        });
        mCustomerDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    Map<String, Object> mapphoto = (Map<String, Object>) dataSnapshot.getValue();
                    ///////////////////User photo////////////////////////////
                    mProfileImageUrl = mapphoto.get("profileImageUrl").toString();
                    Glide.with(Profile_user_info.this).load(mProfileImageUrl).into(circleImageView);
                    /////////////////////User Photo//////////////////////////////
                     name = mapphoto.get("name").toString();
                     firstname = mapphoto.get("firstname").toString();
                    age = mapphoto.get("age").toString();
                    gender = mapphoto.get("gender").toString();
                     city = mapphoto.get("city").toString();
                }
                List<String> values = new ArrayList<>();
                values.add("Name : " + name + " " + firstname);
                values.add("You live in " + city);
                values.add("You are  " + age + " years old");
                values.add("Gender : " + gender);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                        (Profile_user_info.this, android.R.layout.simple_list_item_1, values);
                listView.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}