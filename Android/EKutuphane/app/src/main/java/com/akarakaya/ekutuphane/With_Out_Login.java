package com.akarakaya.ekutuphane;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.provider.ContactsContract;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.io.FileFilter;

public class With_Out_Login extends AppCompatActivity {
    public static File file =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.with__out__login);
        Button sureuzat = (Button) findViewById(R.id.sureuzatma);
        sureuzat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(With_Out_Login.this, Sure.class);
                startActivity(i);
            }
        });

        ListView kitap_listView = (ListView) findViewById(R.id.pdfList);
        File f = new File("/storage/emulated/0/EKutuphane/");
        final File files [] = f.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return !file.isDirectory();
            }
        });
        CustomArrayFile adapter = new CustomArrayFile(this,files);
        kitap_listView.setAdapter(adapter);

        kitap_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                file = files[position];

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(With_Out_Login.this);
                alertDialog.setTitle(file.getName());
                alertDialog.setMessage("Yapmak istediğiniz işlemi seçin")
                        .setCancelable(true)
                        .setPositiveButton("İade", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(With_Out_Login.this, Iade.class));
                            }
                        })
                        .setNegativeButton("Oku", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(With_Out_Login.this, ReadEBook.class));
                            }
                        });

                AlertDialog dialog = alertDialog.create();

                dialog.show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_with__out__login, menu);
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
