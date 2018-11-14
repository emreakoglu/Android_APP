package com.akarakaya.ekutuphane;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Pdf extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);



        Button download = (Button) findViewById(R.id.download);
        Button view = (Button) findViewById(R.id.view);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadFile().execute("http://maven.apache.org/maven-1.x/maven.pdf", "maven.pdf");
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File pdfFile = new File(Environment.getExternalStorageDirectory() + "/testthreepdf/" + "maven.pdf");  // -> filename = maven.pdf
                Uri path = Uri.fromFile(pdfFile);

                //Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                //pdfIntent.setDataAndType(path, "application/pdf");
                ParcelFileDescriptor parcelFileDescriptor = null;
                try {
                    parcelFileDescriptor = ParcelFileDescriptor.open(pdfFile,ParcelFileDescriptor.MODE_READ_ONLY);
                }catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                PdfRenderer pdfRenderer = null;
            }
        });

    }
    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "testthreepdf");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            return null;
        }
    }

}
