package com.gceylan.broxintest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.gceylan.broxintest.beans.Grup;
import com.gceylan.broxintest.beans.Ogrenci;
import com.gceylan.broxintest.beans.TestSekli;
import com.gceylan.broxintest.helper.MalformedControl;
import com.gceylan.broxintest.services.GrupService;
import com.gceylan.broxintest.services.OgrenciService;
import com.gceylan.broxintest.services.TestSekilService;

public class PlayBroxinTest extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6179469930064437034L;
	private JPanel contentPane;

	/*
	 * 
	 * Grup ayarlarý
	 * 
	 * */
	private DefaultTableModel modelGruplar;
	private JTable tableGruplar;
	private Vector<Object> gruplar = new Vector<Object>();
	private GrupService grupService;
	private JComboBox comboBoxGrup;
	private JButton btnGrupEkle;

	private int duzenlenecekSatirNum;
	private Vector<Object> duzenlenecekGrup;

	/*
	 * Öðrenci Ayarlarý
	 * 
	 * */

	private DefaultTableModel modelOgrenciler;
	private JTable tableOgrenciler;
	private Vector<Object> ogrenciler = new Vector<Object>();
	private Vector<String> cinsiyet;
	private JComboBox comboBoxListelenecekGrup;
	private JComboBox comboBoxCinsiyet;
	OgrenciService ogrenciService;
	int duzenlenecekOgrenciSatirNum;
	Vector<Object> duzenlenecekOgrenci;

	/*
	 * Grup ayarlarý
	 * 
	 * */
	private JTextField textFieldGrupAdi;
	private JTextField textFieldAd;
	private JTextField textFieldSoyad;
	private JTextField textFieldDogumTarihi;
	private JTextField textFieldBoy;
	private JTextField textFieldKilo;
	private JTextField textFieldSporcuYasi;
	private JTextField textFieldSporBransi;

	JButton btnOgrenciKaydet;
	private JTextField textFieldTestSekliAd;
	private TestSekilService testSekilService;

	/*
	 * 
	 * Test Þekilleri Ayarlarý
	 * 
	 *
	 */

	private DefaultTableModel modelTestSekilleri;
	private JTable tableTestSekilleri;
	private Vector<Object> testSekilleri;
	private Vector<Object> duzenlenecekTestSekli;

	/*
	 * TESTE BAÞLA
	 * 
	 * */
	private JComboBox comboBoxTestIcinGrup;
	private JComboBox comboBoxTestIcinOgrenci;
	private Vector<Object> secilebilecekOgrenciler = new Vector<Object>();
	private JComboBox comboBoxTestIcinSekil;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayBroxinTest frame = new PlayBroxinTest();
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
	public PlayBroxinTest() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		cinsiyet = new Vector<String>();
		cinsiyet.add("E");
		cinsiyet.add("K");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 833, 524);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnDosya = new JMenu("Dosya");
		menuBar.add(mnDosya);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panel.add(tabbedPane);

		JLayeredPane layeredPaneAnaEkran = new JLayeredPane();
		tabbedPane.addTab("Ana Ekran", null, layeredPaneAnaEkran, null);
		layeredPaneAnaEkran.setLayout(new BorderLayout(0, 0));


		/*
		 * Gruplar Baþlangýç
		 * 
		 * */

		JLayeredPane layeredPaneGruplar = new JLayeredPane();
		tabbedPane.addTab("Gruplar", null, layeredPaneGruplar, null);

		modelGruplar = new DefaultTableModel();
		modelGruplar.addColumn("GRUP_ID");
		modelGruplar.addColumn("AD");

		gruplar.add(0, "Bir Grup Seçin");
		gruplar.addAll(1, new GrupService().tumGruplariGetir());

		for (int i = 1; i < gruplar.size(); i++) {
			modelGruplar.addRow((Vector) gruplar.get(i));
		}

		layeredPaneGruplar.setLayout(new BorderLayout(0, 0));

		JPanel panelGruplarSol = new JPanel();
		layeredPaneGruplar.setLayer(panelGruplarSol, 0);
		panelGruplarSol.setBackground(Color.WHITE);
		layeredPaneGruplar.add(panelGruplarSol, BorderLayout.WEST);
		GridBagLayout gbl_panelGruplarSol = new GridBagLayout();
		gbl_panelGruplarSol.rowHeights = new int[] {30, 30, 30, 30, 286};
		gbl_panelGruplarSol.columnWidths = new int[] {10, 230, 10};
		gbl_panelGruplarSol.columnWeights = new double[]{1.0, 0.0};
		gbl_panelGruplarSol.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		panelGruplarSol.setLayout(gbl_panelGruplarSol);

		JLabel lblNewLabel = new JLabel("Grup Ad\u0131:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		panelGruplarSol.add(lblNewLabel, gbc_lblNewLabel);

		textFieldGrupAdi = new JTextField();
		GridBagConstraints gbc_textFieldGrupAdi = new GridBagConstraints();
		gbc_textFieldGrupAdi.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldGrupAdi.insets = new Insets(0, 0, 5, 10);
		gbc_textFieldGrupAdi.gridx = 1;
		gbc_textFieldGrupAdi.gridy = 1;
		panelGruplarSol.add(textFieldGrupAdi, gbc_textFieldGrupAdi);
		textFieldGrupAdi.setColumns(10);

		btnGrupEkle = new JButton("Ekle");
		btnGrupEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String grupAdi = textFieldGrupAdi.getText().trim();

				if (!MalformedControl.isMalformedGrupAdi(grupAdi)) {
					grupService = new GrupService();

					Vector<Object> eklenen = grupService.grupEkle(grupAdi);
					if (eklenen != null) {
						modelGruplar.addRow(eklenen);
						gruplar.add(eklenen);

						textFieldGrupAdi.setText("");
					}
				}

//				comboBoxGrup.updateUI();
//				comboBoxListelenecekGrup.updateUI();
//				comboBoxTestIcinGrup.updateUI();
				
				resetAnasayfa();
				
				textFieldGrupAdi.setText("");
			}
		});
		GridBagConstraints gbc_btnGrupEkle = new GridBagConstraints();
		gbc_btnGrupEkle.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnGrupEkle.insets = new Insets(0, 0, 5, 10);
		gbc_btnGrupEkle.gridx = 1;
		gbc_btnGrupEkle.gridy = 2;
		panelGruplarSol.add(btnGrupEkle, gbc_btnGrupEkle);

		JButton btnGuncelle = new JButton("G\u00FCncelle");
		btnGuncelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String grupAdi = textFieldGrupAdi.getText().trim();
				if (grupAdi.length() != 0) {
					duzenlenecekGrup.set(1, grupAdi);
					grupService = new GrupService();

					if (grupService.grubuGuncelle(duzenlenecekGrup) == 1) {
						modelGruplar.removeRow(duzenlenecekSatirNum);
						modelGruplar.insertRow(duzenlenecekSatirNum, duzenlenecekGrup);
						gruplar.remove(duzenlenecekSatirNum + 1);
						gruplar.add(duzenlenecekSatirNum + 1, duzenlenecekGrup);
					} else
						JOptionPane.showMessageDialog(null, "Güncelleme baþarýsýz!");
				}

				comboBoxGrup.updateUI();
				comboBoxListelenecekGrup.updateUI();
				comboBoxTestIcinGrup.updateUI();
				textFieldGrupAdi.setText("");
				
				resetAnasayfa();
			}
		});
		GridBagConstraints gbc_btnGuncelle = new GridBagConstraints();
		gbc_btnGuncelle.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnGuncelle.insets = new Insets(0, 0, 5, 10);
		gbc_btnGuncelle.gridx = 1;
		gbc_btnGuncelle.gridy = 3;
		panelGruplarSol.add(btnGuncelle, gbc_btnGuncelle);

		JPanel panelGruplarSag = new JPanel();
		layeredPaneGruplar.add(panelGruplarSag, BorderLayout.CENTER);
		panelGruplarSag.setLayout(new BorderLayout(0, 0));

		tableGruplar = new JTable(modelGruplar);
		tableGruplar.setRowHeight(22);
		tableGruplar.setRowSelectionAllowed(true);
		tableGruplar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableGruplar.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tableGruplar.setBounds(560, 286, -426, -249);
		JScrollPane scrollPane = new JScrollPane(tableGruplar,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panelGruplarSag.add(scrollPane, BorderLayout.CENTER);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panelGruplarSag.add(panel_3, BorderLayout.SOUTH);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{81, 111, 0};
		gbl_panel_3.rowHeights = new int[] {30, 0};
		gbl_panel_3.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);

		JButton btnGrubuSil = new JButton("Grubu Sil");
		btnGrubuSil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int secilenSatirNum = tableGruplar.getSelectedRow();
				Vector<Object> secilenSatir = getSelectedRowData(modelGruplar, secilenSatirNum);

				grupService = new GrupService();
				if (grupService.grubuSil((Integer) secilenSatir.get(0)) == 1) {
					modelGruplar.removeRow(secilenSatirNum);
					gruplar.remove(secilenSatirNum + 1);
				} else {
					JOptionPane.showMessageDialog(null, "\"" + secilenSatir.get(1) + "\" isimli grup silinemedi!");
				}

				resetAnasayfa();
			}
		});

		btnGrubuSil.setAlignmentX(Component.RIGHT_ALIGNMENT);
		GridBagConstraints gbc_btnGrubuSil = new GridBagConstraints();
		gbc_btnGrubuSil.anchor = GridBagConstraints.EAST;
		gbc_btnGrubuSil.insets = new Insets(0, 0, 0, 5);
		gbc_btnGrubuSil.gridx = 0;
		gbc_btnGrubuSil.gridy = 0;
		panel_3.add(btnGrubuSil, gbc_btnGrubuSil);

		JButton btnGrubuDuzenle = new JButton("Grubu D\u00FCzenle");
		btnGrubuDuzenle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				duzenlenecekSatirNum = tableGruplar.getSelectedRow();
				duzenlenecekGrup = getSelectedRowData(modelGruplar, duzenlenecekSatirNum);

				textFieldGrupAdi.setText(duzenlenecekGrup.get(1).toString());
			}
		});
		GridBagConstraints gbc_btnGrubuDuzenle = new GridBagConstraints();
		gbc_btnGrubuDuzenle.anchor = GridBagConstraints.WEST;
		gbc_btnGrubuDuzenle.gridx = 1;
		gbc_btnGrubuDuzenle.gridy = 0;
		panel_3.add(btnGrubuDuzenle, gbc_btnGrubuDuzenle);

		/*
		 * Gruplar son
		 * 
		 * */


		/*
		 * 
		 * Öðrenciler Baþlangýç
		 * 
		 * */

		JLayeredPane layeredPaneOgrenciler = new JLayeredPane();
		tabbedPane.addTab("\u00D6\u011Frenciler", null, layeredPaneOgrenciler, null);
		layeredPaneOgrenciler.setLayout(new BorderLayout(0, 0));

		JPanel panelSol = new JPanel();
		panelSol.setBackground(Color.WHITE);
		layeredPaneOgrenciler.add(panelSol, BorderLayout.WEST);
		GridBagLayout gbl_panelSol = new GridBagLayout();
		gbl_panelSol.columnWidths = new int[] {100, 150, 0};
		gbl_panelSol.rowHeights = new int[] {30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30};
		gbl_panelSol.columnWeights = new double[]{0.0, 1.0};
		gbl_panelSol.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		panelSol.setLayout(gbl_panelSol);

		JLabel lblAd = new JLabel("Ad:");
		GridBagConstraints gbc_lblAd = new GridBagConstraints();
		gbc_lblAd.insets = new Insets(0, 10, 5, 5);
		gbc_lblAd.anchor = GridBagConstraints.WEST;
		gbc_lblAd.gridx = 0;
		gbc_lblAd.gridy = 0;
		panelSol.add(lblAd, gbc_lblAd);

		textFieldAd = new JTextField();
		GridBagConstraints gbc_textFieldAd = new GridBagConstraints();
		gbc_textFieldAd.anchor = GridBagConstraints.WEST;
		gbc_textFieldAd.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldAd.gridx = 1;
		gbc_textFieldAd.gridy = 0;
		panelSol.add(textFieldAd, gbc_textFieldAd);
		textFieldAd.setColumns(15);

		JLabel lblSoyad = new JLabel("Soyad:");
		GridBagConstraints gbc_lblSoyad = new GridBagConstraints();
		gbc_lblSoyad.anchor = GridBagConstraints.WEST;
		gbc_lblSoyad.insets = new Insets(0, 10, 5, 5);
		gbc_lblSoyad.gridx = 0;
		gbc_lblSoyad.gridy = 1;
		panelSol.add(lblSoyad, gbc_lblSoyad);

		textFieldSoyad = new JTextField();
		GridBagConstraints gbc_textFieldSoyad = new GridBagConstraints();
		gbc_textFieldSoyad.anchor = GridBagConstraints.WEST;
		gbc_textFieldSoyad.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldSoyad.gridx = 1;
		gbc_textFieldSoyad.gridy = 1;
		panelSol.add(textFieldSoyad, gbc_textFieldSoyad);
		textFieldSoyad.setColumns(15);

		JLabel lblDoumTarihi = new JLabel("Do\u011Fum Tarihi:");
		GridBagConstraints gbc_lblDoumTarihi = new GridBagConstraints();
		gbc_lblDoumTarihi.anchor = GridBagConstraints.WEST;
		gbc_lblDoumTarihi.insets = new Insets(0, 10, 5, 5);
		gbc_lblDoumTarihi.gridx = 0;
		gbc_lblDoumTarihi.gridy = 2;
		panelSol.add(lblDoumTarihi, gbc_lblDoumTarihi);

		textFieldDogumTarihi = new JTextField();
		GridBagConstraints gbc_textFieldDogumTarihi = new GridBagConstraints();
		gbc_textFieldDogumTarihi.anchor = GridBagConstraints.WEST;
		gbc_textFieldDogumTarihi.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldDogumTarihi.gridx = 1;
		gbc_textFieldDogumTarihi.gridy = 2;
		panelSol.add(textFieldDogumTarihi, gbc_textFieldDogumTarihi);
		textFieldDogumTarihi.setColumns(15);

		JLabel lblCinsiyet = new JLabel("Cinsiyet:");
		GridBagConstraints gbc_lblCinsiyet = new GridBagConstraints();
		gbc_lblCinsiyet.anchor = GridBagConstraints.WEST;
		gbc_lblCinsiyet.insets = new Insets(0, 10, 5, 5);
		gbc_lblCinsiyet.gridx = 0;
		gbc_lblCinsiyet.gridy = 3;
		panelSol.add(lblCinsiyet, gbc_lblCinsiyet);

		comboBoxCinsiyet = new JComboBox(cinsiyet);
		GridBagConstraints gbc_comboBoxCinsiyet = new GridBagConstraints();
		gbc_comboBoxCinsiyet.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxCinsiyet.insets = new Insets(0, 0, 5, 10);
		gbc_comboBoxCinsiyet.gridx = 1;
		gbc_comboBoxCinsiyet.gridy = 3;
		panelSol.add(comboBoxCinsiyet, gbc_comboBoxCinsiyet);

		JLabel lblBoy = new JLabel("Boy:");
		GridBagConstraints gbc_lblBoy = new GridBagConstraints();
		gbc_lblBoy.anchor = GridBagConstraints.WEST;
		gbc_lblBoy.insets = new Insets(0, 10, 5, 5);
		gbc_lblBoy.gridx = 0;
		gbc_lblBoy.gridy = 4;
		panelSol.add(lblBoy, gbc_lblBoy);

		textFieldBoy = new JTextField();
		GridBagConstraints gbc_textFieldBoy = new GridBagConstraints();
		gbc_textFieldBoy.anchor = GridBagConstraints.WEST;
		gbc_textFieldBoy.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldBoy.gridx = 1;
		gbc_textFieldBoy.gridy = 4;
		panelSol.add(textFieldBoy, gbc_textFieldBoy);
		textFieldBoy.setColumns(15);

		JLabel lblKilo = new JLabel("Kilo:");
		GridBagConstraints gbc_lblKilo = new GridBagConstraints();
		gbc_lblKilo.anchor = GridBagConstraints.WEST;
		gbc_lblKilo.insets = new Insets(0, 10, 5, 5);
		gbc_lblKilo.gridx = 0;
		gbc_lblKilo.gridy = 5;
		panelSol.add(lblKilo, gbc_lblKilo);

		textFieldKilo = new JTextField();
		GridBagConstraints gbc_textFieldKilo = new GridBagConstraints();
		gbc_textFieldKilo.anchor = GridBagConstraints.WEST;
		gbc_textFieldKilo.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldKilo.gridx = 1;
		gbc_textFieldKilo.gridy = 5;
		panelSol.add(textFieldKilo, gbc_textFieldKilo);
		textFieldKilo.setColumns(15);

		JLabel lblSporcuYa = new JLabel("Sporcu Ya\u015F\u0131:");
		GridBagConstraints gbc_lblSporcuYa = new GridBagConstraints();
		gbc_lblSporcuYa.anchor = GridBagConstraints.WEST;
		gbc_lblSporcuYa.insets = new Insets(0, 10, 5, 5);
		gbc_lblSporcuYa.gridx = 0;
		gbc_lblSporcuYa.gridy = 6;
		panelSol.add(lblSporcuYa, gbc_lblSporcuYa);

		textFieldSporcuYasi = new JTextField();
		GridBagConstraints gbc_textFieldSporcuYasi = new GridBagConstraints();
		gbc_textFieldSporcuYasi.anchor = GridBagConstraints.WEST;
		gbc_textFieldSporcuYasi.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldSporcuYasi.gridx = 1;
		gbc_textFieldSporcuYasi.gridy = 6;
		panelSol.add(textFieldSporcuYasi, gbc_textFieldSporcuYasi);
		textFieldSporcuYasi.setColumns(15);

		JLabel lblSporBran = new JLabel("Spor Bran\u015F\u0131:");
		GridBagConstraints gbc_lblSporBran = new GridBagConstraints();
		gbc_lblSporBran.anchor = GridBagConstraints.WEST;
		gbc_lblSporBran.insets = new Insets(0, 10, 5, 5);
		gbc_lblSporBran.gridx = 0;
		gbc_lblSporBran.gridy = 7;
		panelSol.add(lblSporBran, gbc_lblSporBran);

		textFieldSporBransi = new JTextField();
		GridBagConstraints gbc_textFieldSporBransi = new GridBagConstraints();
		gbc_textFieldSporBransi.anchor = GridBagConstraints.WEST;
		gbc_textFieldSporBransi.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldSporBransi.gridx = 1;
		gbc_textFieldSporBransi.gridy = 7;
		panelSol.add(textFieldSporBransi, gbc_textFieldSporBransi);
		textFieldSporBransi.setColumns(15);

		JLabel lblGrup = new JLabel("Grup:");
		GridBagConstraints gbc_lblGrup = new GridBagConstraints();
		gbc_lblGrup.anchor = GridBagConstraints.WEST;
		gbc_lblGrup.insets = new Insets(0, 10, 5, 5);
		gbc_lblGrup.gridx = 0;
		gbc_lblGrup.gridy = 8;
		panelSol.add(lblGrup, gbc_lblGrup);

		comboBoxGrup = new JComboBox(gruplar);
		GridBagConstraints gbc_comboBoxGrup = new GridBagConstraints();
		gbc_comboBoxGrup.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxGrup.insets = new Insets(0, 0, 5, 10);
		gbc_comboBoxGrup.gridx = 1;
		gbc_comboBoxGrup.gridy = 8;
		panelSol.add(comboBoxGrup, gbc_comboBoxGrup);

		JButton btnOgrenciBilgiTemizle = new JButton("Temizle");
		btnOgrenciBilgiTemizle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldAd.setText("");
				textFieldSoyad.setText("");
				textFieldDogumTarihi.setText("");
				textFieldKilo.setText("");
				textFieldBoy.setText("");
				textFieldSporBransi.setText("");
				textFieldSporcuYasi.setText("");

				comboBoxGrup.setSelectedIndex(0);
			}
		});
		GridBagConstraints gbc_btnOgrenciBilgiTemizle = new GridBagConstraints();
		gbc_btnOgrenciBilgiTemizle.anchor = GridBagConstraints.EAST;
		gbc_btnOgrenciBilgiTemizle.insets = new Insets(0, 0, 5, 5);
		gbc_btnOgrenciBilgiTemizle.gridx = 0;
		gbc_btnOgrenciBilgiTemizle.gridy = 9;
		panelSol.add(btnOgrenciBilgiTemizle, gbc_btnOgrenciBilgiTemizle);

		btnOgrenciKaydet = new JButton("Kaydet");
		btnOgrenciKaydet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ad = textFieldAd.getText().trim();
				String soyad = textFieldSoyad.getText().trim();
				String dogumTarihi = textFieldDogumTarihi.getText().trim();
				String cinsiyetSecimi = (String) comboBoxCinsiyet.getSelectedItem();
				String boy = textFieldBoy.getText().trim();
				String kilo = textFieldKilo.getText().trim();
				String sporcuYasi = textFieldSporcuYasi.getText().trim();
				String sporBransi = textFieldSporBransi.getText().trim();
				Vector secilenGrup = (Vector) gruplar.get(comboBoxGrup.getSelectedIndex());

				int grupID = Integer.parseInt(secilenGrup.firstElement().toString());

				if (ad.length() != 0 && soyad.length() != 0
						&& cinsiyetSecimi.length() != 0 && boy.length() != 0
						&& kilo.length() != 0 && sporcuYasi.length() != 0
						&& sporBransi.length() != 0) {
					float boyu = Float.parseFloat(boy);
					float kilosu = Float.parseFloat(kilo);
					int sprocuYasi = Integer.parseInt(sporcuYasi);

					Ogrenci ogrenci = new Ogrenci();
					ogrenci.setAd(ad);
					ogrenci.setSoyad(soyad);
					ogrenci.setDogumTarihi(dogumTarihi);
					ogrenci.setCinsiyet(cinsiyetSecimi);
					ogrenci.setBoy(boyu);
					ogrenci.setKilo(kilosu);
					ogrenci.setSporcuYasi(sprocuYasi);
					ogrenci.setSporBransi(sporBransi);
					ogrenci.setGrupID(grupID);

					ogrenciService = new OgrenciService();
					Vector<Object> eklenenOgrenci = ogrenciService.ogrenciEkle(ogrenci);

					if (eklenenOgrenci != null) {
						ogrenciler.add(eklenenOgrenci);
						modelOgrenciler.addRow(eklenenOgrenci);
					}
				}

//				comboBoxTestIcinOgrenci.updateUI();
//				comboBoxListelenecekGrup.setSelectedIndex(0);
				
				resetAnasayfa();
			}
		});
		GridBagConstraints gbc_btnOgrenciKaydet = new GridBagConstraints();
		gbc_btnOgrenciKaydet.insets = new Insets(0, 0, 5, 5);
		gbc_btnOgrenciKaydet.anchor = GridBagConstraints.WEST;
		gbc_btnOgrenciKaydet.gridx = 1;
		gbc_btnOgrenciKaydet.gridy = 9;
		panelSol.add(btnOgrenciKaydet, gbc_btnOgrenciKaydet);

		JButton btnOgrenciGuncelle = new JButton("G\u00FCncelle");
		btnOgrenciGuncelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ad = textFieldAd.getText().trim();
				String soyad = textFieldSoyad.getText().trim();
				String dogumTarihi = textFieldDogumTarihi.getText().trim();
				String cinsiyetSecimi = (String) comboBoxCinsiyet.getSelectedItem();
				String boy = textFieldBoy.getText().trim();
				String kilo = textFieldKilo.getText().trim();
				String sporcuYasi = textFieldSporcuYasi.getText().trim();
				String sporBransi = textFieldSporBransi.getText().trim();
				Vector secilenGrup = (Vector) gruplar.get(comboBoxGrup.getSelectedIndex());

				int grupID = Integer.parseInt(secilenGrup.firstElement().toString());

				if (ad.length() != 0 && soyad.length() != 0
						&& cinsiyetSecimi.length() != 0 && boy.length() != 0
						&& kilo.length() != 0 && sporcuYasi.length() != 0
						&& sporBransi.length() != 0) {
					float boyu = Float.parseFloat(boy);
					float kilosu = Float.parseFloat(kilo);
					int sprocuYasi = Integer.parseInt(sporcuYasi);

					Ogrenci o = new Ogrenci();
					o.setId(Integer.parseInt(duzenlenecekOgrenci.get(0).toString()));
					o.setAd(ad);
					o.setSoyad(soyad);
					o.setDogumTarihi(dogumTarihi);
					o.setCinsiyet(cinsiyetSecimi);
					o.setBoy(boyu);
					o.setKilo(kilosu);
					o.setSporcuYasi(sprocuYasi);
					o.setSporBransi(sporBransi);
					o.setGrupID(grupID);

					ogrenciService = new OgrenciService();
					Vector<Object> guncellenenOgrenci = ogrenciService.ogrenciGuncelle(o);

					if (guncellenenOgrenci != null) {
						modelOgrenciler.removeRow(duzenlenecekOgrenciSatirNum);
						modelOgrenciler.insertRow(duzenlenecekOgrenciSatirNum, guncellenenOgrenci);
						ogrenciler.remove(duzenlenecekOgrenciSatirNum + 1);
						ogrenciler.add(duzenlenecekOgrenciSatirNum + 1, guncellenenOgrenci);
					} else {
						JOptionPane.showMessageDialog(null, "Öðrenci güncellenemedi!!!");
					}
					
//					comboBoxTestIcinOgrenci.updateUI();
//					comboBoxListelenecekGrup.setSelectedIndex(0);
					
					resetAnasayfa();
				}
			}
		});
		GridBagConstraints gbc_btnOgrenciGuncelle = new GridBagConstraints();
		gbc_btnOgrenciGuncelle.anchor = GridBagConstraints.WEST;
		gbc_btnOgrenciGuncelle.insets = new Insets(0, 0, 5, 5);
		gbc_btnOgrenciGuncelle.gridx = 1;
		gbc_btnOgrenciGuncelle.gridy = 10;
		panelSol.add(btnOgrenciGuncelle, gbc_btnOgrenciGuncelle);

		JPanel panelSag = new JPanel();
		layeredPaneOgrenciler.add(panelSag, BorderLayout.CENTER);
		panelSag.setLayout(new BorderLayout(0, 0));

		JPanel panelUst = new JPanel();
		panelUst.setBackground(Color.WHITE);
		panelSag.add(panelUst, BorderLayout.NORTH);
		GridBagLayout gbl_panelUst = new GridBagLayout();
		gbl_panelUst.columnWidths = new int[] {120, 120, 40, 0, 120, 0};
		gbl_panelUst.rowHeights = new int[] {23, 0};
		gbl_panelUst.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0};
		gbl_panelUst.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelUst.setLayout(gbl_panelUst);

		JLabel lblListelenecekGrubuSein = new JLabel("Listelenecek Grubu Se\u00E7in:");
		GridBagConstraints gbc_lblListelenecekGrubuSein = new GridBagConstraints();
		gbc_lblListelenecekGrubuSein.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblListelenecekGrubuSein.insets = new Insets(5, 0, 0, 5);
		gbc_lblListelenecekGrubuSein.gridx = 0;
		gbc_lblListelenecekGrubuSein.gridy = 0;
		panelUst.add(lblListelenecekGrubuSein, gbc_lblListelenecekGrubuSein);

		comboBoxListelenecekGrup = new JComboBox(gruplar);
		GridBagConstraints gbc_comboBoxListelenecekGrup = new GridBagConstraints();
		gbc_comboBoxListelenecekGrup.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxListelenecekGrup.insets = new Insets(5, 0, 5, 5);
		gbc_comboBoxListelenecekGrup.gridx = 1;
		gbc_comboBoxListelenecekGrup.gridy = 0;
		panelUst.add(comboBoxListelenecekGrup, gbc_comboBoxListelenecekGrup);

		comboBoxListelenecekGrup.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (comboBoxListelenecekGrup.getSelectedIndex() != 0) {
					modelOgrencilerTemizle();

					Vector secilenGrup = (Vector) gruplar.get(comboBoxListelenecekGrup.getSelectedIndex());
					int grupID = Integer.parseInt(secilenGrup.firstElement().toString());

					ogrenciService = new OgrenciService();
					Vector<Object> secilenGruptakiOgrenciler = ogrenciService.grubaAitTumOgrencileriGetir(grupID);

					ogrenciler.removeAllElements();
					ogrenciler.add(0, "Bir Öðrenci Seçin");

					if (secilenGruptakiOgrenciler != null && secilenGruptakiOgrenciler.size() > 0) {
						ogrenciler.addAll(1, secilenGruptakiOgrenciler);

						for (int i = 0; i < secilenGruptakiOgrenciler.size(); i++) {
							modelOgrenciler.addRow((Vector) secilenGruptakiOgrenciler.get(i));
						}
					}
				}
			}
		});

		JButton btnTmrencileriListele = new JButton("T\u00FCm \u00D6\u011Frencileri Listele");
		btnTmrencileriListele.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modelOgrencilerTemizle();

				ogrenciService = new OgrenciService();
				Vector<Object> tumOgrenciler = ogrenciService.tumOgrencileriGetir();

				ogrenciler.removeAllElements();
				ogrenciler.add(0, "Bir Öðrenci Seçin");
				ogrenciler.addAll(1, tumOgrenciler);


				for (int i = 0; i < tumOgrenciler.size(); i++) {
					modelOgrenciler.addRow((Vector) tumOgrenciler.get(i));		// veye tumOgrenciler.get[0, 1, ... ] i = 0 dan baþla
				}

				//				if (ogrenciler != null) {
				//					for (Object o : ogrenciler) {
				//						modelOgrenciler.addRow((Vector) o);
				//					}
				//				}

				comboBoxListelenecekGrup.setSelectedIndex(0);
			}
		});
		GridBagConstraints gbc_btnTmrencileriListele = new GridBagConstraints();
		gbc_btnTmrencileriListele.insets = new Insets(0, 0, 0, 5);
		gbc_btnTmrencileriListele.anchor = GridBagConstraints.EAST;
		gbc_btnTmrencileriListele.gridx = 5;
		gbc_btnTmrencileriListele.gridy = 0;
		panelUst.add(btnTmrencileriListele, gbc_btnTmrencileriListele);

		JPanel panelOrta = new JPanel();
		panelSag.add(panelOrta, BorderLayout.CENTER);
		panelOrta.setLayout(new BorderLayout(0, 0));

		modelOgrenciler = new DefaultTableModel();
		String[] ogrencilerSutunAdlari = { "ogrenci_id", "ogrenci_ad",
				"ogrenci_soyad", "ogrenci_dt", "ogrenci_cinsiyet",
				"ogrenci_boy", "ogrenci_kilo", "ogrenci_sprcu_yazi",
				"ogrenci_bransi", "grup_id" };
		for (String item : ogrencilerSutunAdlari) {
			modelOgrenciler.addColumn(item);
		}

		ogrenciler.add("Bir Öðrenci Seçin");
		ogrenciService = new OgrenciService();
		ogrenciler.addAll(1, ogrenciService.tumOgrencileriGetir());

		for (int i = 1; i < ogrenciler.size(); i++) {
			modelOgrenciler.addRow((Vector) ogrenciler.get(i));
		}

		tableOgrenciler = new JTable(modelOgrenciler);
		tableOgrenciler.setRowHeight(22);
		tableOgrenciler.setRowSelectionAllowed(true);
		tableOgrenciler.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableOgrenciler.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tableOgrenciler.setBounds(560, 286, -426, -249);
		JScrollPane scrollPaneOgrenciler = new JScrollPane(tableOgrenciler,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panelOrta.add(scrollPaneOgrenciler);

		JPanel panelAlt = new JPanel();
		panelAlt.setBackground(Color.WHITE);
		panelSag.add(panelAlt, BorderLayout.SOUTH);
		GridBagLayout gbl_panelAlt = new GridBagLayout();
		gbl_panelAlt.columnWidths = new int[] {70, 70, 355, 40, 0};
		gbl_panelAlt.rowHeights = new int[]{23, 0};
		gbl_panelAlt.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelAlt.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelAlt.setLayout(gbl_panelAlt);

		JButton btnOgrenciSil = new JButton("Sil");
		btnOgrenciSil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int secilenSatirNum = tableOgrenciler.getSelectedRow();
				Vector<Object> secilenSatir = getSelectedRowData(modelOgrenciler, secilenSatirNum);

				ogrenciService = new OgrenciService();
				if (ogrenciService.ogrenciSil((Integer) secilenSatir.get(0)) == 1) {
					modelOgrenciler.removeRow(secilenSatirNum);
					ogrenciler.remove(secilenSatirNum + 1);
				} else
					JOptionPane.showMessageDialog(null,
							"\"" + secilenSatir.get(1)
							+ "\" isimli öðrenci silinemedi!");

				resetAnasayfa();
			}
		});
		GridBagConstraints gbc_btnOgrenciSil = new GridBagConstraints();
		gbc_btnOgrenciSil.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnOgrenciSil.anchor = GridBagConstraints.NORTH;
		gbc_btnOgrenciSil.insets = new Insets(5, 0, 5, 5);
		gbc_btnOgrenciSil.gridx = 0;
		gbc_btnOgrenciSil.gridy = 0;
		panelAlt.add(btnOgrenciSil, gbc_btnOgrenciSil);

		JButton btnOgrenciDuzenle = new JButton("D\u00FCzenle");
		btnOgrenciDuzenle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				duzenlenecekOgrenciSatirNum = tableOgrenciler.getSelectedRow();
				duzenlenecekOgrenci = getSelectedRowData(modelOgrenciler, duzenlenecekOgrenciSatirNum);

				verileriFieldaSetEt(duzenlenecekOgrenci);
			}
		});
		GridBagConstraints gbc_btnOgrenciDuzenle = new GridBagConstraints();
		gbc_btnOgrenciDuzenle.insets = new Insets(0, 0, 0, 5);
		gbc_btnOgrenciDuzenle.gridx = 1;
		gbc_btnOgrenciDuzenle.gridy = 0;
		panelAlt.add(btnOgrenciDuzenle, gbc_btnOgrenciDuzenle);

		final JSpinner rowHeight = new JSpinner(new SpinnerNumberModel(25, 20, 50, 1));
		GridBagConstraints gbc_rowHeight = new GridBagConstraints();
		gbc_rowHeight.insets = new Insets(0, 0, 0, 5);
		gbc_rowHeight.anchor = GridBagConstraints.EAST;
		gbc_rowHeight.gridx = 3;
		gbc_rowHeight.gridy = 0;
		panelAlt.add(rowHeight, gbc_rowHeight);

		rowHeight.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				tableOgrenciler.setRowHeight(((Integer) rowHeight.getValue()).intValue());
			}
		});


		/* 
		 * 
		 * TEST ÞEKÝLLERÝ BAÞLANGIÇ
		 * 
		 * */

		JLayeredPane layeredPaneTestSekilleri = new JLayeredPane();
		tabbedPane.addTab("Test \u015Eekilleri", null, layeredPaneTestSekilleri, null);

		modelTestSekilleri = new DefaultTableModel();
		modelTestSekilleri.addColumn("ID");
		modelTestSekilleri.addColumn("Test Þekil Ad");

		testSekilleri = new TestSekilService().tumTestSekilleriniGetir();

		for (Object data : testSekilleri) {
			modelTestSekilleri.addRow((Vector) data);
		}

		layeredPaneTestSekilleri.setLayout(new BorderLayout(0, 0));

		JPanel panelTestSekilleriSol = new JPanel();
		panelTestSekilleriSol.setBackground(Color.WHITE);
		layeredPaneTestSekilleri.add(panelTestSekilleriSol, BorderLayout.WEST);
		GridBagLayout gbl_panelTestSekilleriSol = new GridBagLayout();
		gbl_panelTestSekilleriSol.columnWidths = new int[] {30, 220};
		gbl_panelTestSekilleriSol.rowHeights = new int[] {30, 30, 30, 30, 30, 0, 30, 30};
		gbl_panelTestSekilleriSol.columnWeights = new double[]{0.0, 1.0};
		gbl_panelTestSekilleriSol.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		panelTestSekilleriSol.setLayout(gbl_panelTestSekilleriSol);

		JLabel lblTestekliTanm = new JLabel("Test \u015Eekli Tan\u0131m\u0131");
		GridBagConstraints gbc_lblTestekliTanm = new GridBagConstraints();
		gbc_lblTestekliTanm.anchor = GridBagConstraints.WEST;
		gbc_lblTestekliTanm.insets = new Insets(0, 0, 5, 0);
		gbc_lblTestekliTanm.gridx = 1;
		gbc_lblTestekliTanm.gridy = 0;
		panelTestSekilleriSol.add(lblTestekliTanm, gbc_lblTestekliTanm);

		textFieldTestSekliAd = new JTextField();
		GridBagConstraints gbc_textFieldTestSekliAd = new GridBagConstraints();
		gbc_textFieldTestSekliAd.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldTestSekliAd.insets = new Insets(0, 0, 5, 10);
		gbc_textFieldTestSekliAd.gridx = 1;
		gbc_textFieldTestSekliAd.gridy = 1;
		panelTestSekilleriSol.add(textFieldTestSekliAd, gbc_textFieldTestSekliAd);
		textFieldTestSekliAd.setColumns(10);

		JButton btnTestekliniEkle = new JButton("Test \u015Eeklini Ekle");
		btnTestekliniEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String testAdi = textFieldTestSekliAd.getText().trim();

				if (testAdi.length() != 0) {
					testSekilService = new TestSekilService();

					Vector<Object> eklenen = testSekilService.testSekliEkle(testAdi);
					if (eklenen != null) {
						modelTestSekilleri.addRow(eklenen);
						testSekilleri.add(eklenen);

						textFieldTestSekliAd.setText("");
					}
				}

//				comboBoxTestIcinSekil.updateUI();
				resetAnasayfa();
			}
		});

		GridBagConstraints gbc_btnTestekliniEkle = new GridBagConstraints();
		gbc_btnTestekliniEkle.insets = new Insets(0, 0, 5, 10);
		gbc_btnTestekliniEkle.anchor = GridBagConstraints.EAST;
		gbc_btnTestekliniEkle.gridx = 1;
		gbc_btnTestekliniEkle.gridy = 2;
		panelTestSekilleriSol.add(btnTestekliniEkle, gbc_btnTestekliniEkle);
		
		JButton btnTestekliniGncelle = new JButton("Test \u015Eeklini G\u00FCncelle");
		btnTestekliniGncelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String testSekliAd = textFieldTestSekliAd.getText().trim();
				if (!MalformedControl.testSekliAdIsMalformed(testSekliAd)) {
					duzenlenecekTestSekli.set(1, testSekliAd);
					testSekilService = new TestSekilService();

					if (testSekilService.testSekliniGuncelle(duzenlenecekTestSekli) == 1) {
						modelTestSekilleri.removeRow(duzenlenecekSatirNum);
						modelTestSekilleri.insertRow(duzenlenecekSatirNum, duzenlenecekTestSekli);
						
						testSekilleri.remove(duzenlenecekSatirNum + 1);
						testSekilleri.add(duzenlenecekSatirNum + 1, duzenlenecekTestSekli);
					} else
						JOptionPane.showMessageDialog(null, "Test Þekli Güncelleme baþarýsýz!");
				}
				
				resetAnasayfa();
				
				textFieldTestSekliAd.setText("");
//				comboBoxTestIcinSekil.updateUI();
			}
		});
		GridBagConstraints gbc_btnTestekliniGncelle = new GridBagConstraints();
		gbc_btnTestekliniGncelle.anchor = GridBagConstraints.EAST;
		gbc_btnTestekliniGncelle.insets = new Insets(0, 0, 5, 10);
		gbc_btnTestekliniGncelle.gridx = 1;
		gbc_btnTestekliniGncelle.gridy = 3;
		panelTestSekilleriSol.add(btnTestekliniGncelle, gbc_btnTestekliniGncelle);

		JPanel panelTestSekilleriSag = new JPanel();
		panelTestSekilleriSag.setBackground(Color.WHITE);
		layeredPaneTestSekilleri.add(panelTestSekilleriSag, BorderLayout.CENTER);
		panelTestSekilleriSag.setLayout(new BorderLayout(0, 0));

		tableTestSekilleri = new JTable(modelTestSekilleri);
		tableTestSekilleri.setRowHeight(22);
		tableTestSekilleri.setRowSelectionAllowed(true);
		tableTestSekilleri.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableTestSekilleri.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scrollPaneTestSekilleri = new JScrollPane(tableTestSekilleri,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panelTestSekilleriSag.add(scrollPaneTestSekilleri, BorderLayout.CENTER);

		JPanel panelTestSekilleriIslemleri = new JPanel();
		panelTestSekilleriSag.add(panelTestSekilleriIslemleri, BorderLayout.SOUTH);
		GridBagLayout gbl_panelTestSekilleriIslemleri = new GridBagLayout();
		gbl_panelTestSekilleriIslemleri.rowHeights = new int[] {30};
		gbl_panelTestSekilleriIslemleri.columnWidths = new int[] {0, 100};
		gbl_panelTestSekilleriIslemleri.columnWeights = new double[]{0.0};
		gbl_panelTestSekilleriIslemleri.rowWeights = new double[]{0.0};
		panelTestSekilleriIslemleri.setLayout(gbl_panelTestSekilleriIslemleri);

		JButton btnTestSekliSil = new JButton("Sil");
		btnTestSekliSil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int secilenSatirNum = tableTestSekilleri.getSelectedRow();
				Vector<Object> secilenSatir = getSelectedRowData(modelTestSekilleri, secilenSatirNum);

				testSekilService = new TestSekilService();
				if (testSekilService.testSekliniSil((Integer) secilenSatir.get(0)) == 1) {
					modelTestSekilleri.removeRow(secilenSatirNum);
					testSekilleri.remove(secilenSatirNum + 1);
				} else {
					JOptionPane.showMessageDialog(null, "\"" + secilenSatir.get(1) + "\" isimli Test Þekili silinemedi!");
				}

				comboBoxTestIcinSekil.setSelectedIndex(0);
				comboBoxTestIcinSekil.updateUI();
			}
		});
		GridBagConstraints gbc_btnTestSekliSil = new GridBagConstraints();
		gbc_btnTestSekliSil.anchor = GridBagConstraints.WEST;
		gbc_btnTestSekliSil.insets = new Insets(0, 0, 0, 5);
		gbc_btnTestSekliSil.gridx = 0;
		gbc_btnTestSekliSil.gridy = 0;
		panelTestSekilleriIslemleri.add(btnTestSekliSil, gbc_btnTestSekliSil);

		JButton btnTestSekliDuzenle = new JButton("D\u00FCzenle");
		btnTestSekliDuzenle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				duzenlenecekSatirNum = tableTestSekilleri.getSelectedRow();
				duzenlenecekTestSekli = getSelectedRowData(modelTestSekilleri, duzenlenecekSatirNum);

				textFieldTestSekliAd.setText(duzenlenecekTestSekli.get(1).toString());
			}
		});
		GridBagConstraints gbc_btnTestSekliDuzenle = new GridBagConstraints();
		gbc_btnTestSekliDuzenle.anchor = GridBagConstraints.WEST;
		gbc_btnTestSekliDuzenle.insets = new Insets(0, 0, 0, 5);
		gbc_btnTestSekliDuzenle.gridx = 1;
		gbc_btnTestSekliDuzenle.gridy = 0;
		panelTestSekilleriIslemleri.add(btnTestSekliDuzenle, gbc_btnTestSekliDuzenle);

		/*
		 * Anasayfa
		 * */

		JPanel panelBasla = new JPanel();
		panelBasla.setBackground(Color.WHITE);
		layeredPaneAnaEkran.add(panelBasla);
		GridBagLayout gbl_panelBasla = new GridBagLayout();
		gbl_panelBasla.columnWidths = new int[] {30, 30, 100, 0, 0, 20};
		gbl_panelBasla.rowHeights = new int[]{64, 0, 0, 0, 0, 0};
		gbl_panelBasla.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panelBasla.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelBasla.setLayout(gbl_panelBasla);

		JLabel lblGrubuSein = new JLabel("Grup:");
		GridBagConstraints gbc_lblGrubuSein = new GridBagConstraints();
		gbc_lblGrubuSein.insets = new Insets(0, 0, 5, 5);
		gbc_lblGrubuSein.anchor = GridBagConstraints.EAST;
		gbc_lblGrubuSein.gridx = 2;
		gbc_lblGrubuSein.gridy = 1;
		panelBasla.add(lblGrubuSein, gbc_lblGrubuSein);

		comboBoxTestIcinGrup = new JComboBox(gruplar);
		GridBagConstraints gbc_comboBoxTestIcinGrup = new GridBagConstraints();
		gbc_comboBoxTestIcinGrup.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxTestIcinGrup.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxTestIcinGrup.gridx = 3;
		gbc_comboBoxTestIcinGrup.gridy = 1;
		panelBasla.add(comboBoxTestIcinGrup, gbc_comboBoxTestIcinGrup);

		comboBoxTestIcinGrup.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (comboBoxTestIcinGrup.getSelectedIndex() != 0) {
					Vector secilenGrup = (Vector) gruplar.get(comboBoxTestIcinGrup.getSelectedIndex());
					int grupID = Integer.parseInt(secilenGrup.firstElement().toString());
					ogrenciService = new OgrenciService();
					Vector<Object> secilenGruptakiOgrenciler = ogrenciService.grubaAitTumOgrencileriGetir(grupID);

					ogrenciler.removeAllElements();
					ogrenciler.add(0, "Bir Öðrenci Seçin");
					ogrenciler.addAll(1, secilenGruptakiOgrenciler);

					comboBoxTestIcinOgrenci.updateUI();
					comboBoxTestIcinOgrenci.setSelectedIndex(0);
				}
			}
		});

		JLabel lblrenci = new JLabel("\u00D6\u011Frenci:");
		GridBagConstraints gbc_lblrenci = new GridBagConstraints();
		gbc_lblrenci.anchor = GridBagConstraints.EAST;
		gbc_lblrenci.insets = new Insets(0, 0, 5, 5);
		gbc_lblrenci.gridx = 2;
		gbc_lblrenci.gridy = 2;
		panelBasla.add(lblrenci, gbc_lblrenci);

		comboBoxTestIcinOgrenci = new JComboBox(ogrenciler);
		GridBagConstraints gbc_comboBoxTestIcinOgrenci = new GridBagConstraints();
		gbc_comboBoxTestIcinOgrenci.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxTestIcinOgrenci.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxTestIcinOgrenci.gridx = 3;
		gbc_comboBoxTestIcinOgrenci.gridy = 2;
		panelBasla.add(comboBoxTestIcinOgrenci, gbc_comboBoxTestIcinOgrenci);

		JLabel lblTestekli = new JLabel("Test \u015Eekli:");
		GridBagConstraints gbc_lblTestekli = new GridBagConstraints();
		gbc_lblTestekli.anchor = GridBagConstraints.EAST;
		gbc_lblTestekli.insets = new Insets(0, 0, 5, 5);
		gbc_lblTestekli.gridx = 2;
		gbc_lblTestekli.gridy = 3;
		panelBasla.add(lblTestekli, gbc_lblTestekli);

		testSekilleri.add(0, "Bir Test Þekli Seçin");
		comboBoxTestIcinSekil = new JComboBox(testSekilleri);
		GridBagConstraints gbc_comboBoxTestIcinSekil = new GridBagConstraints();
		gbc_comboBoxTestIcinSekil.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxTestIcinSekil.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxTestIcinSekil.gridx = 3;
		gbc_comboBoxTestIcinSekil.gridy = 3;
		panelBasla.add(comboBoxTestIcinSekil, gbc_comboBoxTestIcinSekil);

		/*
		 * Teste baþla
		 */
		JButton btnBrixtonTestineBala = new JButton("Brixton Testine Ba\u015Fla");
		btnBrixtonTestineBala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int secilenGrupIndex = comboBoxTestIcinGrup.getSelectedIndex();
				int secilenOgrenciIndex = comboBoxTestIcinOgrenci.getSelectedIndex();
				int secilenTestIntex = comboBoxTestIcinSekil.getSelectedIndex();

				if (secilenGrupIndex == 0 || secilenOgrenciIndex == 0 || secilenTestIntex == 0) {
					JOptionPane.showMessageDialog(null, "Grup, Öðrenci ve Test Þekli Seçmelisiniz!!!");
					return;
				}
				
				Vector<Object> secilenGrup = (Vector<Object>) gruplar.get(secilenGrupIndex);
				Grup grup = new Grup(Integer.parseInt(secilenGrup.get(0).toString()), secilenGrup.get(1).toString());
				
				Vector<Object> secilenOgrenci = (Vector<Object>) ogrenciler.get(secilenOgrenciIndex);
				Ogrenci ogrenci = new Ogrenci();
				ogrenci.setId(Integer.parseInt(secilenOgrenci.get(0).toString()));
				ogrenci.setAd(secilenOgrenci.get(1).toString());
				ogrenci.setSoyad(secilenOgrenci.get(2).toString());
				ogrenci.setDogumTarihi(secilenOgrenci.get(3).toString());
				ogrenci.setCinsiyet(secilenOgrenci.get(4).toString());
				ogrenci.setBoy(Float.parseFloat(secilenOgrenci.get(5).toString()));
				ogrenci.setKilo(Float.parseFloat(secilenOgrenci.get(6).toString()));
				ogrenci.setSporcuYasi(Integer.parseInt(secilenOgrenci.get(7).toString()));
				ogrenci.setSporBransi(secilenOgrenci.get(8).toString());
				ogrenci.setGrupID(Integer.parseInt(secilenOgrenci.get(9).toString()));
				
				Vector<Object> secilenTestSekli = (Vector<Object>) testSekilleri.get(secilenTestIntex);
				TestSekli testSekli = new TestSekli(Integer.parseInt(secilenTestSekli.get(0).toString()), secilenTestSekli.get(1).toString());
								
				BrixtonTest testFrame = new BrixtonTest(grup, ogrenci, testSekli);
				testFrame.setVisible(true);
				
				resetAnasayfa();
			}
		});
		GridBagConstraints gbc_btnBrixtonTestineBala = new GridBagConstraints();
		gbc_btnBrixtonTestineBala.anchor = GridBagConstraints.EAST;
		gbc_btnBrixtonTestineBala.insets = new Insets(0, 0, 0, 5);
		gbc_btnBrixtonTestineBala.gridx = 3;
		gbc_btnBrixtonTestineBala.gridy = 4;
		panelBasla.add(btnBrixtonTestineBala, gbc_btnBrixtonTestineBala);

		/*
		 * Anasayfa son
		 * */

	}

	public Vector<Object> getSelectedRowData(TableModel model, int selectedRow) {
		Vector<Object> selectedRowData = new Vector<Object>();

		for (int i = 0; i < model.getColumnCount(); i++) {
			selectedRowData.add((Object) model.getValueAt(selectedRow, i));
		}

		return selectedRowData;
	}

	public void modelOgrencilerTemizle() {
		int tablodakiOgrenciSayisi = modelOgrenciler.getRowCount();

		if (tablodakiOgrenciSayisi > 0) {
			for (int i = 0; i < tablodakiOgrenciSayisi; i++) {
				modelOgrenciler.removeRow(tablodakiOgrenciSayisi - i - 1);
			}
		}
	}

	private void verileriFieldaSetEt(Vector<Object> duzenlenecekOgrenci) {
		textFieldAd.setText(duzenlenecekOgrenci.get(1).toString());
		textFieldSoyad.setText(duzenlenecekOgrenci.get(2).toString());
		textFieldDogumTarihi.setText(duzenlenecekOgrenci.get(3).toString());

		comboBoxCinsiyet.setSelectedIndex(duzenlenecekOgrenci.get(4).toString().equals("E") ? 0 : 1);

		textFieldBoy.setText(duzenlenecekOgrenci.get(5).toString());
		textFieldKilo.setText(duzenlenecekOgrenci.get(6).toString());
		textFieldSporcuYasi.setText(duzenlenecekOgrenci.get(7).toString());
		textFieldSporBransi.setText(duzenlenecekOgrenci.get(8).toString());

		int grupID = Integer.parseInt(duzenlenecekOgrenci.get(9).toString());

		for (int i = 1; i < gruplar.size(); i++) {
			int id = Integer.parseInt(((Vector) gruplar.get(i)).firstElement().toString());

			if (id == grupID)
				grupID = i;
		}

		comboBoxGrup.setSelectedIndex(grupID);
	}

	private void resetAnasayfa() {
		comboBoxTestIcinGrup.updateUI();
		comboBoxTestIcinOgrenci.updateUI();
		comboBoxTestIcinSekil.updateUI();
		
		comboBoxListelenecekGrup.updateUI();
		comboBoxListelenecekGrup.setSelectedIndex(0);
		
		comboBoxTestIcinOgrenci.setSelectedIndex(0);
		comboBoxTestIcinGrup.setSelectedIndex(0);
		comboBoxTestIcinSekil.setSelectedIndex(0);
	}
}
