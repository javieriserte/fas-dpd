package fasdpd.UI.v1;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Vector;

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
import sequences.dna.Primer;
import degeneration.GeneticCode;
import fasdpd.FASDPD;
import fasdpd.SearchParameter;
import fasdpd.UI.v1.filterCreators.FilterCreator;
import fastaIO.FastaMultipleReader;
import fastaIO.Pair;
import filters.Filter;
import filters.primerpair.FilterPrimerPair;
import filters.singlePrimer.FilterSinglePrimer;
import filters.validator.ValidateAlways;
import filters.validator.ValidateForFilterPrimerPair;
import filters.validator.ValidateForFilterSinglePrimer;
import filters.validator.Validate_AND;
import filters.validator.Validator;

public class OptionsPane extends JPanel {
	
	/////////////////////
	// INSTANCE VARIABLES
	private static final long 			serialVersionUID = -5923205806932143474L;
	private Alignment 					align;
	private GeneticCode 				geneticCode;
	private Vector<FilterCreator> 		listOfFilterCreators;
	private MainFASDPD	 				mainframe;
	
	/////////////
	// COMPONENTS
	private AlignmentExplorer 			alignmentExplorer;
	private JButton 					jbDoSearch;
	private JButton 					jbOpenFilters;
	private JTextField 					quantity;
	private ResultViewer 				resultViewer;
	private JTextField 					minimumSize ;
	private JTextField 					maximumSize ;
	private JTextField 					rangeFrom ;
	private JTextField 					rangeTo ;
	private SpinnerModel 				tmemodel;
	private JSpinner 					strand;
	private JTextField 					nyt;
	private JTextField 					nxt;
	private JTextField 					apt;
	
	
	///////////////
	// CONSTRUCTORS
	
	public 			OptionsPane			(Alignment align, GeneticCode gc) {
		super();
		this.mainframe = null;
		this.geneticCode = gc;
		this.align = align;
		this.alignmentExplorer = new AlignmentExplorer(this.align,this.geneticCode );
		this.listOfFilterCreators = new Vector<FilterCreator>();
		this.createGUI();
	}
	
	public 			OptionsPane			(Alignment align, GeneticCode gc, MainFASDPD mainframe) {
		super();
		this.mainframe = mainframe;
		this.geneticCode = gc;
		this.align = align;
		this.alignmentExplorer = new AlignmentExplorer(this.align,this.geneticCode );
		this.listOfFilterCreators = new Vector<FilterCreator>();
		this.createGUI();
	}

	
	//////////////////
	// PRIVATE METHODS
	
	private void 	createGUI			() {
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

			
			// TextArea for view results // Other Pane			
			resultViewer = new ResultViewer(this.mainframe, align.lenght());
			resultViewer.setOpaque(true);
			
			
			// Spinner for strand selection
			
			SpinnerListModel strandsmodel = new SpinnerListModel(new String[] {"forward","reverse","both"});
			strand = new JSpinner(strandsmodel);
//			JSpinner strand = new JSpinner(strandsmodel);
			JPanel strandPanel = new JPanel();
			strandPanel.add(new JLabel("Strand:"));
			strandPanel.add(strand);
			strandPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			// Text Field and label for Quantity selection
			JPanel quantityPanel = new JPanel();
//			JTextField quantity = new JTextField("10");
			quantity = new JTextField("10");
			quantity.setColumns(2);			
			
			quantityPanel.add(new JLabel("Quantity:"));
			quantityPanel.add(quantity);
			quantityPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			// 2 Text Area and 2 label for Size selection
//			JTextField minimumSize = new JTextField("20");
			minimumSize = new JTextField("20");
			minimumSize.setColumns(2);			
//			JTextField maximumSize = new JTextField("25");
			maximumSize = new JTextField("25");
			maximumSize.setColumns(2);			
			JPanel sizePanel = new JPanel();
			sizePanel.add(new JLabel("Size:"));
			sizePanel.add(minimumSize);
			sizePanel.add(new JLabel("To:"));
			sizePanel.add(maximumSize);
			sizePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			// 2 Text Area and 2 label for range selection			
			rangeFrom = new JTextField("1");
			rangeFrom.setToolTipText("The sequence start in position 1, not 0");
//			JTextField rangeFrom = new JTextField("0");			
			rangeFrom.setColumns(2);
			rangeTo = new JTextField("-1");
//			JTextField rangeTo = new JTextField("-1");			
			rangeTo.setColumns(2);			
			JPanel rangePanel = new JPanel();
			rangePanel.add(new JLabel("Range:"));
			rangePanel.add(rangeFrom);
			rangePanel.add(new JLabel("To:"));
			rangePanel.add(rangeTo);
			rangePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

			// Spinner for TME selection
			
			tmemodel = new SpinnerListModel(new String[] {"Santalucia 1998", "Simple Tm"});
//			SpinnerModel tmemodel = new SpinnerListModel(new String[] {"Santalucia 1998", "Simple Tm"});			
			JSpinner tmeSpinner = new JSpinner(tmemodel);			
			JPanel tmePanel = new JPanel();
			tmePanel.add(new JLabel("Tm:"));
			tmePanel.add(tmeSpinner);
			tmePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

			// Text Field and label for ny
			nyt = new JTextField("1");
//			JTextField nyt = new JTextField("1");			
			nyt.setColumns(2);
			JPanel nyPanel = new JPanel();
			nyPanel.add(new JLabel("Ny:"));
			nyPanel.add(nyt);
			nyPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			
			// Text Field and label for nx
			nxt = new JTextField("1");
//			JTextField nxt = new JTextField("1");			
			nxt.setColumns(2);			
			JPanel nxPanel = new JPanel();
			nxPanel.add(new JLabel("Nx:"));
			nxPanel.add(nxt);
			nxPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			// Text Field and label for aP
			apt = new JTextField("0");
//			JTextField apt = new JTextField("0");			
			apt.setColumns(2);
			JPanel apPanel = new JPanel();
			apPanel.add(new JLabel("Ap:"));
			apPanel.add(apt);
			apPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

			// Button for open filter selection pane
			jbOpenFilters = new JButton("Filters");
			jbOpenFilters.addActionListener(new jbOpenFiltersAction());
			

			// Button for search
			jbDoSearch = new JButton("do Search");
			jbDoSearch.addActionListener(new jbDoSearchAction());


			// Adding components
			c.fill = GridBagConstraints.BOTH; c.gridx = 0; c.gridy = 1; c.gridheight = 5; c.anchor = GridBagConstraints.CENTER;
			this.add(resultViewer,c);

			c.gridheight = 1; c.fill = GridBagConstraints.BOTH; c.gridx = 2; c.gridy = 4;  c.anchor = GridBagConstraints.CENTER;
			this.add(jbOpenFilters,c);
			
			c.fill = GridBagConstraints.BOTH; c.gridx = 2; c.gridy = 5; c.anchor = GridBagConstraints.CENTER; 
			this.add(jbDoSearch,c);
			
			c.fill = GridBagConstraints.BOTH; c.gridx = 2; c.gridy = 1; c.anchor = GridBagConstraints.WEST; 
			this.add(nyPanel,c);

			c.fill = GridBagConstraints.BOTH; c.gridx = 2; c.gridy = 2; c.anchor = GridBagConstraints.WEST; 
			this.add(nxPanel,c);

			c.fill = GridBagConstraints.BOTH; c.gridx = 2; c.gridy = 3; c.anchor = GridBagConstraints.WEST;
			this.add(apPanel,c);
			
			c.fill = GridBagConstraints.BOTH; c.gridx = 1; c.gridy = 4; c.anchor = GridBagConstraints.WEST;
			this.add(tmePanel,c);
			
			c.fill = GridBagConstraints.BOTH; c.gridx = 1; c.gridy = 1; c.anchor = GridBagConstraints.WEST;
			this.add(rangePanel,c);

			c.gridwidth =1 ; c.fill = GridBagConstraints.BOTH;  c.gridx = 1; c.gridy = 3; c.anchor = GridBagConstraints.WEST;
			this.add(quantityPanel,c);

			c.fill = GridBagConstraints.BOTH; c.gridx = 1; c.gridy = 5; c.anchor = GridBagConstraints.WEST;
			this.add(strandPanel,c);
			
			c.fill = GridBagConstraints.BOTH; c.gridx = 1;  c.gridy = 2; c.anchor = GridBagConstraints.WEST;
			this.add(sizePanel,c);
			
			c.gridheight = 1; c.gridwidth = 4; c.fill = GridBagConstraints.BOTH; c.gridx = 0; c.gridy = 0; c.anchor = GridBagConstraints.CENTER;
			this.add(alignmentExplorer,c);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	////////////////////
	// AUXILIARY CLASSES
	
	private class 		jbOpenFiltersAction 	implements 		ActionListener {

		@Override public void actionPerformed(ActionEvent e) {
			
			if (listOfFilterCreators==null) {
				listOfFilterCreators = new Vector<FilterCreator>();
			}
			
			new FiltersSelectionPane(mainframe, listOfFilterCreators);
//			FiltersSelectionPane comp = new FiltersSelectionPane(mainframe, listOfFilterCreators);			
			System.out.println(listOfFilterCreators);
			
		}
		
	}
	
	private class 		jbDoSearchAction 		implements 		ActionListener {

		@Override public void actionPerformed(ActionEvent e) {

			List<Boolean> loopStrand = new Vector<Boolean>();
			// check if is forward, reverse or both strands
			if (strand.getValue() == "forward" ) {
				// forward
				loopStrand.add(true);
			} else 
			if (strand.getValue() == "reverse" ) {
				// reverse
				loopStrand.add(false);
			} else {
				// both
				loopStrand.add(true);
				loopStrand.add(false);
			}

			
			// check every value
			// ny, nx, ap, rango, tme, strand, sizes, quantity
			
			SearchParameter sp = mainframe.getSearchParameter();
			
			sp.setQuantity(Integer.valueOf(quantity.getText()));
			sp.setLenMin(Integer.valueOf(minimumSize.getText()));	
			sp.setLenMax(Integer.valueOf(maximumSize.getText()));
			sp.setStartPoint(Integer.valueOf(rangeFrom.getText()));
			sp.setEndPoint(Integer.valueOf(rangeTo.getText()));
			
			if (tmemodel.getValue() == "Santalucia 1998") {  // TODO use enumerations instead of strings 
				sp.setUseSantaLuciaToEstimateTm(true);
			} else {
				sp.setUseSantaLuciaToEstimateTm(false);
			}
			
			sp.setNx(Integer.valueOf(nxt.getText()));
			sp.setNy(Integer.valueOf(nyt.getText()));
			sp.setpA(Integer.valueOf(apt.getText()));
			
			// create Filters
			Validator filterSinglePrimers  = new ValidateAlways();
			Validator filterPrimerPairs  = new ValidateAlways();

			List<ValidateForFilterSinglePrimer>    vffsp = new Vector<ValidateForFilterSinglePrimer>(); 
			List<ValidateForFilterPrimerPair>    vffpp = new Vector<ValidateForFilterPrimerPair>();

			for (FilterCreator filterCreator : listOfFilterCreators) {
				
				Filter filter = filterCreator.create();
				if (filter.isSinglePrimerFilter()) {
					vffsp.add(new ValidateForFilterSinglePrimer((FilterSinglePrimer) filter));
				} else {
					vffpp.add(new ValidateForFilterPrimerPair((FilterPrimerPair) filter));
				}
				
			}
			
			for (ValidateForFilterSinglePrimer vr : vffsp) {
				filterSinglePrimers = new Validate_AND(filterSinglePrimers, vr);
			}

			for (ValidateForFilterPrimerPair vr : vffpp) {
				filterPrimerPairs = new Validate_AND(filterPrimerPairs , vr);
			}

			sp.setFilter(filterSinglePrimers);
			sp.setFilterpair(filterPrimerPairs);
			

			
			// make fasdpd work

			FASDPD.ResultOfSearch results = null;
			
			if (sp.isSearchPair()) {
				// if pair search is selected, override strands option
				results = mainframe.getControl().doSearch(sp);
				// post results to table
				OptionsPane.this.resultViewer.setPairdata(results.primerPairs);
				
				
			} else {
				
				
				// if not, loop for strands
				List<Primer> partialResult = new Vector<Primer>();
				
				for (Boolean strandBoolean : loopStrand) {
					sp.setDirectStrand(strandBoolean);
					results = mainframe.getControl().doSearch(sp);
					if (results.primers!=null) {
						partialResult.addAll(results.primers);
					}
					results.primers = partialResult;
				}
				// post results to table				
				OptionsPane.this.resultViewer.setdata(results.primers);
			}
		}
	}

	// EXECUTABLE MAIN. DO NOT USE IT.
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		Alignment alin1 = new Alignment();
				
		FastaMultipleReader mfr = new FastaMultipleReader();

		List<Pair<String, String>> l = null;
		try { l = mfr.readFile("C:\\Javier\\Informatica\\Proyectos\\FASDPD\\JavaWorkspace\\FAS-DPD\\example\\Cyto_c_ox.fas");
		} catch (FileNotFoundException e) { e.printStackTrace(); }

		if (l!=null) { 
			for (Pair<String, String> pair : l) alin1.addSequence(new DNASeq( pair.getSecond(),pair.getFirst()));
		} else { return; }
		
		OptionsPane comp = new OptionsPane(alin1, new GeneticCode("StandardCode"));
			// 	TODO Ugly !! file hardcoded.!
		comp.setOpaque(true);
		frame.setContentPane(comp);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
	}
	
}
