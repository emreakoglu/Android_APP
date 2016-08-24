package com.akarakaya.ekutuphane;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by akarakaya on 27.12.2015.
 */
public class CustomListKitap extends ArrayAdapter<Kitap> {

    private final Activity context;
    private final List<Kitap> kitaplar;

    public CustomListKitap(Activity context,List<Kitap> kitap) {
        super(context,R.layout.mylist,kitap);
        this.context = context;
        this.kitaplar = kitap;

    }
    public View getView (int position ,View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.kutuphane_adi);
        TextView imageView = (TextView) rowView.findViewById(R.id.kutuphane_il);

        txtTitle.setText(kitaplar.get(position).getKitap_adi());
        imageView.setText(kitaplar.get(position).getKutuphane_adi());
        return rowView;
    }
}
