package com.example.hlarbi.app3.API.objects.FitBitApi;

/* FitBitApi Package : in this package you find the setters an getters to retrieve data from Callback response after authentification  */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Activities {

    @SerializedName("goals")
    @Expose
    private Goals goals = null;
    @SerializedName("summary")
    @Expose
    private Summary summary = null;


    public Goals getGoals() {
        return goals;
    }

    public void setGoals(Goals goals) {
        this.goals = goals;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }
}
