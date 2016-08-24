package com.akarakaya.ekutuphane;

import android.util.Log;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Aykut Karakaya on 17.03.2016.
 */
public class Sifrele {
    static String opendeFileName = "";

    public static void dosya_sifrele(String fileUtl,File file,String fileUrl,String fileName,int kiralamaId) {

        try {
            file.createNewFile();  //Sonu pdf ile bitiyor
        }catch (IOException e) {
            e.printStackTrace();
        }
        FileDownloader.downloadFile(fileUtl, file); // orjinal dosya
        try {

            FileWriter fw = new FileWriter(fileUrl+fileName,true);
            fw.write("ozelkarakter1" + Login.user.getKisiye_ozel() + "ozelkarakter1" + kiralamaId + "ozelkarakter1");
            fw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        String key = Login.user.getKisiye_ozel();
        Log.i("Dosya Adi",fileUrl+fileName);
        try {
            byte[] temp_pdf = FileUtils.readFileToByteArray(file);
            byte [] encrypt_temp = Arrays.copyOfRange(temp_pdf, 0, 15);
            byte []encrypt = AESCrypt.encrypt(key, encrypt_temp);
            for (int i = 0;i<encrypt_temp.length;i++) {
                temp_pdf[i] = encrypt[i];
            }
            byte [] sifreli_son = merge(temp_pdf, encrypt);
            FileUtils.writeByteArrayToFile(new File(fileUrl + fileName), sifreli_son);
        }catch (Exception e){
            e.printStackTrace();
        }

        File log = new File(fileUrl+"Log/"+kiralamaId);
        try {
            log.createNewFile();
            Calendar calendar = Calendar.getInstance();
            Timestamp kiralamatarih = new Timestamp(calendar.getTime().getTime());
            FileWriter writer = new FileWriter(log);
            long fifteenDays = 15*24*60*60*1000;
            Timestamp teslimTarihi = new Timestamp(calendar.getTime().getTime()+fifteenDays);
            String writeText = kiralamatarih+"ozelkarakter1"+teslimTarihi+"ozelkarakter1"+kiralamaId+"ozelkarakter1"+InLogin.kitap.getKitap_adi()+"ozelkarakter1"+InLogin.kitap.getId()+
            "ozelkarakter1"+Kutuphane_Liste.kutuphane.getId()+"ozelkarakter1"+Login.user.getId()+"ozelkarakter2"+kiralamatarih;
            writeText = AESCrypt.encrypt(key,writeText);
            writer.write(writeText);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        opendeFileName = fileUrl+fileName;
    }

    public static byte[] merge(byte []a,byte[]b) {
        int size = a.length+b.length;
        byte [] yeni = new byte[size];
        System.arraycopy(a,0,yeni,0,a.length);
        System.arraycopy(b,0,yeni,a.length,b.length);
        return yeni;
    }
}
