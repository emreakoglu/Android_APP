package com.android;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/login")
public class Login {
	 // HTTP Get Method
    @GET
    // Path: http://localhost/<appln-folder-name>/login/dologin
    @Path("/dologin")
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON)
    // Query parameters are parameters: http://localhost/<appln-folder-name>/login/dologin?username=abc&password=xyz
    public String doLogin(@QueryParam("username") String uname, @QueryParam("password") String pwd,@QueryParam("kutuphid") String kutuphid){
    	
        String response = "  dadsadas";
        String komut = "SELECT * FROM kullanici WHERE kullanici_adi='"+uname+"' AND sifre='"+pwd+"' AND kutuphid="+Integer.parseInt(kutuphid);
        Connection myConnec;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        DBConnection mybag = new DBConnection();
        myConnec = mybag.setConnection();
        
        try {
        	preparedStatement = myConnec.prepareStatement(komut);
        	resultSet = preparedStatement.executeQuery();
        	while (resultSet.next()) {
        		int id = resultSet.getInt("kullaniciid");
        		String adi = resultSet.getString("adi");
        		String soyadi = resultSet.getString("soyadi");
        		String tc = resultSet.getString("tc");
        		int yas = resultSet.getInt("yas");
        		String meslek = resultSet.getString("meslek");
        		String kullanici_adi = resultSet.getString("kullanici_adi");
        		String sifre = resultSet.getString("sifre");
        		String kisiye_ozel = resultSet.getString("kisiye_ozel");
        		int kullanici_kontrolu = resultSet.getInt("kullanici_kontrolu");
        		User user = new User(id, adi, soyadi, tc, yas, meslek, kullanici_adi, sifre, kisiye_ozel,Integer.parseInt(kutuphid));
        		if (kullanici_kontrolu == 0) {
        			response = Utitlity.userJSON(user, false);
        		}
        		else {
        			response = Utitlity.userJSON(user, true);
        		}
        	}
        }catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
		}
        System.out.println(response);
        return response;        
    }
 
}
