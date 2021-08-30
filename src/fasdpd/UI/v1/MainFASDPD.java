package fasdpd.UI.v1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;

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
		OpenFilePane ofp = new OpenFilePane(this);
		this.setContentPane(ofp);
		ofp.addSelectFileListener(
			new OpenFilePane.SelectFileListener() {
				@Override
				public void afterSelectFile(File selected, boolean isDNA) {
					getSearchParameter()
						.setInfile(selected.getAbsolutePath())
						.setDNA(isDNA);
					loadOptionsPane();
				}
			}
		);
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
		};
		ActionListener x = new ActionListener(){
			@Override public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame();
				JPanel p = new OpenFilePane(MainFASDPD.this);
				frame.add(p);
				frame.setVisible(true);
			}
		};
		b1.addActionListener(x);
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
