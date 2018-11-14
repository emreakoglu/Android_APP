package com.akarakaya.ekutuphane;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
    EditText etUsername, etPassword;

    ProgressDialog prgDialog;

    static String username = "";
    static String password = "";

    static User user ;
    static List<Kitap> kitaps;
    Kutuphane kutuphane;
    User temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button bLogin = (Button) findViewById(R.id.bLogin);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        kutuphane = Kutuphane_Liste.kutuphane;

        Log.i("kutuphane id", kutuphane.getId() + "");
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prgDialog = new ProgressDialog(Login.this);
                prgDialog.setMessage("Please wait...");
                prgDialog.setCancelable(false);
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();
                RequestParams params = new RequestParams();
                params.put("username",username);
                params.put("password", password);
                String kutuphid = "" + kutuphane.getId();
                params.put("kutuphid", kutuphid);
                login(params);
            }
        });
    }

    public void login (RequestParams requestParams) {
        prgDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(MainActivity.servis_Url+"login/dologin", requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                prgDialog.hide();
                try {
                    // JSON Object
                    JSONObject obj = new JSONObject(response);
                    // When the JSON response has status boolean value assigned with true
                    if (obj.getBoolean("status")) {
                        Toast.makeText(getApplicationContext(), "You are successfully logged in!", Toast.LENGTH_LONG).show();
                        response = obj.getString("context");
                        Log.i("Response", response);
                        String[] user_array = response.split(",");
                        user = new User(Integer.parseInt(user_array[0]), user_array[1], user_array[2], user_array[3], Integer.parseInt(user_array[4]), user_array[5], user_array[6], user_array[7],user_array[8] ,Integer.parseInt(user_array[9]));
                        //tempUser = new User(Integer.parseInt(user_array[0]), user_array[1], user_array[2], user_array[3], Integer.parseInt(user_array[4]), user_array[5], user_array[6], user_array[7], Integer.parseInt(user_array[8]));
                        RequestParams params = new RequestParams();
                        params.put("kutuphaneId",kutuphane.getId()+"");
                        serviceKitap(params);
                    } else {
                        Toast.makeText(getApplicationContext(), "Uygunsuz bir işlem yapmış olabilirsiniz. Hesabınız askıya alındı", Toast.LENGTH_LONG).show();
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

    public void serviceKitap (RequestParams requestParams) {
        prgDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(MainActivity.servis_Url+"kitap_service/listele",requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                // Hide Progress Dialog
                prgDialog.hide();
                try {
                    // JSON Object
                    JSONObject obj = new JSONObject(response);
                    // When the JSON response has status boolean value assigned with true
                    if (obj.getBoolean("status")) {
                        kitaps = new ArrayList<Kitap>();
                        Toast.makeText(getApplicationContext(), "You are successfully logged in!", Toast.LENGTH_LONG).show();
                        response = obj.getString("context");
                        String[] kitaplar_parser = response.split("&");
                        for (int i=0;i<kitaplar_parser.length;i++) {
                            String[] iller_parser = kitaplar_parser[i].split(",");
                            Kitap kitap = new Kitap(Integer.parseInt(iller_parser[0]),Integer.parseInt(iller_parser[1]),kutuphane.getName(),iller_parser[2],Integer.parseInt(iller_parser[3]),iller_parser[4],
                                    iller_parser[5],iller_parser[6],Integer.parseInt(iller_parser[7]),Integer.parseInt(iller_parser[8]),iller_parser[9]);
                            kitaps.add(kitap);
                        }
                        Intent i = new Intent(Login.this, InLogin.class);
                        startActivity(i);
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
    }
}
