package com.example.user.login.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 09/01/2020.
 */

public class NotifResult {
    @SerializedName("notif")
    @Expose
    private List<Notif> notif = null;
    @SerializedName("slot1")
    @Expose
    private String slot1;
    @SerializedName("slot2")
    @Expose
    private String slot2;
    @SerializedName("gedungid")
    @Expose
    private String gedungid;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("detik")
    @Expose
    private Integer detik;

    public List<Notif> getNotif() {
        return notif;
    }

    public void setNotif(List<Notif> notif) {
        this.notif = notif;
    }

    public String getSlot1() {
        return slot1;
    }

    public void setSlot1(String slot1) {
        this.slot1 = slot1;
    }

    public String getSlot2() {
        return slot2;
    }

    public void setSlot2(String slot2) {
        this.slot2 = slot2;
    }

    public String getGedungid() {
        return gedungid;
    }

    public void setGedungid(String gedungid) {
        this.gedungid = gedungid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDetik() {
        return detik;
    }

    public void setDetik(Integer detik) {
        this.detik = detik;
    }
}
