package fasdpd.UI.v1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import sequences.alignment.Alignment;
import sequences.dna.DNASeq;
import sequences.protein.ProtSeq;
import degeneration.GeneticCode;

import fasdpd.FASDPD;
import fasdpd.SearchParameter;
import fastaIO.FastaFilter;
import fastaIO.FastaMultipleReader;
import fastaIO.Pair;

public class MainFASDPD extends javax.swing.JFrame {

	private static final long serialVersionUID = -3916944172322638197L;
	private FASDPD control;
	private SearchParameter searchParameter;
	private OptionsPane op;
	private List<SelectFileListener> selectFileListeners;
	private File selectedFile;
	private boolean isDNA = true;
	private PropertyChangeSupport dna = new PropertyChangeSupport(this);

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainFASDPD inst = new MainFASDPD();
				inst.setControl(new FASDPD());
				inst.setSearchParameter(new SearchParameter());
				inst.setLocationRelativeTo(null);
				inst.setTitle("FAS - DPD main window");
				inst.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				inst.createGUI();
				inst.setVisible(true);
				inst.pack();
			}
		});
	}

	public MainFASDPD() {
		super();
		selectFileListeners = new ArrayList<SelectFileListener>();
		this.addSelectFileListener(
			new SelectFileListener() {
				@Override
				public void afterSelectFile(File selected, boolean isDNA) {
					getSearchParameter()
						.setInfile(selected.getAbsolutePath())
						.setDNA(isDNA);
					op.updateAlignment(getAlignment());
				}
			}
		);
	}

	private void createGUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			loadOptionsPane();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Alignment getAlignment() {
		Alignment aln = new Alignment();
		FastaMultipleReader mfr = new FastaMultipleReader();
		List<Pair<String, String>> seqPairs = null;
		try {
			Optional<String> infileOpt = this.searchParameter.getInfile();
			if (infileOpt.isPresent()) {
				seqPairs = mfr.readFile(infileOpt.get());
			} else {
				seqPairs = new ArrayList<Pair<String,String>>();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (seqPairs != null) {
			for (Pair<String, String> pair : seqPairs) {
				if (this.getSearchParameter().isDNA()) {
					aln.addSequence(new DNASeq(pair.getSecond(), pair.getFirst()));
				} else {
					aln.addSequence(new ProtSeq(pair.getSecond(), pair.getFirst()));
				}
			}
		}
		return aln;
	}

	public void addSelectFileListener(SelectFileListener listener) {
		selectFileListeners.add(listener);
	}

	private void notifySelectFileListerners() {
		for (SelectFileListener l : selectFileListeners) {
			l.afterSelectFile(selectedFile, isDNA);
		}
	}

	protected void loadOptionsPane() {
		GeneticCode gc = new GeneticCode("StandardCode");
		this.searchParameter.setGCfile("StandardCode");
		op = new OptionsPane(getAlignment(), gc, MainFASDPD.this);
		op.setOpaque(true);
		this.getContentPane().removeAll();
		this.getContentPane().setBackground(Color.WHITE);
		BorderLayout bl = new BorderLayout();
		bl.setHgap(10);
		bl.setVgap(10);
		this.setLayout(bl);
		JToolBar tb = createToolbar();
		tb.setBackground(Color.WHITE);
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
		final JButton b3 = new JButton("DNA");
		JButton b4 = new JButton("Parameters");
		JButton b5 = new JButton("Search");
		dna.addPropertyChangeListener(
			new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if (evt.getPropertyName().equals("isdna")) {
						boolean v = Boolean.parseBoolean((String) evt.getNewValue());
						String t = v ? "DNA" : "Protein";
						b3.setText(t);
					}
				}
			}
		);

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
		b1.addActionListener(new jbOpenFileAction());
		b3.addActionListener(new toggleDnaAndProteinAction());
		t.setFloatable(false);
		return t;
	}

	public void setIsDna(boolean value) {
		if (this.isDNA!=value) {
			this.isDNA = value;
			this.dna.firePropertyChange(
				"isdna",
				String.valueOf(!value),
				String.valueOf(value)
			);
		}
	}

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

	private class toggleDnaAndProteinAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			setIsDna(!isDNA);
		}
	}

	private class jbOpenFileAction implements ActionListener {
		@Override public void actionPerformed(ActionEvent e) {
			JFileChooser fc = createFileChooser();
			int dialogResponse = fc.showOpenDialog(MainFASDPD.this);
			processResponse(dialogResponse, fc);
		}

		private void processResponse(int response, JFileChooser fc) {
			if (response == JFileChooser.APPROVE_OPTION) {
				selectedFile = fc.getSelectedFile();
				notifySelectFileListerners();
			}
		}

		private JFileChooser createFileChooser() {
			FileFilter fastaFilter = getFileFilter();
			JFileChooser iFile = new JFileChooser(System.getProperty("user.dir"));
				// Open a Dialog Box for to load a fasta file.
			iFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
			iFile.setMultiSelectionEnabled(false);
			iFile.setDialogTitle("Select Fasta Alignment");
			iFile.setDialogType(JFileChooser.OPEN_DIALOG);
			iFile.setCurrentDirectory(new java.io.File( "." ));
			iFile.setFileFilter(fastaFilter);
			return iFile;
		}
	}

	private FileFilter getFileFilter() {
		return new FileFilter() {
			@Override public String getDescription() {
				return "Fasta files";
			}
			@Override public boolean accept(File f) {
				return f.isDirectory() || (new FastaFilter()).accept(f);
			}
		};
	}

	interface SelectFileListener {
		void afterSelectFile(File selected, boolean isDNA);
	}
}
