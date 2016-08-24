package com.android;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	Connection connection=null;
	PreparedStatement preparedStatement;
	
	private String dbName;
	private String dbUser;
	private String dbPass;	
	private String url;
	public DBConnection() {
		// TODO Auto-generated constructor stub
		this.dbName = "ekutuphane";
		this.dbUser = "postgres";   //postgres
		this.dbPass = "pg1234"; //
		this.url = "jdbc:postgresql://localhost:5432/" + dbName ;
	}
	public Connection setConnection(){
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, dbUser, dbPass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			return null;
		}
		return connection;
	}
	public void closeConnection() {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
