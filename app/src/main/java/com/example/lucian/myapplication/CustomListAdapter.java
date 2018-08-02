package com.example.lucian.myapplication;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucian on 26-07-18.
 */

public class CustomListAdapter extends ArrayAdapter<String> {

    ArrayList<String> a_imagen;
    String[] a_descc;
    Context mContext;

    public CustomListAdapter(Context context, ArrayList<String> imagen, String[] descc) {
        super(context, R.layout.listview_item);
        this.a_descc = descc;
        this.a_imagen = imagen;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return a_descc.length;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder mViewHolder = new ViewHolder();

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.listview_item, parent, false);
            mViewHolder.dog = (ImageView) convertView.findViewById(R.id.imagenDog);
            mViewHolder.descr = (TextView) convertView.findViewById(R.id.desccDog);
            convertView.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        Picasso.get().load(Uri.parse( a_imagen.get(position))).fit().into(mViewHolder.dog);
        mViewHolder.descr.setText(a_descc[position]);

        return convertView;
    }

    static class ViewHolder{
        ImageView dog;
        TextView descr;
    }
}
