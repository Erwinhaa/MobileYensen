package com.example.user.login.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 25/09/2019.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("mobil")
    @Expose
    private List<Mobil> mobil = null;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("saldo")
    @Expose
    private Integer saldo;
    @SerializedName("nohp")
    @Expose
    private String nohp;

    public List<Mobil> getMobil() {
        return mobil;
    }

    public void setMobil(List<Mobil> mobil) {
        this.mobil = mobil;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

}



