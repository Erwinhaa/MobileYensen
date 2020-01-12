package com.example.user.login.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tiket {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Mobil_Id")
    @Expose
    private String mobilId;
    @SerializedName("Slot_Id")
    @Expose
    private String slotId;
    @SerializedName("Tanggal_Order")
    @Expose
    private String tanggalOrder;
    @SerializedName("Check_In")
    @Expose
    private Object checkIn;
    @SerializedName("Check_Out")
    @Expose
    private Object checkOut;
    @SerializedName("Status_Ticket")
    @Expose
    private Integer statusTicket;
    @SerializedName("Pinalti")
    @Expose
    private Integer pinalti;
    @SerializedName("User_Id")
    @Expose
    private String userId;
    @SerializedName("Biaya")
    @Expose
    private Integer biaya;
    @SerializedName("Gedung_Id")
    @Expose
    private String gedungId;
    @SerializedName("Stat")
    @Expose
    private String stat;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobilId() {
        return mobilId;
    }

    public void setMobilId(String mobilId) {
        this.mobilId = mobilId;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public String getTanggalOrder() {
        return tanggalOrder;
    }

    public void setTanggalOrder(String tanggalOrder) {
        this.tanggalOrder = tanggalOrder;
    }

    public Object getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Object checkIn) {
        this.checkIn = checkIn;
    }

    public Object getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Object checkOut) {
        this.checkOut = checkOut;
    }

    public Integer getStatusTicket() {
        return statusTicket;
    }

    public void setStatusTicket(Integer statusTicket) {
        this.statusTicket = statusTicket;
    }

    public Integer getPinalti() {
        return pinalti;
    }

    public void setPinalti(Integer pinalti) {
        this.pinalti = pinalti;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getBiaya() {
        return biaya;
    }

    public void setBiaya(Integer biaya) {
        this.biaya = biaya;
    }

    public String getGedungId() {
        return gedungId;
    }

    public void setGedungId(String gedungId) {
        this.gedungId = gedungId;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

}