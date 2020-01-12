package com.example.user.login;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.login.Adapter.OhistoryAdapter;
import com.example.user.login.Adapter.TiketAdapter;
import com.example.user.login.Model.Tiket;
import com.example.user.login.Model.TiketResult;
import com.example.user.login.apihelper.ApiService;
import com.example.user.login.apihelper.UtilApi;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 25/12/2019.
 */

public class historyac extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    ApiService mapiservice;
    Context mcontext;
    String iduser;
    List<Tiket> listt;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historyac);

        mapiservice = UtilApi.getAPIService();
        mcontext = this;
        sharedPrefManager = new SharedPrefManager(this);

        iduser = sharedPrefManager.getSPNama();

        InitList();
    }

    public void InitList(){
        Call<TiketResult> profil = mapiservice.historder(iduser);
        profil.enqueue(new Callback<TiketResult>() {
            @Override
            public void onResponse(Call<TiketResult> call, Response<TiketResult> response) {
                listt = response.body().getTicket();
                if(listt.isEmpty()){
                    Toast.makeText(mcontext, "Belum ada pemesanan", Toast.LENGTH_SHORT).show();
                }
                else {
                    tampilkan(response.body().getTicket());
                }
            }

            @Override
            public void onFailure(Call<TiketResult> call, Throwable t) {

            }
        });
    }

    private void tampilkan(final List<Tiket> tikets) {
        OhistoryAdapter tktAdapter = new OhistoryAdapter(this, R.layout.item_history, tikets);
        listView = (ListView) findViewById(R.id.listhist);
        listView.setAdapter(tktAdapter);
        tktAdapter.notifyDataSetChanged();

    }

}
