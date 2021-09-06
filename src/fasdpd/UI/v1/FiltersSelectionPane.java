package fasdpd.UI.v1;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fasdpd.UI.v1.filterCreators.Filter5vs3StabilityCreator;
import fasdpd.UI.v1.filterCreators.FilterAmpliconSizeCreator;
import fasdpd.UI.v1.filterCreators.FilterBaseRunsCreator;
import fasdpd.UI.v1.filterCreators.FilterCGClampCreator;
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
import fasdpd.UI.v1.filterCreators.FilterPrimerScoreCreator;
import fasdpd.UI.v1.filterCreators.FilterRepeatedEndCreator;
// import fasdpd.UI.v1.filterCreators.FilterSmallAmpliconSizeCreator;

public class FiltersSelectionPane extends javax.swing.JDialog {
	private static final long serialVersionUID = 224125693439076213L;
	private List<FilterCreator> availableFilters;
	private List<FilterCreator> selectedFilters;
	private List<FilterCreator> currentFilters;
	private boolean includePairFilters;
	private List<Runnable> onSaveRunable;

	private JList<FilterCreator> jlSelectedFilters;
	private JButton addButton;
	private JButton addAllButton;
	private JButton remButton;
	private JButton setButton;
	private JButton saveButton;
	private JComboBox<FilterCreator> cbAvailableFilters;
	private DefaultComboBoxModel<FilterCreator> filterModel;
	private FilterCreator currentSelectedFilterCreator;
	private JScrollPane jspFilters;
	private JScrollPane jspOptions;

	// CONSTRUCTOR
	public FiltersSelectionPane(
			JFrame owner,
			List<FilterCreator> currentFilters,
			boolean includePair) {
		super(owner, true);
		this.currentFilters = currentFilters;
		this.includePairFilters = includePair;
		this.selectedFilters = new Vector<FilterCreator>(currentFilters);
		this.onSaveRunable = new ArrayList<Runnable>();
		this.setTitle("Select Primer Filters");
		this.createGUI();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void createAndSetLayout() {
		this.setSize(new Dimension(600, 450));
		this.setPreferredSize(new Dimension(600, 450));
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWeights = new double[] { 1, 1, 1, 1, 1 };
		gbl.columnWidths = new int[] { 50, 50, 50, 50, 50 };
		gbl.rowWeights = new double[] { 0, 0.7, 0.3, 0 };
		gbl.rowHeights = new int[] { 50, 200, 100, 50 };
		this.setLayout(gbl);
	}

	private List<FilterCreator> getAvailableFilters() {
		List<FilterCreator> filters = new Vector<FilterCreator>();
		filters.add(new Filter5vs3StabilityCreator());
		filters.add(new FilterBaseRunsCreator());
		filters.add(new FilterCGContentCreator());
		filters.add(new FilterDegeneratedEndCreator());
		filters.add(new FilterHomoDimerCreator());
		filters.add(new FilterHomoDimerFixed3Creator());
		filters.add(new FilterMeltingPointTemperatureCreator());
		filters.add(new FilterPrimerScoreCreator());
		filters.add(new FilterRepeatedEndCreator());
		filters.add(new FilterCGClampCreator());
		if (this.includePairFilters) {
			filters.add(new FilterAmpliconSizeCreator());
			filters.add(new FilterGCCompatibilityCreator());
			filters.add(new FilterHeteroDimerCreator());
			filters.add(new FilterHeteroDimerFixed3Creator());
			filters.add(new FilterMeltingTempCompatibilityCreator());
			// availableFilters.add( new FilterOverlappingCreator());
			// TODO: This must be always present!!!
		}
		return filters;
	}

	private void createFilterCreatorsToChooseComboBox() {
		availableFilters = this.getAvailableFilters();
		filterModel = new DefaultComboBoxModel<FilterCreator>(
			(Vector<FilterCreator>) availableFilters
		);
		this.cbAvailableFilters = new JComboBox<FilterCreator>(filterModel);
		cbAvailableFilters.setEditable(false);
		cbAvailableFilters.setOpaque(false);
		cbAvailableFilters.setRenderer(new FilterCreatorRendered());
		cbAvailableFilters.addActionListener(new jcbFilterAction());
	}

	public void onSaveFilters(Runnable r) {
		this.onSaveRunable.add(r);
	}

	private void createGUI() {
		// this.setOpaque(true);
		this.createAndSetLayout();
		this.createFilterCreatorsToChooseComboBox();
		this.createAddFilterCreatorButton();
		this.createAddAllFilterCreatorButton();
		this.createDeleteFilterCreatorButton();
		this.createSetFilterCreatorButton();
		this.createSaveFilterCreatorButton();
		this.createSelecteFiltersJList();
		this.createFilterPane();
		this.createOptionsPane();
		this.setComponentsInLayout();
		this.pack();
	}

	private void createFilterPane() {
		this.jspFilters = new JScrollPane();
		jspFilters.setViewportView(jlSelectedFilters);
	}

	private void createOptionsPane() {
		this.jspOptions = new JScrollPane();
		jspOptions.setViewportView(new JPanel());
	}

	private void setComponentsInLayout() {
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 5, 5, 5);
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 5;
		c.gridx = 0;
		c.gridy = 0;
		this.add(cbAvailableFilters, c);
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 0;
		c.gridy = 1;
		this.add(this.jspFilters, c);
		c.gridy = 2;
		this.add(jspOptions, c);
		c.gridwidth = 1;
		c.gridy = 3;
		this.add(addButton, c);
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 3;
		this.add(addAllButton, c);
		c.gridwidth = 1;
		c.gridx = 2;
		this.add(remButton, c);
		c.gridwidth = 1;
		c.gridx = 3;
		this.add(setButton, c);
		c.gridwidth = 1;
		c.gridx = 4;
		this.add(saveButton, c);
	}

	private void createSelecteFiltersJList() {
		jlSelectedFilters = new JList<FilterCreator>();
		jlSelectedFilters
			.setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION
			);
		jlSelectedFilters
			.addListSelectionListener(
				new jlFilterCreatorsAddedSelectionChanged()
			);
		jlSelectedFilters.setModel(
			new DefaultComboBoxModel<FilterCreator>(
				(Vector<FilterCreator>) this.selectedFilters
			)
		);
		jlSelectedFilters.setCellRenderer(new FilterCreatorRendered());
	}

	private void createSaveFilterCreatorButton() {
		saveButton = new JButton("Save");
		saveButton.addActionListener(new jbSaveAction());
	}

	private void createSetFilterCreatorButton() {
		setButton = new JButton("Set");
		setButton.addActionListener(new jbSetAction());
		setButton.setEnabled(false);
	}

	private void createDeleteFilterCreatorButton() {
		remButton = new JButton("Rem");
		remButton.addActionListener(new jbRemAction());
		remButton.setEnabled(false);
	}

	private void createAddFilterCreatorButton() {
		addButton = new JButton("Add");
		addButton.addActionListener(new jbAddAction());
		addButton.setEnabled(false);
	}

	private void createAddAllFilterCreatorButton() {
		addAllButton = new JButton("Add All");
		addAllButton.addActionListener(new jbAddAllAction());
		addAllButton.setEnabled(true);
	}

	// AUXILIARY CLASSES
	private class jcbFilterAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			FiltersSelectionPane.this.currentSelectedFilterCreator =
				(FilterCreator) cbAvailableFilters.getSelectedItem();
			FiltersSelectionPane.this.jspOptions.setViewportView(
				FiltersSelectionPane.this.currentSelectedFilterCreator
					.getCreationPanel());
			FiltersSelectionPane.this.addButton.setEnabled(true);
			FiltersSelectionPane.this.setButton.setEnabled(false);
			FiltersSelectionPane.this.remButton.setEnabled(false);
		}
	}

	private class jbAddAllAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			selectedFilters.clear();
			for (FilterCreator f : availableFilters) {
				selectedFilters.add(f);
			}
			jlSelectedFilters.updateUI();
		}
	}

	private class jbAddAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			FiltersSelectionPane.this.selectedFilters.add(
				FiltersSelectionPane.this.currentSelectedFilterCreator
					.duplicateWithGUIvalues());
			int index = FiltersSelectionPane.this.jlSelectedFilters
				.getSelectedIndex();
			boolean canSelect = (index >= 0
				&& index < selectedFilters.size());
			FiltersSelectionPane.this.setButton.setEnabled(canSelect);
			FiltersSelectionPane.this.remButton.setEnabled(canSelect);
			FiltersSelectionPane.this.jlSelectedFilters.updateUI();
		}
	}

	private class jbRemAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int index = FiltersSelectionPane.this.jlSelectedFilters
				.getSelectedIndex();
			FiltersSelectionPane.this.selectedFilters.remove(index);
			boolean canSelect = (index >= 0
				&& index < selectedFilters.size());
			FiltersSelectionPane.this.setButton.setEnabled(false);
			FiltersSelectionPane.this.remButton.setEnabled(canSelect);
			FiltersSelectionPane.this.jlSelectedFilters.updateUI();
		}
	}

	private class jbSetAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int index = FiltersSelectionPane.this.jlSelectedFilters
				.getSelectedIndex();
			if (index >= 0) {
				FiltersSelectionPane.this.selectedFilters.set(
					index,
					FiltersSelectionPane.this.currentSelectedFilterCreator
						.duplicateWithGUIvalues());
				boolean canSelect = (FiltersSelectionPane.this.jlSelectedFilters
					.getSelectedIndex() >= 0);
				FiltersSelectionPane.this.setButton.setEnabled(canSelect);
				FiltersSelectionPane.this.remButton.setEnabled(canSelect);
				FiltersSelectionPane.this.jlSelectedFilters.updateUI();
			}
		}
	}

	private class jbSaveAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			FiltersSelectionPane.this.currentFilters.clear();
			FiltersSelectionPane.this.currentFilters
				.addAll(FiltersSelectionPane.this.selectedFilters);
			FiltersSelectionPane.this.onSavePerformed();
			FiltersSelectionPane.this.setVisible(false);
		}
	}

	private class jlFilterCreatorsAddedSelectionChanged
		implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			FiltersSelectionPane.this.currentSelectedFilterCreator =
				(FilterCreator) jlSelectedFilters.getSelectedValue();
			FiltersSelectionPane.this.jspOptions.setViewportView(
				FiltersSelectionPane.this.currentSelectedFilterCreator
					.getCreationPanel()
			);
			FiltersSelectionPane.this.setButton.setEnabled(true);
			FiltersSelectionPane.this.remButton.setEnabled(true);
			FiltersSelectionPane.this.addButton.setEnabled(false);
		}
	}

	protected void onSavePerformed() {
		for (Runnable r : this.onSaveRunable) {
			r.run();
		}
	}

	class FilterCreatorRendered extends JLabel implements ListCellRenderer<FilterCreator> {

		@Override
		public Component getListCellRendererComponent(
				JList<? extends FilterCreator> list,
				FilterCreator value,
				int index,
				boolean isSelected,
				boolean cellHasFocus) {
			JLabel l = new JLabel() {
				public Dimension getPreferredSize() {
					return new Dimension(200, 35);
				}
			};
			l.setBorder(new EmptyBorder(0,10,0,0));
			l.setText(value.toString());
			if (isSelected) {
				l.setOpaque(true);
				l.setForeground(Color.BLACK);
				l.setBackground(new Color(87, 165, 255));
			}
			return l;
		}
	}

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Vector<FilterCreator> result2 = new Vector<FilterCreator>();
		@SuppressWarnings("unused")
		FiltersSelectionPane comp = new FiltersSelectionPane(frame, result2, true);
	}

}
