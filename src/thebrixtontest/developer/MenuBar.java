package thebrixtontest.developer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MenuBar extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* Add Menu Bar */
	public JMenuBar addMenuBar() {
		JMenuBar menubar = new JMenuBar();

		JMenu dosya = new JMenu("Dosya");
		menubar.add(dosya);
		JMenuItem yeni = new JMenuItem("Yeni");
		dosya.add(yeni);
		JMenuItem kaydet = new JMenuItem("Kaydet");
		dosya.add(kaydet);
		JMenuItem cikis = new JMenuItem("��k��");
		dosya.add(cikis);

		JMenu yardim = new JMenu("Yard�m");
		menubar.add(yardim);
		JMenuItem hakkimizda = new JMenuItem("Hakk�m�zda");
		yardim.add(hakkimizda);

		cikis.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		return menubar;
	}

}
