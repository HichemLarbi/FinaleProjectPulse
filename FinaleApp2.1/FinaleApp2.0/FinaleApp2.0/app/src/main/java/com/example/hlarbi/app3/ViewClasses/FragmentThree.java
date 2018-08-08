package com.example.hlarbi.app3.ViewClasses;
/*Provide a Recycle View and a profile photo
* NB : the onclick event is set in the adapter.
 * */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.hlarbi.app3.MainClasses.FragThree.SettingRecycleAdapter;
import com.example.hlarbi.app3.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
public class FragmentThree extends Fragment implements View.OnClickListener {
    de.hdodenhof.circleimageview.CircleImageView circleImageView;
    String mProfileImageUrl;
    FirebaseAuth mAuth;
    DatabaseReference mCustomerDatabase;
    String userID;
    Button bntname;
    String name;
    public FragmentThree() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             final Bundle savedInstanceState) {
       final View profile_view = inflater.inflate(R.layout.activity_setting_menu, container, false);
        circleImageView = (CircleImageView) profile_view.findViewById(R.id.profileImageMenu);
        bntname =(Button) profile_view.findViewById(R.id.buttonname) ;
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        mCustomerDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    Map<String, Object> mapphoto = (Map<String, Object>) dataSnapshot.getValue();
                    ///////////////////User photo////////////////////////////
                    mProfileImageUrl = mapphoto.get("profileImageUrl").toString();
                    name = mapphoto.get("name").toString();
                }
                Glide.with(getActivity()).load(mProfileImageUrl).into(circleImageView);
                bntname.setText(name);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
circleImageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i = new Intent(getContext(),Profile_user_info.class);
        startActivity(i); }
});
        RecyclerView profile_recyclerView = (RecyclerView) profile_view.findViewById(R.id.recycleview_profile);
        SettingRecycleAdapter listAdapter = new SettingRecycleAdapter() {
        };
        profile_recyclerView.setAdapter(listAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL,false);
        profile_recyclerView.setLayoutManager(layoutManager);
        return profile_view;
    }

    @Override
    public void onClick(View v) {

    }
}


