package com.akarakaya.ekutuphane;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity{

    ProgressDialog prgDialog;

    public static String servis_Url = "http://192.168.1.10:8081/KutuphaneService/";

    boolean sureUzatmaDurumu = false;

    public static List<Kutuphane> kutuphaneler;
    private Spinner iller;
    static int il_kodu = 0;
    static String[] illerArray = {"Adana","Adıyaman","Afyon","Ağrı","Amasya","Ankara","Antalya","Artvin","Aydın","Balıkesir","Bilecik","Bingöl","Bitlis","Bolu","Burdur","Bursa","Çanakkale",
            "Çankırı","Çorum","Denizli","Diyarbakır","Edirne","Elazığ","Erzincan","Erzurum","Eskişehir","Gaziantep","Giresun","Gümüşhane","Hakkari","Hatay","Isparta","Mersin","İstanbul",
            "İzmir","Kars","Kastamonu","Kayseri","Kırklareli","Kırşehir","Kocaeli","Konya","Kütahya","Malatya","Manisa","Kahramanmaraş","Mardin","Muğla","Muş","Nevşehir","Niğde","Ordu",
            "Rize","Sakarya","Samsun","Siirt","Sinop","Sivas","Tekirdağ","Tokat","Trabzon","Tunceli","Şanlıurfa","Uşak","Van","Yozgat","Zonguldak","Aksaray","Bayburt","Karaman","Kırıkkale",
            "Batman","Şırnak","Bartın","Ardahan","Iğdır","Yalova","Karabük","Kilis","Osmaniye","Düzce"};

    private ArrayAdapter<String> dataAdapterForIller;
    static String kiralamaId = "";

    //ilk açıldığında
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button buton = (Button) findViewById(R.id.button);
        Button withoutlogin = (Button) findViewById(R.id.withoutlogin);

        File loglar = new File("/storage/emulated/0/EKutuphane/Log/");
        if (!loglar.exists()) {
            loglar.mkdirs();
        }
        File kitaplar [] = new File("/storage/emulated/0/EKutuphane/").listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return !pathname.isDirectory();
            }
        });
        if(kitaplar.length > 0) {
            String key ="";
            try {
                byte[] temp_pdf = FileUtils.readFileToByteArray(kitaplar[0]);
                String[] file_String = new String(temp_pdf,"ISO-8859-1").split("ozelkarakter1");
                key = file_String[1];
            } catch (IOException e) {
                e.printStackTrace();
            }
            File files [] = loglar.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return !pathname.isDirectory();
                }
            });
            for (int i=0; i<files.length;i++) {
                try {
                    byte[] temp_log = FileUtils.readFileToByteArray(files[i]);
                    String file_String = new String(temp_log,"ISO-8859-1");
                    String contentLog = AESCrypt.decrypt(key, file_String);
                    Calendar calendar = Calendar.getInstance();
                    Timestamp suankizaman = new Timestamp(calendar.getTime().getTime());
                    String tarihler[] = contentLog.split("ozelkarakter2");
                    String sontarihString = tarihler[tarihler.length-1];
                    Timestamp sonokunmatarih = Timestamp.valueOf(sontarihString);
                    String logTarihler[] = contentLog.split("ozelkarakter1");
                    String teslimString = logTarihler[1];
                    long threeDays = 3*24*60*60*1000;
                    Timestamp teslimTarihiUcGun = Timestamp.valueOf(teslimString);
                    teslimTarihiUcGun = new Timestamp(teslimTarihiUcGun.getTime()+ threeDays);
                    if (sonokunmatarih.after(suankizaman)) {  // Tarih saat kontrolü
                        kiralamaId = contentLog.split("ozelkarakter1")[2];
                        RequestParams params = new RequestParams();
                        params.put("kiralamaId", kiralamaId + "");
                        for (int j = 0;j<kitaplar.length;j++) {
                            kitaplar[j].delete();
                        }
                        for (int j =0; j<files.length;j++) {
                            files[j].delete();
                        }
                        karaListe(params);
                        break;
                    }else if (teslimTarihiUcGun.before(suankizaman)) { // Süre Uzatma
                        kiralamaId = contentLog.split("ozelkarakter1")[2];
                        RequestParams params = new RequestParams();
                        params.put("kiralamaId", kiralamaId + "");
                        boolean sureUzadiMi = sureuzatma(params);
                        if (!sureUzadiMi) {
                            files[i].delete();
                            File kitap = new File("/storage/emulated/0/EKutuphane/"+logTarihler[3]);
                            kitap.delete();
                            continue;
                        }
                        long fifteenDays = 15*24*60*60*1000;
                        Timestamp yeniTeslimTarihi = Timestamp.valueOf(teslimString+fifteenDays);
                        logTarihler[1] = yeniTeslimTarihi+"";
                        StringBuilder sb = new StringBuilder();
                        for (int j = 0; j<logTarihler.length;j++) {
                            sb.append(logTarihler[j]);
                            if (j != logTarihler.length - 1) {
                                sb.append("ozelkarakter1");
                            }
                        }
                        contentLog = sb.toString();
                    }
                    contentLog = contentLog + "ozelkarakter2"+suankizaman;
                    contentLog = AESCrypt.encrypt(key,contentLog);
                    FileWriter fileWriter = new FileWriter(files[i],false);
                    fileWriter.write(contentLog);
                    fileWriter.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        iller = (Spinner) findViewById(R.id.iller);
        dataAdapterForIller = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, illerArray);
        iller.setAdapter(dataAdapterForIller);
        iller.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                il_kodu = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                prgDialog = new ProgressDialog(MainActivity.this);
                prgDialog.setMessage("Please wait...");
                prgDialog.setCancelable(false);
                RequestParams params = new RequestParams();
                params.put("ilkodu", il_kodu+"");
                kutuphaneler = serviceKutuphane(params);

            }
        });
        withoutlogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, With_Out_Login.class);
                startActivity(i);
            }
        });
    }

    public List<Kutuphane> serviceKutuphane (RequestParams requestParams) {
        prgDialog.show();
        final List<Kutuphane> kutuphaneler = new ArrayList<Kutuphane>();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(servis_Url + "kutuphane_service/listele", requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                // Hide Progress Dialog
                prgDialog.hide();
                try {
                    // JSON Object
                    JSONObject obj = new JSONObject(response);
                    // When the JSON response has status boolean value assigned with true
                    if (obj.getBoolean("status")) { // status true olacak o zaman sıkıntı yok. serviste. java resources utility içinde
                        response = obj.getString("context");
                        String[] kutuphaneler_parser = response.split("&");
                        Log.i("kutuphaneler", kutuphaneler_parser.toString());
                        for (int i = 0; i < kutuphaneler_parser.length; i++) {
                            String[] iller_parser = kutuphaneler_parser[i].split(",");
                            int il = Integer.parseInt(iller_parser[2]) - 1;
                            Kutuphane kutuphane = new Kutuphane(Integer.parseInt(iller_parser[0]), iller_parser[1], MainActivity.illerArray[il]);
                            kutuphaneler.add(kutuphane);
                        }
                        Intent ıntent = new Intent(MainActivity.this, Kutuphane_Liste.class);
                        startActivity(ıntent);
                    }
                    // Else display error message
                    else {
                        Toast.makeText(getApplicationContext(), obj.getString("Kütüphaneler Listelenemedi"), Toast.LENGTH_LONG).show();
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
        return kutuphaneler;
    }

    public boolean sureuzatma(RequestParams requestParams) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(servis_Url+"indexOperations/sureuzatma",requestParams,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                try {
                    JSONObject obj = new JSONObject(content);
                    if (!obj.getBoolean("ok")) {
                        if (obj.getBoolean("sureasimi")) {
                            Toast.makeText(getApplicationContext(),"Bir kitabı iki defadan fazla uzatamazsınız,kitap iade ediliyor",Toast.LENGTH_LONG).show();
                            sureUzatmaDurumu = false;
                        }else{
                            sureUzatmaDurumu = false;
                            Toast.makeText(getApplicationContext(),"Süre uzatılamadı,kitap iade ediliyor",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        sureUzatmaDurumu = true;
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                        alertDialog.setTitle("Süre Uzatma");
                        alertDialog.setMessage("Kiralama Süresi 15 Gün Uzatıldı")
                                .setCancelable(false)
                                .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getApplicationContext(), "Süre 15 gün daha uzatıldı", Toast.LENGTH_LONG).show();
                                    }
                                });

                        AlertDialog dialog = alertDialog.create();
                        dialog.show();
                    }
                }catch (Exception e){
                    sureUzatmaDurumu = false;
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                if (statusCode == 404) {
                    sureUzatmaDurumu = false;
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    sureUzatmaDurumu = false;
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    sureUzatmaDurumu = false;
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
        return sureUzatmaDurumu;
    }

    public void karaListe(RequestParams requestParams) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(servis_Url+"indexOperations/karaliste",requestParams,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String content) {
                try {
                    JSONObject obj = new JSONObject(content);
                    if (obj.getBoolean("ok")) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                        alertDialog.setTitle("Hata Durumu");
                        alertDialog.setMessage("Muhtemelen Sistem Saati Yanlış")
                                .setCancelable(false)
                                .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Intent.ACTION_MAIN);
                                        intent.addCategory(Intent.CATEGORY_HOME);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                });

                        AlertDialog dialog = alertDialog.create();

                        dialog.show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Kara listeye alma sırasında problem oluştu", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                super.onFailure(statusCode, error, content);
            }
        });
    }

}

