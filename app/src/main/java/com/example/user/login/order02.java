package com.example.user.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.login.Adapter.LantaiAdapter;
import com.example.user.login.Adapter.SlotAdapter;
import com.example.user.login.Model.GedungResult;
import com.example.user.login.Model.Lantai;
import com.example.user.login.Model.Slot;
import com.example.user.login.apihelper.ApiService;
import com.example.user.login.apihelper.UtilApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 20/11/2019.
 */

public class order02 extends AppCompatActivity {
    String resultid, idlt;
    Spinner spinner;
    ApiService mapiservice;
    Context mcontext;
    GridView gridView;
    AlertDialog.Builder alertb;
    LayoutInflater layoutInflater;
    View dialogView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order02);
        spinner = (Spinner) findViewById(R.id.ltspinner);
        gridView = (GridView) findViewById(R.id.gridslot);
        mcontext = this;
        mapiservice = UtilApi.getAPIService();
        //get id gedung
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            resultid = extras.getString("idgdg");

        initSpinnerLt();

    }

    private void initSpinnerLt() {
        Call<GedungResult> ltt = mapiservice.getLt(resultid);
        ltt.enqueue(new Callback<GedungResult>() {
            @Override
            public void onResponse(Call<GedungResult> call, Response<GedungResult> response) {
                if (response.isSuccessful()) {
                    tampilkanlt(response.body().getLantai());
                } else {
                    Toast.makeText(mcontext, "Gagal mengambil data lantai", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GedungResult> call, Throwable t) {
                Toast.makeText(mcontext, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void tampilkanlt (final List<Lantai> lt) {
        LantaiAdapter ltAdapter = new LantaiAdapter(this, R.layout.item_lantai, lt);
        spinner.setAdapter(ltAdapter);
        ltAdapter.notifyDataSetChanged();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Lantai lt = (Lantai) parent.getItemAtPosition(position);
                idlt = lt.getId().toString();
                initGridSlot();
                Toast.makeText(mcontext, "Kamu memilih lantai " + lt.getNamaLantai(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void tampilkan(final List<Slot> lt) {
        SlotAdapter sltAdapter = new SlotAdapter(this, R.layout.item_slot, lt);
        gridView.setAdapter(sltAdapter);
        sltAdapter.notifyDataSetChanged();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long dunno) {
                final Slot nslot = (Slot) parent.getItemAtPosition(position);
                if (nslot.getStatusSlot() == 3|| nslot.getStatusSlot() == 4)
                {
                    Toast.makeText(mcontext, "Slot tidak dapat dipesan, mohon memilih slot yang lain",Toast.LENGTH_LONG).show();
                }
                if (nslot.getStatusSlot() == 0 || nslot.getStatusSlot() == 5 ||nslot.getStatusSlot() == 6 ||nslot.getStatusSlot() == 11 ||nslot.getStatusSlot() == 12 ||nslot.getStatusSlot() == 13 ||nslot.getStatusSlot() == 14 ){
                }
                else {
                    alertb = new AlertDialog.Builder(order02.this);
                    alertb.setTitle("Anda yakin ingin memilih slot ini?");
                    alertb
                            .setMessage("Klik \"Ya\" untuk melanjutkan")
                            .setIcon(R.mipmap.ic_launcher)
                            .setCancelable(false)
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(order02.this, orderconfirmation.class);
                                    intent.putExtra("idslot", nslot.getId().toString());
                                    intent.putExtra("idgedung", resultid);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    alertb.show();
                }
            }
        });
    }

    private void initGridSlot(){
        Call<GedungResult> slt = mapiservice.getSlot(idlt);
        slt.enqueue(new Callback<GedungResult>() {
            @Override
            public void onResponse(Call<GedungResult> call, Response<GedungResult> response) {
                tampilkan(response.body().getSlot());
            }

            @Override
            public void onFailure(Call<GedungResult> call, Throwable t) {
                Toast.makeText(mcontext, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
