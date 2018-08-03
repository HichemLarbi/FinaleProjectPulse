package com.example.hlarbi.app3.MainClasses;

public class OauthDataBase {
    private String oauthname;
    private String oauthnumber;
    private int id;

    public OauthDataBase(int id, String oauthname, String oauthnumber) {
        this.id = id;
        this.oauthname = oauthname;
        this.oauthnumber = oauthnumber;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOauthname() {
        return oauthname;
    }

    public void setOauthname(String oauthname) {
        this.oauthname = oauthname;
    }

    public String getOauthnumber() {
        return oauthnumber;
    }

    public void setOauthnumber(String oauthnumber) {
        this.oauthnumber = oauthnumber;
    }






}
