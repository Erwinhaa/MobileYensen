package com.example.user.login.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 28/11/2019.
 */

public class Slot {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Nama_Slot")
    @Expose
    private Object namaSlot;
    @SerializedName("Status_Slot")
    @Expose
    private Integer statusSlot;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("Gedung_Id")
    @Expose
    private Integer gedungId;
    @SerializedName("Lantai_Id")
    @Expose
    private Integer lantaiId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getNamaSlot() {
        return namaSlot;
    }

    public void setNamaSlot(Object namaSlot) {
        this.namaSlot = namaSlot;
    }

    public Integer getStatusSlot() {
        return statusSlot;
    }

    public void setStatusSlot(Integer statusSlot) {
        this.statusSlot = statusSlot;
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

    public Integer getGedungId() {
        return gedungId;
    }

    public void setGedungId(Integer gedungId) {
        this.gedungId = gedungId;
    }

    public Integer getLantaiId() {
        return lantaiId;
    }

    public void setLantaiId(Integer lantaiId) {
        this.lantaiId = lantaiId;
    }
}
