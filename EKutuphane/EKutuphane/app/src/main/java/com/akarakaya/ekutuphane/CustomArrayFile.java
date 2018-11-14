package com.akarakaya.ekutuphane;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.File;

/**
 * Created by Aykut Karakaya on 11.04.2016.
 */
public class CustomArrayFile extends ArrayAdapter<File> {

    private final Activity context;
    private final File[] files;

    public CustomArrayFile(Activity context,File[] files){
        super(context,R.layout.arraylist,files);
        this.context = context;
        this.files = files;
    }

    public View getView (int position,View view,ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View rowView = layoutInflater.inflate(R.layout.arraylist, null, true);

        TextView fileName = (TextView)rowView.findViewById(R.id.kitap_adi);

        fileName.setText(files[position].getName());

        return rowView;
    }

}