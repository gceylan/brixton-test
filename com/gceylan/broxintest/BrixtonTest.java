package com.gceylan.broxintest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.gceylan.broxintest.beans.Grup;
import com.gceylan.broxintest.beans.Ogrenci;
import com.gceylan.broxintest.beans.Sonuc;
import com.gceylan.broxintest.beans.TestSekli;
import com.gceylan.broxintest.services.SonucService;

public class BrixtonTest extends JFrame {
	private static final long serialVersionUID = -7513624641385543799L;
	private JPanel contentPane;
	
	JPanel circlePanel;
	JPanel pCenter;

	private static Grup grup;
	private static Ogrenci ogrenci;
	private static TestSekli testSekli;
	private int dogruSayisi;
	private int yanlisSayisi;
	private int sayfaNumarasi = 1;


	/*
	 * Test frame
	 * 
	 * */
	MyButtonListener buttonListener;

	BufferedImage beyazButonImg;
	BufferedImage maviButonImg;
	BufferedImage turuncuButonImg;
	BufferedImage kirmiziButonImg;

	int[] cevaplar = {0, 2, 3, 4, 5, 6, 5, 4, 3, 2, 1, 10, 5, 10, 5, 10, 5, 10,
			5, 6, 7, 8, 9, 10, 1, 2, 1, 10, 9, 10, 1, 2, 3, 4, 10, 4, 10, 4, 10,
			4, 10, 9, 9, 9, 9, 9, 9, 9, 8, 9, 8, 9, 8, 9, 8, 9};

	JButton nextButton;
	JButton oncekiSecilenButon = new JButton();
	JButton sonSecilenButon;
	JButton maviButon = new JButton("1");
	JButton kirmiziButon = new JButton("0");

	int oncekiSecilenButonNumarasi = 0;
	int sonSecilenButonNumarasi = 0;	// turuncu buton
	int maviButonNumarasi = 0;
	int kirmiziButonNumarasi = 0;

	JLabel title = new JLabel();

	ArrayList<JButton> buttons = new ArrayList<JButton>();

	/*
	 * Test frame sonu
	 * 
	 * */

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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BrixtonTest frame = new BrixtonTest(grup, ogrenci, testSekli);
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BrixtonTest(Grup g, Ogrenci o, TestSekli t) {
		
		if (g == null || o == null || t == null ) {
			System.out.println("parametreler g�nderilemedi...\n" +
					"PlayBroxinTest.java' y� �al��t�r�n");
			System.exit(0);
		}
		
		this.grup = g;
		this.ogrenci = o;
		this.testSekli = t;
		
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(50, 50, 980, 550);
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		/*
		 * Men�ye gerek olmayabilir.
		 * 
		 * */
//		JMenuBar menubar = new JMenuBar();
//
//		JMenu dosya = new JMenu("Dosya");
//		menubar.add(dosya);
//		JMenuItem yeni = new JMenuItem("Yeni");
//		dosya.add(yeni);
//		JMenuItem kaydet = new JMenuItem("Kaydet");
//		dosya.add(kaydet);
//		JMenuItem cikis = new JMenuItem("��k��");
//		dosya.add(cikis);
//
//		JMenu yardim = new JMenu("Yard�m");
//		menubar.add(yardim);
//		JMenuItem hakkimizda = new JMenuItem("Hakk�m�zda");
//		yardim.add(hakkimizda);
//		
//		setJMenuBar(menubar);

		/*
		 * �st panel
		 * 
		 * */
		JPanel pNorth = new JPanel();
		pNorth.setBackground(Color.BLUE);
		String baslik = "Se�ilen Grup: " + getGrup().getAd() + ", "
				+ "Se�ilen ��renci: " + getOgrenci().getAd() + ", Test �ekli: " + getTestSekli().getAd();
		pNorth.add(new JLabel(baslik));
		contentPane.add(pNorth, BorderLayout.NORTH);

		
		/*
		 * 
		 * Test bile�enleri
		 * pCenter eklenecek
		 * 
		 * */
		
		try {
			beyazButonImg = ImageIO.read(new File("images/beyaz.png"));
			maviButonImg = ImageIO.read(new File("images/mavi.png"));
			turuncuButonImg = ImageIO.read(new File("images/turuncu.png"));
			kirmiziButonImg = ImageIO.read(new File("images/kirmizi.png"));
		} catch (IOException ioEx) {
			ioEx.printStackTrace();
			System.out.println("Resim Dosyalar� Bulunamad�!");
		}
		
		buttonListener = new MyButtonListener();

		circlePanel = new JPanel(new GridLayout(2, 5, 10, 10));
		circlePanel.setBackground(Color.LIGHT_GRAY);
		
		title.setText("Sayfa Numaras�: " + sayfaNumarasi + "---- > Se�ilmesi Gereken Buton Numaras�: " + cevaplar[sayfaNumarasi]);
		
		durumuGoruntule(maviButon, kirmiziButon);
		
		/*
		 * pCenter paneli 3 panelden olu�maktad�r.
		 * 
		 * titlePanel (�st k�s�m)
		 * circlePanel (10 butonun oldu�u k�s�m)
		 * 
		 * 
		 * */
		JPanel notificationPanel = new JPanel();
		notificationPanel.setBackground(Color.WHITE);
		notificationPanel.add(title);
		
		JPanel nextPanel = new JPanel();
		nextPanel.setBackground(Color.WHITE);
		nextButton = new JButton("NEXT >>");
		nextButton.setEnabled(false);
		nextPanel.add(nextButton, BorderLayout.EAST);
		
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (sayfaNumarasi <= 55) {
//					nextButton.setEnabled(false);
					
					circlePanel.removeAll();		// bu t�m panel �zerindeki componentleri siler.
					
					maviButon.setIcon(new ImageIcon(beyazButonImg));
					maviButonNumarasi = cevaplar[sayfaNumarasi];		/* mavi boyanacak buton numaras� */
					
					if (maviButonNumarasi == sonSecilenButonNumarasi) {
						durumuGoruntule(new JButton("" + maviButonNumarasi), new JButton("0"));
						dogruSayisi++;
					} else {
						durumuGoruntule(new JButton("" + maviButonNumarasi), sonSecilenButon);
						yanlisSayisi++;
					}
					
					if (sayfaNumarasi < 55) {
						title.setText("Sayfa Numaras�: " + (sayfaNumarasi + 1) + ", " +
								"Se�ilmesi Gereken Buton Numaras�: " + cevaplar[sayfaNumarasi + 1] + "                                     "
								+ "T: " + dogruSayisi + ", F: " + yanlisSayisi);
						sayfaNumarasi++;
					}
					
					else if (sayfaNumarasi == 55 && sayfaNumarasi < 56) {

						title.setText("Test Bitti!");
						String message = "Do�ru: " + dogruSayisi + "\nYanl��: " + yanlisSayisi + "";
						JOptionPane.showMessageDialog(null, message);
						
						/*
						 * puan neye g�re hesaplanacak?
						 * */
						SonucService sonucService = new SonucService();
						sonucService.sonucuKaydet(getGrup(), getOgrenci(), getTestSekli(), dogruSayisi, yanlisSayisi, "puan: XXX");
						
						circlePanel.removeAll();
						nextButton.setEnabled(false);
					}
				}
				
				/*
				 * Cevaplar� test Et
				 * int rand = (int)((Math.random() + 1) * 20);
				 * System.out.println("rad: " + rand + " -> cevaplar[i]: " + cevaplar[rand]);
				 * 
				 * */
			}
		});
		
		pCenter = new JPanel();
		
		pCenter.setLayout(new BorderLayout(10, 10));
		pCenter.setBackground(Color.LIGHT_GRAY);	// RED
		pCenter.add(notificationPanel, BorderLayout.NORTH);
		pCenter.add(circlePanel, BorderLayout.CENTER);
		pCenter.add(nextPanel, BorderLayout.SOUTH);
		
		contentPane.add(pCenter, BorderLayout.CENTER);

		
		/*
		 * 
		 * Alt panel
		 * */
		JPanel pSouth = new JPanel();
		pSouth.setBackground(Color.BLUE);
		pSouth.add(new JLabel("Merhaba D�nya! (3)"));
		contentPane.add(pSouth, BorderLayout.SOUTH);

		getInfomationTestter();
	}
	
	public void durumuGoruntule(JButton mavi, JButton kirmizi) {
		int maviButonNum = getButtonNumber(mavi);
		int kirmiziButtonNum = getButtonNumber(kirmizi);
		
		for (int i = 0; i <= 10; i++) {
			/* buttons dizisinin 0. indisi de bir buton! */
			JButton b;
			if (i == 0) {
				b = new JButton("0");
				buttons.add(i, b);
				continue;
			} else if (i == maviButonNum) {
				maviButon = new JButton("" + i, new ImageIcon(maviButonImg));
				maviButon.addActionListener(buttonListener);
				maviButon.setBackground(Color.LIGHT_GRAY);
				maviButon.setHorizontalTextPosition(SwingConstants.CENTER);
				maviButon.setBorder(new LineBorder(Color.LIGHT_GRAY));
				buttons.add(i, maviButon);
				circlePanel.add(maviButon);
				continue;
			} else if (i == kirmiziButtonNum) {
				kirmiziButon = new JButton("" + i, new ImageIcon(kirmiziButonImg));
				kirmiziButon.addActionListener(buttonListener);
				kirmiziButon.setBackground(Color.LIGHT_GRAY);
				kirmiziButon.setHorizontalTextPosition(SwingConstants.CENTER);
				kirmiziButon.setBorder(new LineBorder(Color.LIGHT_GRAY));
				buttons.add(i, kirmiziButon);
				circlePanel.add(kirmiziButon);
				continue;
			} else {
				b = new JButton("" + i, new ImageIcon(beyazButonImg));
			}
			
			b.addActionListener(buttonListener);
			b.setBackground(Color.LIGHT_GRAY);
			b.setHorizontalTextPosition(SwingConstants.CENTER);
			b.setBorder(new LineBorder(Color.LIGHT_GRAY));
			buttons.add(i, b);
			circlePanel.add(b);
		}
	}
	
	public int getButtonNumber(JButton button) {
		return (button.getText() == null) ? 0 : Integer.parseInt(button.getText());
	}

	private void getInfomationTestter() {
		System.out.println("Grup:\n" + grup.getAd() + "\n");
		System.out.println("��renci: \n" +
				"ad: " + ogrenci.getAd() + "\nsoyad: " + ogrenci.getSoyad() + "\ngrupID: " + ogrenci.getGrupID());

		System.out.println("Test:\n" + testSekli.getAd());

		System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - ");
	}
	
	private class MyButtonListener implements ActionListener {
		/* My Action Listener */
		@Override
		public void actionPerformed(ActionEvent e) {
			/* bir sonraki sayfaya ge�ebilmek i�in butonu aktif et. */
			nextButton.setEnabled(true);
			
			sonSecilenButon = (JButton) e.getSource();		/* son se�ilen buton -> turuncu buton */
			sonSecilenButonNumarasi = getButtonNumber(sonSecilenButon);
			
			maviButonNumarasi = getButtonNumber(maviButon);
			kirmiziButonNumarasi = getButtonNumber(kirmiziButon);
			
			System.out.println("Son Se�ilen Buton Numaras�: " + sonSecilenButonNumarasi);
			System.out.println("�nceki se�ilen Buton Num  : " + oncekiSecilenButonNumarasi);
			System.out.println("Mavi Buton Numaras�       : " + maviButonNumarasi);
			System.out.println("K�rm�z� Buton Numaras�    : " + kirmiziButonNumarasi);

			if ((sonSecilenButonNumarasi == maviButonNumarasi)
					|| (sonSecilenButonNumarasi == kirmiziButonNumarasi)) {
				System.out.println("\n1. if\n");
				if (sonSecilenButonNumarasi == maviButonNumarasi) {
					oncekiSecilenButonNumarasi = maviButonNumarasi;
				}
				else if (sonSecilenButonNumarasi == kirmiziButonNumarasi) {
					oncekiSecilenButonNumarasi = kirmiziButonNumarasi;
				}
				
				kirmiziButon.setIcon(new ImageIcon(kirmiziButonImg));
				maviButon.setIcon(new ImageIcon(maviButonImg));
				
				oncekiSecilenButon.setIcon(new ImageIcon(beyazButonImg));
				
			} else {
				System.out.println("\nelse\n");
				maviButon.setIcon(new ImageIcon(maviButonImg));
				kirmiziButon.setIcon(new ImageIcon(kirmiziButonImg));

				sonSecilenButon.setIcon(new ImageIcon(turuncuButonImg));
				oncekiSecilenButon.setIcon(new ImageIcon(beyazButonImg));
				
				oncekiSecilenButonNumarasi = sonSecilenButonNumarasi;
				oncekiSecilenButon = sonSecilenButon;
			}
			
			/* tester kesin bir buton se�mek zorunda */
			sonSecilenButon.setIcon(new ImageIcon(turuncuButonImg));
		}
		
	}

}
