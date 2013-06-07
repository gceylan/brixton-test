package com.gceylan.broxintest.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.gceylan.broxintest.beans.Ogrenci;

public class OgrenciService {
	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	public OgrenciService() {
	}
	
	public Vector<Object> tumOgrencileriGetir() {
		DatabaseConnection dbConnection = new DatabaseConnection();
		connection = dbConnection.setConnection();

		Vector<Object> ogrenciler = new Vector<Object>();
		Vector<Object> ogrenci;

		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM tOgrenci");
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ogrenci = new Vector<Object>();
				ogrenci.add(resultSet.getInt("ogrenci_id"));
				ogrenci.add(resultSet.getString("ad"));
				ogrenci.add(resultSet.getString("soyad"));
				ogrenci.add(resultSet.getDate("dogum_tarihi"));
				ogrenci.add(resultSet.getString("cinsiyet"));
				ogrenci.add(resultSet.getFloat("boy"));
				ogrenci.add(resultSet.getString("kilo"));
				ogrenci.add(resultSet.getInt("sporcu_yasi"));
				ogrenci.add(resultSet.getString("spor_bransi"));
				ogrenci.add(resultSet.getString("grup_id"));

				ogrenciler.add(ogrenci);
			}
		} catch (SQLException e) {
			System.out.println("Tum gruplar jtable eklenirken hata olustu.");
			e.printStackTrace();
		}

		dbConnection.closeConnection();
		return ogrenciler;
	}

	public Vector<Object> ogrenciEkle(Ogrenci ogrenci) {
		DatabaseConnection dbConnection = new DatabaseConnection();
		connection = dbConnection.setConnection();
		
		int insert = 0;
		
		try {
			preparedStatement = connection.prepareStatement("INSERT INTO tOgrenci " +
					"(ad, soyad, dogum_tarihi, cinsiyet, boy, kilo, sporcu_yasi, spor_bransi, grup_id) " +
					"values(?,?,?,?,?,?,?,?,?)");
			preparedStatement.setString(1, ogrenci.getAd());
			preparedStatement.setString(2, ogrenci.getSoyad());
			preparedStatement.setString(3, ogrenci.getDogumTarihi());
			preparedStatement.setString(4, ogrenci.getCinsiyet());
			preparedStatement.setFloat(5, ogrenci.getBoy());
			preparedStatement.setFloat(6, ogrenci.getKilo());
			preparedStatement.setInt(7, ogrenci.getSporcuYasi());
			preparedStatement.setString(8, ogrenci.getSporBransi());
			preparedStatement.setInt(9, ogrenci.getGrupID());
			
			insert = preparedStatement.executeUpdate();
			
			if (insert == 1) {
				Connection conn = dbConnection.setConnection();
				preparedStatement = conn.prepareStatement("select ogrenci_id from tOgrenci where (ad = ? && soyad = ? && kilo = ?)");
				preparedStatement.setString(1, ogrenci.getAd());
				preparedStatement.setString(2, ogrenci.getSoyad());
				preparedStatement.setFloat(3, ogrenci.getKilo());

				resultSet = preparedStatement.executeQuery();
				
				if (resultSet.next()) {
					Vector<Object> v = new Vector<Object>();
					
					v.add(resultSet.getInt("ogrenci_id"));
					v.add(ogrenci.getAd());
					v.add(ogrenci.getSoyad());
					v.add(ogrenci.getDogumTarihi());
					v.add(ogrenci.getCinsiyet());
					v.add(ogrenci.getBoy());
					v.add(ogrenci.getKilo());
					v.add(ogrenci.getSporcuYasi());
					v.add(ogrenci.getSporBransi());
					v.add(ogrenci.getGrupID());
					
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

	public int ogrenciSil(int ogrenciID) {
		DatabaseConnection dbConnection = new DatabaseConnection();
		connection = dbConnection.setConnection();
		
		int delete = 0;
		
		try {
			preparedStatement = connection.prepareStatement("DELETE FROM tOgrenci WHERE ogrenci_id = ?");
			preparedStatement.setInt(1, ogrenciID);
			
			delete = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("ogrenci silinirken hata olustu.");
			e.printStackTrace();
		}
		
		dbConnection.closeConnection();
		return delete;
	}

	public Vector<Object> grubaAitTumOgrencileriGetir(int grupID) {
		DatabaseConnection dbConnection = new DatabaseConnection();
		connection = dbConnection.setConnection();

		Vector<Object> ogrenciler = new Vector<Object>();
		Vector<Object> ogrenci;

		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM tOgrenci WHERE grup_id = ?");
			preparedStatement.setInt(1, grupID);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ogrenci = new Vector<Object>();
				ogrenci.add(resultSet.getInt("ogrenci_id"));
				ogrenci.add(resultSet.getString("ad"));
				ogrenci.add(resultSet.getString("soyad"));
				ogrenci.add(resultSet.getDate("dogum_tarihi"));
				ogrenci.add(resultSet.getString("cinsiyet"));
				ogrenci.add(resultSet.getFloat("boy"));
				ogrenci.add(resultSet.getString("kilo"));
				ogrenci.add(resultSet.getInt("sporcu_yasi"));
				ogrenci.add(resultSet.getString("spor_bransi"));
				ogrenci.add(resultSet.getString("grup_id"));

				ogrenciler.add(ogrenci);
			}
		} catch (SQLException e) {
			System.out.println("Tum gruplar jtable eklenirken hata olustu.");
			e.printStackTrace();
		}

		dbConnection.closeConnection();
		return ogrenciler;
	}
	
	public ArrayList<Integer> grubaAitTumOgrencileriIdleriniGetir(int grupID) {
		DatabaseConnection dbConnection = new DatabaseConnection();
		connection = dbConnection.setConnection();

		ArrayList<Integer> ogrenciID = new ArrayList<Integer>();

		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM tOgrenci WHERE grup_id = ?");
			preparedStatement.setInt(1, grupID);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Integer id = new Integer(resultSet.getInt("ogrenci_id"));

				ogrenciID.add(id);
			}
		} catch (SQLException e) {
			System.out.println("Tum gruplar jtable eklenirken hata olustu.");
			e.printStackTrace();
		}

		dbConnection.closeConnection();
		return ogrenciID;
	}
	
	public Vector<Object> ogrenciGuncelle(Ogrenci ogrenci) {
		DatabaseConnection dbConnection = new DatabaseConnection();
		connection = dbConnection.setConnection();
		
		int update = 0;
		
		try {
			preparedStatement = connection.prepareStatement("UPDATE tOgrenci SET " +
					"ad=?, soyad=?, dogum_tarihi=?, cinsiyet=?, boy=?, kilo=?, sporcu_yasi=?, spor_bransi=?, grup_id=? " +
					"WHERE ogrenci_id = ?");
			preparedStatement.setString(1, ogrenci.getAd());
			preparedStatement.setString(2, ogrenci.getSoyad());
			preparedStatement.setString(3, ogrenci.getDogumTarihi());
			preparedStatement.setString(4, ogrenci.getCinsiyet());
			preparedStatement.setFloat(5, ogrenci.getBoy());
			preparedStatement.setFloat(6, ogrenci.getKilo());
			preparedStatement.setInt(7, ogrenci.getSporcuYasi());
			preparedStatement.setString(8, ogrenci.getSporBransi());
			preparedStatement.setInt(9, ogrenci.getGrupID());
			
			preparedStatement.setInt(10, ogrenci.getId());
			
			update = preparedStatement.executeUpdate();
			
			if (update == 1) {
				Vector<Object> guncellenenOgrenci = getOgrenci(ogrenci.getId());
				
				dbConnection.closeConnection();
				return guncellenenOgrenci;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		dbConnection.closeConnection();
		return null;
	}

	public Vector<Object> getOgrenci(int id) {
		DatabaseConnection dbConnection = new DatabaseConnection();
		connection = dbConnection.setConnection();
		
		Vector<Object> ogrenci = null;

		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM tOgrenci WHERE ogrenci_id = ?");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				ogrenci = new Vector<Object>();
				ogrenci.add(resultSet.getInt("ogrenci_id"));
				ogrenci.add(resultSet.getString("ad"));
				ogrenci.add(resultSet.getString("soyad"));
				ogrenci.add(resultSet.getDate("dogum_tarihi"));
				ogrenci.add(resultSet.getString("cinsiyet"));
				ogrenci.add(resultSet.getFloat("boy"));
				ogrenci.add(resultSet.getString("kilo"));
				ogrenci.add(resultSet.getInt("sporcu_yasi"));
				ogrenci.add(resultSet.getString("spor_bransi"));
				ogrenci.add(resultSet.getString("grup_id"));
			}
		} catch (SQLException e) {
			System.out.println("Tum gruplar jtable eklenirken hata olustu.");
			e.printStackTrace();
		}
		
		dbConnection.closeConnection();
		return ogrenci;
	}

}
