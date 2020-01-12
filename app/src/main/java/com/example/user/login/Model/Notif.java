package com.example.user.login.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 09/01/2020.
 */

public class Notif {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("User_Id")
    @Expose
    private Integer userId;
    @SerializedName("Operator_Id")
    @Expose
    private Integer operatorId;
    @SerializedName("Ticket_Id")
    @Expose
    private Integer ticketId;
    @SerializedName("Keluhan")
    @Expose
    private String keluhan;
    @SerializedName("Solusi")
    @Expose
    private String solusi;
    @SerializedName("Tanggal")
    @Expose
    private String tanggal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }

    public String getSolusi() {
        return solusi;
    }

    public void setSolusi(String solusi) {
        this.solusi = solusi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

}
