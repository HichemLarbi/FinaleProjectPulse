package com.example.hlarbi.app3.API.objects.FitBitApi;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Activities {

    @SerializedName("activities")
    @Expose
    private List<Activity1> activities = null;
    @SerializedName("goals")
    @Expose
    private Goals goals = null;
    @SerializedName("summary")
    @Expose
    private Summary summary = null;

    public List<Activity1> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity1> activities) {
        this.activities = activities;
    }

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
