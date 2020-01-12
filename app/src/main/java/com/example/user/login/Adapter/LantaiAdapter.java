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

import com.example.user.login.Model.Lantai;
import com.example.user.login.Model.Slot;
import com.example.user.login.R;

import java.util.List;

/**
 * Created by User on 28/11/2019.
 */

public class LantaiAdapter extends ArrayAdapter<Lantai> {
    private Context context;

    public LantaiAdapter(@NonNull Context context, int resource, @NonNull List<Lantai> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_lantai, parent, false
            );
        }
        final Lantai nlantai = getItem(position);
        TextView nmlt = (TextView) convertView.findViewById(R.id.nama_lantai);
        nmlt.setText(nlantai.getNamaLantai().toString());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        final Lantai nlantai = getItem(position);
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setText(nlantai.getNamaLantai().toString());

        return label;
    }
}
