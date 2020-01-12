package com.example.user.login.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 17/12/2019.
 */

public class DetailTiket {

    @SerializedName("ticket")
    @Expose
    private List<Tiket> ticket = null;
    @SerializedName("namagedung")
    @Expose
    private String namagedung;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("namaslot")
    @Expose
    private String namaslot;
    @SerializedName("namalantai")
    @Expose
    private String namalantai;
    @SerializedName("jam")
    @Expose
    private Integer jam;
    @SerializedName("menit")
    @Expose
    private Integer menit;
    @SerializedName("detik")
    @Expose
    private Integer detik;
    @SerializedName("jumlah")
    @Expose
    private Integer jumlah;

    public List<Tiket> getTicket() {
        return ticket;
    }

    public void setTicket(List<Tiket> ticket) {
        this.ticket = ticket;
    }

    public String getNamagedung() {
        return namagedung;
    }

    public void setNamagedung(String namagedung) {
        this.namagedung = namagedung;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNamaslot() {
        return namaslot;
    }

    public void setNamaslot(String namaslot) {
        this.namaslot = namaslot;
    }

    public String getNamalantai() {
        return namalantai;
    }

    public void setNamalantai(String namalantai) {
        this.namalantai = namalantai;
    }

    public Integer getJam() {
        return jam;
    }

    public void setJam(Integer jam) {
        this.jam = jam;
    }

    public Integer getMenit() {
        return menit;
    }

    public void setMenit(Integer menit) {
        this.menit = menit;
    }

    public Integer getDetik() {
        return detik;
    }

    public void setDetik(Integer detik) {
        this.detik = detik;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

}

