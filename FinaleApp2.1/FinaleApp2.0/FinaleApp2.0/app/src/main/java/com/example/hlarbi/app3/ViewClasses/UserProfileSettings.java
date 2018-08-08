package com.example.hlarbi.app3.ViewClasses;
/*In this class :  the user can update his profile by change some parameters and also a profile photo
 * all new data are send to firebase and replace old ones*/
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import com.bumptech.glide.Glide;
import com.example.hlarbi.app3.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
public class UserProfileSettings extends AppCompatActivity {
    private Button mBack, mConfirm;
    private CircleImageView mProfileImage;
    private FirebaseAuth mAuth;
    private DatabaseReference mCustomerDatabase;
    private String userID;
    private String mProfileImageUrl;
    private Uri resultUri;
    private EditText inputname,inputnickname, input_age;
    private Spinner spinner_sex, spinner_city;
    private String name, firstname,age,gender,city;
    private ArrayAdapter<CharSequence> adapter_gender ;
    private  ArrayAdapter<CharSequence> adapter_city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        adapter_gender  = ArrayAdapter.createFromResource(this,R.array.gender, android.R.layout.simple_dropdown_item_1line);
        adapter_city = ArrayAdapter.createFromResource(this,R.array.city, android.R.layout.simple_dropdown_item_1line);
        mProfileImage = (CircleImageView) findViewById(R.id.profileImage);
        mBack = (Button) findViewById(R.id.back);
        mConfirm = (Button) findViewById(R.id.confirm);
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        inputname = (EditText) findViewById(R.id.name_register);
        inputnickname = (EditText) findViewById(R.id.nickname_register);
        input_age =(EditText)findViewById(R.id.age_register);
        spinner_city = (Spinner) findViewById(R.id.spinner_city);
        adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_city.setAdapter(adapter_city);
        spinner_sex = (Spinner) findViewById(R.id.spinner_gender);
        adapter_gender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_sex.setAdapter(adapter_gender);
        getUserInfo();
        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInformation();
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                return;
            }
        });
    }
    private void getUserInfo()
    {
        mCustomerDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    Map<String, Object> mapphoto = (Map<String, Object>) dataSnapshot.getValue();

                    ///////////////////User photo////////////////////////////
                    if (mapphoto.get("profileImageUrl") != null) {
                        mProfileImageUrl = mapphoto.get("profileImageUrl").toString();
                        Glide.with(getApplication()).load(mProfileImageUrl).into(mProfileImage);
                        /////////////////////User Photo//////////////////////////////
                    }
                    if (mapphoto.get("name")!=null){
                        name = mapphoto.get("name").toString();
                        inputname.setText(name);
                    }
                    if (mapphoto.get("firstname")!=null){
                        firstname = mapphoto.get("firstname").toString();
                        inputnickname.setText(firstname);
                    }
                    if (mapphoto.get("age")!=null){
                        age = mapphoto.get("age").toString();
                        input_age.setText(age);
                    }
                    if (mapphoto.get("gender")!=null){
                        gender = mapphoto.get("gender").toString();
                        int spinnerPosition = adapter_gender.getPosition(gender);
                        spinner_sex.setSelection(spinnerPosition);
                    }
                    if (mapphoto.get("city")!=null){
                        city = mapphoto.get("city").toString();
                        int spinnerPosition = adapter_city.getPosition(city);
                        spinner_city.setSelection(spinnerPosition);
                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    private void saveUserInformation() {

        String name = inputname.getText().toString().trim();
        final String nickname = inputnickname.getText().toString().trim();
        String age = input_age.getText().toString().trim();
        String emptyString = "";
        city = spinner_city.getSelectedItem().toString();
        gender = spinner_sex.getSelectedItem().toString();
        if (resultUri != null) {
            final StorageReference filePath = FirebaseStorage.getInstance().getReference().child("profile_images").child(userID);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = filePath.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    finish();
                    return;
                }
            });
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri downloadUrl = uri;
                            Map newImage = new HashMap();
                            newImage.put("profileImageUrl", downloadUrl.toString());
                            mCustomerDatabase.updateChildren(newImage);
                            finish();
                            return;
                        }

                    });
                }
            });
        } else {

            finish();
        }
        final String finalName = name;
        final String finalAge = age;
        Map newPost = new HashMap();
        newPost.put("name", finalName);
        newPost.put("firstname",nickname);
        newPost.put("age", finalAge);
        newPost.put("gender", gender);
        newPost.put("city", city);
        mCustomerDatabase.updateChildren(newPost);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            final Uri imageUri = data.getData();
            resultUri = imageUri;
            mProfileImage.setImageURI(resultUri);
        }
    }}
































































































































