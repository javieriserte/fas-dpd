package fasdpd.UI.v1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.File;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import sequences.alignment.Alignment;
import sequences.dna.DNASeq;
import sequences.dna.Primer;
import sequences.protein.ProtSeq;
import degeneration.GeneticCode;
import fasdpd.PrimerSearchType;
import fasdpd.ResourceLoader;
import fasdpd.SearchParameter;
import fasdpd.StrandSearchDirection;
import fasdpd.FASDPDController;
import fasdpd.UI.v1.filterCreators.FilterCreator;
import fastaIO.FastaFilter;
import fastaIO.FastaMultipleReader;
import fastaIO.Pair;

public class MainFASDPD extends javax.swing.JFrame {

	private static final long serialVersionUID = -3916944172322638197L;
	private static final System.Logger LOGGER = System.getLogger(
		MainFASDPD.class.getName()
	);
	private FASDPDController control;
	private SearchParameter searchParameter;
	private OptionsPane op;
	private List<SelectFileListener> selectFileListeners;
	private File selectedFile;

	private boolean isDNA = true;
	private PropertyChangeSupport dna = new PropertyChangeSupport(this);

	private PrimerSearchType searchType = PrimerSearchType.SingleEnd;
	private PropertyChangeSupport searchTypeProp = new PropertyChangeSupport(
		this);
	public ParametersPane params;
	protected ArrayList<FilterCreator> filterCreators;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainFASDPD inst = new MainFASDPD();
				inst.setControl(new FASDPDController());
				inst.setSearchParameter(new SearchParameter());
				inst.setTitle("FAS - DPD");
				inst.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				inst.createGUI();
				inst.setIcon();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				inst.pack();
			}
		});
	}

	public MainFASDPD() {
		super();
		selectFileListeners = new ArrayList<SelectFileListener>();
		filterCreators = new ArrayList<FilterCreator>();
		this.addSelectFileListener(new SelectFileListener() {
			@Override
			public void afterSelectFile(File selected, boolean isDNA) {
				getSearchParameter().setInfile(selected.getAbsolutePath())
					.setDNA(isDNA);
				op.updateAlignment(getAlignment());
			}
		});
	}

	private ImageIcon getIconFromResource(String resourcePath) {
		var iconUrl = ResourceLoader.findResourceUrl(getClass(), resourcePath);
		if (iconUrl.isPresent()) {
			return new ImageIcon(iconUrl.get());
		}
		LOGGER.log(
			System.Logger.Level.WARNING,
			"Missing icon resource: " + resourcePath
		);
		BufferedImage emptyImage = new BufferedImage(
			1,
			1,
			BufferedImage.TYPE_INT_ARGB
		);
		return new ImageIcon(emptyImage);
	}

	private void setIcon() {
		ImageIcon icon = getIconFromResource("fasdpd.png");
	this.setIconImage(icon.getImage());
	}

	public void doSearch() throws IOException {
		FilterValidatorBuilder fb = new FilterValidatorBuilder()
			.add(filterCreators);
		searchParameter.setFilter(fb.getSinglePrimerValidator());
		searchParameter.setFilterpair(fb.getPrimerPairValidator());
		FASDPDController.ResultOfSearch results = null;
		if (searchParameter.isSearchPair()) {
			results = control.doSearch(searchParameter);
			op.setPairData(results.primerPairs);
		} else {
				List<Primer> partialResult = new ArrayList<Primer>();
			for (StrandSearchDirection strand : searchParameter.getStrands()) {
				searchParameter
					.setDirectStrand(strand.equals(StrandSearchDirection.Forward));
				results = control.doSearch(searchParameter);
				if (results.primers != null) {
					partialResult.addAll(results.primers);
				}
				results.primers = partialResult;
			}
			op.setPrimerData(results.primers);
		}
	}

	private void createGUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			setMinimumSize(new Dimension(800, 500));
			setPreferredSize(new Dimension(1200, 700));
			createParametersPane();
			loadOptionsPane();
		} catch (
			ClassNotFoundException
			| InstantiationException
			| IllegalAccessException
			| UnsupportedLookAndFeelException
			| IOException
			| RuntimeException e
		) {
			LOGGER.log(
				System.Logger.Level.ERROR,
				"Failed to initialize GUI.",
				e
			);
			JOptionPane.showMessageDialog(
				this,
				"No se pudo inicializar la interfaz.",
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
		}
	}

	private void updateSearchParams(SearchParameter sp) {
		searchParameter.setEndPoint(sp.getEndPoint());
		searchParameter.setStartPoint(sp.getStartPoint());
		searchParameter.setLenMax(sp.getLenMax());
		searchParameter.setLenMin(sp.getLenMin());
		searchParameter.setNx(sp.getNx());
		searchParameter.setNx(sp.getNx());
		searchParameter.setpA(sp.getpA());
		searchParameter.setQuantity(sp.getQuantity());
		searchParameter
			.setUseSantaLuciaToEstimateTm(sp.isUseSantaLuciaToEstimateTm());
		searchParameter.clearStrands();
		for (StrandSearchDirection s : sp.getStrands()) {
			searchParameter.addStrand(s);
		}
	}

	private void createParametersPane() {
		params = new ParametersPane();
		params.populateParameterValues(searchParameter);
		params.addParameterSetChangeListener(new ParameterSetChangeListener() {
			@Override
			public void stateChanged(SearchParameter sp) {
				updateSearchParams(sp);
			}
		});
		params.addOnCloseListener(new CloseActionListener() {
			@Override
			public void executeOnClose() {
				MainFASDPD.this.remove(params);
				MainFASDPD.this.add(op, BorderLayout.CENTER);
				op.updateUI();
			}
		});
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
				seqPairs = new ArrayList<>();
			}
		} catch (IOException e) {
			LOGGER.log(
				System.Logger.Level.WARNING,
				"Could not read alignment input file.",
				e
			);
		}
		if (seqPairs != null) {
			for (Pair<String, String> pair : seqPairs) {
				if (this.getSearchParameter().isDNA()) {
					aln.addSequence(
						new DNASeq(
							pair.getSecond(),
							pair.getFirst()
						)
					);
				} else {
					aln.addSequence(
						new ProtSeq(
							pair.getSecond(),
							pair.getFirst()
						)
					);
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

	private GeneticCode loadDefaultGeneticCode() throws IOException {
		try {
			var filesystemCodePath = ResourceLoader.resolveReadablePath("StandardCode");
			if (filesystemCodePath.isPresent()) {
				return new GeneticCode(filesystemCodePath.get().toString());
			}

			var classpathCodePath = ResourceLoader.materializeResourceToTempFile(
				MainFASDPD.class,
				"StandardCode",
				"fasdpd-standard-code-",
				".txt"
			);
			if (classpathCodePath.isPresent()) {
				Path tempPath = classpathCodePath.get();
				return new GeneticCode(tempPath.toString());
			}
		} catch (RuntimeException e) {
			LOGGER.log(
				System.Logger.Level.WARNING,
				"Unexpected error loading StandardCode. Falling back to default code.",
				e
			);
		}

		LOGGER.log(
			System.Logger.Level.INFO,
			"StandardCode not available. Using default in-memory genetic code."
		);
		return GeneticCode.standard();
	}

	protected void loadOptionsPane() throws IOException {
		GeneticCode gc = loadDefaultGeneticCode();

		this.searchParameter.setGC(gc);
		op = new OptionsPane(getAlignment(), gc, MainFASDPD.this);
		op.setOpaque(true);
		op.addExportFiltersActionListener(new ExportFiltersActionListener());
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
		l.columnWidths = new int[] { 150 };
		l.columnWeights = new double[] { 1 };
		l.rowHeights = new int[] { 75, 75, 75, 75, 75, 25, 75, 500 };
		l.rowWeights = new double[] { 0, 0, 0, 0, 0, 0, 0, 1 };
		return l;
	}

	private void setTextValueForDnaButton(JButton b) {
		if (isDNA) {
			b.setIcon(getIconFromResource("dna.png"));
			b.setToolTipText("DNA MSA");
		} else {
			b.setIcon(getIconFromResource("protein.png"));
			b.setToolTipText("Protein MSA");
		}
	}

	private void setTextValueForSearchTypeButton(JButton b) {
		if (searchType.equals(PrimerSearchType.SingleEnd)) {
			b.setIcon(getIconFromResource("single-primer.png"));
			b.setToolTipText("Single Primer");
		}
		if (searchType.equals(PrimerSearchType.DoubleEnd)) {
			b.setIcon(getIconFromResource("pair_primer.png"));
			b.setToolTipText("Primer Pair");
		}
	}

	private JButton createOpenMsaButton() {
		JButton b = new JButton();
		b.setToolTipText("Open MSA");
		b.addActionListener(new jbOpenFileAction());
		b.setIcon(getIconFromResource("open.png"));
		return b;
	}

	private JButton createSearchTypeButton() {
		final JButton b = new JButton();
		setTextValueForSearchTypeButton(b);
		searchTypeProp
			.addPropertyChangeListener(
				"search-type",
				new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						setTextValueForSearchTypeButton(b);
					}
				}
			);
		b.addActionListener(new toggleSearchTypeAction());
		return b;
	}

	private JButton createToggleMolTypeButton() {
		final JButton b3 = new JButton();
		setTextValueForDnaButton(b3);
		dna.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("isdna")) {
					setTextValueForDnaButton(b3);
				}
			}
		});
		b3.addActionListener(new toggleDnaAndProteinAction());
		return b3;
	}

	private JButton createSearchParametersButton() {
		final JButton b = new JButton();
		b.setToolTipText("Parameters");
		b.setIcon(getIconFromResource("parameters.png"));
		b.addActionListener(new showParametersAction());
		return b;
	}

	private JButton createPerformSearchButton() {
		JButton b = new JButton();
		b.setToolTipText("Search");
		b.setIcon(getIconFromResource("doSearch.png"));
		b.addActionListener(new jbDoSearchAction());
		return b;
	}

	private JButton createFiltersButton() {
		JButton b = new JButton();
		b.setToolTipText("Filters");
		b.setIcon(getIconFromResource("filters.png"));
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FiltersSelectionPane filterPane = new FiltersSelectionPane(
					MainFASDPD.this,
					filterCreators,
					getSearchParameter().isSearchPair());
				filterPane.dispose();
			}
		});
		return b;
	}

	private JToolBar createToolbar() {
		JToolBar t = new JToolBar(JToolBar.VERTICAL);
		t.setLayout(toolbarLayout());
		JButton[] buttons = new JButton[] {
			createOpenMsaButton(),
			createSearchTypeButton(),
			createToggleMolTypeButton(),
			createSearchParametersButton(),
			createFiltersButton(),
			createPerformSearchButton()
		};
		addToolbarButtons(t, buttons);
		t.setFloatable(false);
		return t;
	}

	private void addToolbarButtons(JToolBar t, JButton[] buttons) {
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		c.ipadx = 5;
		c.ipady = 5;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 0;
		for (JButton b : buttons) {
			b.setMinimumSize(new Dimension(100, 30));
			b.setPreferredSize(new Dimension(100, 100));
			b.setMaximumSize(new Dimension(100, 100));
			b.setMargin(new Insets(10, 10, 10, 10));
			b.setOpaque(false);
			if (c.gridy == 5) {
				c.gridy = 6;
			}
			t.add(b, c);
			c.gridy++;
		}
		;
	}

	public void toggleSearchType() {
		PrimerSearchType old = this.searchType;
		PrimerSearchType novel;
		if (old.equals(PrimerSearchType.SingleEnd)) {
			novel = PrimerSearchType.DoubleEnd;
		} else {
			novel = PrimerSearchType.SingleEnd;
		}
		this.searchType = novel;
		searchParameter.setSearchPair(novel == PrimerSearchType.DoubleEnd);
		searchTypeProp.firePropertyChange("search-type", old, novel);
	}

	public void setIsDna(boolean value) {
		if (this.isDNA != value) {
			this.isDNA = value;
			this.searchParameter.setDNA(isDNA);
			this.op.updateDna(isDNA);
			;
			this.dna.firePropertyChange(
				"isdna",
				String.valueOf(!value),
				String.valueOf(value));
		}
	}

	public void setSearchParameter(SearchParameter searchParameter) {
		this.searchParameter = searchParameter;
	}

	public SearchParameter getSearchParameter() {
		return searchParameter;
	}

	public void setControl(FASDPDController control) {
		this.control = control;
	}

	public FASDPDController getControl() {
		return control;
	}

	private class toggleDnaAndProteinAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			setIsDna(!isDNA);
		}
	}

	private class showParametersAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			MainFASDPD.this.remove(op);
			MainFASDPD.this.add(params, BorderLayout.CENTER);
			params.updateUI();
		}
	}

	private class toggleSearchTypeAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			toggleSearchType();
			params.updatePrimerSearchType(searchType);
		}
	}

	private class jbOpenFileAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
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
			JFileChooser iFile = new JFileChooser(
				System.getProperty("user.dir")
			);
			iFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
			iFile.setMultiSelectionEnabled(false);
			iFile.setDialogTitle("Select Fasta Alignment");
			iFile.setDialogType(JFileChooser.OPEN_DIALOG);
			iFile.setCurrentDirectory(new java.io.File("."));
			iFile.setFileFilter(fastaFilter);
			return iFile;
		}
	}

	private FileFilter getFileFilter() {
		return new FileFilter() {
			@Override
			public String getDescription() {
				return "Fasta files";
			}

			@Override
			public boolean accept(File f) {
				return f.isDirectory() || (new FastaFilter()).accept(f);
			}
		};
	}

	interface SelectFileListener {
		void afterSelectFile(File selected, boolean isDNA);
	}

	private class jbDoSearchAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				doSearch();
			} catch (IOException exc) {
				LOGGER.log(
					System.Logger.Level.ERROR,
					"Search action failed.",
					exc
				);
				JOptionPane.showMessageDialog(
					MainFASDPD.this,
					"No se pudo ejecutar la busqueda: "
						+ exc.getLocalizedMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE
				);
			};
		}
	}

	private class ExportFiltersActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
		JFileChooser fc = createFileChooser();
		int dialogResponse = fc.showOpenDialog(MainFASDPD.this);
		processResponse(fc, dialogResponse);
    }

    private void processResponse(JFileChooser fc, int dialogResponse) {
		if (dialogResponse != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File selected = fc.getSelectedFile();
		try {
			try (BufferedWriter writer = Files.newBufferedWriter(
				selected.toPath(),
				StandardCharsets.UTF_8
			)) {
				for (FilterCreator f: filterCreators) {
					writer.write(String.valueOf(f));
					writer.newLine();
				}
			}
		} catch (IOException e1) {
			LOGGER.log(
				System.Logger.Level.ERROR,
				"Could not export filters to file: " + selected,
				e1
			);
			JOptionPane.showMessageDialog(
				MainFASDPD.this,
				"No se pudieron exportar los filtros.",
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
		}
		}

		private JFileChooser createFileChooser() {
		FileFilter fastaFilter = getFileFilter();
		JFileChooser iFile = new JFileChooser(
			System.getProperty("user.dir")
		);
		iFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
		iFile.setMultiSelectionEnabled(false);
		iFile.setDialogTitle("Save Filter Data");
		iFile.setDialogType(JFileChooser.SAVE_DIALOG);
		iFile.setCurrentDirectory(new java.io.File("."));
		iFile.setFileFilter(fastaFilter);
		return iFile;
		}
	}
}
