package fasdpd.UI.v1;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fasdpd.UI.v1.filterCreators.Filter5vs3StabilityCreator;
import fasdpd.UI.v1.filterCreators.FilterAmpliconSizeCreator;
import fasdpd.UI.v1.filterCreators.FilterBaseRunsCreator;
import fasdpd.UI.v1.filterCreators.FilterCGContentCreator;
import fasdpd.UI.v1.filterCreators.FilterCreator;
import fasdpd.UI.v1.filterCreators.FilterDegeneratedEndCreator;
import fasdpd.UI.v1.filterCreators.FilterGCCompatibilityCreator;
import fasdpd.UI.v1.filterCreators.FilterHeteroDimerCreator;
import fasdpd.UI.v1.filterCreators.FilterHeteroDimerFixed3Creator;
import fasdpd.UI.v1.filterCreators.FilterHomoDimerCreator;
import fasdpd.UI.v1.filterCreators.FilterHomoDimerFixed3Creator;
import fasdpd.UI.v1.filterCreators.FilterMeltingPointTemperatureCreator;
import fasdpd.UI.v1.filterCreators.FilterMeltingTempCompatibilityCreator;
import fasdpd.UI.v1.filterCreators.FilterOverlappingCreator;
import fasdpd.UI.v1.filterCreators.FilterPrimerScoreCreator;
import fasdpd.UI.v1.filterCreators.FilterRepeatedEndCreator;

public class FiltersSelectionPane extends javax.swing.JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 224125693439076213L;
	private List<FilterCreator> listOfAllFilterCreators;

	private JList jlFilterCreatorsAdded;
	private List<FilterCreator> listOfAllFilterCreatorsAdded; 
	private List<FilterCreator> result;

	private JButton addButton;
	private JButton remButton;
	private JButton setButton;
	private JButton saveButton;

	
	private JComboBox filterCreatorsToChoose;
	private DefaultComboBoxModel filterModel;
	private FilterCreator currentSelectedFilterCreator;

	private JScrollPane jspOptions;
	private JPanel optionsForFilters;

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		Vector<FilterCreator> result2 = new Vector<FilterCreator>();
		FiltersSelectionPane comp = new FiltersSelectionPane(frame, result2);
		
		System.out.println(result2);
		//comp.setOpaque(true);
//		frame.getContentPane().add(comp);
//		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//		frame.pack();
//		frame.setVisible(true);
	}
	
	
	public FiltersSelectionPane(JFrame owner, List<FilterCreator> result) {
		super(owner,true);
		this.result = result;		
		this.createGUI();
		this.setSize(new Dimension(300, 450));
		this.setSize(350, 450);
		this.setTitle("adding Filters");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}


	private void createGUI() {
		
//		this.setOpaque(true);
		
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWeights = new double[] {1,1,1,1};
		gbl.columnWidths = new int[] {50,50,50,50};
		gbl.rowWeights = new double[] {0,0.7,0.3,0};
		gbl.rowHeights = new int[] {30,200,100,30};
		
		this.setLayout(gbl);
		
		GridBagConstraints c = new GridBagConstraints();
		
		JScrollPane jspFilters = new JScrollPane();
		this.jspOptions = new JScrollPane();
		

		
		listOfAllFilterCreators = new Vector<FilterCreator>();
		
		listOfAllFilterCreators.add( new Filter5vs3StabilityCreator());
		listOfAllFilterCreators.add( new FilterAmpliconSizeCreator());
		listOfAllFilterCreators.add( new FilterBaseRunsCreator());
		listOfAllFilterCreators.add( new FilterCGContentCreator());
		listOfAllFilterCreators.add( new FilterDegeneratedEndCreator());
		listOfAllFilterCreators.add( new FilterGCCompatibilityCreator());
		listOfAllFilterCreators.add( new FilterHeteroDimerCreator());
		listOfAllFilterCreators.add( new FilterHeteroDimerFixed3Creator());
		listOfAllFilterCreators.add( new FilterHomoDimerCreator());
		listOfAllFilterCreators.add( new FilterHomoDimerFixed3Creator());
		listOfAllFilterCreators.add( new FilterMeltingPointTemperatureCreator());
		listOfAllFilterCreators.add( new FilterMeltingTempCompatibilityCreator());
		listOfAllFilterCreators.add( new FilterOverlappingCreator());
		listOfAllFilterCreators.add( new FilterPrimerScoreCreator());
		listOfAllFilterCreators.add( new FilterRepeatedEndCreator());
		
		filterModel = new DefaultComboBoxModel((Vector<FilterCreator>)listOfAllFilterCreators);

//		newFilters = new JComboBox(lfc.toArray());
		filterCreatorsToChoose = new JComboBox(filterModel);		
		filterCreatorsToChoose.setEditable(false);
		filterCreatorsToChoose.setOpaque(true);
		
				
		filterCreatorsToChoose.addActionListener(new jcbFilterAction());
		
		
		addButton = new JButton("Add");
		addButton.addActionListener(new jbAddAction());
		remButton = new JButton("Rem");
		remButton.addActionListener(new jbRemAction() );
		setButton = new JButton("Set");
		setButton.addActionListener(new jbSetAction());
		saveButton = new JButton("Save");
		saveButton.addActionListener(new jbSaveAction());
		
		
		listOfAllFilterCreatorsAdded = new Vector<FilterCreator>();
		jlFilterCreatorsAdded = new JList();
		jlFilterCreatorsAdded.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
		jlFilterCreatorsAdded.addListSelectionListener(new jlFilterCreatorsAddedSelectionChanged());
		
		jlFilterCreatorsAdded.setModel(new DefaultComboBoxModel((Vector<FilterCreator>)this.listOfAllFilterCreatorsAdded));
		jspFilters.setViewportView(jlFilterCreatorsAdded);

		optionsForFilters = new JPanel();
		jspOptions.setViewportView(optionsForFilters);
		
		
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth=4;
		c.gridx=0;
		c.gridy=1;
		this.add(jspFilters,c);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth=4;
		c.gridx=0;
		c.gridy=2;
		this.add(jspOptions,c);

		c.fill = GridBagConstraints.BOTH;
		c.gridwidth=4;
		c.gridx=0;
		c.gridy=0;
		this.add(filterCreatorsToChoose,c);

		
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth=1;
		c.gridx=0;
		c.gridy=3;
		this.add(addButton,c);

		c.fill = GridBagConstraints.BOTH;
		c.gridwidth=1;
		c.gridx=1;
		c.gridy=3;
		this.add(remButton,c);

		c.fill = GridBagConstraints.BOTH;
		c.gridwidth=1;
		c.gridx=2;
		c.gridy=3;
		this.add(setButton,c);

		c.fill = GridBagConstraints.BOTH;
		c.gridwidth=1;
		c.gridx=3;
		c.gridy=3;
		this.add(saveButton,c);
		
	}
	

	
	private class jcbFilterAction implements ActionListener {
		@Override public void actionPerformed(ActionEvent e) {
			FiltersSelectionPane.this.currentSelectedFilterCreator = ((FilterCreator) filterCreatorsToChoose.getSelectedItem());
			System.out.println(FiltersSelectionPane.this.currentSelectedFilterCreator);
			FiltersSelectionPane.this.jspOptions.setViewportView(FiltersSelectionPane.this.currentSelectedFilterCreator.getCreationPanel());
		}
	}
	
	private class jbAddAction implements ActionListener {
		@Override public void actionPerformed(ActionEvent e) {
			FiltersSelectionPane.this.listOfAllFilterCreatorsAdded.add(
			FiltersSelectionPane.this.currentSelectedFilterCreator.duplicateWithGUIvalues());
			FiltersSelectionPane.this.jlFilterCreatorsAdded.updateUI();	
		}
	}
	
	private class jbRemAction implements ActionListener {
		@Override public void actionPerformed(ActionEvent e) {
			int index = FiltersSelectionPane.this.jlFilterCreatorsAdded.getSelectedIndex();
			FiltersSelectionPane.this.listOfAllFilterCreatorsAdded.remove(index);
			FiltersSelectionPane.this.jlFilterCreatorsAdded.updateUI();
		}
	}
	
	private class jbSetAction implements ActionListener {
		@Override public void actionPerformed(ActionEvent e) {
			int index = FiltersSelectionPane.this.jlFilterCreatorsAdded.getSelectedIndex();
			FiltersSelectionPane.this.listOfAllFilterCreatorsAdded.set(index,FiltersSelectionPane.this.currentSelectedFilterCreator.duplicateWithGUIvalues());
			FiltersSelectionPane.this.jlFilterCreatorsAdded.updateUI();
		}
	}
	
	private class jbSaveAction implements ActionListener {
		@Override public void actionPerformed(ActionEvent e) {
			FiltersSelectionPane.this.result.addAll(FiltersSelectionPane.this.listOfAllFilterCreatorsAdded);
			
			FiltersSelectionPane.this.getOwner().dispose();
		}
	}
	
	
	private class jlFilterCreatorsAddedSelectionChanged implements ListSelectionListener {
		@Override public void valueChanged(ListSelectionEvent e) {
			FiltersSelectionPane.this.currentSelectedFilterCreator = ((FilterCreator) jlFilterCreatorsAdded.getSelectedValue());
			System.out.println(FiltersSelectionPane.this.currentSelectedFilterCreator);
			FiltersSelectionPane.this.jspOptions.setViewportView(FiltersSelectionPane.this.currentSelectedFilterCreator.getCreationPanel());
		}
	}
	
	
}