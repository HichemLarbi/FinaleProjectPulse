package com.example.hlarbi.app3.API.objects.ClassRequest;

/* FitBitApi Package : in this package you find the main http request that are used for Retrofit Classes.
 * Here are the only get request which is used with Activities setters and gettters
  * */

import com.example.hlarbi.app3.API.objects.FitBitApi.Activities;


import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;

public interface GetResquest {

    @GET("/1/user/{user_id}/activities/date/{date}.json")
    Call<Activities> getActivitiesData(
            @HeaderMap Map<String, String> headers,
            @Path("user_id") String value,
            @Path("date") String value1
    );}
