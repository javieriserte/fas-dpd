package fasdpd.UI.v1;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.EventObject;
import java.util.List;
import java.util.Vector;

import javax.swing.CellEditor;
import javax.swing.CellRendererPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.WindowConstants;
import javax.swing.event.CellEditorListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

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

public class FiltersSelectionPane extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 224125693439076213L;
	private List<FilterCreator> lfc;
	private JList jllfc;
	private JList options;
	private JTable optionsT; 
	private JPanel optionsP;
	
	private JButton addButton;
	private JButton remButton;
	private JButton setButton;
	private JButton saveButton;
	
	private JComboBox newFilters;
	
	private DefaultComboBoxModel filterModel;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		FiltersSelectionPane comp = new FiltersSelectionPane();
		comp.setOpaque(true);
		frame.getContentPane().add(comp);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	
	public FiltersSelectionPane() {
		super();
		this.createGUI();

	}


	private void createGUI() {
		
		this.setOpaque(true);
		
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWeights = new double[] {1,1,1,1};
		gbl.columnWidths = new int[] {50,50,50,50};
		gbl.rowWeights = new double[] {0,0.7,0.3,0};
		gbl.rowHeights = new int[] {30,200,100,30};
		
		this.setLayout(gbl);
		
		GridBagConstraints c = new GridBagConstraints();
		
		JScrollPane jspFilters = new JScrollPane();
		JScrollPane jspOptions = new JScrollPane();
		

		
		Vector<FilterCreator> lfc = new Vector<FilterCreator>();
		
		lfc.add( new Filter5vs3StabilityCreator());
		lfc.add( new FilterAmpliconSizeCreator());
		lfc.add( new FilterBaseRunsCreator());
		lfc.add( new FilterCGContentCreator());
		lfc.add( new FilterDegeneratedEndCreator());
		lfc.add( new FilterGCCompatibilityCreator());
		lfc.add( new FilterHeteroDimerCreator());
		lfc.add( new FilterHeteroDimerFixed3Creator());
		lfc.add( new FilterHomoDimerCreator());
		lfc.add( new FilterHomoDimerFixed3Creator());
		lfc.add( new FilterMeltingPointTemperatureCreator());
		lfc.add( new FilterMeltingTempCompatibilityCreator());
		lfc.add( new FilterOverlappingCreator());
		lfc.add( new FilterPrimerScoreCreator());
		lfc.add( new FilterRepeatedEndCreator());
		
		filterModel = new DefaultComboBoxModel(lfc);

		newFilters = new JComboBox(lfc.toArray());
		newFilters.setEditable(false);
		newFilters.setOpaque(true);
		
		newFilters.addActionListener(new jcbFilterAction());
		
		
		addButton = new JButton("Add");
		remButton = new JButton("Rem");
		setButton = new JButton("Set");
		saveButton = new JButton("Save");
		
		jllfc = new JList();
		
		optionsT = new JTable();
		options = new JList();
		optionsP = new JPanel();
//		optionsP.setPreferredSize(new Dimension(500,500));
//		options.setCellRenderer(new jlOptionsRenderer());
		options.enableInputMethods(true);
		
		optionsT.setRowHeight(40);
		
		jspFilters.setViewportView(jllfc);
		jspOptions.setViewportView(optionsP);
		
		
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
		this.add(newFilters,c);

		
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

			String[] parametersComments = ((FilterCreator) newFilters.getSelectedItem()).getParametersComments();
			JPanel optionsPanel = new JPanel();
			GridLayout gl = new GridLayout();
			gl.setColumns(1);
			optionsPanel.setLayout(gl);
			if (parametersComments != null) {

				int len = parametersComments.length;
				gl.setRows(len);
				for(int i=0;i<len;i++) {
					optionsPanel.add(this.option(i));
					System.out.println("A");
				}
			} else {
				gl.setRows(1);
				optionsPanel.add(new JLabel("No config Needed"));
			}
			optionsP.removeAll();
			optionsP.add(optionsPanel); 
//			optionsP.repaint();
			optionsP.updateUI();
			System.out.println("optionsP changed");
			System.out.println(optionsP);
			
		}
		
		private JPanel option(int index) {
			FilterCreator fc = (FilterCreator) newFilters.getSelectedItem();
			String comment;
			String value;
			comment = fc.getParametersComments()[index];
			value = fc.getParametersValues()[index];
			JPanel jl = new JPanel();
			
			jl.add(new JLabel(comment));
			jl.add(new JTextField(value));
			return jl;
		}
		
	}
	

}