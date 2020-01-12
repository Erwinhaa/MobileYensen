package com.example.user.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.login.Adapter.TiketAdapter;
import com.example.user.login.Model.Mobil;
import com.example.user.login.Model.Tiket;
import com.example.user.login.Model.TiketResult;
import com.example.user.login.Model.UserModel;
import com.example.user.login.apihelper.ApiService;
import com.example.user.login.apihelper.UtilApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class dashboard extends AppCompatActivity {
    ApiService mapiservice;
    TextView saldo, nama;
    String resultNama, idtkt, id;
    int jlhsaldo, position;
    Context mcontext;
    Button pesan;
    SharedPrefManager sharedPrefManager;
    List<Tiket> listt;
    List<Mobil> listm;
    ListView listView;
    int status;
    ImageView prof;
    RelativeLayout history, historysaldo;
    AlertDialog.Builder dialog;
    View dialogView;
    LayoutInflater layoutInflater;
    EditText tipemob, bk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        getSupportActionBar().hide();

        prof = (ImageView) findViewById(R.id.buttonprof);
        pesan = (Button) findViewById(R.id.pesan);
        saldo = (TextView) findViewById(R.id.saldonom);
        nama = (TextView) findViewById(R.id.nama);
        listView = (ListView) findViewById(R.id.listiket);
        history = (RelativeLayout) findViewById(R.id.menu1);
        historysaldo = (RelativeLayout) findViewById(R.id.menu2);
        mapiservice = UtilApi.getAPIService();
        mcontext = this;
        sharedPrefManager = new SharedPrefManager(this);

        resultNama = sharedPrefManager.getSPNama();


        Call<UserModel> profil = mapiservice.getProf(resultNama);

        profil.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                listm = response.body().getMobil();
                jlhsaldo = response.body().getSaldo();
                saldo.setText(response.body().getSaldo().toString());
                nama.setText(response.body().getNama());
                if (listm.isEmpty()){
                    DialogForm();
                }
                else{
                    pesan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (jlhsaldo < 3000) {
                                Toast.makeText(mcontext, "Saldo tidak mencukupi", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent next = new Intent(mcontext, order1.class);
                                next.putExtra("result_id", id);
                                startActivity(next);
                            }
                        }

                    });
                }

            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
        getOrder();

        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent next = new Intent(mcontext, profil.class);
                next.putExtra("result_id", resultNama);
                startActivity(next);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent next = new Intent(mcontext, historyac.class);
                next.putExtra("result_id", resultNama);
                startActivity(next);
            }
        });
        historysaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent next = new Intent(mcontext, historytrx.class);
                next.putExtra("result_id", resultNama);
                startActivity(next);
            }
        });

    }
    private void DialogForm() {
        dialog = new AlertDialog.Builder(dashboard.this);
        layoutInflater = getLayoutInflater();
        dialogView = layoutInflater.inflate(R.layout.newmbl, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Tambah Mobil");


        tipemob = (EditText) dialogView.findViewById(R.id.txttipemob);
        bk = (EditText) dialogView.findViewById(R.id.txtbk);


        dialog.setPositiveButton("TAMBAH", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(TextUtils.isEmpty(tipemob.getText().toString())||TextUtils.isEmpty(bk.getText().toString())){
                    Toast.makeText(dashboard.this, "Ada Field yang kosong", Toast.LENGTH_LONG).show();
                    DialogForm();
                }
                else{
                    AddMobil();
                    dialog.dismiss();
                    dashboard.this.finish();
                    overridePendingTransition(0, 0);
                    Intent next = new Intent(dashboard.this, dashboard.class);
                    overridePendingTransition(0, 0);
                    startActivity(next);
                    overridePendingTransition(0, 0);
                }
            }
        });

        dialog.show();
    }

    public void AddMobil() {
        mapiservice.addMobil(resultNama, tipemob.getText().toString(), bk.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("error").equals("false")) {
                                    String success_message = jsonRESULTS.getString("pesan");
                                    Toast.makeText(mcontext, success_message, Toast.LENGTH_LONG).show();
                                } else {
                                    String error_message = jsonRESULTS.getString("pesan");
                                    Toast.makeText(mcontext, error_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                    }
                });

    }


    public void getOrder() {
        Call<TiketResult> profil = mapiservice.getTiket(resultNama);
        profil.enqueue(new Callback<TiketResult>() {
            @Override
            public void onResponse(Call<TiketResult> call, Response<TiketResult> response) {
                listt = response.body().getTicket();
                if (listt.isEmpty()) {
                    listView.setVisibility(View.GONE);
                    pesan.setBackgroundColor(Color.parseColor("#3383C1"));
                    pesan.setClickable(true);
                } else {
                    listView.setVisibility(View.VISIBLE);
                    Tiket tkt = listt.get(position);
                    status = tkt.getStatusTicket();
                    idtkt = tkt.getId().toString();
                    tampilkan(response.body().getTicket());
                    pesan.setBackgroundColor(Color.GRAY);
                    pesan.setClickable(false);
                    pesan.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<TiketResult> call, Throwable t) {

            }
        });
    }

    public void getOrdercheck() {
        Call<TiketResult> profil = mapiservice.getTiket(resultNama);
        profil.enqueue(new Callback<TiketResult>() {
            @Override
            public void onResponse(Call<TiketResult> call, Response<TiketResult> response) {
                listt = response.body().getTicket();
                if (listt.isEmpty()) {
                    Toast.makeText(mcontext, "Pesanan tidak ditemukan", Toast.LENGTH_LONG).show();
                    Intent a = new Intent(dashboard.this, dashboard.class);
                    overridePendingTransition( 0, 0);
                    startActivity(a);
                    overridePendingTransition( 0, 0);
                }
                else{
                    if (status == 1 || status == 2) {
                        Intent a = new Intent(dashboard.this, confirmedorder.class);
                        a.putExtra("idtiket", idtkt);
                        startActivity(a);
                    } else {
                        Intent intent = new Intent(dashboard.this, ordercon.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<TiketResult> call, Throwable t) {

            }
        });
    }

    private void tampilkan(final List<Tiket> tikets) {
        TiketAdapter tktAdapter = new TiketAdapter(this, R.layout.item_tiket, tikets);
        listView = (ListView) findViewById(R.id.listiket);
        listView.setAdapter(tktAdapter);
        ;
        tktAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long dunno) {
                getOrdercheck();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }
}


