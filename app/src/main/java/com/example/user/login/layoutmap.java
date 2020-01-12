package com.example.user.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.login.Adapter.LMapAdapter;
import com.example.user.login.Model.GedungResult;
import com.example.user.login.Model.Slot;
import com.example.user.login.apihelper.ApiService;
import com.example.user.login.apihelper.UtilApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 05/01/2020.
 */

public class layoutmap extends AppCompatActivity {
    String idlt, nmgdg, nmlt, nmslot, idslot;
    TextView gdg;
    ApiService mapiservice;
    GridView gridView;
    Context mcontext;
    List<Slot> listslt;
    int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutmap);

        mapiservice = UtilApi.getAPIService();
        mcontext = this;
        gdg = (TextView) findViewById(R.id.infpeta);
        gridView = (GridView) findViewById(R.id.gridslot);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idslot = extras.getString("idslot");
            nmgdg = extras.getString("nmgdg");
            nmlt = extras.getString("nmlt");
            nmslot = extras.getString("nmslot");
        }
        gdg.setText(nmgdg + "    Lantai " + nmlt + " Slot " + nmslot);

        initSlot();

    }

    protected void initSlot() {
        Call<GedungResult> profil = mapiservice.getInfSlot(idslot);
        profil.enqueue(new Callback<GedungResult>() {
            @Override
            public void onResponse(Call<GedungResult> call, Response<GedungResult> response) {
                listslt = response.body().getSlot();
                Slot slt = listslt.get(position);
                idlt = slt.getLantaiId().toString();
                initGridSlot();
            }

            @Override
            public void onFailure(Call<GedungResult> call, Throwable t) {

            }
        });
    }

    private void initGridSlot() {
        Call<GedungResult> slt = mapiservice.getSlot(idlt);
        slt.enqueue(new Callback<GedungResult>() {
            @Override
            public void onResponse(Call<GedungResult> call, Response<GedungResult> response) {
                tampilkan(response.body().getSlot(), idslot);
            }

            @Override
            public void onFailure(Call<GedungResult> call, Throwable t) {
                Toast.makeText(mcontext, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void tampilkan(final List<Slot> lt, String idsl) {
        LMapAdapter sltAdapter = new LMapAdapter(this, R.layout.item_slot, lt, idsl);
        gridView.setAdapter(sltAdapter);
        sltAdapter.notifyDataSetChanged();
    }

}


