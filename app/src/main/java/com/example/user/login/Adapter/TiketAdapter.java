package com.example.user.login.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.user.login.Model.Gedung;
import com.example.user.login.Model.GedungResult;
import com.example.user.login.Model.Tiket;
import com.example.user.login.R;
import com.example.user.login.apihelper.ApiService;
import com.example.user.login.apihelper.UtilApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 10/12/2019.
 */

public class TiketAdapter extends ArrayAdapter<Tiket> {
    ApiService mapiservice;
    String idgdg, nmgdgt, alamatgdgt;
    int position, status;
    List<Gedung> gdgl;
    TextView nmgdg;

    public TiketAdapter(@NonNull Context context, int resource, @NonNull List<Tiket> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_tiket, parent, false
            );
        }
        final Tiket ntiket = getItem(position);
        nmgdg = (TextView) convertView.findViewById(R.id.nmgdg);
        idgdg = ntiket.getGedungId().toString();
        mapiservice = UtilApi.getAPIService();

        loadGedung(idgdg);

        return convertView;

    }
    private void loadGedung(String idgdg) {
        Call<GedungResult> bukus = mapiservice.getLt(idgdg);
        bukus.enqueue(new Callback<GedungResult>() {
            @Override
            public void onResponse(Call<GedungResult> call, Response<GedungResult> response) {
                gdgl = response.body().getGedung();
                Gedung gdg = gdgl.get(position);
                nmgdgt = gdg.getNamaGedung();
                alamatgdgt = gdg.getAlamat();

                nmgdg.setText(nmgdgt);
            }

            @Override
            public void onFailure(Call<GedungResult> call, Throwable t) {

            }
        });
    }

}
