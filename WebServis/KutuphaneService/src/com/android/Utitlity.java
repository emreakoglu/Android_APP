package com.android;

import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
 
public class Utitlity {
    /**
     * Null check Method
     * 
     * @param txt
     * @return
     */
    public static boolean isNotNull(String txt) {
        // System.out.println("Inside isNotNull");
        return txt != null && txt.trim().length() >= 0 ? true : false;
    }
 
    public static String userJSON(User user, boolean status) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("context", user.toString());
            obj.put("status", new Boolean(status));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return obj.toString();
    }
    
    public static String kutuphaneJSON (List <Kutuphane> kutuphaneler, boolean status) {
    	JSONObject obj = new JSONObject();
    	try {
    		obj.put("context", kutuphaneler.toString().substring(2, kutuphaneler.toString().length()-1));
    		obj.put("status", true);
    	}catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return obj.toString();
    }
    
    public static String kitapJSON(List<Kitap> kitaplar, boolean status) {
    	JSONObject obj = new JSONObject();
    	try {
    		obj.put("context",kitaplar.toString().substring(2, kitaplar.toString().length()-1));
    		obj.put("status", true);
    	}catch (JSONException e) {
    		
    	}
    	return obj.toString();
    }
    
    public static String kiralaJSON(int kiralamaId,boolean status) {
    	JSONObject obj = new JSONObject();
    	try {
    		if (status) {
    			obj.put("context", "Kitap Ödünç Alýndý");
    			obj.put("kiralamaId", kiralamaId);
    		}
    		else {
    			obj.put("context", "Mevcut Kitaplar Ödünç Verildi, Ödünç Verilemiyor");
    		}
    	}catch (JSONException e) {
    		
    	}
    	return obj.toString();
    }
    
    public static String iadeJSON(boolean onay) {
    	JSONObject obj = new JSONObject();
    	try {
    		obj.put("onay", onay);
    	}catch (JSONException e) {
    		
    	}
    	return obj.toString();
    }
    
    public static String pdftopsJSON (boolean status) {
    	JSONObject obj = new JSONObject();
    	try {
    		obj.put("ok", status);
    	}catch (JSONException e) {
    		
    	}
    	return obj.toString();
    }
 
    /**
     * Method to construct JSON with Error Msg
     * 
     * @param tag
     * @param status
     * @param err_msg
     * @return
     */
    public static String constructJSON(String tag, boolean status,String err_msg) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("tag", tag);
            obj.put("status", new Boolean(status));
            obj.put("error_msg", err_msg);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return obj.toString();
    }

}
