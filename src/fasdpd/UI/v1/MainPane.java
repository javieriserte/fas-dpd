package fasdpd.UI.v1;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainPane extends JPanel {
	private JButton jbSetIndividual;
	private JButton jbSetPairs;
	private MainFASDPD mainFrame;
	// TODO implement singleton
	
	public MainPane(MainFASDPD mainFASDPD) {
		super();
		// TODO Separate GUI creation of Constructor
		this.mainFrame = mainFASDPD;
		
		GridBagLayout thisLayout = new GridBagLayout();
		thisLayout.columnWeights = new double[] {1, 0,0,0,1}; 
		thisLayout.columnWidths = new int[] {50, 100, 10,100,50}; // total width of panel is 310
		thisLayout.rowWeights = new double[] {1, 0, 1};
		thisLayout.rowHeights = new int[] {50, 60, 50}; // total width of panel is 160
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(thisLayout);
		
		jbSetIndividual = new JButton();
		jbSetIndividual.setText("<HTML><CENTER>Design Individual Primers</CENTER></html>");
		jbSetIndividual.setMargin(new Insets(2, 2,2,2));
		jbSetIndividual.setPreferredSize(new Dimension(100, 60));
		jbSetIndividual.setHorizontalAlignment(SwingConstants.CENTER );
		jbSetIndividual.addActionListener(new jbSetIndAction());
	
	
		c.fill = GridBagConstraints.HORIZONTAL ;
		c.gridx = 1;
		c.gridy = 1;
		this.add(jbSetIndividual,c);

		
		jbSetPairs = new JButton();
		jbSetPairs.setText("<HTML><CENTER>Design PCR primer pairs</CENTER></html>");
		jbSetPairs.setMargin(new Insets(2, 2,2,2));
		jbSetPairs.setHorizontalAlignment(SwingConstants.TRAILING );
		jbSetPairs.setPreferredSize(new Dimension(100, 60));
		jbSetPairs.addActionListener(new jbSetPairAction());
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 1;
		this.add(jbSetPairs,c);
	}
	
	// LISTENERS & EXTRA CLASSES

	private class jbSetIndAction implements ActionListener {

		@Override public void actionPerformed(ActionEvent e) {
			System.out.println("Individual Set");
			MainPane.this.mainFrame.getSearchParameter().setSearchPair(false);
			MainPane.this.mainFrame.loadOpenFilePane();
		}
	}

	private class jbSetPairAction implements ActionListener {

		@Override public void actionPerformed(ActionEvent e) {
			System.out.println("Pair Set");
			MainPane.this.mainFrame.getSearchParameter().setSearchPair(true);
			MainPane.this.mainFrame.loadOpenFilePane();
		}
	}
	
	
	
}
