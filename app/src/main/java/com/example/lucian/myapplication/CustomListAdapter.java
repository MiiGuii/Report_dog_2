package com.example.lucian.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucian on 26-07-18.
 */

public class CustomListAdapter extends ArrayAdapter<Reporte> {

    ArrayList<Reporte> reportes;
    Context context;
    int resource;
    public CustomListAdapter(@NonNull Context context, int resource, @NonNull List<Reporte> objects) {
        super(context, resource, objects);
        this.reportes = reportes;
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){
            LayoutInflater layoutInflater =  (LayoutInflater)getContext().
                    getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_list_layout, null, true);
        }
        Reporte reporte = getItem(position);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);

        TextView txtDescc = (TextView) convertView.findViewById(R.id.desccText);
        txtDescc.setText(reporte.getDescc());

        return super.getView(position, convertView, parent);
    }
}
