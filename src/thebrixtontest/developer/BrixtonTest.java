package thebrixtontest.developer;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;

public class BrixtonTest extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BrixtonTest() throws IOException {
		setJMenuBar(new MenuBar().addMenuBar());
		
		add(new PNorth().getPanel("The Brixton Test"), BorderLayout.NORTH);
		
		add(new PCenterTest().getPanel(), BorderLayout.CENTER);
		
		add(new PSouth().getPanel("Merhaba Dünya! (3)"), BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 980, 550);
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void main(String[] args) throws IOException {
		new BrixtonTest();
	}

}
