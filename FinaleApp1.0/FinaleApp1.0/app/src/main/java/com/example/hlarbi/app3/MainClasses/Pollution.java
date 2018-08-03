package com.example.hlarbi.app3.MainClasses;

public class Pollution {
    private int id;
    private String fullname;
    private String polluname;
    private String pollunumber;

    public Pollution(String fullname,String polluname, String pollunumber, int id) {
        this.fullname = fullname;
        this.polluname = polluname;
        this.pollunumber = pollunumber;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPolluname() {
        return polluname;
    }

    public void setPolluname(String polluname) {
        this.polluname = polluname;
    }

    public String getPollunumber() {
        return pollunumber;
    }

    public void setPollunumber(String pollunumber) {
        this.pollunumber = pollunumber;
    }










}
