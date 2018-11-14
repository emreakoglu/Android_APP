package com.akarakaya.ekutuphane;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class Kitap_Ayrinti extends AppCompatActivity {

    static Kitap kitap;
    static ProgressDialog prgDialog;
    Kutuphane kutuphane;
    String toast = "";
    String fileUrl ="";
    String fileName ="";
    File folder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitap__ayrinti);

        TextView kul_adi = (TextView) findViewById(R.id.kul_adi);
        TextView kitap_adi = (TextView) findViewById(R.id.kitap_adi);
        TextView kutuphane_adi = (TextView) findViewById(R.id.kutuphane);
        TextView sayfa_sayisi = (TextView) findViewById(R.id.sayfa_sayisi);
        TextView ozellik = (TextView) findViewById(R.id.ozellik);
        TextView yazarlar = (TextView) findViewById(R.id.yazarlar);
        TextView isbn = (TextView) findViewById(R.id.isbn);
        TextView adet = (TextView) findViewById(R.id.adet);
        TextView suanki_adet = (TextView) findViewById(R.id.suanki_adet);

        kutuphane = Kutuphane_Liste.kutuphane;
        kitap = InLogin.kitap;
        User user = Login.user;
        kul_adi.setText(user.getAdi() +" " +user.getSoyadi());
        kitap_adi.setText(kitap.getKitap_adi());
        kutuphane_adi.setText(kitap.getKutuphane_adi());
        sayfa_sayisi.setText(""+kitap.getSayfa_sayisi());
        ozellik.setText(kitap.getOzellik());
        yazarlar.setText(kitap.getYazarlari());
        isbn.setText(kitap.getIsbn());
        adet.setText(""+kitap.getAdet());
        suanki_adet.setText(""+kitap.getSuanki_adet());

        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Please wait...");
        prgDialog.setCancelable(false);


        Button onizleme = (Button) findViewById(R.id.onizleme);
        Button kirala = (Button) findViewById(R.id.kirala);

        onizleme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prgDialog.show();
                new Onizlemek().execute("http://192.168.1.10:8081/KitapDeposu/"+kutuphane.getId() + "/" + kitap.getKitap_adi() + ".pdf", kitap.getId()+"");
            }
        });

        kirala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prgDialog.show();
                execute("http://192.168.1.10:8081/KitapDeposu/" + kutuphane.getId() + "/" + kitap.getKitap_adi() + ".pdf", kitap.getKitap_adi());
            }
        });

    }

    private class Onizlemek extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String fileUtl = strings[0];
            fileName = strings[1];
            String extStorageDirectoty = Environment.getExternalStorageDirectory().toString();
            folder = new File (extStorageDirectoty,"EKutuphane");
            folder.mkdir();

            File pdfFile = new File(folder,fileName);

            try {
                pdfFile.createNewFile();
            }catch (IOException e) {
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUtl,pdfFile);
            prgDialog.dismiss();

            Intent intent = new Intent(Kitap_Ayrinti.this, Onizleme.class);
            startActivity(intent);
            return null;
        }
    }
    protected void execute(String... strings) {
        fileUrl = strings[0];
        fileName = strings[1];
        String extStorageDirectoty = Environment.getExternalStorageDirectory().toString();
        folder = new File (extStorageDirectoty,"EKutuphane");
        folder.mkdir();

        RequestParams params = new RequestParams();
        params.put("kitapId", kitap.getId() + "");
        params.put("kullaniciid",Login.user.getId()+"");
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(MainActivity.servis_Url+"kitap_service/kirala", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                try {
                    // JSON Object
                    JSONObject obj = new JSONObject(response);
                    toast = obj.getString("context");
                    // When the JSON response has status boolean value assigned with true
                    if (obj.getString("context").equals("Kitap Ödünç Alındı")) { // Kitap kiralandı
                        Toast.makeText(getApplicationContext(), obj.getString("context"), Toast.LENGTH_LONG).show();
                        File pdfFile = new File(folder,fileName);
                        Sifrele.dosya_sifrele(fileUrl, pdfFile, "/storage/emulated/0/EKutuphane/", fileName, obj.getInt("kiralamaId"));
                        Intent intent = new Intent(Kitap_Ayrinti.this, Kirala.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("context"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {
                // Hide Progress Dialog
                prgDialog.hide();
                // When Http response code is '404'
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kitap__ayrinti, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
