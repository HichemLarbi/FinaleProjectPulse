package com.example.hlarbi.app3.API.objects.FitBitApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Goals {

    @SerializedName("caloriesOut")
    @Expose
    private Double caloriesOut = 0.0;
    @SerializedName("distance")
    @Expose
    private Double distance = 0.0;
    @SerializedName("floors")
    @Expose
    private Integer floors = 0;
    @SerializedName("steps")
    @Expose
    private Integer steps = 0;

    public Double getCaloriesOut() {
        return caloriesOut;
    }

    public void setCaloriesOut(Double caloriesOut) {
        this.caloriesOut = caloriesOut;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getFloors() {
        return floors;
    }

    public void setFloors(Integer floors) {
        this.floors = floors;
    }

    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

}
