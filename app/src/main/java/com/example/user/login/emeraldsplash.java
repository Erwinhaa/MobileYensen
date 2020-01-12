package com.example.user.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

/**
 * Created by User on 19/11/2019.
 */

public class emeraldsplash extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    String resultNama;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //menghilangkan ActionBar
        getSupportActionBar().hide();
        setContentView(R.layout.emeraldsplash);

        sharedPrefManager = new SharedPrefManager(this);
        resultNama = sharedPrefManager.getSPNama();


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sharedPrefManager.getSPSudahLogin()) {
                    startActivity(new Intent(getApplicationContext(), dashboard.class));
                    finish();
                }
                else {
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                }
            }
        }, 3000L); //3000 L = 3 detik
    }
}
