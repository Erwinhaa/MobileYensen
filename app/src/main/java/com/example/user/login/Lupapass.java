package com.example.user.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Lupapass extends AppCompatActivity {

    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lupapass);
        getSupportActionBar().hide();

        email = (EditText) findViewById(R.id.txt_id);
        Button reg = (Button) findViewById(R.id.lpbnfmbutt);


        reg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent regis = new Intent(Lupapass.this,Login.class);
                startActivity(regis);
            }
        });
    }
}
