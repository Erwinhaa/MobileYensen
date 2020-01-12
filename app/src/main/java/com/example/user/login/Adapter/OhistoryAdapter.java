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
import com.example.user.login.Model.Tiket;
import com.example.user.login.R;

import java.util.List;

/**
 * Created by User on 25/12/2019.
 */

public class OhistoryAdapter extends ArrayAdapter<Tiket> {

    public OhistoryAdapter(@NonNull Context context, int resource, @NonNull List<Tiket> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_history, parent, false
            );
        }

        final Tiket item = getItem(position);

        TextView NamaGdg = (TextView) convertView.findViewById(R.id.nmgdg);
        NamaGdg.setText(item.getGedungId());

        TextView InfLantai = (TextView) convertView.findViewById(R.id.ltinfo);
        InfLantai.setText(item.getSlotId());

        TextView infmobil = (TextView) convertView.findViewById(R.id.mobil);
        infmobil.setText(item.getMobilId());

        TextView biaya = (TextView) convertView.findViewById(R.id.biaya);
        biaya.setText(item.getBiaya().toString());

        TextView tggl = (TextView) convertView.findViewById(R.id.Tanggal);
        tggl.setText(item.getTanggalOrder().toString());

        TextView status = (TextView) convertView.findViewById(R.id.status);
        if (item.getStatusTicket() == 3) {
            status.setText("finished");
            status.setTextColor(Color.GREEN);

        } else if (item.getStatusTicket() == 4) {
            status.setText("canceled");
            status.setTextColor(Color.RED);
        }


        return convertView;
    }
}
