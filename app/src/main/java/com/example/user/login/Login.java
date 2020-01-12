package com.example.user.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.login.apihelper.ApiService;
import com.example.user.login.apihelper.UtilApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Login extends AppCompatActivity {

    EditText email, pass;
    Context mcontext;
    ApiService mapiservice;
    Button reg, log;
    ProgressDialog loading;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getSupportActionBar().hide();

        sharedPrefManager = new SharedPrefManager(this);
        mcontext = this;
        mapiservice = UtilApi.getAPIService(); // meng-init yang ada di package apihelper

        if (sharedPrefManager.getSPSudahLogin()){
            startActivity(new Intent(Login.this, dashboard.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
        initComponents();
    }

    public void initComponents(){
        email = (EditText) findViewById(R.id.txt_id);
        pass = (TextInputEditText) findViewById(R.id.etPassword);
        reg = (Button) findViewById(R.id.ButtReg);
        log = (Button) findViewById(R.id.ButtLogin);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading = ProgressDialog.show(mcontext, null, "Harap Tunggu...", true, false);
                requestLogin();
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regis = new Intent(Login.this, Reg03.class);
                startActivity(regis);
            }
        });

    }


    public void requestLogin() {
        mapiservice.loginRequest(email.getText().toString(), pass.getText().toString())
                        .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("error").equals("false")) {
                                    // Jika login berhasil maka data nama yang ada di response API
                                    // akan diparsing ke activity selanjutnya.
                                    Toast.makeText(mcontext, "BERHASIL LOGIN", Toast.LENGTH_LONG).show();
                                    String id = jsonRESULTS.getString("id");
                                    /*Intent next = new Intent(mcontext, dashboard.class);
                                    next.putExtra("result_id", id);
                                    startActivity(next);*/
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, id);
                                    // Shared Pref ini berfungsi untuk menjadi trigger session login
                                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                                    startActivity(new Intent(mcontext, dashboard.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                } else {
                                    // Jika login gagal
                                    String error_message = jsonRESULTS.getString("pesan");
                                    Toast.makeText(mcontext, error_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });
        };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }
}

