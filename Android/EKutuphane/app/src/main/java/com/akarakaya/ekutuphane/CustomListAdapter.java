package com.akarakaya.ekutuphane;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by emre.akoglu on 13.09.2015.
 */
public class CustomListAdapter extends ArrayAdapter<Kutuphane> {

    private final Activity context;
    private final List<Kutuphane> kutuphane;

    public CustomListAdapter(Activity context,List<Kutuphane> kutuphane) {
        super(context,R.layout.mylist,kutuphane);
        this.context = context;
        this.kutuphane = kutuphane;

    }
    public View getView (int position ,View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.kutuphane_adi);
        TextView imageView = (TextView) rowView.findViewById(R.id.kutuphane_il);

        txtTitle.setText(kutuphane.get(position).getName());
        imageView.setText(kutuphane.get(position).getIl());
        return rowView;
    }
}
