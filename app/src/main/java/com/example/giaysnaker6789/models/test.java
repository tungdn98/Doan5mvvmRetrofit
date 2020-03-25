package com.example.giaysnaker6789.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class test {
    @SerializedName("id")
    @Expose
    private Integer Id;

    @SerializedName("user")
    @Expose
    private String user;

    @SerializedName("pass")
    @Expose
    private String pass;

    public test(Integer id, String user, String pass) {
        Id = id;
        this.user = user;
        this.pass = pass;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
