/*
 * You may not change or alter any portion of this comment or credits
 * of supporting developers from this source code or any supporting source code
 * which is considered copyrighted (c) material of the original comment or credit authors.
 *
 * THERE IS NO WARRANTY FOR THE PROGRAM, TO THE EXTENT PERMITTED BY APPLICABLE LAW. 
 * EXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT HOLDERS AND/OR OTHER PARTIES 
 * PROVIDE THE PROGRAM “AS IS” WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, 
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
 * 	"Microbiología molecular básica y aplicaciones biotecnológicas"
 * 		(Basic Molecular Microbiology and biotechnological applications)
 * 
 * And is being conducted in:
 * 	LIGBCM: Laboratorio de Ingeniería Genética y Biología Celular y Molecular.
 *		(Laboratory of Genetic Engineering and Cellular and Molecular Biology)
 *	Universidad Nacional de Quilmes.
 *		(National University Of Quilmes)
 *	Quilmes, Buenos Aires, Argentina.
 *
 * The complete team for this project is formed by:
 *	Lic.  Javier A. Iserte.
 *	Lic.  Betina I. Stephan.
 * 	ph.D. Sandra E. Goñi.
 * 	ph.D. P. Daniel Ghiringhelli.
 *	ph.D. Mario E. Lozano.
 *
 * Corresponding Authors:
 *	Javier A. Iserte. <jiserte@unq.edu.ar>
 *	Mario E. Lozano. <mlozano@unq.edu.ar>
 */

package fasdpd.UI.v1;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
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
//import fasdpd.UI.v1.filterCreators.FilterSmallAmpliconSizeCreator;

public class FiltersSelectionPane extends javax.swing.JDialog {

	/////////////////////
	// INSTANCE VARIABLES

	private static final long 		serialVersionUID 				= 224125693439076213L;
	private List<FilterCreator> 	listOfAllFilterCreators      	;
	private JList 					jlFilterCreatorsAdded        	;
	private List<FilterCreator> 	listOfAllFilterCreatorsAdded 	; 
	private List<FilterCreator> 	result                       	;
	private SingleOrPair			filtersToShow					;

	/////////////
	// COMPONENTS
	
	private JButton 				addButton						;
	private JButton 				remButton						;
	private JButton 				setButton						;
	private JButton 				saveButton						;
	private JComboBox 				filterCreatorsToChoose			;
	private DefaultComboBoxModel 	filterModel						;
	private FilterCreator 			currentSelectedFilterCreator	;
	private JScrollPane 			jspOptions						;
	private JPanel 					optionsForFilters				;


	//////////////
	// CONSTRUCTOR
	
	public 							FiltersSelectionPane		(JFrame owner, List<FilterCreator> result, boolean includePair) {
		super(owner,true);
		this.result = result;
		if ( includePair) filtersToShow = SingleOrPair.both ; else
            filtersToShow = SingleOrPair.single;
		this.createGUI();
		this.setSize(new Dimension(300, 450));
		this.setSize(350, 450);
		this.setTitle("adding Filters");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
	}


	private void 					createGUI					() {
		
//		this.setOpaque(true);
		
		this.setPreferredSize(new Dimension(300, 450));
		
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
		listOfAllFilterCreators.add( new FilterBaseRunsCreator());
		listOfAllFilterCreators.add( new FilterCGContentCreator());
		listOfAllFilterCreators.add( new FilterDegeneratedEndCreator());
		listOfAllFilterCreators.add( new FilterHomoDimerCreator());
		listOfAllFilterCreators.add( new FilterHomoDimerFixed3Creator());
		listOfAllFilterCreators.add( new FilterMeltingPointTemperatureCreator());
		listOfAllFilterCreators.add( new FilterPrimerScoreCreator());
		listOfAllFilterCreators.add( new FilterRepeatedEndCreator());

		if (filtersToShow==SingleOrPair.both) {
			listOfAllFilterCreators.add( new FilterAmpliconSizeCreator());
//			listOfAllFilterCreators.add( new FilterSmallAmpliconSizeCreator());
			listOfAllFilterCreators.add( new FilterGCCompatibilityCreator());
			listOfAllFilterCreators.add( new FilterHeteroDimerCreator());
			listOfAllFilterCreators.add( new FilterHeteroDimerFixed3Creator());
			listOfAllFilterCreators.add( new FilterMeltingTempCompatibilityCreator());
//			listOfAllFilterCreators.add( new FilterOverlappingCreator()); // TODO: This must be always present!!!
		}
		filterModel = new DefaultComboBoxModel((Vector<FilterCreator>)listOfAllFilterCreators);

//		newFilters = new JComboBox(lfc.toArray());
		filterCreatorsToChoose = new JComboBox(filterModel);		
		filterCreatorsToChoose.setEditable(false);
		filterCreatorsToChoose.setOpaque(true);
		
				
		filterCreatorsToChoose.addActionListener(new jcbFilterAction());
		
		
		addButton = new JButton("Add");
		addButton.addActionListener(new jbAddAction());
		addButton.setEnabled(false);
		
		remButton = new JButton("Rem");
		remButton.addActionListener(new jbRemAction() );
		remButton.setEnabled(false);
		
		setButton = new JButton("Set");
		setButton.addActionListener(new jbSetAction());
		setButton.setEnabled(false);
		
		saveButton = new JButton("Save");
		saveButton.addActionListener(new jbSaveAction());
		
		
		listOfAllFilterCreatorsAdded = new Vector<FilterCreator>();
		listOfAllFilterCreatorsAdded.addAll(this.result);
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
		
		this.pack();
		
	}
	
	////////////////////
	// AUXILIARY CLASSES
	
	private class 		jcbFilterAction 	implements 		ActionListener {
		@Override public void actionPerformed(ActionEvent e) {
			FiltersSelectionPane.this.currentSelectedFilterCreator = ((FilterCreator) filterCreatorsToChoose.getSelectedItem());
			System.out.println(FiltersSelectionPane.this.currentSelectedFilterCreator);
			FiltersSelectionPane.this.jspOptions.setViewportView(FiltersSelectionPane.this.currentSelectedFilterCreator.getCreationPanel());
			FiltersSelectionPane.this.addButton.setEnabled(true);
			FiltersSelectionPane.this.setButton.setEnabled(false);
			FiltersSelectionPane.this.remButton.setEnabled(false);

		}
	}
	
	private class 		jbAddAction 		implements 		ActionListener {
		@Override public void actionPerformed(ActionEvent e) {
			FiltersSelectionPane.this.listOfAllFilterCreatorsAdded.add(
			FiltersSelectionPane.this.currentSelectedFilterCreator.duplicateWithGUIvalues());

			int index = FiltersSelectionPane.this.jlFilterCreatorsAdded.getSelectedIndex();
			boolean canSelect = (index>=0 && index<listOfAllFilterCreatorsAdded.size());			
			FiltersSelectionPane.this.setButton.setEnabled(canSelect);
			FiltersSelectionPane.this.remButton.setEnabled(canSelect);
			
			FiltersSelectionPane.this.jlFilterCreatorsAdded.updateUI();
//			FiltersSelectionPane.this.addButton.setEnabled(false);
		}
	}
	
	private class 		jbRemAction 		implements 		ActionListener {
		@Override public void actionPerformed(ActionEvent e) {
			int index = FiltersSelectionPane.this.jlFilterCreatorsAdded.getSelectedIndex();
			FiltersSelectionPane.this.listOfAllFilterCreatorsAdded.remove(index);
			boolean canSelect = (index>=0 && index<listOfAllFilterCreatorsAdded.size());			
			FiltersSelectionPane.this.setButton.setEnabled(false);
			FiltersSelectionPane.this.remButton.setEnabled(canSelect);
			FiltersSelectionPane.this.jlFilterCreatorsAdded.updateUI();			
		}
	}
	
	private class 		jbSetAction 		implements 		ActionListener {
		@Override public void actionPerformed(ActionEvent e) {
			
			int index = FiltersSelectionPane.this.jlFilterCreatorsAdded.getSelectedIndex();
			if (index>=0) {
				FiltersSelectionPane.this.listOfAllFilterCreatorsAdded.set(index,FiltersSelectionPane.this.currentSelectedFilterCreator.duplicateWithGUIvalues());
				boolean canSelect = (FiltersSelectionPane.this.jlFilterCreatorsAdded.getSelectedIndex()>=0);
				FiltersSelectionPane.this.setButton.setEnabled(canSelect);
				FiltersSelectionPane.this.remButton.setEnabled(canSelect);
				FiltersSelectionPane.this.jlFilterCreatorsAdded.updateUI();
			}
		}
	}
	
	private class 		jbSaveAction 		implements 		ActionListener {
		@Override public void actionPerformed(ActionEvent e) {
			FiltersSelectionPane.this.result.clear();
			FiltersSelectionPane.this.result.addAll(FiltersSelectionPane.this.listOfAllFilterCreatorsAdded);
			FiltersSelectionPane.this.dispose();
		}
	}
	
	private class 		jlFilterCreatorsAddedSelectionChanged 		implements 		ListSelectionListener {
		@Override public void valueChanged(ListSelectionEvent e) {
			FiltersSelectionPane.this.currentSelectedFilterCreator = ((FilterCreator) jlFilterCreatorsAdded.getSelectedValue());
			System.out.println(FiltersSelectionPane.this.currentSelectedFilterCreator);
			FiltersSelectionPane.this.jspOptions.setViewportView(FiltersSelectionPane.this.currentSelectedFilterCreator.getCreationPanel());
			FiltersSelectionPane.this.setButton.setEnabled(true);
			FiltersSelectionPane.this.remButton.setEnabled(true);
			FiltersSelectionPane.this.addButton.setEnabled(false);
		}
	}
	
	public enum 		SingleOrPair {
		single, both;
	}
	
	//////////////////////////////////
	// EXECUTABLE MAIN. DO NOT USE IT.
	
	public static void 				main						(String[] args) {
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);		
		Vector<FilterCreator> result2 = new Vector<FilterCreator>();
		FiltersSelectionPane comp = new FiltersSelectionPane(frame, result2,true);
		System.out.println(result2);

	}
}