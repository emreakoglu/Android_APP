package com.akarakaya.ekutuphane;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akarakaya on 17.11.2015.
 */

public class InLogin extends AppCompatActivity {

    public static Kitap kitap= null;
    ProgressDialog prgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inlogin);

        TextView kul_adi = (TextView) findViewById(R.id.kul_adi);
        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Please wait...");
        prgDialog.setCancelable(false);
        final List<Kitap> kitaplar  = new ArrayList<Kitap>();
        InputStream inInputStream = null;
        String result = "";
        final Kutuphane kutuphane = Kutuphane_Liste.kutuphane;
        List<Kitap> kitaps = Login.kitaps;
        kul_adi.setText(Login.user.getAdi() + " "+Login.user.getSoyadi());
        String kutuphid = ""+kutuphane.getId();

        RequestParams params = new RequestParams();
        params.put("kutuphaneId", kutuphid);

        // CustomListAdapter.java
        ListView listView = (ListView) findViewById(R.id.kitaplar);
        CustomListKitap adapter = new CustomListKitap(this,kitaps);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                kitap = Login.kitaps.get(position);
                Intent i = new Intent(InLogin.this, Kitap_Ayrinti.class);
                startActivity(i);
            }
        });
    }

    protected void execute (String... strings) {
        RequestParams params = new RequestParams();
        params.put("kitapadi", strings[0]);
        params.put("kutuphaneId",strings[1]);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(MainActivity.servis_Url+"pdftops/converter", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    if (jsonObject.getBoolean("ok")) {

                    } else {
                        Toast.makeText(getApplicationContext(), "Hata", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

