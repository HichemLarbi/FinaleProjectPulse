package com.example.hlarbi.app3.ViewClasses;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.hlarbi.app3.MainClasses.MyRecycleAdapter;
import com.example.hlarbi.app3.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentThree extends Fragment implements View.OnClickListener {
    final int REQUEST_CODE_GALLERY = 999;


    public FragmentThree() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View profile_view = inflater.inflate(R.layout.activity_setting_menu, container, false);
        RecyclerView profile_recyclerView = (RecyclerView) profile_view.findViewById(R.id.recycleview_profile);
        MyRecycleAdapter listAdapter = new MyRecycleAdapter() {
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


