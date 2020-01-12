package com.example.user.login.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.user.login.Model.Mobil;
import com.example.user.login.R;

import java.util.List;

/**
 * Created by Avin on 11/07/2018.
 */

public class ListMobilAdapter extends ArrayAdapter<Mobil> {
    Context context;

    public ListMobilAdapter(@NonNull Context context, int resource, @NonNull List<Mobil> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.listmobil, parent, false
            );
        }

        Mobil mobket = getItem(position);

        TextView bk = (TextView) convertView.findViewById(R.id.bk);
        bk.setText(mobket.getNoPlat());

        TextView tipe = (TextView)convertView.findViewById(R.id.tipe);
        tipe.setText(mobket.getTipe());

        return convertView;
    }


}