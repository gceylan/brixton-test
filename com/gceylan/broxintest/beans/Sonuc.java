package com.gceylan.broxintest.beans;

public class Sonuc {
	int id;
	Grup grup;
	Ogrenci ogrenci;
	TestSekli testSekli;
	int dogruSayisi;
	int yanlisSayisi;
	String puan;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Grup getGrup() {
		return grup;
	}
	
	public void setGrup(Grup grup) {
		this.grup = grup;
	}
	
	public Ogrenci getOgrenci() {
		return ogrenci;
	}
	
	public void setOgrenci(Ogrenci ogrenci) {
		this.ogrenci = ogrenci;
	}
	
	public TestSekli getTestSekli() {
		return testSekli;
	}
	
	public void setTestSekli(TestSekli testSekli) {
		this.testSekli = testSekli;
	}
	
	public int getDogruSayisi() {
		return dogruSayisi;
	}
	
	public void setDogruSayisi(int dogruSayisi) {
		this.dogruSayisi = dogruSayisi;
	}
	
	public int getYanlisSayisi() {
		return yanlisSayisi;
	}
	
	public void setYanlisSayisi(int yanlisSayisi) {
		this.yanlisSayisi = yanlisSayisi;
	}
	
	public String getPuan() {
		return puan;
	}
	
	public void setPuan(String puan) {
		this.puan = puan;
	}
	
	

}
