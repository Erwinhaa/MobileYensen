package com.example.user.login.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gedung {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Nama_Gedung")
    @Expose
    private String namaGedung;
    @SerializedName("Alamat")
    @Expose
    private String alamat;
    @SerializedName("IsFull")
    @Expose
    private Integer isFull;
    @SerializedName("Operator_Id")
    @Expose
    private Integer operatorId;
    @SerializedName("Status_Gedung")
    @Expose
    private Integer statusGedung;
    @SerializedName("Jumlah_Lantai")
    @Expose
    private Integer jumlahLantai;
    @SerializedName("Biaya")
    @Expose
    private Integer biaya;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamaGedung() {
        return namaGedung;
    }

    public void setNamaGedung(String namaGedung) {
        this.namaGedung = namaGedung;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Integer getIsFull() {
        return isFull;
    }

    public void setIsFull(Integer isFull) {
        this.isFull = isFull;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getStatusGedung() {
        return statusGedung;
    }

    public void setStatusGedung(Integer statusGedung) {
        this.statusGedung = statusGedung;
    }

    public Integer getJumlahLantai() {
        return jumlahLantai;
    }

    public void setJumlahLantai(Integer jumlahLantai) {
        this.jumlahLantai = jumlahLantai;
    }

    public Integer getBiaya() {
        return biaya;
    }

    public void setBiaya(Integer biaya) {
        this.biaya = biaya;
    }

}