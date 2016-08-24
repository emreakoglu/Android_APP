package com.akarakaya.ekutuphane;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class ReadEBook extends AppCompatActivity {

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
        setContentView(R.layout.activity_read_ebook);

        pdfView = (ImageView) findViewById(R.id.pdfView);
        previous = (Button) findViewById(R.id.previous);
        next = (Button) findViewById(R.id.next);

        File file = With_Out_Login.file;
        openedPdfFileName = file.getAbsolutePath();

        cozulen_doysa_path = "/storage/emulated/0/EKutuphane/cozuldu.pdf";
        try {
            byte[] temp_pdf = FileUtils.readFileToByteArray(new File(openedPdfFileName));
            String[] file_String = new String(temp_pdf,"ISO-8859-1").split("ozelkarakter1");
            int neresi = temp_pdf.length;
            byte[] decrypt = Arrays.copyOfRange(temp_pdf, neresi - 16, neresi);

            byte[] decrypt_temp = AESCrypt.decrypt(file_String[1],decrypt);
            for (int i=0;i<decrypt_temp.length;i++) {
                temp_pdf[i] = decrypt_temp[i];
            }
            FileUtils.writeByteArrayToFile(new File(cozulen_doysa_path), temp_pdf);
        }catch (Exception e){
            e.printStackTrace();
        }
        openRenderer(cozulen_doysa_path);
        updateView();
        File deletedfile = new File(cozulen_doysa_path);
        deletedfile.delete();

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

}
