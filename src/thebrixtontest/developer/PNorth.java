package thebrixtontest.developer;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PNorth {
	
	JPanel pNorth;

	public JPanel getPanel(String title) {
		/* Üst Kýsým */
		pNorth = new JPanel();
		pNorth.setBackground(Color.BLUE);
		pNorth.add(new JLabel(title));
		
		return pNorth;
	}

}
