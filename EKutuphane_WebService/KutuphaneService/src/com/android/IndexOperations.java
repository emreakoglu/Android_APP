package com.android;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/indexOperations")
public class IndexOperations {
	
	@GET
	@Path("/karaliste")
	@Produces(MediaType.APPLICATION_JSON)
	public String karaliste(@QueryParam("kiralamaId") String kiralamaId){
		int kiralama = Integer.parseInt(kiralamaId);
		String komut = "SELECT * FROM kiralama WHERE kiralamaid="+kiralama;
		Connection myConnec;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        DBConnection mybag = new DBConnection();
        myConnec = mybag.setConnection();
        int kullaniciId = 0;
        JSONObject obj = new JSONObject();
        
        try {
        	preparedStatement = myConnec.prepareStatement(komut);
        	resultSet = preparedStatement.executeQuery();
        	while (resultSet.next()) {
        		kullaniciId = resultSet.getInt("kullaniciid");
        	}
        	String kitaplar_sifirla = "SELECT * FROM kiralama WHERE kullaniciid="+kullaniciId+" AND kontrol=1";
        	preparedStatement = myConnec.prepareStatement(kitaplar_sifirla);
        	resultSet = preparedStatement.executeQuery();
        	while(resultSet.next()) {
        		int kitapid = resultSet.getInt("kitapid");
        		String kitap_Arttir = "UPDATE kitap SET suanki_adedi = suanki_adedi+1 WHERE kitapid="+kitapid;
        		preparedStatement = myConnec.prepareStatement(kitap_Arttir);
        		preparedStatement.executeUpdate();
        	}
        	String kontrol_sifirla = "UPDATE kiralama SET kontrol = ? WHERE kullaniciid="+kullaniciId;
        	preparedStatement = myConnec.prepareStatement(kontrol_sifirla);
        	preparedStatement.setInt(1, 0);
        	preparedStatement.executeUpdate();
        	String kullanici_Sifirla = "UPDATE kullanici SET kullanici_kontrolu=0 WHERE kullaniciid="+kullaniciId;
        	preparedStatement = myConnec.prepareStatement(kullanici_Sifirla);
        	preparedStatement.executeUpdate();
        	obj.put("ok", true);
        }catch(Exception e) {
        	try {
				obj.put("ok", false);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	e.printStackTrace();
        }
        return obj.toString();
        
	}
	
	@GET
	@Path("/sureuzatma")
	@Produces(MediaType.APPLICATION_JSON)
	public String sureuzatma(@QueryParam("kiralamaId") String kiralamaId) {
		int kiralama = Integer.parseInt(kiralamaId);
		String komut = "SELECT * FROM kiralama WHERE kiralamaid="+kiralama;
		Connection myConnec;
		PreparedStatement preStat;
		ResultSet result;
		DBConnection mybag = new DBConnection();
		myConnec = mybag.setConnection();
		
		JSONObject obj = new JSONObject();
		
		try {
			preStat = myConnec.prepareStatement(komut);
			result = preStat.executeQuery();
			while (result.next()) {
				int sure_uzatma = result.getInt("sure_uzatma");
				int kitapid = result.getInt("kitapid");
				if (sure_uzatma >= 2) {
					try {
						String updateKiralama = "UPDATE kiralama SET kontrol = ? WHERE kiralamaid="+kiralama;
						preStat = myConnec.prepareStatement(updateKiralama);
						preStat.setInt(1, 0);
						preStat.executeUpdate();
						String updateKitap = "UPDATE kitap SET suanki_adedi = suanki_adedi+1 WHERE kitapid="+kitapid;
						preStat = myConnec.prepareStatement(updateKitap);
						preStat.executeUpdate();
						obj.put("ok", false);
						obj.put("sureasimi", true);
					}catch(Exception e) {
						try {
							obj.put("ok", false);
							obj.put("sureasimi", false);
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}else {
					sure_uzatma++;
					Calendar calendar = Calendar.getInstance();
					Timestamp kiralamatarih = new Timestamp(calendar.getTime().getTime());
					komut = "UPDATE kiralama SET sure_uzatma = ?, kiralama_tarihi = ? WHERE kiralamaid="+kiralama;
					try {
						preStat = myConnec.prepareStatement(komut);
						preStat.setInt(1, sure_uzatma);
						preStat.setTimestamp(2, kiralamatarih);
						preStat.executeUpdate();
						obj.put("ok", true);
						obj.put("sureasimi", false);
					}catch (SQLException e) {
						try {
							obj.put("ok", false);
							obj.put("sureasimi", false);
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}catch (JSONException e) {
						try {
							obj.put("ok", false);
							obj.put("sureasimi", false);
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		}catch(SQLException e) {
			try {
				obj.put("ok", false);
				obj.put("sureasimi", false);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return obj.toString();
	}
}
