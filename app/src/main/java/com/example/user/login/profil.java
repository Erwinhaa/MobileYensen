package com.example.user.login;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.login.Adapter.ListMobilAdapter;
import com.example.user.login.Model.Mobil;
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

public class profil extends AppCompatActivity {
    ApiService mapiservice;
    Button tmbhmob, lgout;
    ImageView edit, delmob;
    TextView nama, email, nohp;
    String resultid, idmobil;
    SharedPrefManager sharedPrefManager;
    ListView listView;
    AlertDialog.Builder dialog, alertb;
    LayoutInflater layoutInflater;
    View dialogView;
    EditText tipemob, bk;
    Context mcontext;
    List<Mobil> listm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil);

        lgout = (Button) findViewById(R.id.ButtLogout);
        tmbhmob = (Button) findViewById(R.id.Butttmob);
        edit = (ImageView) findViewById(R.id.editbutt);
        nama = (TextView) findViewById(R.id.nama);
        email = (TextView) findViewById(R.id.txt_email);
        nohp = (TextView) findViewById(R.id.nohpe);
        delmob = (ImageView) findViewById(R.id.trash);
        mapiservice = UtilApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(this);
        mcontext = this;

        resultid = sharedPrefManager.getSPNama();

        Call<UserModel> profil = mapiservice.getProf(resultid);
        profil.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                nama.setText(response.body().getNama());
                email.setText(response.body().getEmail());
                nohp.setText(response.body().getNohp());
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });

        loadMobil();

        tmbhmob.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                DialogForm();
            }
        });

        edit.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                Intent logon = new Intent(profil.this, editprofil.class);
                startActivity(logon);
            }
        });

        lgout.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                startActivity(new Intent(profil.this, Login.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });
    }

    private void loadMobil() {
        Call<UserModel> bukus = mapiservice.getProf(resultid);
        bukus.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                tampilkan(response.body().getMobil());
                listm = response.body().getMobil();
                if(listm.size() == 3){
                    tmbhmob.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view) {
                            alertfull();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
    }

    private void tampilkan(List<Mobil> mobils) {
        final ListMobilAdapter mobilAdapter = new ListMobilAdapter(this, R.layout.listmobil, mobils);
        listView = (ListView) findViewById(R.id.listmobilpro);
        listView.setAdapter(mobilAdapter);
        mobilAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long dunno) {
                Mobil nmobil = (Mobil) parent.getItemAtPosition(position);
                idmobil = nmobil.getId().toString();
                alert();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(profil.this, dashboard.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    private void DialogForm() {
        dialog = new AlertDialog.Builder(profil.this);
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
                    Toast.makeText(profil.this, "Ada Field yang kosong", Toast.LENGTH_LONG).show();
                    DialogForm();
                }
                else {
                    AddMobil();
                    dialog.dismiss();
                    overridePendingTransition(0, 0);
                    Intent next = new Intent(profil.this, profil.class);
                    overridePendingTransition(0, 0);
                    startActivity(next);
                    overridePendingTransition(0, 0);
                }
            }
        });

        dialog.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void AddMobil() {
        mapiservice.addMobil(resultid, tipemob.getText().toString(), bk.getText().toString())
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

    public void alert() {
        alertb = new AlertDialog.Builder(profil.this);
        alertb.setTitle("Ingin menghapus kendaraan ini?");
        alertb
                .setMessage("Klik \"Ya\" untuk menghapus")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        delMobil();
                        profil.this.finish();
                        overridePendingTransition(0, 0);
                        Intent next = new Intent(profil.this, profil.class);
                        overridePendingTransition(0, 0);
                        startActivity(next);
                        overridePendingTransition(0, 0);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        alertb.show();
    }

    public void alertfull() {
        alertb = new AlertDialog.Builder(profil.this);
        alertb.setTitle("Tidak dapat menambah mobil lagi");
        alertb
                .setMessage("Hapus mobil yang terdaftar untuk menambah mobil kembali")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        alertb.show();
    }

    public void delMobil() {
        mapiservice.dMobil(idmobil).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonRESULTS = new JSONObject(response.body().string());
                    if (jsonRESULTS.getString("error").equals("false")) {
                        Toast.makeText(mcontext, "Mobil Dihapus", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String error_message = jsonRESULTS.getString("pesan");
                        Toast.makeText(mcontext, error_message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mcontext, "Koneksi bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
