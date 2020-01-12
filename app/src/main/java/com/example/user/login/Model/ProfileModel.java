package com.example.user.login.Model;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by Avin on 11/07/2018.
 */

public class ProfileModel {
    @SerializedName("mobil")
    @Expose
    private List<Object> mobil = null;
    @SerializedName("user")
    @Expose
    private List<UserModel> user = null;

    public List<Object> getMobil() {
        return mobil;
    }

    public void setMobil(List<Object> mobil) {
        this.mobil = mobil;
    }

    public List<UserModel> getUser() {
        return user;
    }

    public void setUser(List<UserModel> user) {
        this.user = user;
    }
}
