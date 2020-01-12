package com.example.user.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.login.apihelper.ApiService;
import com.example.user.login.apihelper.UtilApi;

public class saldo extends AppCompatActivity {

    ApiService mapiservice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saldo);
        getSupportActionBar().hide();

        mapiservice = UtilApi.getAPIService();

    }
}
