package com.akarakaya.ekutuphane;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Onizleme extends AppCompatActivity {

    private ParcelFileDescriptor mFileDescriptor;
    // For rendering a PDF document
    private PdfRenderer mPdfRenderer;
    private PdfRenderer.Page mCurrentPage;

    Button next;
    Button previous;
    String openedPdfFileName = "";
    private static int currentView;
    private static int currentPage = 0;

    Kitap kitap = null ;

    ImageView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onizleme);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Kutuphane kutuphane = Kutuphane_Liste.kutuphane;
        kitap = InLogin.kitap;

        pdfView = (ImageView) findViewById(R.id.pdfView);
        previous = (Button) findViewById(R.id.previous);
        next = (Button) findViewById(R.id.next);

        openedPdfFileName = "/storage/emulated/0/testthreepdf/"+kitap.getId()+".pdf";
        openRenderer(openedPdfFileName);
        updateView();
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage--;
                showPage(currentPage);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage++;
                showPage(currentPage);
            }
        });
    }

    private void openRenderer(String filePath) {
        File file = new File(filePath);
        try {
            mFileDescriptor = ParcelFileDescriptor.open(file,ParcelFileDescriptor.MODE_READ_ONLY);
            mPdfRenderer = new PdfRenderer(mFileDescriptor);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateView () {
        currentView = 2;
        showPage(currentPage);
//        closeOption.setVisible(true);
    }

    private void showPage(int index) {
        if (mPdfRenderer == null || mPdfRenderer.getPageCount() <= index
                || index < 0) {
            Log.i("burdayım", "ASDJASDABDNADAJDN");
            return;
        }
        // For closing the current page before opening another one.
        try {
            if (mCurrentPage != null) {
                mCurrentPage.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Open page with specified index
        mCurrentPage = mPdfRenderer.openPage(index);
        Bitmap bitmap = Bitmap.createBitmap(mCurrentPage.getWidth(),
                mCurrentPage.getHeight(), Bitmap.Config.ARGB_8888);

        // Pdf page is rendered on Bitmap
        mCurrentPage.render(bitmap, null, null,
                PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        // Set rendered bitmap to ImageView
        pdfView.setImageBitmap(bitmap);
        updateActionBarText();
    }

    private void updateActionBarText() {
        if (currentView == 2) {
            int index = mCurrentPage.getIndex();
            int pageCount = 4;
            previous.setEnabled(0 != index);
            next.setEnabled(index + 1 < pageCount);
//            getActionBar().setTitle(
            //                  openedPdfFileName + "(" + (index + 1) + "/" + pageCount
            //                        + ")");
        } else {
            getActionBar().setTitle(R.string.app_name);
        }
    }

    @Override
    public void onBackPressed() {
        File file = new File(openedPdfFileName);
        if (file.exists()) {
            file.delete();
        }
        Intent intent = new Intent(Onizleme.this,Kitap_Ayrinti.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            onBackPressed();
        }
        return false;
    }


}
