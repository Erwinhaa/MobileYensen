package com.example.user.login.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.login.Model.Gedung;
import com.example.user.login.R;
import com.example.user.login.order02;

import java.util.List;

/**
 * Created by User on 20/11/2019.
 */

public class GedungAdapter extends ArrayAdapter<Gedung> {

    public GedungAdapter(@NonNull Context context, int resource, @NonNull List<Gedung> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_gedung, parent, false
            );
        }

        final Gedung gdg = getItem(position);
        LinearLayout but = (LinearLayout) convertView.findViewById(R.id.itemcontain);

        if (gdg.getStatusGedung() == 0) {
            but.setBackgroundColor(Color.parseColor("#E0E0E0"));
            TextView NamaGdg = (TextView) convertView.findViewById(R.id.nmgdg);
            NamaGdg.setText(gdg.getNamaGedung());

            TextView Alamat = (TextView) convertView.findViewById(R.id.alamat);
            Alamat.setText(gdg.getAlamat());
        }
        if (gdg.getStatusGedung() == 1) {
            but.setBackgroundColor(Color.parseColor("#ffffff"));
            TextView NamaGdg = (TextView) convertView.findViewById(R.id.nmgdg);
            NamaGdg.setText(gdg.getNamaGedung());

            TextView Alamat = (TextView) convertView.findViewById(R.id.alamat);
            Alamat.setText(gdg.getAlamat());
        }


        /*ImageView imageView = (ImageView)convertView.findViewById(R.id.img_sampul);
        Picasso.get()
                .load("http://192.168.1.7:8080/belajarapi/public/"+buku.getSampul())
                .into(imageView);*/

        return convertView;
    }
}
