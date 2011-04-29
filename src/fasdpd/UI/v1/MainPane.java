package fasdpd.UI.v1;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainPane extends JPanel {
	private JButton jButton1;
	private JButton jButton2;

	public MainPane() {
		super();
		
		GridBagLayout thisLayout = new GridBagLayout();
		thisLayout.columnWeights = new double[] {1, 0,0,0,1}; 
		thisLayout.columnWidths = new int[] {50, 100, 10,100,50}; // total width of panel is 310
		thisLayout.rowWeights = new double[] {1, 0, 1};
		thisLayout.rowHeights = new int[] {50, 60, 50}; // total width of panel is 160
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(thisLayout);
		
		

		jButton1 = new JButton();
		jButton1.setText("<HTML><CENTER>Design Individual Primers</CENTER></html>");
		jButton1.setMargin(new Insets(2, 2,2,2));
		jButton1.setPreferredSize(new Dimension(100, 60));
		jButton1.setHorizontalAlignment(SwingConstants.CENTER );
	
	
		c.fill = GridBagConstraints.HORIZONTAL ;
		c.gridx = 1;
		c.gridy = 1;
		this.add(jButton1,c);

		
		jButton2 = new JButton();
		jButton2.setText("<HTML><CENTER>Design PCR primer pairs</CENTER></html>");
		jButton2.setMargin(new Insets(2, 2,2,2));
		jButton2.setHorizontalAlignment(SwingConstants.TRAILING );
		jButton2.setPreferredSize(new Dimension(100, 60));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 1;
		this.add(jButton2,c);
	}

	
	// TODO implement singleton
	
	
	
}
