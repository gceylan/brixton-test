package com.gceylan.broxintest.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.gceylan.broxintest.beans.Grup;

public class DatabaseConnection {
	
	Connection connection = null;
	PreparedStatement preparedStatement;
	
	private String dbName;
	private String dbUser;
	private String dbPass;	
	private String url;
	
	public DatabaseConnection() {
		this.dbName = "dbbrixton";
		this.dbUser = "root";
		this.dbPass = "gceylan";
		this.url = "jdbc:mysql://localhost/" + dbName + "?useUnicode=yes&characterEncoding=UTF-8";
	}
	
	public Connection setConnection() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, dbUser, dbPass);
		} catch (ClassNotFoundException driverEx) {
			driverEx.printStackTrace();
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
	
	public static void main(String[] args) {
		DatabaseConnection dbConnection = new DatabaseConnection();
		Connection conn = dbConnection.setConnection();
		
		ArrayList<Grup> gruplar = new ArrayList<Grup>();
		
		try {
			PreparedStatement ps = conn.prepareStatement("select * from tGrup;");
			ResultSet rs = ps.executeQuery();
			
			Grup g; 
			
			while (rs.next()) {
				g = new Grup();
				
				g.setId(rs.getInt("grup_id"));
				g.setAd(rs.getString("ad"));
				
				gruplar.add(g);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("> Gruplar_");
		for (Grup grup : gruplar) {
			System.out.println("id: " + grup.getId());
			System.out.println("ad: " + grup.getAd());
		}
	}

}
