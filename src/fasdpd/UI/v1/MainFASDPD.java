/*
 * You may not change or alter any portion of this comment or credits
 * of supporting developers from this source code or any supporting source code
 * which is considered copyrighted (c) material of the original comment or credit authors.
 *
 * THERE IS NO WARRANTY FOR THE PROGRAM, TO THE EXTENT PERMITTED BY APPLICABLE LAW. 
 * EXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT HOLDERS AND/OR OTHER PARTIES 
 * PROVIDE THE PROGRAM �AS IS� WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, 
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS 
 * FOR A PARTICULAR PURPOSE. THE ENTIRE RISK AS TO THE QUALITY AND PERFORMANCE OF THE 
 * PROGRAM IS WITH YOU. SHOULD THE PROGRAM PROVE DEFECTIVE, YOU ASSUME THE COST OF ALL 
 * NECESSARY SERVICING, REPAIR OR CORRECTION.
 
 * IN NO EVENT UNLESS REQUIRED BY APPLICABLE LAW OR AGREED TO IN WRITING WILL ANY COPYRIGHT 
 * HOLDER, OR ANY OTHER PARTY WHO MODIFIES AND/OR CONVEYS THE PROGRAM AS PERMITTED ABOVE, 
 * BE LIABLE TO YOU FOR DAMAGES, INCLUDING ANY GENERAL, SPECIAL, INCIDENTAL OR CONSEQUENTIAL
 * DAMAGES ARISING OUT OF THE USE OR INABILITY TO USE THE PROGRAM (INCLUDING BUT NOT LIMITED 
 * TO LOSS OF DATA OR DATA BEING RENDERED INACCURATE OR LOSSES SUSTAINED BY YOU OR THIRD 
 * PARTIES OR A FAILURE OF THE PROGRAM TO OPERATE WITH ANY OTHER PROGRAMS), EVEN IF SUCH 
 * HOLDER OR OTHER PARTY HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * FAS-DPD project, including algorithms design, software implementation and experimental laboratory work, is being developed as a part of the Research Program:
 * 	"Microbiolog�a molecular b�sica y aplicaciones biotecnol�gicas"
 * 		(Basic Molecular Microbiology and biotechnological applications)
 * 
 * And is being conducted in:
 * 	LIGBCM: Laboratorio de Ingenier�a Gen�tica y Biolog�a Celular y Molecular.
 *		(Laboratory of Genetic Engineering and Cellular and Molecular Biology)
 *	Universidad Nacional de Quilmes.
 *		(National University Of Quilmes)
 *	Quilmes, Buenos Aires, Argentina.
 *
 * The complete team for this project is formed by:
 *	Lic.  Javier A. Iserte.
 *	Lic.  Betina I. Stephan.
 * 	ph.D. Sandra E. Go�i.
 * 	ph.D. P. Daniel Ghiringhelli.
 *	ph.D. Mario E. Lozano.
 *
 * Corresponding Authors:
 *	Javier A. Iserte. <jiserte@unq.edu.ar>
 *	Mario E. Lozano. <mlozano@unq.edu.ar>
 */

package fasdpd.UI.v1;
import java.awt.Dimension;
import java.awt.Insets;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.*;

import sequences.alignment.Alignment;
import sequences.dna.DNASeq;
import sequences.protein.ProtSeq;
import degeneration.GeneticCode;

import fasdpd.FASDPD;
import fasdpd.SearchParameter;
import fastaIO.FastaMultipleReader;
import fastaIO.Pair;

public class MainFASDPD extends javax.swing.JFrame {

	private static final long serialVersionUID = -3916944172322638197L;
	// INSTANCE VARIABLES
	private FASDPD control;
		// is the program itself.
	private SearchParameter searchParameter;
		// the every option for running FASDPD

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
			for (Pair<String, String> pair : l) {
				
				if (this.getSearchParameter().isDNA()) {
					
					alin1.addSequence(new DNASeq( pair.getSecond(),pair.getFirst()));
					
				} else {
					
					alin1.addSequence(new ProtSeq( pair.getSecond(),pair.getFirst()));
					
				}
				
				
			}
		} else { return; }
		
		GeneticCode gc = new GeneticCode("StandardCode");
		this.searchParameter.setGCfile("StandardCode");
		OptionsPane op = new OptionsPane(alin1, gc, MainFASDPD.this);
			// 	TODO Ugly !! file hard-coded.!
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
