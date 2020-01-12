package com.example.user.login.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.user.login.Model.Gedung;
import com.example.user.login.Model.GedungResult;
import com.example.user.login.Model.History;
import com.example.user.login.Model.Tiket;
import com.example.user.login.R;
import com.example.user.login.apihelper.ApiService;
import com.example.user.login.apihelper.UtilApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 08/01/2020.
 */

public class THistoryAdapter extends ArrayAdapter<History> {
    ApiService mapiservice;
    List<Gedung> gdgl;
    String idgdg, nmgdgt;
    int position;
    TextView NamaGdg;

    public THistoryAdapter(@NonNull Context context, int resource, @NonNull List<History> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.itemhsaldo, parent, false
            );
        }

        final History item = getItem(position);
        mapiservice = UtilApi.getAPIService();
        idgdg = item.getOperatorId().toString();


        TextView Noref = (TextView) convertView.findViewById(R.id.no_ref);
        Noref.setText("No. Referensi: "+ item.getId().toString());

        NamaGdg = (TextView) convertView.findViewById(R.id.nmgdg);
        NamaGdg.setText(item.getNamaGedung().toString());

        TextView trx = (TextView) convertView.findViewById(R.id.umasuk);
        trx.setText(item.getJumlah().toString());

        TextView tggl = (TextView) convertView.findViewById(R.id.Tanggal);
        tggl.setText(item.getCreatedAt().toString());



        return convertView;
    }
}

