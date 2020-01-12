package com.example.user.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.login.Adapter.ListMobilAdapter;
import com.example.user.login.Adapter.ListMobilAdapterpsn;
import com.example.user.login.Model.Mobil;
import com.example.user.login.Model.Slot;
import com.example.user.login.Model.UserModel;
import com.example.user.login.apihelper.ApiService;
import com.example.user.login.apihelper.UtilApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class plhmbl extends AppCompatActivity {
    String idslot, idgdg, iduser, idnya;
    SharedPrefManager sharedPrefManager;
    ApiService mapiservice;
    ListView listView;
    TextView tes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plhmbl);
        getSupportActionBar().hide();
        sharedPrefManager = new SharedPrefManager(this);
        iduser = sharedPrefManager.getSPNama();
        tes = (TextView) findViewById(R.id.testt);
        mapiservice = UtilApi.getAPIService();

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            idslot = extras.getString("idslot");
            idgdg = extras.getString("idgedung");

        }

        loadMobil();
    }

    private void loadMobil() {
        Call<UserModel> mobil = mapiservice.getProf(iduser);
        mobil.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                //tes.setText(iduser);
                tampilkan(response.body().getMobil());
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
    }

    private void tampilkan(List< Mobil> mobils) {
        ListMobilAdapterpsn mobilAdapter = new ListMobilAdapterpsn(this, R.layout.listmobilpsn, mobils);
        listView = (ListView)findViewById(R.id.plhmbl);
        listView.setAdapter(mobilAdapter);
        mobilAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long dunno) {
                Mobil nbil = (Mobil) parent.getItemAtPosition(position);
                Intent intent = new Intent(plhmbl.this, orderconfirmation.class);
                intent.putExtra("idmbl", nbil.getId().toString());
                intent.putExtra("idgedung", idgdg);
                intent.putExtra("idslot", idslot);
                startActivity(intent);
            }
        });
    }
}
