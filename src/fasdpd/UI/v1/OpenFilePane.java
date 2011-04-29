package fasdpd.UI.v1;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.JFrame;

public class OpenFilePane extends javax.swing.JPanel {

	private JButton jBopen;
	private ButtonGroup dnaorprotein;
	private JRadioButton dna;
	private JRadioButton protein;
	
	
	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new OpenFilePane());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public OpenFilePane() {
		super();
		initGUI();
	}
	
	private void initGUI() {

	
		GridBagLayout thisLayout = new GridBagLayout();
		thisLayout.columnWeights = new double[] {1, 0,0,0,1}; 
		thisLayout.columnWidths = new int[] {50, 100, 10,100,50}; // total width of panel is 310
		thisLayout.rowWeights = new double[] {1, 0, 0, 1};
		thisLayout.rowHeights = new int[] {50, 30, 30, 50}; // total width of panel is 160
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(thisLayout);
		
		

		jBopen = new JButton();
		jBopen.setText("<HTML><CENTER>Open Alignment File</CENTER></html>");
		jBopen.setMargin(new Insets(2, 2,2,2));
		jBopen.setPreferredSize(new Dimension(100, 60));
		jBopen.setHorizontalAlignment(SwingConstants.CENTER );
	
		c.gridheight = 2;
		c.fill = GridBagConstraints.HORIZONTAL ;
		c.gridx = 1;
		c.gridy = 1;
		this.add(jBopen,c);

		
		dna = new JRadioButton();
		dna.setText("from DNA ");
		dna.setMargin(new Insets(2, 2,2,2));
		dna.setPreferredSize(new Dimension(100, 30));
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 3;
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(dna,c);
		
		protein = new JRadioButton();
		protein.setText("from protein ");
		protein.setMargin(new Insets(2, 2,2,2));
		protein.setPreferredSize(new Dimension(100, 30));
		c.fill = GridBagConstraints.NONE;
		c.gridx = 3;
		c.gridy = 2;
		this.add(protein,c);
		
		this.dnaorprotein = new ButtonGroup();
		this.dnaorprotein.add(dna);
		this.dnaorprotein.add(protein);
		dna.setSelected(true);
		
	}
	
	

}
