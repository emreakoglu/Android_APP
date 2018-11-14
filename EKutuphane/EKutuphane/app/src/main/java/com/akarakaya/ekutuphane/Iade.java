package com.akarakaya.ekutuphane;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Arrays;

public class Iade extends AppCompatActivity {

    boolean toast;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iade);

        file = With_Out_Login.file;

        try {
            byte[] temp_pdf = FileUtils.readFileToByteArray(file);
            String[] file_String = new String(temp_pdf,"ISO-8859-1").split("ozelkarakter1");
            iade_et(file_String[2]);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void iade_et (final String kiramalamaId) {
        RequestParams params = new RequestParams();
        params.put("kiralamaId", kiramalamaId);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(MainActivity.servis_Url+"kitap_service/iade",params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                try {
                    JSONObject obj = new JSONObject(content);
                    toast = obj.getBoolean("onay");
                    if (toast) {
                        File logFile = new File("/storage/emulated/0/EKutuphane/Log/"+kiramalamaId);
                        logFile.delete();
                        file.delete();
                        Toast.makeText(getApplicationContext(), "İade Başarılı", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Iade.this,With_Out_Login.class));
                    }else {
                        Toast.makeText(getApplicationContext(), "İade Başarılı Olmadı", Toast.LENGTH_LONG).show();
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
