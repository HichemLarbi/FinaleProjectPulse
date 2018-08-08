package com.example.hlarbi.app3.API.objects.FitBitApi;
/* FitBitApi Package : in this package you find the setters an getters to retrieve data from Callback response after authentification  */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Distance {

    @SerializedName("activity")
    @Expose
    private String activity = "";
    @SerializedName("distance")
    @Expose
    private Double distance = 0.0;

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

}
