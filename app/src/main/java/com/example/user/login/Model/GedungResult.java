package com.example.user.login.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GedungResult {

    @SerializedName("gedung")
    @Expose
    private List<Gedung> gedung = null;
    @SerializedName("lantai")
    @Expose
    private List<Lantai> lantai = null;
    @SerializedName("slot")
    @Expose
    private List<Slot> slot = null;

    public List<Gedung> getGedung() {
        return gedung;
    }

    public void setGedung(List<Gedung> gedung) {
        this.gedung = gedung;
    }

    public List<Lantai> getLantai() {
        return lantai;
    }

    public void setLantai(List<Lantai> lantai) {
        this.lantai = lantai;
    }

    public List<Slot> getSlot() {
        return slot;
    }

    public void setSlot(List<Slot> slot) {
        this.slot = slot;
    }

}
