package com.example.hlarbi.app3.MainClasses;

public class GoalRunning {
    private String goalnumber;
    private String goalname;
    private int id;

    public GoalRunning(String goalname, String goalnumber, int id) {
        this.goalname = goalname;
        this.goalnumber = goalnumber;
        this.id = id;
    }



    public String getGoalname() {
        return goalname;
    }

    public void setGoalname(String goalname) {
        this.goalname = goalname;
    }

    public String getGoalnumber() {
        return goalnumber;
    }

    public void setGoalnumber(String goalnumber) {
        this.goalnumber = goalnumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
