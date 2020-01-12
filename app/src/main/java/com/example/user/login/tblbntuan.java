package com.example.user.login;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.login.Adapter.LantaiAdapter;
import com.example.user.login.Adapter.SlotAdapter;
import com.example.user.login.Model.GedungResult;
import com.example.user.login.Model.Lantai;
import com.example.user.login.Model.Slot;
import com.example.user.login.apihelper.ApiService;
import com.example.user.login.apihelper.UtilApi;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 20/12/2019.
 */

public class tblbntuan extends AppCompatActivity {
    String resultid, idlt, idtiket, idslot, idslotselect;
    Spinner spinner;
    ApiService mapiservice;
    Context mcontext;
    GridView gridView;
    AlertDialog.Builder alertb;
    LayoutInflater layoutInflater;
    View dialogView;
    public static final int notifikasi = 1;
    Button notif;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tblbntuan);
        spinner = (Spinner) findViewById(R.id.ltspinner);
        gridView = (GridView) findViewById(R.id.gridslot);
        notif = (Button) findViewById(R.id.notif);
        mcontext = this;
        mapiservice = UtilApi.getAPIService();
        //get id gedung
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            resultid = extras.getString("idgdg");
            idtiket = extras.getString("idtiket");
            idslot = extras.getString("idslot");
        }
        notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), dashboard.class);
                showNotification(mcontext, intent);
            }
        });
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

    private void tampilkanlt(final List<Lantai> lt) {
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
                Slot nslot = (Slot) parent.getItemAtPosition(position);
                if (nslot.getStatusSlot() == 3 || nslot.getStatusSlot() == 4) {
                    Toast.makeText(mcontext, "Slot tidak dapat dipesan, mohon memilih slot yang lain", Toast.LENGTH_LONG).show();
                }
                if (nslot.getStatusSlot() == 0) {

                } else {

                    idslotselect = nslot.getId().toString();
                    alert();

                }
            }
        });
    }

    public void alert() {
        alertb = new AlertDialog.Builder(tblbntuan.this);
        alertb.setTitle("Anda yakin ingin memilih slot ini?");
        alertb
                .setMessage("Klik \"Ya\" untuk melanjutkan")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        postKlhn();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        alertb.show();
    }

    private void initGridSlot() {
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

    private void postKlhn() {
        Call<ResponseBody> pstkl = mapiservice.postKeluhan(idtiket, idslotselect);
        pstkl.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Intent intent = new Intent(getApplicationContext(), dashboard.class);
                showNotification(mcontext, intent);
                finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mcontext, "Terjadi kesalahan tak terduga", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showNotification(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "ch1";
        String channelName = "ChannelNm";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setChannel(channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("MOBILE PARKING")
                .setContentText("Keluhan telah dikirimkan");

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());
        Toast.makeText(mcontext, "Keluhan sudah dikirim", Toast.LENGTH_SHORT).show();
    }

}
