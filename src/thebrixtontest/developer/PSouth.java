package thebrixtontest.developer;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PSouth {

	JPanel pSouth;
	
	public JPanel getPanel(String title) {
		/* Alt kýsým */
		pSouth = new JPanel();
		pSouth.setBackground(Color.BLUE);
		pSouth.add(new JLabel(title));
		
		return pSouth;
	}

}
