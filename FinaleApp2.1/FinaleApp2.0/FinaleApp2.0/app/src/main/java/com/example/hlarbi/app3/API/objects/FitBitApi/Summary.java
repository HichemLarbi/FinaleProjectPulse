package com.example.hlarbi.app3.API.objects.FitBitApi;
/* FitBitApi Package : in this package you find the setters an getters to retrieve data from Callback response after authentification  */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Summary {

    @SerializedName("activityCalories")
    @Expose
    private Double activityCalories = 0.0;
    @SerializedName("caloriesBMR")
    @Expose
    private Double caloriesBMR = 0.0;
    @SerializedName("caloriesOut")
    @Expose
    private Double caloriesOut = 0.0;
    @SerializedName("distances")
    @Expose
    private List<Distance> distances = null;
    @SerializedName("elevation")
    @Expose
    private Double elevation = 0.0;
    @SerializedName("fairlyActiveMinutes")
    @Expose
    private Integer fairlyActiveMinutes = 0;
    @SerializedName("floors")
    @Expose
    private Integer floors = 0;
    @SerializedName("lightlyActiveMinutes")
    @Expose
    private Integer lightlyActiveMinutes = 0;
    @SerializedName("marginalCalories")
    @Expose
    private Integer marginalCalories = 0;
    @SerializedName("sedentaryMinutes")
    @Expose
    private Integer sedentaryMinutes = 0;
    @SerializedName("steps")
    @Expose
    private Integer steps = 0;
    @SerializedName("veryActiveMinutes")
    @Expose
    private Integer veryActiveMinutes = 0;

    public Double getActivityCalories() {
        return activityCalories;
    }

    public void setActivityCalories(Double activityCalories) {
        this.activityCalories = activityCalories;
    }

    public Double getCaloriesBMR() {
        return caloriesBMR;
    }

    public void setCaloriesBMR(Double caloriesBMR) {
        this.caloriesBMR = caloriesBMR;
    }

    public Double getCaloriesOut() {
        return caloriesOut;
    }

    public void setCaloriesOut(Double caloriesOut) {
        this.caloriesOut = caloriesOut;
    }

    public List<Distance> getDistances() {
        return distances;
    }

    public void setDistances(List<Distance> distances) {
        this.distances = distances;
    }

    public Double getElevation() {
        return elevation;
    }

    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }

    public Integer getFairlyActiveMinutes() {
        return fairlyActiveMinutes;
    }

    public void setFairlyActiveMinutes(Integer fairlyActiveMinutes) {
        this.fairlyActiveMinutes = fairlyActiveMinutes;
    }

    public Integer getFloors() {
        return floors;
    }

    public void setFloors(Integer floors) {
        this.floors = floors;
    }

    public Integer getLightlyActiveMinutes() {
        return lightlyActiveMinutes;
    }

    public void setLightlyActiveMinutes(Integer lightlyActiveMinutes) {
        this.lightlyActiveMinutes = lightlyActiveMinutes;
    }

    public Integer getMarginalCalories() {
        return marginalCalories;
    }

    public void setMarginalCalories(Integer marginalCalories) {
        this.marginalCalories = marginalCalories;
    }

    public Integer getSedentaryMinutes() {
        return sedentaryMinutes;
    }

    public void setSedentaryMinutes(Integer sedentaryMinutes) {
        this.sedentaryMinutes = sedentaryMinutes;
    }

    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    public Integer getVeryActiveMinutes() {
        return veryActiveMinutes;
    }

    public void setVeryActiveMinutes(Integer veryActiveMinutes) {
        this.veryActiveMinutes = veryActiveMinutes;
    }

}
