package com.gceylan.broxintest.beans;


public class Ogrenci {
	private int id;
	private String ad;
	private String soyad;
	private String dogumTarihi;
	private String cinsiyet;
	private float boy;
	private float kilo;
	private int sporcuYasi;
	private String sporBransi;
	private int grupID;
	
	public Ogrenci() {
	}
	
	public Ogrenci(String ad, String soyad, String dogumTarihi, String cinsiyet,
			float boy, float kilo, int sporcuYasi, String sporBransi, int grupID) {
		this.ad = ad;
		this.soyad = soyad;
		this.dogumTarihi = dogumTarihi;
		this.cinsiyet = cinsiyet;
		this.boy = boy;
		this.kilo = kilo;
		this.sporcuYasi = sporcuYasi;
		this.sporBransi = sporBransi;
		this.grupID = grupID;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getAd() {
		return ad;
	}
	
	public void setAd(String ad) {
		this.ad = ad;
	}
	
	public String getSoyad() {
		return soyad;
	}
	
	public void setSoyad(String soyad) {
		this.soyad = soyad;
	}
	
	public String getDogumTarihi() {
		return dogumTarihi;
	}
	
	public void setDogumTarihi(String dogumTarihi) {
		this.dogumTarihi = dogumTarihi;
	}
	
	public String getCinsiyet() {
		return cinsiyet;
	}
	
	public void setCinsiyet(String cinsiyet) {
		this.cinsiyet = cinsiyet;
	}
	
	public float getBoy() {
		return boy;
	}
	
	public void setBoy(float boy) {
		this.boy = boy;
	}
	
	public float getKilo() {
		return kilo;
	}
	
	public void setKilo(float kilo) {
		this.kilo = kilo;
	}
	
	public int getSporcuYasi() {
		return sporcuYasi;
	}
	
	public void setSporcuYasi(int sporcuYasi) {
		this.sporcuYasi = sporcuYasi;
	}
	
	public String getSporBransi() {
		return sporBransi;
	}
	
	public void setSporBransi(String sporBransi) {
		this.sporBransi = sporBransi;
	}
	
	public int getGrupID() {
		return grupID;
	}
	
	public void setGrupID(int grupID) {
		this.grupID = grupID;
	}
}
