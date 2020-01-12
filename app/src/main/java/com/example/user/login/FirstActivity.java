package com.example.user.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Button login = (Button) findViewById(R.id.ButtLogin);
        Button reg = (Button) findViewById(R.id.ButtReg);
        TextView lupas = (TextView) findViewById(R.id.lupapass);

        getSupportActionBar().hide();

        lupas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent lupass = new Intent(FirstActivity.this,Lupapass.class);
                startActivity(lupass);
            }
        });

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent log = new Intent(FirstActivity.this,Login.class);
                startActivity(log);
            }
        });

        reg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent regis = new Intent(FirstActivity.this,Reg03.class);
                startActivity(regis);
            }
        });
    }
}
