package com.example.user.login.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.login.Model.Gedung;
import com.example.user.login.Model.Lantai;
import com.example.user.login.Model.Slot;
import com.example.user.login.R;

import java.util.List;

/**
 * Created by User on 24/11/2019.
 */

public class SlotAdapter extends ArrayAdapter <Slot> {

    public SlotAdapter(@NonNull Context context, int resource, @NonNull List<Slot> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_slot, parent, false
            );
        }
        final Slot nslot = getItem(position);
        TextView nmslt = (TextView) convertView.findViewById(R.id.itemslot);

        if (nslot.getStatusSlot() == 0){
            nmslt.setText(" ");
        }
        else {
            if (nslot.getStatusSlot() == 2) {
                nmslt.setBackgroundResource(R.drawable.green);
                nmslt.setText(nslot.getNamaSlot().toString());
            }
            if (nslot.getStatusSlot() == 4){
                nmslt.setBackgroundResource(R.drawable.red);
                nmslt.setText(nslot.getNamaSlot().toString());
            }
            if (nslot.getStatusSlot() == 3){
                nmslt.setBackgroundResource(R.drawable.yellow);
                nmslt.setText(nslot.getNamaSlot().toString());
            }
            if (nslot.getStatusSlot() == 5){
                nmslt.setBackgroundResource(R.drawable.pintumsk);
                nmslt.setText(" ");
            }
            if (nslot.getStatusSlot() == 6){
                nmslt.setBackgroundResource(R.drawable.pintukluar);
                nmslt.setText(" ");
            }
            if (nslot.getStatusSlot() == 11){
                nmslt.setBackgroundResource(R.drawable.atas);
                nmslt.setText(" ");
            }
            if (nslot.getStatusSlot() == 12){
                nmslt.setBackgroundResource(R.drawable.kiri);
                nmslt.setText(" ");
            }
            if (nslot.getStatusSlot() == 13){
                nmslt.setBackgroundResource(R.drawable.kanan);
                nmslt.setText(" ");
            }
            if (nslot.getStatusSlot() == 14){
                nmslt.setBackgroundResource(R.drawable.bawah);
                nmslt.setText(" ");
            }

        }
        return convertView;
    }
}
