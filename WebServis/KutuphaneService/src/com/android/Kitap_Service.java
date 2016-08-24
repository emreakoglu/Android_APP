package com.android;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/kitap_service")
public class Kitap_Service {
	
	@GET
	@Path("/listele")
	@Produces(MediaType.APPLICATION_JSON)
	public String kitapListele (@QueryParam("kutuphaneId") String kutuphaneId) {
		String komut = "SELECT * FROM kitap WHERE kutuphid="+Integer.parseInt(kutuphaneId);
		String response = "";
		List<Kitap> kitaplar = new ArrayList<Kitap>();
		Connection myConnec;
		PreparedStatement preStat;
		ResultSet result;
		DBConnection mybag = new DBConnection();
		myConnec = mybag.setConnection();
		
		try {
			preStat = myConnec.prepareStatement(komut);
			result = preStat.executeQuery();
			while (result.next()) {
				int id = result.getInt("kitapid");
				int kutuphid = result.getInt("kutuphid");
				String kitap_adi = result.getString("kitap_adi");
				int sayfa_sayisi = result.getInt("sayfa_sayisi");
				String ozellik = result.getString("ozellikleri");
				String yazarlar = result.getString("yazarlari");
				String isbn = result.getString("isbn");
				int adet = result.getInt("adedi");
				int suanki_adet = result.getInt("suanki_adedi");
				String ekitap_path = result.getString("ekitap");
				Kitap a = new Kitap(id, kutuphid,kitap_adi, sayfa_sayisi, ozellik, yazarlar, isbn, adet, suanki_adet, ekitap_path);
				kitaplar.add(a);
				response = Utitlity.kitapJSON(kitaplar, true);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(response);
		return response;
	}
	
	
	@GET
	@Path("/kirala")
	@Produces(MediaType.APPLICATION_JSON)
	public String kirala(@QueryParam("kitapId") String kitapId,@QueryParam("kullaniciid") String kullaniciId) {
		String response = "";
		String komut = "SELECT * from kitap WHERE kitapid="+Integer.parseInt(kitapId);
		Connection myConnec;
		PreparedStatement preStat;
		ResultSet result;
		DBConnection mybag = new DBConnection();
		myConnec = mybag.setConnection();
		Kitap kitap = null;
		
		try {
			preStat = myConnec.prepareStatement(komut);
			result = preStat.executeQuery();
			while (result.next()) {
				int id = result.getInt("kitapid");
				int kutuphid = result.getInt("kutuphid");
				String kitap_adi = result.getString("kitap_adi");
				int sayfa_sayisi = result.getInt("sayfa_sayisi");
				String ozellik = result.getString("ozellikleri");
				String yazarlar = result.getString("yazarlari");
				String isbn = result.getString("isbn");
				int adet = result.getInt("adedi");
				int suanki_adet = result.getInt("suanki_adedi");
				String ekitap_path = result.getString("ekitap");
				kitap = new Kitap(id, kutuphid,kitap_adi, sayfa_sayisi, ozellik, yazarlar, isbn, adet, suanki_adet, ekitap_path);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		if (kitap.suanki_adet > 0) {
			
			 Calendar calendar = Calendar.getInstance();
			 Timestamp kiralamatarih = new Timestamp(calendar.getTime().getTime());
			komut = "UPDATE kitap SET suanki_adedi="+(kitap.suanki_adet-1)+" WHERE kitapid="
					+kitap.id;
			String komut2 = "INSERT INTO kiralama (kullaniciid, kitapid, kontrol, sure_uzatma, "
					+ "kiralama_tarihi) VALUES (?,?,?,?,?)";
			
			String kiralamasorgu = "SELECT kiralamaid FROM kiralama WHERE kullaniciid="+Integer.parseInt(kullaniciId)+
					" AND kitapid="+Integer.parseInt(kitapId)+" AND kiralama_tarihi=?"+
					" AND kontrol=1";
			try {
				preStat = myConnec.prepareStatement(komut);
				preStat.executeUpdate();
				
				preStat = myConnec.prepareStatement(komut2);
				preStat.setInt(1, Integer.parseInt(kullaniciId));
				preStat.setInt(2, Integer.parseInt(kitapId));
				preStat.setInt(3, 1);
				preStat.setInt(4, 0);
				preStat.setTimestamp(5, kiralamatarih);
				preStat.execute();
				preStat = myConnec.prepareStatement(kiralamasorgu);
				preStat.setTimestamp(1, kiralamatarih);
				result = preStat.executeQuery();
				while (result.next()) {
					int kiralamaId = result.getInt("kiralamaid");
					response = Utitlity.kiralaJSON(kiralamaId,true);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response = Utitlity.kiralaJSON(0,false);
			}
		}
		else {
			response = Utitlity.kiralaJSON(0,false);
		}
		return response;
	}
	
	@GET
	@Path("/iade")
	@Produces(MediaType.APPLICATION_JSON)
	public String iade(@QueryParam("kiralamaId") String kiralamaId) {
		String response = "";
		String komut = "SELECT * FROM kiralama WHERE kiralamaid="+Integer.parseInt(kiralamaId);
		Connection myConnec;
		PreparedStatement preStat;
		ResultSet result;
		DBConnection mybag = new DBConnection();
		myConnec = mybag.setConnection();
		
		try {
			preStat = myConnec.prepareStatement(komut);
			result = preStat.executeQuery();
			int id = 0;
			while (result.next()) {
				 id = result.getInt("kitapid");
			}
			komut = "SELECT * FROM kitap WHERE kitapid="+id;
			preStat = myConnec.prepareStatement(komut);
			result = preStat.executeQuery();
			Kitap kitap = null;
			while (result.next()) {
				id = result.getInt("kitapid");
				int kutuphid = result.getInt("kutuphid");
				String kitap_adi = result.getString("kitap_adi");
				int sayfa_sayisi = result.getInt("sayfa_sayisi");
				String ozellik = result.getString("ozellikleri");
				String yazarlar = result.getString("yazarlari");
				String isbn = result.getString("isbn");
				int adet = result.getInt("adedi");
				int suanki_adet = result.getInt("suanki_adedi");
				String ekitap_path = result.getString("ekitap");
				kitap = new Kitap(id, kutuphid,kitap_adi, sayfa_sayisi, ozellik, yazarlar, isbn, adet, suanki_adet, ekitap_path);
			}
			komut = "UPDATE kitap SET suanki_adedi="+(kitap.suanki_adet+1)+" WHERE kitapid="+kitap.getId();
			preStat = myConnec.prepareStatement(komut);
			preStat.executeUpdate();
			komut = "UPDATE kiralama SET kontrol=0 WHERE kiralamaid="+Integer.parseInt(kiralamaId);
			preStat = myConnec.prepareStatement(komut);
			preStat.executeUpdate();
			response = Utitlity.iadeJSON(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			response = Utitlity.iadeJSON(false);
			e.printStackTrace();
		}
		
		return response;
	}
}
