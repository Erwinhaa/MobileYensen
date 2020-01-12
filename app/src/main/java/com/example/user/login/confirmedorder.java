package com.example.user.login;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.login.Model.DetailTiket;
import com.example.user.login.Model.GedungResult;
import com.example.user.login.Model.Mobil;
import com.example.user.login.Model.Notif;
import com.example.user.login.Model.NotifResult;
import com.example.user.login.Model.Slot;
import com.example.user.login.Model.Tiket;
import com.example.user.login.Model.UserModel;
import com.example.user.login.apihelper.ApiService;
import com.example.user.login.apihelper.UtilApi;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 17/12/2019.
 */

public class confirmedorder extends AppCompatActivity {
    String idtiket, iduser, idmbl, h, m, s, lama, idslot, idgdg, nmgdgex, nmlt, nmslot;
    ApiService mapiservice;
    List<Tiket> listt;
    int position;
    TextView nmuser, nmgdg, alamatg, tby, Noplat, TpMbl, lamapark, inflantai, sl1, sl2;
    SharedPrefManager sharedPrefManager;
    Handler handler;
    Button tblbntuan, petal, btnStart;
    List<Mobil> ltm;
    List<Slot> listslt;
    List<Notif> lntf;
    Runnable myrun;
    boolean run = true;
    RelativeLayout lay5;
    private CountDownTimer countDownTimer;
    private int timeToStart;
    private confirmedorder.TimerState timerState;
    private static final int MAX_TIME = 600;
    long startTime, Time;
    Context mcontext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmedorder);

        mapiservice = UtilApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(this);
        iduser = sharedPrefManager.getSPNama();
        mcontext = this;

        nmgdg = (TextView) findViewById(R.id.nmgdg);
        alamatg = (TextView) findViewById(R.id.alamat);
        nmuser = (TextView) findViewById(R.id.namauser);
        tby = (TextView) findViewById(R.id.biaya);
        Noplat = (TextView) findViewById(R.id.nopl);
        TpMbl = (TextView) findViewById(R.id.tpm);
        lamapark = (TextView) findViewById(R.id.lmprk);
        inflantai = (TextView) findViewById(R.id.infslot);
        tblbntuan = (Button) findViewById(R.id.tblbantuan);
        petal = (Button) findViewById(R.id.peta);
        lay5 = (RelativeLayout) findViewById(R.id.lay05);
        sl1 = (TextView) findViewById(R.id.ketslot1);
        sl2 =(TextView) findViewById(R.id.ketslot2);
        btnStart = (Button) findViewById(R.id.btnstart);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idtiket = extras.getString("idtiket");
        }

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runTime();
            }
        });

        getNm();
        getSetHour();
        runHandler();

    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(myrun);
    }

    @Override
    protected  void onRestart(){
        super.onRestart();
        runHandler();
    }

    public void runHandler(){
        handler = new Handler();
        handler.postDelayed(myrun = new Runnable() {
            @Override
            public void run() {
                if(run == true) {
                    detailOrder();
                    getTblbtn();
                    handler.postDelayed(this, 5000);
                }
            }

        }, 1000);
    }

    public void detailOrder() {
        Call<DetailTiket> profil = mapiservice.getdeTicket(idtiket);
        profil.enqueue(new Callback<DetailTiket>() {
            @Override
            public void onResponse(Call<DetailTiket> call, Response<DetailTiket> response) {
                listt = response.body().getTicket();
                if (listt.isEmpty()) {
                    Intent i = new Intent(confirmedorder.this, dashboard.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                } else {

                    Tiket tkt = listt.get(position);
                    idmbl = tkt.getMobilId().toString();
                    idgdg = tkt.getGedungId().toString();
                    idslot = tkt.getSlotId().toString();

                    nmgdgex = response.body().getNamagedung();
                    nmlt = response.body().getNamalantai();
                    nmslot = response.body().getNamaslot();
                    tblbntuan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent tbb = new Intent(confirmedorder.this, tblbntuan.class);
                            tbb.putExtra("idgdg", idgdg);
                            tbb.putExtra("idslot", idslot);
                            tbb.putExtra("idtiket", idtiket);
                            startActivity(tbb);
                        }
                    });
                    petal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent map = new Intent(confirmedorder.this, layoutmap.class);
                            map.putExtra("nmgdg", nmgdgex);
                            map.putExtra("idslot", idslot);
                            map.putExtra("nmlt", nmlt);
                            map.putExtra("nmslot", nmslot);
                            startActivity(map);
                        }
                    });
                    getInfMbl();
                    nmgdg.setText(nmgdgex);
                    alamatg.setText(response.body().getAlamat());
                    tby.setText(tkt.getBiaya().toString());
                    inflantai.setText("Lantai "+nmlt+" Slot "+nmslot);

                    if (tkt.getStatusTicket() == 2) {
                        tblbntuan.setClickable(false);
                        tblbntuan.setVisibility(View.GONE);
                        getSetHour();
                    }
                    if (tkt.getStatusTicket() == 3) {
                        run = false;
                        finish();
                        Intent i = new Intent(confirmedorder.this, dashboard.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                }

            }

            @Override
            public void onFailure(Call<DetailTiket> call, Throwable t) {

            }
        });
    }

    public void getNm() {
        Call<UserModel> profil = mapiservice.getProf(iduser);
        profil.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                nmuser.setText(response.body().getNama());
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
    }

    public void getInfMbl() {
        Call<UserModel> profil = mapiservice.getInfMbl(idmbl);
        profil.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                ltm = response.body().getMobil();
                if (ltm.isEmpty()) {

                } else {
                    Mobil mbb = ltm.get(position);
                    Noplat.setText(mbb.getNoPlat());
                    TpMbl.setText(mbb.getTipe());
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
    }

    public void getSetHour() {
        Call<DetailTiket> profil = mapiservice.getdeTicket(idtiket);
        profil.enqueue(new Callback<DetailTiket>() {
            @Override
            public void onResponse(Call<DetailTiket> call, Response<DetailTiket> response) {
                listt = response.body().getTicket();
                Tiket tkt = listt.get(position);
                if (tkt.getStatusTicket() == 0 || tkt.getStatusTicket() == 1) {
                    lamapark.setText("Pesanan sedang berjalan.");
                } else {
                    h = response.body().getJam().toString();
                    m = response.body().getMenit().toString();
                    s = response.body().getDetik().toString();
                    lama = h +" : "+ m ;
                    lamapark.setText(lama);
                }

            }

            @Override
            public void onFailure(Call<DetailTiket> call, Throwable t) {

            }
        });

    }

    public void getTblbtn() {
        Call<NotifResult> profil = mapiservice.getKeluhan(iduser);
        profil.enqueue(new Callback<NotifResult>() {
            @Override
            public void onResponse(Call<NotifResult> call, Response<NotifResult> response) {
                lntf = response.body().getNotif();
                Notif ntf = lntf.get(position);
                if(lntf.isEmpty()){
                    lay5.setVisibility(View.GONE);
                }
                else {
                    if(ntf.getStatus() == "1" || ntf.getStatus() == "2") {
                        lay5.setVisibility(View.VISIBLE);
                        sl1.setText(ntf.getSolusi());
                        sl2.setText(ntf.getSolusi());
                        Time = response.body().getDetik();
                        tblbntuan.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(mcontext,"Mohon menunggu", Toast.LENGTH_LONG).show();
                            }
                        });
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                btnStart.performClick();
                            }
                        }, 500);
                        initTimer();

                    }
                    else
                    {
                        lay5.setVisibility(View.GONE);

                    }
                }

            }

            @Override
            public void onFailure(Call<NotifResult> call, Throwable t) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(confirmedorder.this, dashboard.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    private void runTime() {
        if (timerState == confirmedorder.TimerState.STOPPED) {
            startTimer();
            timerState = confirmedorder.TimerState.RUNNING;
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
        if (timerState == confirmedorder.TimerState.RUNNING) {
            countDownTimer.cancel();
        }
    }

    private void initTimer() {
        timeToStart = (int) Time;
        if (timeToStart <= 0) {
            // TIMER EXPIRED
            timeToStart = MAX_TIME;
            timerState = confirmedorder.TimerState.STOPPED;
            startTime = 0;
            updatingUI();
        } else {
            startTimer();
            timerState = confirmedorder.TimerState.RUNNING;
        }
    }

    private void onTimerFinish() {
        timeToStart = MAX_TIME;
        updatingUI();
        countDownTimer.cancel();

    }

    private void updatingUI() {
        int minutes = (int) timeToStart / 60;
        int seconds = (int) timeToStart % 60;
        String timeleftTxt;

        timeleftTxt = " " + minutes;
        timeleftTxt += ":";
        if (seconds < 10) timeleftTxt += "0";
        timeleftTxt += seconds;

        tblbntuan.setText(timeleftTxt);
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
                timerState = confirmedorder.TimerState.STOPPED;
                onTimerFinish();
                updatingUI();
            }
        }.start();
    }


    private enum TimerState {
        STOPPED,
        RUNNING
    }

}
