package com.example.user.login;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.login.Adapter.LantaiAdapter;
import com.example.user.login.Adapter.ListMobilAdapterpsn;
import com.example.user.login.Model.Gedung;
import com.example.user.login.Model.GedungResult;
import com.example.user.login.Model.Lantai;
import com.example.user.login.Model.Mobil;
import com.example.user.login.Model.Slot;
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

public class orderconfirmation extends AppCompatActivity {
    String idslot, idgdg, iduser, idmobil, idnya;
    TextView nmgdg, alamat, lantai, slot;
    SharedPrefManager sharedPrefManager;
    ApiService mapiservice;
    List <Gedung> listm;
    List <Lantai> listlt;
    List <Slot> listslt;
    int position;
    Spinner spinner;
    Context mcontext;
    Button tblpsn;
    LinearLayout laygdg, layslot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderconfirmation);
        spinner = (Spinner) findViewById(R.id.spinnermbl);
        mcontext = this;

        sharedPrefManager = new SharedPrefManager(this);
        mapiservice = UtilApi.getAPIService();
        iduser = sharedPrefManager.getSPNama();
        nmgdg = (TextView) findViewById(R.id.nmgdg);
        alamat = (TextView) findViewById(R.id.alamat);
        lantai = (TextView) findViewById(R.id.inflantai);
        slot = (TextView) findViewById(R.id.infslot);
        tblpsn = (Button) findViewById(R.id.buttpesan);
        laygdg = (LinearLayout) findViewById(R.id.infogdg) ;
        layslot = (LinearLayout) findViewById(R.id.lay01) ;

        Bundle extras = getIntent().getExtras();
        if (extras != null)
            idslot = extras.getString("idslot");
            idgdg = extras.getString("idgedung");

        initSpinnerMbl();
        initGedung();
        initLantai();
        initSlot();

        tblpsn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postpesan();
            }
        });
        laygdg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(orderconfirmation.this, order1.class);
                startActivity(intent);
            }
        });
        layslot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(orderconfirmation.this, order02.class);
                intent.putExtra("idgdg", idgdg);
                startActivity(intent);
            }
        });



    }

    private void initSpinnerMbl() {
        Call<UserModel> ltt = mapiservice.getProf(iduser);
        ltt.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()) {
                    tampilkanmb(response.body().getMobil());
                } else {
                    Toast.makeText(mcontext, "Gagal mengambil data Mobil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(mcontext, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void tampilkanmb (final List<Mobil> lt) {
        ListMobilAdapterpsn mblAdapter = new ListMobilAdapterpsn (this, R.layout.spinnermbldd, lt);
        spinner.setAdapter(mblAdapter);
        mblAdapter.notifyDataSetChanged();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Mobil mbselect = (Mobil) parent.getItemAtPosition(position);
                idmobil = mbselect.getId().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    protected void initGedung(){
        Call<GedungResult> profil = mapiservice.getinfgdg(idgdg);
        profil.enqueue(new Callback<GedungResult>() {
            @Override
            public void onResponse(Call<GedungResult> call, Response<GedungResult> response) {
                listm = response.body().getGedung();
                Gedung gdg = listm.get(position);
                nmgdg.setText(gdg.getNamaGedung());
                alamat.setText(gdg.getAlamat());
            }

            @Override
            public void onFailure(Call<GedungResult> call, Throwable t) {

            }
        });
    }
    protected void initLantai(){
        Call<GedungResult> profil = mapiservice.getinfgdg(idgdg);
        profil.enqueue(new Callback<GedungResult>() {
            @Override
            public void onResponse(Call<GedungResult> call, Response<GedungResult> response) {
                listlt = response.body().getLantai();
                Lantai gdg = listlt.get(position);
                lantai.setText("Lantai " + gdg.getNamaLantai());
            }

            @Override
            public void onFailure(Call<GedungResult> call, Throwable t) {

            }
        });
    }
    protected void initSlot(){
        Call<GedungResult> profil = mapiservice.getInfSlot(idslot);
        profil.enqueue(new Callback<GedungResult>() {
            @Override
            public void onResponse(Call<GedungResult> call, Response<GedungResult> response) {
                listslt = response.body().getSlot();
                Slot slt = listslt.get(position);
                slot.setText(slt.getNamaSlot().toString());
            }

            @Override
            public void onFailure(Call<GedungResult> call, Throwable t) {

            }
        });
    }



    protected void postpesan(){
        Call<ResponseBody> pesan = mapiservice.postPesan(iduser, idslot, idmobil);
        pesan.enqueue((new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Intent timer = new Intent(orderconfirmation.this, ordercon.class);
                timer.putExtra("idslot", idslot);
                timer.putExtra("idmobil", idmobil);
                startActivity(timer);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        }));
    }



}
