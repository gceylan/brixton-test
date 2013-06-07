package com.gceylan.broxintest.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class GrupService {
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	
	public Vector<Object> tumGruplariGetir() {
		DatabaseConnection dbConnection = new DatabaseConnection();
		connection = dbConnection.setConnection();
		
		Vector<Object> data = new Vector<Object>();
		Vector<Object> row;
		
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM tGrup");
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				row = new Vector<Object>();
				row.add(resultSet.getInt("grup_id"));
				row.add(resultSet.getString("ad"));
				
				data.add(row);
			}
		} catch (SQLException e) {
			System.out.println("Tum gruplar jtable eklenirken hata olustu.");
			e.printStackTrace();
		}
		
		dbConnection.closeConnection();
		return data;
	}

	public Vector<Object> grupEkle(String grupAdi) {
		DatabaseConnection dbConnection = new DatabaseConnection();
		connection = dbConnection.setConnection();
		
		String sql = "insert into tGrup (ad, olusturulma_tarihi) values (?, now())";
		int insert = 0;
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, grupAdi);
			insert = preparedStatement.executeUpdate();
			
			if (insert == 1) {
				Connection conn = dbConnection.setConnection();
				preparedStatement = conn.prepareStatement("select * from tGrup where ad = ?");
				preparedStatement.setString(1, grupAdi);
				resultSet = preparedStatement.executeQuery();
				
				if (resultSet.next()) {
					Vector<Object> v = new Vector<Object>();
					
					v.add(resultSet.getInt("grup_id"));
					v.add(grupAdi);
					
					dbConnection.closeConnection();
					return v;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		dbConnection.closeConnection();
		return null;
	}

	public int grubuSil(int grupID) {
		
		OgrenciService os = new OgrenciService();
		ArrayList<Integer> ids = os.grubaAitTumOgrencileriIdleriniGetir(grupID);
		
		if (ids != null) {
			for (Integer ogrenciID : ids) {
				os.ogrenciSil(ogrenciID);
			}
		}
		
		DatabaseConnection dbConnection = new DatabaseConnection();
		connection = dbConnection.setConnection();
		int delete = 0;
		
		try {
			preparedStatement = connection.prepareStatement("DELETE FROM tGrup WHERE grup_id = ?");
			preparedStatement.setInt(1, grupID);
			delete = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		dbConnection.closeConnection();
		return delete;
	}

	public int grubuGuncelle(Vector<Object> grup) {
		DatabaseConnection dbConnection = new DatabaseConnection();
		connection = dbConnection.setConnection();
		
		int update = 0;
		
		try {
			preparedStatement = connection.prepareStatement("UPDATE tGrup SET ad = ? WHERE grup_id = ?");
			preparedStatement.setString(1, String.valueOf(grup.get(1)));
			preparedStatement.setInt(2, (Integer) grup.get(0));
			update = preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		dbConnection.closeConnection();
		return update;
	}
	
}