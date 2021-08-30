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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
	private FASDPD control;
	private SearchParameter searchParameter;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainFASDPD inst = new MainFASDPD();
				// creates the main instance
				inst.setControl(new FASDPD());
				inst.setSearchParameter(new SearchParameter());
				inst.setLocationRelativeTo(null);
				inst.setTitle("FAS - DPD main window");
				inst.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				inst.setVisible(true);
				inst.pack();
			}
		});
	}

	public MainFASDPD() {
		super();
		createGUI();
	}

	private void createGUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			this.loadMainPane();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadMainPane() {
		this.setContentPane(new MainPane(this));
		Insets cpinsets = getContentPane().getInsets();
		setSize(800, 600);
		Insets frameinsets = this.getInsets();
		this.setMinimumSize(
			new Dimension(
				310 + cpinsets.left + frameinsets.left + cpinsets.right
					+ frameinsets.right,
				160 + cpinsets.top + frameinsets.top + cpinsets.bottom
					+ frameinsets.bottom));
	}

	protected void loadOpenFilePane() {
		JPanel ofp = new OpenFilePane(this);
		this.setContentPane(ofp);
		ofp.updateUI();
		this.pack();
	}

	protected void loadOptionsPane() {
		Alignment alin1 = new Alignment();
		FastaMultipleReader mfr = new FastaMultipleReader();
		List<Pair<String, String>> l = null;
		try {
			l = mfr.readFile(this.searchParameter.getInfile());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		if (l != null) {
			for (Pair<String, String> pair : l) {
				if (this.getSearchParameter().isDNA()) {
					alin1.addSequence(new DNASeq(pair.getSecond(), pair.getFirst()));
				} else {
					alin1.addSequence(new ProtSeq(pair.getSecond(), pair.getFirst()));
				}
			}
		} else {
			return;
		}

		GeneticCode gc = new GeneticCode("StandardCode");
		this.searchParameter.setGCfile("StandardCode");
		OptionsPane op = new OptionsPane(alin1, gc, MainFASDPD.this);
		// TODO Ugly !! file hard-coded.!
		op.setOpaque(true);
		this.getContentPane().removeAll();
		this.getContentPane().setBackground(new Color(255,255,255));
		// this.setContentPane(new JPanel());
		BorderLayout bl = new BorderLayout();
		bl.setHgap(10);
		bl.setVgap(10);
		this.setLayout(bl);
		JToolBar tb = createToolbar();
		tb.setBackground(new Color(255,255,255));
		this.add(tb, BorderLayout.WEST);
		this.add(op, BorderLayout.CENTER);
		op.updateUI();
		tb.updateUI();
		this.pack();
	}

	private GridBagLayout toolbarLayout() {
		GridBagLayout l = new GridBagLayout();
		l.columnWidths = new int[] { 100 };
		l.columnWeights = new double[] { 1 };
		l.rowHeights = new int[] {75, 75, 75, 75, 25, 75, 75};
		l.rowWeights = new double[] {0, 0, 0, 0, 0, 0, 1};
		return l;
	}

	private JToolBar createToolbar() {
		JToolBar t = new JToolBar(JToolBar.VERTICAL);
		t.setLayout(toolbarLayout());
		t.setMinimumSize( new Dimension(100, 100));
		JButton b1 = new JButton("Open MSA");
		JButton b2 = new JButton("Single primers");
		JButton b3 = new JButton("DNA");
		JButton b4 = new JButton("Parameters");
		JButton b5 = new JButton("Search");
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		c.ipadx = 5;
		c.ipady = 5;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 0;
		for (JButton b : new JButton[]{b1, b2, b3, b4, b5}) {
			b.setMinimumSize(new Dimension(100, 100));
			b.setMargin(new Insets(10, 10, 10, 10));
			b.setOpaque(false);
			if (c.gridy==4) {c.gridy=5;}
			t.add(b, c);
			c.gridy++;
		}
		t.setFloatable(false);
		return t;
	}





	// GETTERS AND SETTERS
	public void setSearchParameter(SearchParameter searchParameter) {
		this.searchParameter = searchParameter;
	}

	public SearchParameter getSearchParameter() {
		return searchParameter;
	}

	public void setControl(FASDPD control) {
		this.control = control;
	}

	public FASDPD getControl() {
		return control;
	}
}
