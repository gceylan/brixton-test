package com.gceylan.broxintest.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.gceylan.broxintest.beans.Grup;
import com.gceylan.broxintest.beans.Ogrenci;
import com.gceylan.broxintest.beans.TestSekli;

public class SonucService {

	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	public int sonucuKaydet(Grup grup, Ogrenci ogrenci, TestSekli testSekli,
			int dogruSayisi, int yanlisSayisi, String puan) {
		DatabaseConnection dbConnection = new DatabaseConnection();
		connection = dbConnection.setConnection();

		String sql = "INSERT INTO tSonuc (grup_id, ogrenci_id, test_sekil_id, "
				+ "dogru_sayisi, yanlis_sayisi, puan) values(?, ?, ?, ?, ?, ?)";
		int insert = 0;

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, grup.getId());
			preparedStatement.setInt(2, ogrenci.getId());
			preparedStatement.setInt(3, testSekli.getId());
			preparedStatement.setInt(4, dogruSayisi);
			preparedStatement.setInt(5, yanlisSayisi);
			preparedStatement.setString(6, puan);
			
			insert = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return insert;
	}

}
