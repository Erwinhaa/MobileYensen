package com.example.user.login.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 08/12/2019.
 */

public class TiketResult {
    @SerializedName("ticket")
    @Expose
    private List<Tiket> ticket = null;

    public List<Tiket> getTicket() {
        return ticket;
    }

    public void setTicket(List<Tiket> ticket) {
        this.ticket = ticket;
    }
}
