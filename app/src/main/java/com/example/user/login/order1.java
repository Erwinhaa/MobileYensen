package com.example.user.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.login.Adapter.GedungAdapter;
import com.example.user.login.Model.Gedung;
import com.example.user.login.Model.GedungResult;
import com.example.user.login.Model.UserModel;
import com.example.user.login.apihelper.ApiService;
import com.example.user.login.apihelper.UtilApi;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 20/11/2019.
 */

public class order1 extends AppCompatActivity {
    ListView listView;
    ApiService mapiservice;
    Context mcontext;
    private static String EXTRA = "extra";
    String nmgdg = "", idgdg, rnmgdg;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order01);
        mapiservice = UtilApi.getAPIService();
        mcontext = this;

        final EditText edtSearch = (EditText)findViewById(R.id.search);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nmgdg = edtSearch.getText().toString().trim();
                loadGedung(nmgdg);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        loadGedung("");
    }

    private void loadGedung(String nmgdg) {
        Call<GedungResult> bukus = mapiservice.getGdg(nmgdg);
        bukus.enqueue(new Callback<GedungResult>() {
            @Override
            public void onResponse(Call<GedungResult> call, Response<GedungResult> response) {
                tampilkan(response.body().getGedung());
            }

            @Override
            public void onFailure(Call<GedungResult> call, Throwable t) {

            }
        });
    }

    private void tampilkan(final List<Gedung> bukus) {
        GedungAdapter gdgAdapter = new GedungAdapter(this, R.layout.item_gedung, bukus);
        listView = (ListView)findViewById(R.id.list_gedung);
        listView.setAdapter(gdgAdapter);;
        gdgAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView <?> parent, View view, int position, long dunno) {
                Gedung ngedung = (Gedung) parent.getItemAtPosition(position);
                if(ngedung.getStatusGedung() == 0){
                    String infnm = ngedung.getNamaGedung();
                    Toast.makeText(mcontext, infnm+" masih tutup", Toast.LENGTH_LONG ).show();
                }
                else {
                    Intent intent = new Intent(order1.this, order02.class);
                    intent.putExtra("idgdg", ngedung.getId().toString());
                    startActivity(intent);
                }
            }
        });
    }

}
