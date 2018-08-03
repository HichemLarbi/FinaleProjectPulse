package com.example.hlarbi.app3.API.objects.ProfileUserFitbit;

import com.example.hlarbi.app3.API.objects.FitBitApi.Activities;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;

public interface GetResquestUserProfile {

    @GET("/1/user/-/profile.json")
    Call<ProfileUserFitbit> getUserProfile(
                    @HeaderMap Map<String, String> headers

    );}
