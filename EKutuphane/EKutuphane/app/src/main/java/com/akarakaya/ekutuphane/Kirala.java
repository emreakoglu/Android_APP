
package com.akarakaya.ekutuphane;

import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.FileObserver;
import android.os.ParcelFileDescriptor;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.apache.commons.io.FileUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.BasicPermission;
import java.util.Arrays;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Kirala extends AppCompatActivity {

    Kutuphane kutuphane;
    Kitap kitap;

    private ParcelFileDescriptor mFileDescriptor;
    private PdfRenderer mPdfRenderer;
    private PdfRenderer.Page mCurrentPage;

    Button next;
    Button previous;
    String openedPdfFileName = "";
    String cozulen_doysa_path = "";
    private static int currentView;
    private static int currentPage = 0;

    ImageView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kirala);

        pdfView = (ImageView) findViewById(R.id.pdfView); //
        previous = (Button) findViewById(R.id.previous);
        next = (Button) findViewById(R.id.next);
        openedPdfFileName = Sifrele.opendeFileName; //şifreli dosyanın yeri
        String key = Login.user.getKisiye_ozel();
        cozulen_doysa_path = "/storage/emulated/0/EKutuphane/cozuldu.pdf";
        try {
            byte[] temp_pdf = FileUtils.readFileToByteArray(new File(openedPdfFileName));
            int neresi = temp_pdf.length;
            byte[] decrypt = Arrays.copyOfRange(temp_pdf,neresi-16,neresi);
            byte[] decrypt_temp = AESCrypt.decrypt(key,decrypt);
            for (int i=0;i<decrypt_temp.length;i++) {
                temp_pdf[i] = decrypt_temp[i];
            }
            FileUtils.writeByteArrayToFile(new File(cozulen_doysa_path), temp_pdf);
        }catch (Exception e){
            e.printStackTrace();
        }
        Kitap_Ayrinti.prgDialog.hide();
        openRenderer(cozulen_doysa_path);
        updateView();
        File file = new File(cozulen_doysa_path);
        file.delete();
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
        super.onBackPressed();
        File file = new File(cozulen_doysa_path);
        file.delete();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            File file = new File(cozulen_doysa_path);
            file.delete();
        }
        if (keyCode == KeyEvent.KEYCODE_MOVE_HOME) {
            File file = new File(cozulen_doysa_path);
            file.delete();
        }
        return super.onKeyDown(keyCode, event);
    }
}