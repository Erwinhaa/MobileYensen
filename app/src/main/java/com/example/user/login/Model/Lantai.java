package com.example.user.login.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lantai {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Nama_Lantai")
    @Expose
    private String namaLantai;
    @SerializedName("Gedung_Id")
    @Expose
    private Integer gedungId;
    @SerializedName("Jumlah_Slot")
    @Expose
    private Integer jumlahSlot;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamaLantai() {
        return namaLantai;
    }

    public void setNamaLantai(String namaLantai) {
        this.namaLantai = namaLantai;
    }

    public Integer getGedungId() {
        return gedungId;
    }

    public void setGedungId(Integer gedungId) {
        this.gedungId = gedungId;
    }

    public Integer getJumlahSlot() {
        return jumlahSlot;
    }

    public void setJumlahSlot(Integer jumlahSlot) {
        this.jumlahSlot = jumlahSlot;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

}