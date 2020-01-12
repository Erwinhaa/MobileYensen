package com.example.user.login.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 08/01/2020.
 */

public class HSResult {
    @SerializedName("history")
    @Expose
    private List<History> history = null;

    public List<History> getHistory() {
        return history;
    }

    public void setHistory(List<History> history) {
        this.history = history;
    }
}
