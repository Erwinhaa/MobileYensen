package com.example.user.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Reg03 extends AppCompatActivity{
    EditText etNama;
    EditText etEmail;
    EditText etPassword;
    EditText etConfirm;
    EditText etnomhp;
    Button btnRegister;
    ProgressDialog loading;

    Context mcontext;
    ApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register03);
        getSupportActionBar().hide();

        mcontext = this;
        mApiService = UtilApi.getAPIService();
        // methode ini berfungsi untuk mendeklarasikan widget yang ada
        // di layout activity_daftar
        initComponents();
    }
        public void initComponents(){
            etNama = (EditText) findViewById(R.id.txt_username);
            etEmail = (EditText) findViewById(R.id.txt_email);
            etnomhp = (EditText) findViewById(R.id.nohp);
            etPassword = (TextInputEditText) findViewById(R.id.etPassword);
            etConfirm = (TextInputEditText) findViewById(R.id.etPassword2);
            btnRegister = (Button) findViewById(R.id.ButtReg);

            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loading = ProgressDialog.show(mcontext, null, "Harap Tunggu...", true, false);
                    requestRegister();
                }
            });
        }

        public void requestRegister(){
            mApiService.registerRequest(etNama.getText().toString(),
                    etEmail.getText().toString(),
                    etPassword.getText().toString(),
                    etConfirm.getText().toString(),
                    etnomhp.getText().toString())
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()){
                                Log.i("debug", "onResponse: Berhasil");
                                loading.dismiss();
                                try {
                                    JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                    if (jsonRESULTS.getString("error").equals("false")){
                                        Toast.makeText(mcontext, "", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(mcontext, "Anda Berhasil Registrasi", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(mcontext, Login.class));
                                    } else {
                                        String error_message = jsonRESULTS.getString("pesan");
                                        Toast.makeText(mcontext, error_message, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                Log.i("debug", "onResponse: Tidak Berhasil");
                                loading.dismiss();
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                            Toast.makeText(mcontext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();

                        }
                    });
        }
}
