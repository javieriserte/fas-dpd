package fasdpd.UI.v1;
import java.awt.Dimension;
import java.awt.Insets;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.*;

import sequences.alignment.Alignment;
import sequences.dna.DNASeq;
import degeneration.GeneticCode;

import fasdpd.FASDPD;
import fasdpd.SearchParameter;
import fastaIO.FastaMultipleReader;
import fastaIO.Pair;

public class MainFASDPD extends javax.swing.JFrame {

	// INSTANCE VARIABLES
	private FASDPD control;
		// is the program itself.
	private SearchParameter searchParameter;
		// the every option for ruuning FASDPD

	// EXECUATABLE MAIN
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainFASDPD inst = new MainFASDPD();
					// creates the main instance
				
				inst.setControl(new FASDPD());
				inst.setSearchParameter(new SearchParameter());
				
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				inst.setTitle("FAS - DPD main window");
				inst.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				inst.pack();
					// set swing properties of MainFASDPD
			}
		});
	}
	
	
	// CONSTRUCTOR
	public MainFASDPD() {
		super();
		createGUI();
	}
	
	// PRIVATE METHODS
	private void 			createGUI					() {
		try {

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	        	// Set System L&F
			this.loadMainPane();
				// Brings the main pane to screen

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void 			loadMainPane				() {
	    this.setContentPane(new MainPane(this));
		Insets cpinsets = getContentPane().getInsets();
		setSize(400, 300);
		Insets frameinsets = this.getInsets();
		this.
		setMinimumSize(new Dimension(310 + cpinsets.left   + frameinsets.left  + 
				                           cpinsets.right  + frameinsets.right  
				                    ,160 + cpinsets.top    + frameinsets.top   +
				                           cpinsets.bottom + frameinsets.bottom));
	}

	protected void 			loadOpenFilePane			() {
		
		JPanel ofp = new OpenFilePane(this);
		this.setContentPane(ofp);
		ofp.updateUI();
		this.pack();
		
	}

	protected void 			loadOptionsPane				() {

		Alignment alin1 = new Alignment();
		
		FastaMultipleReader mfr = new FastaMultipleReader();

		List<Pair<String, String>> l = null;
		try { l = mfr.readFile(this.searchParameter.getInfile());
		} catch (FileNotFoundException e) { e.printStackTrace(); }

		if (l!=null) { 
			for (Pair<String, String> pair : l) alin1.addSequence(new DNASeq( pair.getSecond(),pair.getFirst()));
		} else { return; }
		
		GeneticCode gc = new GeneticCode("StandardCode");
		this.searchParameter.setGCfile("StandardCode");
		OptionsPane op = new OptionsPane(alin1, gc, MainFASDPD.this);
			// 	TODO Ugly !! file hardcoded.!
		op.setOpaque(true);
		
		this.setContentPane(op);
		op.updateUI();
		this.pack();

		
	}

	
	// GETTERS AND SETTERS
	public void 			setSearchParameter		(SearchParameter searchParameter) {
		this.searchParameter = searchParameter;
	}

	public SearchParameter 	getSearchParameter		() {
		return searchParameter;
	}

	public void 			setControl				(FASDPD control) {
		this.control = control;
	}

	public FASDPD 			getControl				() {
		return control;
	}
}
