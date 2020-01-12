package com.example.user.login;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.login.Model.UserModel;
import com.example.user.login.apihelper.ApiService;
import com.example.user.login.apihelper.UtilApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class editprofil extends AppCompatActivity {
    ApiService mapiservice;
    SharedPrefManager sharedPrefManager;
    Context mcontext;
    EditText nama, nohp;
    String tnm, tnhp;
    String resultid;
    Button editbutt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprof);
        getSupportActionBar().hide();

        editbutt = (Button) findViewById(R.id.Buttedit);
        nama = (EditText) findViewById(R.id.txt_nama);
        nohp = (EditText) findViewById(R.id.txt_nohp);
        mapiservice = UtilApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(this);
        mcontext = this;

        resultid = sharedPrefManager.getSPNama();

        initprof();
    }

    public void initprof() {
        Call<UserModel> profil = mapiservice.getProf(resultid);
        profil.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                tnm = response.body().getNama();
                tnhp = response.body().getNohp();
                nama.setText(tnm);
                nohp.setText(tnhp);

                editbutt.setOnClickListener(new View.OnClickListener()

                {
                    @Override
                    public void onClick(View view) {
                        editprofil();
                        overridePendingTransition(0, 0);
                        Intent next = new Intent(editprofil.this, profil.class);
                        overridePendingTransition(0, 0);
                        startActivity(next);
                        overridePendingTransition(0, 0);
                    }
                });
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
    }


    public void editprofil() {
        mapiservice.edprof(resultid, nama.getText().toString(), nohp.getText().toString()).enqueue(new Callback<ResponseBody>() {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(editprofil.this, profil.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

}
