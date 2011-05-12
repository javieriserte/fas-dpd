package fasdpd.UI.v1;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.WindowConstants;
import javax.swing.JFrame;

import sequences.alignment.Alignment;
import sequences.dna.DNASeq;
import degeneration.GeneticCode;
import fastaIO.FastaMultipleReader;
import fastaIO.Pair;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class OptionsPane extends JPanel {
	AlignmentExplorer ae;
	GeneticCode geneticCode = new GeneticCode("StandardCode"); // TODO Ugly !! file hardcoded.!
	
	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		OptionsPane comp = new OptionsPane();
		comp.setOpaque(true);
		frame.setContentPane(comp);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
	}
	
	public OptionsPane() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			
			// SET LAYOUT FORMAT
			GridBagLayout thisLayout = new GridBagLayout();
			GridBagConstraints c = new GridBagConstraints();
			
			this.setPreferredSize(new java.awt.Dimension(640, 500));
			thisLayout.rowWeights = new double[] {1, 0, 0, 0,0,0};
			thisLayout.rowHeights = new int[] {250, 50, 50, 50 ,50 ,50};
			thisLayout.columnWeights = new double[] {1, 0, 0};
			thisLayout.columnWidths = new int[] {250, 100, 100};
			this.setLayout(thisLayout);
			
			
			// ALIGNMENT EXPLORER  
			Alignment alin1 = new Alignment();
						
			FastaMultipleReader mfr = new FastaMultipleReader();
			
			List<Pair<String,String>> l = mfr.readFile("C:\\Javier\\Informatica\\Proyectos\\FASDPD\\JavaWorkspace\\FAS-DPD\\example\\Cyto_c_ox.fas");
			
			if (l!=null) {
				for (Pair<String, String> pair : l) {
					alin1.addSequence(new DNASeq( pair.getSecond(),pair.getFirst()));
				}
			} else {
				return;
			}
			
			// TextArea for view results // Other Pane
			
			ResultViewer rv = new ResultViewer();
			
			// Spinner for strand selection
			
			SpinnerListModel strandsmodel = new SpinnerListModel(new String[] {"forward","reverse","both"});
			JSpinner strand = new JSpinner(strandsmodel);
			JPanel strandPanel = new JPanel();
			strandPanel.add(new JLabel("Strand:"));
			strandPanel.add(strand);
			strandPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			// Text Field and label for Quantity selection
			JPanel quantityPanel = new JPanel();
			JTextField qt = new JTextField("10");
			
			quantityPanel.add(new JLabel("Quantity:"));
			quantityPanel.add(qt);
			quantityPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			// 2 Text Area and 2 label for Size selection
			JTextField sf = new JTextField("20");
			JTextField st = new JTextField("25");
			JPanel sizePanel = new JPanel();
			sizePanel.add(new JLabel("Size:"));
			sizePanel.add(sf);
			sizePanel.add(new JLabel("To:"));
			sizePanel.add(st);
			sizePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			// 2 Text Area and 2 label for range selection			
			JTextField rf = new JTextField("0");
			JTextField rt = new JTextField("-1");
			JPanel rangePanel = new JPanel();
			rangePanel.add(new JLabel("Range:"));
			rangePanel.add(rf);
			rangePanel.add(new JLabel("To:"));
			rangePanel.add(rt);
			rangePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

			// Spinner for TME selection
			
			SpinnerModel tmemodel = new SpinnerListModel(new String[] {"Santalucia 1998", "Simple Tm"});
			JSpinner tmeSpinner = new JSpinner(tmemodel);
			JPanel tmePanel = new JPanel();
			tmePanel.add(new JLabel("Tm:"));
			tmePanel.add(tmeSpinner);
			tmePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

			// Text Field and label for ny
			JTextField nyt = new JTextField("1");
			JPanel nyPanel = new JPanel();
			nyPanel.add(new JLabel("Ny:"));
			nyPanel.add(nyt);
			
			// Text Field and label for nx
			JTextField nxt = new JTextField("1");
			JPanel nxPanel = new JPanel();
			nxPanel.add(new JLabel("Nx:"));
			nxPanel.add(nxt);
			
			// Text Field and label for aP
			JTextField apt = new JTextField("0");
			JPanel apPanel = new JPanel();
			apPanel.add(new JLabel("Ap:"));
			apPanel.add(apt);
			apPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			// Button for open filter selection pane
			
			JButton jbFilters = new JButton("Filters");
			
			// Button for search

			JButton jbSearch = new JButton("do Search");

			rv.setOpaque(true);
			c.fill = GridBagConstraints.BOTH;
			c.gridx = 0;
			c.gridy = 1;
			c.gridheight = 5;
			c.anchor = GridBagConstraints.CENTER;
			this.add(rv,c);

			c.gridheight = 1;
			c.fill = GridBagConstraints.BOTH;
			c.gridx = 2;
			c.gridy = 4;
			c.anchor = GridBagConstraints.CENTER;
			this.add(jbFilters,c);
			
			c.fill = GridBagConstraints.BOTH;
			c.gridx = 2;
			c.gridy = 5;
			c.anchor = GridBagConstraints.CENTER;
			this.add(jbSearch,c);
			
			c.fill = GridBagConstraints.BOTH;
			c.gridx = 2;
			c.gridy = 1;
			c.anchor = GridBagConstraints.WEST;
			this.add(nyPanel,c);

			c.fill = GridBagConstraints.BOTH;
			c.gridx = 2;
			c.gridy = 2;
			c.anchor = GridBagConstraints.WEST;
			this.add(nxPanel,c);

			c.fill = GridBagConstraints.BOTH;
			c.gridx = 2;
			c.gridy = 3;
			c.anchor = GridBagConstraints.WEST;
			this.add(apPanel,c);

			
			c.fill = GridBagConstraints.BOTH;
			c.gridx = 1;
			c.gridy = 4;
			c.anchor = GridBagConstraints.WEST;
			this.add(tmePanel,c);
			
			c.fill = GridBagConstraints.BOTH;
			c.gridx = 1;
			c.gridy = 1;
			c.anchor = GridBagConstraints.WEST;
			this.add(rangePanel,c);

			c.gridwidth =1 ;
			c.fill = GridBagConstraints.BOTH;
			c.gridx = 1;
			c.gridy = 3;
			c.anchor = GridBagConstraints.WEST;
			this.add(quantityPanel,c);

			
			c.fill = GridBagConstraints.BOTH;
			c.gridx = 1;
			c.gridy = 5;
			c.anchor = GridBagConstraints.WEST;
			this.add(strandPanel,c);

			
			c.fill = GridBagConstraints.BOTH;
			c.gridx = 1;
			c.gridy = 2;
			c.anchor = GridBagConstraints.WEST;
			this.add(sizePanel,c);

			
			c.gridheight = 1;
			c.gridwidth = 4;
			c.fill = GridBagConstraints.BOTH;
			c.gridx = 0;
			c.gridy = 0;
			c.anchor = GridBagConstraints.CENTER;
			
			AlignmentExplorer ae = new AlignmentExplorer(alin1, geneticCode); 
			this.add(ae,c);
			

			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
