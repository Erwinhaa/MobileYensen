package com.example.user.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.login.Model.DetailTiket;
import com.example.user.login.Model.Tiket;
import com.example.user.login.Model.TiketResult;
import com.example.user.login.apihelper.ApiService;
import com.example.user.login.apihelper.UtilApi;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 28/11/2019.
 */

public class ordercon extends AppCompatActivity {
    private TextView timerText;
    private TextView nmgdg, detlt, alamatg;
    String idslot, idmbl, iduser, idtiket;
    private Button btnStart, btnStop;
    private CountDownTimer countDownTimer;
    private int timeToStart, position;
    private TimerState timerState;
    private static final int MAX_TIME = 1800;
    ApiService mapiservice;
    SharedPrefManager sharedPrefManager;
    long startTime, Time;
    List<Tiket> listt;
    List<Tiket> listt2;
    Context mcontext;
    Handler handler;
    Runnable myrun;
    boolean run = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ordercon);

        timerText = (TextView) findViewById(R.id.timer);
        btnStart = (Button) findViewById(R.id.button);
        btnStop = (Button) findViewById(R.id.buttonstop);
        nmgdg = (TextView) findViewById(R.id.nmgdg);
        detlt = (TextView) findViewById(R.id.namauser);
        alamatg = (TextView) findViewById(R.id.alamat);
        mcontext = this;
        handler = new Handler();

        btnStart.setVisibility(View.GONE);
        mapiservice = UtilApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(this);
        iduser = sharedPrefManager.getSPNama();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idslot = extras.getString("idslot");
            idmbl = extras.getString("idmobil");
        } else {
            idslot = "";
            idmbl = "";
        }



       getOrder();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                btnStart.performClick();
            }
        }, 500);


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runTime();
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timerState = TimerState.STOPPED;
                onTimerFinish();
                updatingUI();
            }
        });
    }

    public void cekpd(){
        handler = new Handler();
        handler.postDelayed(myrun = new Runnable() {
            @Override
            public void run() {
                if(run == true) {
                    cekStat();
                    handler.postDelayed(this, 5000);
                }
            }

        }, 1000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(myrun);
    }

   private void runTime() {
        if (timerState == TimerState.STOPPED) {
            getOrder();
            startTimer();
            timerState = TimerState.RUNNING;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        runTime();
        updatingUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timerState == TimerState.RUNNING) {
            countDownTimer.cancel();
        }
    }

    private void initTimer() {
            timeToStart = (int) Time;
            if (timeToStart <= 0) {
                // TIMER EXPIRED
                timeToStart = MAX_TIME;
                timerState = TimerState.STOPPED;
                startTime = 0;
                updatingUI();
                cancelOrder();
            } else {
                startTimer();
                timerState = TimerState.RUNNING;
            }
    }

    private void onTimerFinish() {
        timeToStart = MAX_TIME;
        updatingUI();
        countDownTimer.cancel();
        cancelOrder();

    }

    private void updatingUI() {
        int minutes = (int) timeToStart / 60;
        int seconds = (int) timeToStart % 60;
        String timeleftTxt;

        timeleftTxt = " " + minutes;
        timeleftTxt += ":";
        if (seconds < 10) timeleftTxt += "0";
        timeleftTxt += seconds;

        timerText.setText(timeleftTxt);
    }


    private void startTimer() {
        countDownTimer = new CountDownTimer(timeToStart * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeToStart -= 1;
                updatingUI();
            }

            @Override
            public void onFinish() {
                timerState = TimerState.STOPPED;
                onTimerFinish();
                updatingUI();
            }
        }.start();
    }


    private enum TimerState {
        STOPPED,
        RUNNING
    }

    public void getOrder() {
        Call<TiketResult> profil = mapiservice.getTiket(iduser);
        profil.enqueue(new Callback<TiketResult>() {
            @Override
            public void onResponse(Call<TiketResult> call, Response<TiketResult> response) {
                listt = response.body().getTicket();
                if (listt.isEmpty()) {
                    Intent i = new Intent(ordercon.this, dashboard.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                } else {
                    Tiket tkt = listt.get(position);
                    idtiket = tkt.getId().toString();
                    detailOrder();
                    getSecond();
                    cekpd();
                }
            }

            @Override
            public void onFailure(Call<TiketResult> call, Throwable t) {

            }
        });
    }

    public void cekStat(){
        Call<TiketResult> profil = mapiservice.getTiket(iduser);
        profil.enqueue(new Callback<TiketResult>() {
            @Override
            public void onResponse(Call<TiketResult> call, Response<TiketResult> response) {
                listt2 = response.body().getTicket();
                if (listt2.isEmpty()) {
                    Intent i = new Intent(ordercon.this, dashboard.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                } else {
                    Tiket tkt = listt2.get(position);
                    if (tkt.getStatusTicket() == 1) {
                        startTime = 0;
                        Intent a = new Intent(ordercon.this, confirmedorder.class);
                        a.putExtra("idtiket", idtiket);
                        startActivity(a);
                        run = false;
                    }
                    if (tkt.getStatusTicket() == 3) {
                        startTime = 0;
                        Intent a = new Intent(ordercon.this, dashboard.class);
                        startActivity(a);
                        run = false;
                    }
                }
            }

            @Override
            public void onFailure(Call<TiketResult> call, Throwable t) {

            }
        });
    }

    public void cancelOrder() {
        Call<ResponseBody> profil = mapiservice.btlPesan(idtiket);
        profil.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                run = false;
                finish();
                Intent i = new Intent(ordercon.this, dashboard.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ordercon.this, dashboard.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    public void detailOrder() {
        Call<DetailTiket> profil = mapiservice.getdeTicket(idtiket);
        profil.enqueue(new Callback<DetailTiket>() {
            @Override
            public void onResponse(Call<DetailTiket> call, Response<DetailTiket> response) {
                listt = response.body().getTicket();
                if (listt.isEmpty()) {
                    Intent i = new Intent(ordercon.this, dashboard.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                } else {
                    nmgdg.setText(response.body().getNamagedung());
                    detlt.setText("Lantai " + response.body().getNamalantai() + " Slot " + response.body().getNamaslot());
                    alamatg.setText(response.body().getAlamat());
                }
            }

            @Override
            public void onFailure(Call<DetailTiket> call, Throwable t) {

            }
        });
    }

    public void getSecond() {
        Call<DetailTiket> profil = mapiservice.getdeTicket(idtiket);
        profil.enqueue(new Callback<DetailTiket>() {
            @Override
            public void onResponse(Call<DetailTiket> call, Response<DetailTiket> response) {
                listt = response.body().getTicket();
                if (listt.isEmpty()) {
                    Intent i = new Intent(ordercon.this, dashboard.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                } else {
                    Time = response.body().getDetik();
                    initTimer();
                }
            }

            @Override
            public void onFailure(Call<DetailTiket> call, Throwable t) {

            }
        });
    }

}
