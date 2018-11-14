package com.android;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/kutuphane_service")
public class Kutuphane_Service {
	
	@GET
	@Path("/listele")
	@Produces(MediaType.APPLICATION_JSON)
	public String il (@QueryParam("ilkodu") String ilkodu) {
		String komut = "SELECT * FROM kutuph WHERE bulundugu_il="+Integer.parseInt(ilkodu);
		List <Kutuphane> kutuphaneler = new ArrayList<Kutuphane>();
		Connection myConnec;
		PreparedStatement preStat;
		ResultSet result;
		DBConnection mybag = new DBConnection();
		myConnec = mybag.setConnection();
		String response = "";
		try {
			preStat = myConnec.prepareStatement(komut);
			result = preStat.executeQuery();
			while (result.next()) {
				int id = result.getInt("kutuphid");
				String kutuphane_adi = result.getString("kutuphane_adi");
				int bulundugu_il = result.getInt("bulundugu_il");
				Kutuphane a = new Kutuphane(id, kutuphane_adi, bulundugu_il);
				kutuphaneler.add(a);
				response = Utitlity.kutuphaneJSON(kutuphaneler, true);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(response);
		return response;
	}

}
