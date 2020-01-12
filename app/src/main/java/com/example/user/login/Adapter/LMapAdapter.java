package com.example.user.login.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.login.Model.Slot;
import com.example.user.login.R;

import java.util.List;

/**
 * Created by User on 06/01/2020.
 */

public class LMapAdapter extends ArrayAdapter<Slot> {
    String idslota;


    public LMapAdapter(@NonNull Context context, int resource, @NonNull List<Slot> objects, String idslot) {
        super(context, resource, objects);
        idslota = idslot;
        Toast.makeText(context, idslota, Toast.LENGTH_LONG).show();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_slot, parent, false
            );
        }
        final Slot nslot = getItem(position);
        TextView nmslt = (TextView) convertView.findViewById(R.id.itemslot);


        if (nslot.getStatusSlot() == 0) {
            nmslt.setText(" ");
        }
        if (nslot.getId().toString().equals(idslota)) {
            nmslt.setBackgroundResource(R.drawable.blue);
            nmslt.setText(nslot.getNamaSlot().toString());
        }
        else {

            if (nslot.getStatusSlot() == 2 || nslot.getStatusSlot() == 3 || nslot.getStatusSlot() == 4) {
                nmslt.setBackgroundResource(R.drawable.box);
                nmslt.setText(nslot.getNamaSlot().toString());
            }
            if (nslot.getStatusSlot() == 5) {
                nmslt.setBackgroundResource(R.drawable.pintumsk);
                nmslt.setText(" ");
            }
            if (nslot.getStatusSlot() == 6) {
                nmslt.setBackgroundResource(R.drawable.pintukluar);
                nmslt.setText(" ");
            }
            if (nslot.getStatusSlot() == 11) {
                nmslt.setBackgroundResource(R.drawable.atas);
                nmslt.setText(" ");
            }
            if (nslot.getStatusSlot() == 12) {
                nmslt.setBackgroundResource(R.drawable.kiri);
                nmslt.setText(" ");
            }
            if (nslot.getStatusSlot() == 13) {
                nmslt.setBackgroundResource(R.drawable.kanan);
                nmslt.setText(" ");
            }
            if (nslot.getStatusSlot() == 14) {
                nmslt.setBackgroundResource(R.drawable.bawah);
                nmslt.setText(" ");
            }

        }
        return convertView;
    }

}
