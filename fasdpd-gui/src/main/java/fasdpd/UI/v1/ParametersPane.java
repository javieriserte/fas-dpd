package fasdpd.UI.v1;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fasdpd.PrimerSearchType;
import fasdpd.SearchParameter;
import fasdpd.StrandSearchDirection;


public class ParametersPane extends JPanel {
	private static final long serialVersionUID = -5923205806932143474L;

	private List<ParameterSetChangeListener> listeners;
	private JTextField quantity;
	private JTextField minimumSize;
	private JTextField maximumSize;
	private JTextField rangeFrom;
	private JTextField rangeTo;
	private SpinnerModel tmemodel;
	private JSpinner strand;
	private JTextField nyt;
	private JTextField nxt;
	private JTextField apt;

	private List<CloseActionListener> onCloseListeners;

	public ParametersPane() {
		super();
		listeners = new ArrayList<ParameterSetChangeListener>();
		onCloseListeners = new ArrayList<CloseActionListener>();
		this.createGUI();
	}

	public void addOnCloseListener(CloseActionListener a) {
		onCloseListeners.add(a);
	}

	private void notifyCloseActionListener() {
		for (CloseActionListener a: onCloseListeners) {
			a.executeOnClose();
		}
	}

	public void addParameterSetChangeListener(
			ParameterSetChangeListener listener
			){
		this.listeners.add(listener);
	};

	private void notifyParamentersSetChangeListeners(SearchParameter sp) {
		for (ParameterSetChangeListener l : listeners) {
			l.stateChanged(sp);
		}
	}

	public void updatePrimerSearchType(PrimerSearchType s) {
		this.strand.setEnabled(s.equals(PrimerSearchType.SingleEnd));
	}

	public void populateParameterValues(SearchParameter s) {
		pupulateStrandParameter(s);
		populateTmParameter(s);
		populateNumericParameters(s);
	}

	private void populateNumericParameters(SearchParameter s) {
		quantity.setText(String.valueOf(s.getQuantity()));
		minimumSize.setText(String.valueOf(s.getLenMin()));
		maximumSize.setText(String.valueOf(s.getLenMax()));
		rangeFrom.setText(String.valueOf(s.getStartPoint()));
		rangeTo.setText(String.valueOf(s.getEndPoint()));
		nyt.setText(String.valueOf(s.getNy()));
		nxt.setText(String.valueOf(s.getNx()));
		apt.setText(String.valueOf(s.getpA()));
	}

	private void populateTmParameter(SearchParameter s) {
		if (s.isUseSantaLuciaToEstimateTm()) {
			tmemodel.setValue("Santalucia 1998");
		} else {
			tmemodel.setValue("Simple Tm");
		}
	}

	private void pupulateStrandParameter(SearchParameter s) {
		Set<StrandSearchDirection> st = s.getStrands();
		if (st.isEmpty()) {
			strand.setValue("forward");
		}
		else if (st.size()==2 ) {
			strand.setValue("both");
		}
		else if (st.contains(StrandSearchDirection.Forward)) {
			strand.setValue("forward");
		} else {
			strand.setValue("reverse");
		}
	}


	private void createGUI() {
		try {
			// SET LAYOUT FORMAT
			GridBagLayout thisLayout = new GridBagLayout();
			GridBagConstraints c = new GridBagConstraints();
			thisLayout.rowWeights = new double[] { 0, 0, 0, 0, 0, 0, 0, 1 };
			thisLayout.rowHeights = new int[] { 75, 35, 35, 35, 75, 35, 35, 0 };
			thisLayout.columnWeights = new double[] { 0, 0, 0, 1 };
			thisLayout.columnWidths = new int[] { 40, 100, 100, 0 };
			this.setLayout(thisLayout);
			this.setBackground(Color.WHITE);

			Font commonFont = new Font("DejaVu Sans", Font.PLAIN, 15);

			// Spinner for strand selection
			SpinnerListModel strandsmodel = new SpinnerListModel(
				new String[] { "forward", "reverse", "both" });
			strand = new JSpinner(strandsmodel);
			ParameterChangeListener listener = new ParameterChangeListener();
			strand.addChangeListener(listener);
			strand.setFont(commonFont);
			JPanel strandPanel = new JPanel();
			JLabel strandLabel = new JLabel("Strand:");
			strandLabel.setFont(commonFont);

			strandPanel.add(strandLabel);
			strandPanel.add(strand);
			strandPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			strandPanel.setBackground(Color.WHITE);
			// if (mainframe.getSearchParameter().isSearchPair()) {
			// strand.setEnabled(false);
			// strand.setValue(strandsmodel.getList().get(2));
			// }

			// Text Field and label for Quantity selection
			JPanel quantityPanel = new JPanel();
			quantity = new JTextField("10");
			quantity.setFont(commonFont);
			quantity.setColumns(2);
			quantity.setBackground(Color.WHITE);
			quantity.addCaretListener(listener);

			JLabel quantityLabel = new JLabel("Quantity:");
			quantityLabel.setFont(commonFont);
			quantityPanel.add(quantityLabel);
			quantityPanel.add(quantity);
			quantityPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			quantityPanel.setBackground(Color.WHITE);

			// 2 Text Area and 2 label for Size selection
			// JTextField minimumSize = new JTextField("20");
			minimumSize = new JTextField("20");
			minimumSize.setColumns(2);
			minimumSize.setFont(commonFont);
			minimumSize.setBackground(Color.WHITE);
			minimumSize.addCaretListener(listener);
			// JTextField maximumSize = new JTextField("25");
			maximumSize = new JTextField("25");
			maximumSize.setColumns(2);
			maximumSize.setBackground(Color.WHITE);
			maximumSize.setFont(commonFont);
			maximumSize.addCaretListener(listener);

			JPanel sizePanel = new JPanel();
			JLabel minimumSizeLabel = new JLabel("Size:");
			minimumSizeLabel.setFont(commonFont);
			sizePanel.add(minimumSizeLabel);
			sizePanel.add(minimumSize);
			JLabel maximumSizeLabel = new JLabel("To:");
			maximumSizeLabel.setFont(commonFont);
			sizePanel.add(maximumSizeLabel);
			sizePanel.add(maximumSize);
			sizePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			sizePanel.setBackground(Color.WHITE);

			// 2 Text Area and 2 label for range selection
			rangeFrom = new JTextField("1");
			rangeFrom.setToolTipText(
				"The sequence start in position 1, not 0"
			);
			rangeFrom.setFont(commonFont);
			rangeFrom.addCaretListener(listener);

			// JTextField rangeFrom = new JTextField("0");
			rangeFrom.setColumns(2);
			rangeFrom.setBackground(Color.WHITE);
			rangeTo = new JTextField("-1");
			rangeTo.addCaretListener(listener);
			// JTextField rangeTo = new JTextField("-1");
			rangeTo.setColumns(2);
			rangeTo.setBackground(Color.WHITE);
			rangeTo.setFont(commonFont);
			JPanel rangePanel = new JPanel();
			JLabel rangeFromLabel = new JLabel("Range:");
			rangeFromLabel.setFont(commonFont);
			rangePanel.add(rangeFromLabel);
			rangePanel.add(rangeFrom);
			JLabel rangeToLabel = new JLabel("To:");
			rangeToLabel.setFont(commonFont);
			rangePanel.add(rangeToLabel);
			rangePanel.add(rangeTo);
			rangePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			rangePanel.setBackground(Color.WHITE);
			// Spinner for TME selection

			tmemodel = new SpinnerListModel(
				new String[] {"Santalucia 1998", "Simple Tm" });
			// SpinnerModel tmemodel = new SpinnerListModel(new String[] {"Santalucia
			// 1998", "Simple Tm"});
			JSpinner tmeSpinner = new JSpinner(tmemodel);
			tmeSpinner.addChangeListener(listener);
			tmeSpinner.setFont(commonFont);
			JPanel tmePanel = new JPanel();
			JLabel tmLabel = new JLabel("Tm:");
			tmLabel.setFont(commonFont);
			tmePanel.add(tmLabel);
			tmePanel.add(tmeSpinner);
			tmePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			tmePanel.setBackground(Color.WHITE);

			// Text Field and label for ny
			nyt = new JTextField("1");
			nyt.setFont(commonFont);
			// JTextField nyt = new JTextField("1");
			nyt.setColumns(2);
			nyt.addCaretListener(listener);
			JPanel nyPanel = new JPanel();
			JLabel nyLabel = new JLabel("Ny:");
			nyLabel.setFont(commonFont);
			nyPanel.add(nyLabel);
			nyPanel.add(nyt);
			nyPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			nyPanel.setBackground(Color.WHITE);

			// Text Field and label for nx
			nxt = new JTextField("1");
			nxt.setFont(commonFont);
			// JTextField nxt = new JTextField("1");
			nxt.setColumns(2);
			nxt.addCaretListener(listener);
			JPanel nxPanel = new JPanel();
			JLabel nxLabel = new JLabel("Nx:");
			nxLabel.setFont(commonFont);
			nxPanel.add(nxLabel);
			nxPanel.add(nxt);
			nxPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			nxPanel.setBackground(Color.WHITE);

			// Text Field and label for aP
			apt = new JTextField("0");
			apt.setFont(commonFont);
			apt.setColumns(2);
			apt.addCaretListener(listener);
			JPanel apPanel = new JPanel();
			JLabel apLabel = new JLabel("Ap:");
			apLabel.setFont(commonFont);
			apPanel.add(apLabel);
			apPanel.add(apt);
			apPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			apPanel.setBackground(Color.WHITE);

			c.fill = GridBagConstraints.BOTH;
			c.anchor = GridBagConstraints.WEST;

			JLabel title = new JLabel("Search Parameters");
			Font titleFont = title.getFont().deriveFont(Font.BOLD, 20);
			c.gridx = 1;
			c.gridy = 0;
			c.gridwidth = 2;
			title.setFont(titleFont);
			title.setVerticalAlignment(JLabel.CENTER);
			title.setHorizontalAlignment(JLabel.CENTER);
			this.add(title, c);

			c.gridx = 1;
			c.gridy = 4;
			JLabel titleAdv = new JLabel("Advanced Parameters");
			titleAdv.setFont(titleAdv.getFont().deriveFont(Font.BOLD, 20));
			titleAdv.setVerticalAlignment(JLabel.CENTER);
			titleAdv.setHorizontalAlignment(JLabel.CENTER);
			this.add(titleAdv, c);

			c.gridwidth = 1;
			c.gridx = 1;
			c.gridy = 5;
			this.add(nyPanel, c);

			c.gridx = 1;
			c.gridy = 6;
			this.add(nxPanel, c);

			c.gridx = 2;
			c.gridy = 5;
			this.add(apPanel, c);

			c.gridx = 1;
			c.gridy = 3;
			this.add(tmePanel, c);

			c.gridx = 1;
			c.gridy = 1;
			this.add(rangePanel, c);

			c.gridx = 1;
			c.gridy = 2;
			this.add(quantityPanel, c);

			c.gridx = 2;
			c.gridy = 2;
			this.add(strandPanel, c);

			c.gridx = 2;
			c.gridy = 1;
			this.add(sizePanel, c);

			setUpCloseButton();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setUpCloseButton() {
		JButton b = new JButton("Close");
		GridBagConstraints c = new GridBagConstraints();
		b.addActionListener(new CloseAction());
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 2;
		c.gridy = 6;
		this.add(b, c);
	}

	public SearchParameter createSearchParametersFromComponentInput() {
		SearchParameter sp = new SearchParameter();
		sp.setUseSantaLuciaToEstimateTm(
			tmemodel.getValue() == "Santalucia 1998"
		);
		String st = (String) strand.getValue();
		if (st.equals("both")||st.equals("forward")) {
			sp.addStrand(StrandSearchDirection.Forward);
		}
		if (st.equals("both")||st.equals("reverse")) {
			sp.addStrand(StrandSearchDirection.Reveserse);
		}
		sp.setQuantity(Integer.valueOf(quantity.getText()));
		sp.setLenMin(Integer.valueOf(minimumSize.getText()));
		sp.setLenMax(Integer.valueOf(maximumSize.getText()));
		sp.setStartPoint(Integer.valueOf(rangeFrom.getText()));
		sp.setEndPoint(Integer.valueOf(rangeTo.getText()));
		sp.setNx(Float.valueOf(nxt.getText()));
		sp.setNy(Float.valueOf(nyt.getText()));
		sp.setpA(Float.valueOf(apt.getText()));
		return sp;
	}

	private void processInputsAfterChange() {
		if (validateInputs()) {
			SearchParameter sp = createSearchParametersFromComponentInput();
			notifyParamentersSetChangeListeners(sp);
		}
	}

	public static boolean isInteger(String strNum) {
    if (strNum == null) {
        return false;
    }
    try {
        Integer.parseInt(strNum);
    } catch (NumberFormatException nfe) {
        return false;
    }
    return true;
	}

	public static boolean isFloat(String strNum) {
    if (strNum == null) {
        return false;
    }
    try {
        Float.parseFloat(strNum);
    } catch (NumberFormatException nfe) {
        return false;
    }
    return true;
	}


	private boolean validateInputs() {
		return (
			isInteger(quantity.getText()) &&
			isInteger(minimumSize.getText()) &&
			isInteger(maximumSize.getText()) &&
			isInteger(rangeFrom.getText()) &&
			isInteger(rangeTo.getText()) &&
			isFloat(nxt.getText()) &&
			isFloat(nyt.getText()) &&
			isFloat(apt.getText())
		);
	}

	class ParameterChangeListener implements ChangeListener, CaretListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			processInputsAfterChange();
		}

		@Override
		public void caretUpdate(CaretEvent e) {
			processInputsAfterChange();
		}
	}

	class CloseAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			notifyCloseActionListener();
		}
	}

}

interface ParameterSetChangeListener {
	public void stateChanged(SearchParameter sp);
}

interface CloseActionListener {
	public void executeOnClose();
}