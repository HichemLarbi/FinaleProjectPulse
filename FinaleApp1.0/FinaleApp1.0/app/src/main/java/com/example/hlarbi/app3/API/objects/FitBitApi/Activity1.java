package com.example.hlarbi.app3.API.objects.FitBitApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Activity1 {

    @SerializedName("activityId")
    @Expose
    private Integer activityId = 0;
    @SerializedName("activityParentId")
    @Expose
    private Integer activityParentId = 0;
    @SerializedName("calories")
    @Expose
    private Double calories = 0.0;
    @SerializedName("description")
    @Expose
    private String description = "";
    @SerializedName("distance")
    @Expose
    private Double distance = 0.0;
    @SerializedName("duration")
    @Expose
    private Integer duration = 0;
    @SerializedName("hasStartTime")
    @Expose
    private Boolean hasStartTime = false;
    @SerializedName("isFavorite")
    @Expose
    private Boolean isFavorite = false;
    @SerializedName("logId")
    @Expose
    private Integer logId = 0;
    @SerializedName("name")
    @Expose
    private String name = "";
    @SerializedName("startTime")
    @Expose
    private String startTime = "";
    @SerializedName("steps")
    @Expose
    private Integer steps = 0;

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getActivityParentId() {
        return activityParentId;
    }

    public void setActivityParentId(Integer activityParentId) {
        this.activityParentId = activityParentId;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Boolean getHasStartTime() {
        return hasStartTime;
    }

    public void setHasStartTime(Boolean hasStartTime) {
        this.hasStartTime = hasStartTime;
    }

    public Boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }
}
