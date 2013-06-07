package com.gceylan.broxintest.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TestSekilService {
	
	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	public Vector<Object> tumTestSekilleriniGetir() {
		DatabaseConnection dbConnection = new DatabaseConnection();
		connection = dbConnection.setConnection();

		Vector<Object> testSekilleri = new Vector<Object>();
		Vector<Object> test;

		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM tTestSekli");
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				test = new Vector<Object>();
				test.add(resultSet.getInt("id"));
				test.add(resultSet.getString("ad"));

				testSekilleri.add(test);
			}
		} catch (SQLException e) {
			System.out.println("Tum test sekilleri jtable eklenirken hata olustu.");
			e.printStackTrace();
		}

		dbConnection.closeConnection();
		return testSekilleri;
	}

	public Vector<Object> testSekliEkle(String testAdi) {
		DatabaseConnection dbConnection = new DatabaseConnection();
		connection = dbConnection.setConnection();
		
		String sql = "insert into tTestSekli (ad) values (?)";
		int insert = 0;
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, testAdi);
			insert = preparedStatement.executeUpdate();
			
			if (insert == 1) {
				Connection conn = dbConnection.setConnection();
				preparedStatement = conn.prepareStatement("select * from tTestSekli where ad = ?");
				preparedStatement.setString(1, testAdi);
				resultSet = preparedStatement.executeQuery();
				
				if (resultSet.next()) {
					Vector<Object> v = new Vector<Object>();
					
					v.add(resultSet.getInt("id"));
					v.add(testAdi);
					
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

	public int testSekliniSil(int id) {
		DatabaseConnection dbConnection = new DatabaseConnection();
		connection = dbConnection.setConnection();

		int delete = 0;
		
		try {
			preparedStatement = connection.prepareStatement("DELETE FROM tTestSekli WHERE id = ?");
			preparedStatement.setInt(1, id);
			delete = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		dbConnection.closeConnection();
		return delete;
	}

	public int testSekliniGuncelle(Vector<Object> testSekli) {
		DatabaseConnection dbConnection = new DatabaseConnection();
		connection = dbConnection.setConnection();
		
		int update = 0;
		
		try {
			preparedStatement = connection.prepareStatement("UPDATE tTestSekli SET ad = ? WHERE id = ?");
			preparedStatement.setString(1, String.valueOf(testSekli.get(1)));
			preparedStatement.setInt(2, (Integer) testSekli.get(0));
			update = preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		dbConnection.closeConnection();
		return update;
	}
	
}
