package com.example.user.login;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.login.Adapter.OhistoryAdapter;
import com.example.user.login.Adapter.THistoryAdapter;
import com.example.user.login.Model.HSResult;
import com.example.user.login.Model.History;
import com.example.user.login.apihelper.ApiService;
import com.example.user.login.apihelper.UtilApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 08/01/2020.
 */

public class historytrx extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    ApiService mapiservice;
    Context mcontext;
    String iduser;
    List<History> listt;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historysal);

        mapiservice = UtilApi.getAPIService();
        mcontext = this;
        sharedPrefManager = new SharedPrefManager(this);

        iduser = sharedPrefManager.getSPNama();

        InitList();
    }

    public void InitList(){
        Call<HSResult> profil = mapiservice.histsaldo(iduser);
        profil.enqueue(new Callback<HSResult>() {
            @Override
            public void onResponse(Call<HSResult> call, Response<HSResult> response) {
                listt = response.body().getHistory();
                if(listt.isEmpty()){
                    Toast.makeText(mcontext, "Belum ada pengisian saldo", Toast.LENGTH_SHORT).show();
                }
                else {
                    tampilkan(response.body().getHistory());
                }
            }

            @Override
            public void onFailure(Call<HSResult> call, Throwable t) {

            }
        });
    }

    private void tampilkan(final List<History> saldos) {
        THistoryAdapter tktAdapter = new THistoryAdapter(this, R.layout.itemhsaldo, saldos);
        listView = (ListView) findViewById(R.id.listhist);
        listView.setAdapter(tktAdapter);
        tktAdapter.notifyDataSetChanged();

    }


}
