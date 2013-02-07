package thebrixtontest.developer;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class PCenterTest extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JPanel circlePanel;
	JPanel pCenter;
	
	MyButtonListener buttonListener;

	BufferedImage beyazButonImg;
	BufferedImage maviButonImg;
	BufferedImage turuncuButonImg;
	BufferedImage kirmiziButonImg;
	
	int[] cevaplar = {0, 1, 2, 3, 4, 5, 5, 4, 3, 2, 1, 10, 5, 10, 5, 10, 5, 10,
			5, 6, 7, 8, 9, 10, 1, 2, 1, 10, 9, 10, 1, 2, 3, 4, 10, 4, 10, 4, 10,
			4, 10, 9, 9, 9, 9, 9, 9, 9, 8, 9, 8, 9, 8, 9, 8, 9};
	int sayfaNumarasi = 1;
	
	int dogruSayisi = 0;
	int yanlisSayisi = 0;
	
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

	public PCenterTest() throws IOException {
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
	
	private class MyButtonListener implements ActionListener {

		/* My Action Listener */
		@Override
		public void actionPerformed(ActionEvent e) {
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
				if (sonSecilenButonNumarasi == maviButonNumarasi)
					oncekiSecilenButonNumarasi = maviButonNumarasi;
				else if (sonSecilenButonNumarasi == kirmiziButonNumarasi)
					oncekiSecilenButonNumarasi = kirmiziButonNumarasi;
				
				sonSecilenButon.setIcon(new ImageIcon(turuncuButonImg));
				oncekiSecilenButon.setIcon(new ImageIcon(beyazButonImg));

			} else {

				maviButon.setIcon(new ImageIcon(maviButonImg));
				kirmiziButon.setIcon(new ImageIcon(kirmiziButonImg));

				sonSecilenButon.setIcon(new ImageIcon(turuncuButonImg));
				oncekiSecilenButon.setIcon(new ImageIcon(beyazButonImg));
				
				oncekiSecilenButonNumarasi = sonSecilenButonNumarasi;
				oncekiSecilenButon = sonSecilenButon;
			}
		}
	}
	
	public JPanel getPanel() {
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(Color.WHITE);
		titlePanel.add(title);
		
		JPanel pGuney = new JPanel();
		pGuney.setBackground(Color.WHITE);
		nextButton = new JButton("NEXT >>");
		pGuney.add(nextButton, BorderLayout.EAST);
		
		nextButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (sayfaNumarasi <= 55) {
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
								"Se�ilmesi Gereken Buton Numaras�: " + cevaplar[sayfaNumarasi + 1]);
						sayfaNumarasi++;
					}
					else if (sayfaNumarasi == 55) {
						title.setText("Test Bitti!");
						String message = "Do�ru: " + dogruSayisi + "\nYanl��: " + yanlisSayisi + "";
						JOptionPane.showMessageDialog(null, message);
						nextButton.setEnabled(false);
						circlePanel.removeAll();
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
		
		/* Ana K�s�m */
		pCenter = new JPanel();
		pCenter.setLayout(new BorderLayout(10, 10));
		pCenter.setBackground(Color.LIGHT_GRAY);	// RED
		pCenter.add(titlePanel, BorderLayout.NORTH);
		pCenter.add(circlePanel, BorderLayout.CENTER);
		pCenter.add(pGuney, BorderLayout.SOUTH);
		
		return pCenter;
	}
	
	public void getInformation() {
		System.out.println("Do�ru say�s� : " + dogruSayisi);
		System.out.println("Yanl�� say�s�: " + yanlisSayisi);
		System.out.println("Sayfan No    : " + sayfaNumarasi);
	}
	
	public int getButtonNumber(JButton button) {
		return (button.getText() == null) ? 0 : Integer.parseInt(button.getText());
	}

}
