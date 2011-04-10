package fasdpd.UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;

import fastaIO.FastaFilter;
import filters.singlePrimer.FilterDegeneratedEnd;
import filters.singlePrimer.FilterRepeatedEnd;
import filters.validator.ValidateForFilterSinglePrimer;
import filters.validator.Validator;

/**
 * Jpanel that contains GUI for selecting the options for the search.
 *
 * @author jiserte
 *
 */
public class SearchOptionsPane extends JPanel {

	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7371206174951188671L;

	// CONSTRUCTOR	
	public SearchOptionsPane() {
		
//		Dimension size = new Dimension(320, 240); 
//		this.setPreferredSize(size);
		Color color = new Color(250, 250, 250);
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));		
		// Set size of primers
		

		
		JPanel setSizePane = new JPanel();
		setSizePane.setLayout(new BoxLayout(setSizePane, BoxLayout.LINE_AXIS));
		
		JLabel labelSetSize = new JLabel("Set desired length of primer:");
		JSpinner spinnerPrimerLength = new JSpinner();

		
		spinnerPrimerLength.setEnabled(true);
		spinnerPrimerLength.setValue(new Integer(20));
		
		spinnerPrimerLength.setPreferredSize(new Dimension(40, 20));
		spinnerPrimerLength.setMaximumSize(new Dimension(40, 20));
		
		
		setSizePane.add(labelSetSize);
		setSizePane.add(Box.createHorizontalGlue());
		setSizePane.add(spinnerPrimerLength);
		
		setSizePane.setAlignmentX(LEFT_ALIGNMENT);
		setSizePane.setBackground(color);
		
		

		// Set init & end position & Is DNA or Protein

		JPanel setFromTO = new JPanel();
		
		setFromTO.setLayout(new BoxLayout(setFromTO, BoxLayout.LINE_AXIS));
		
		JLabel labelSetInit = new JLabel("From: ");

		JLabel labelSetTo = new JLabel("To: ");
		
		JTextField  txtFrom = new JTextField("0");
		
		txtFrom.setPreferredSize(new Dimension(40, 20));
		txtFrom.setMaximumSize(new Dimension(100, 20));
		
		
		
		JTextField  txtTo = new JTextField("-1");
		
		txtTo.setPreferredSize(new Dimension(40, 20));
		txtTo.setMaximumSize(new Dimension(100, 20));
		
		JCheckBox cbisDNA = new JCheckBox("is DNA");
		cbisDNA.setBackground(null);
		
		setFromTO.add(labelSetInit);
		setFromTO.add(Box.createRigidArea(new Dimension(10, 0)));
		setFromTO.add(txtFrom);
		setFromTO.add(Box.createRigidArea(new Dimension(20, 0)));
		setFromTO.add(Box.createHorizontalGlue());
		setFromTO.add(labelSetTo);
		setFromTO.add(Box.createRigidArea(new Dimension(10, 0)));
		setFromTO.add(txtTo);
		setFromTO.add(Box.createRigidArea(new Dimension(20, 0)));
		setFromTO.add(Box.createHorizontalGlue());
		setFromTO.add(cbisDNA);
		
		setFromTO.setBackground(color);
		setFromTO.setAlignmentX(LEFT_ALIGNMENT);
		
		// Set Complemetary & Quantity	
		
		
		JPanel quantityAndComp = new JPanel();
		quantityAndComp.setLayout(new BoxLayout(quantityAndComp, BoxLayout.LINE_AXIS));
		
		JLabel lblQuantity = new JLabel("Nº of Primers:");
		JSpinner spnQuantity = new JSpinner();
		spnQuantity.setValue(new Integer(20));
		
		spnQuantity.setMaximumSize(new Dimension(40,20));
		JLabel lblComplementary = new JLabel("Complementaty Chain:");
		JCheckBox chbComplementary = new JCheckBox();
		
		chbComplementary.setBackground(null);
		
		quantityAndComp.add(lblQuantity);
		quantityAndComp.add(Box.createRigidArea(new Dimension(10, 0)));
		quantityAndComp.add(spnQuantity);
		quantityAndComp.add(Box.createRigidArea(new Dimension(10, 0)));
		quantityAndComp.add(Box.createHorizontalGlue());		
		quantityAndComp.add(lblComplementary);
		quantityAndComp.add(Box.createRigidArea(new Dimension(10, 0)));
		quantityAndComp.add(chbComplementary);
		
		quantityAndComp.setBackground(color);
		quantityAndComp.setAlignmentX(LEFT_ALIGNMENT);

		// Filter Deg End & End Repeated
		JPanel filters = new JPanel();
		filters.setLayout(new BoxLayout(filters, BoxLayout.LINE_AXIS));
		
		
		Vector<Validator> filterListModel= new Vector<Validator>();
		
		filterListModel.add( new ValidateForFilterSinglePrimer(new FilterDegeneratedEnd()));
		filterListModel.add( new ValidateForFilterSinglePrimer(new FilterRepeatedEnd()));
		JList filterList = new JList(filterListModel);

		
		FilterRendered cellRenderer = new FilterRendered();
		
		filterList.setCellRenderer(cellRenderer);
		
		filterList.setBackground(color);
	
		
		
		filters.setBackground(color);          
		filters.setAlignmentX(LEFT_ALIGNMENT); 

		filters.add(filterList);
		
		filters.add(Box.createHorizontalGlue());
		
		// Genetic Code File
		JPanel gcpane = new JPanel();
		gcpane.setLayout(new BoxLayout(gcpane, BoxLayout.LINE_AXIS));
	
		JLabel lblfile = new JLabel( "file:");
		JButton choosegcfile = new JButton("Select Genetic Code");
		
		choosegcfile.addActionListener(new gcButtonPressed());
		
		gcpane.add(lblfile);
		gcpane.add(Box.createHorizontalGlue());
		gcpane.add(choosegcfile);
		
		gcpane.setBackground(color);          
		gcpane.setAlignmentX(LEFT_ALIGNMENT); 
		
		// pA, Nx and Ny parameters
	
		JPanel mathParamPane = new JPanel();
		mathParamPane.setLayout(new BoxLayout(mathParamPane, BoxLayout.LINE_AXIS));
		
		JLabel pA = new JLabel("pA:");
		JTextField txtPA = new JTextField("0");
		JLabel NX = new JLabel("Nx:");
		JTextField txtNX = new JTextField("0");
		JLabel NY = new JLabel("Ny:");
		JTextField txtNY = new JTextField("0");
		
		txtNY.setMaximumSize(new Dimension(100,20));
		txtNX.setMaximumSize(new Dimension(100,20));
		txtPA.setMaximumSize(new Dimension(100,20));
		
		txtNY.setPreferredSize(new Dimension(50,20));
		txtNX.setPreferredSize(new Dimension(50,20));
		txtPA.setPreferredSize(new Dimension(50,20));
		
		mathParamPane.add(pA);
		mathParamPane.add(Box.createRigidArea(new Dimension(5,5)));
		mathParamPane.add(txtPA);
		mathParamPane.add(Box.createRigidArea(new Dimension(5,5)));
		mathParamPane.add(Box.createHorizontalGlue());
		mathParamPane.add(NX);
		mathParamPane.add(Box.createRigidArea(new Dimension(5,5)));
		mathParamPane.add(txtNX);
		mathParamPane.add(Box.createRigidArea(new Dimension(5,5)));
		mathParamPane.add(Box.createHorizontalGlue());
		mathParamPane.add(NY);
		mathParamPane.add(Box.createRigidArea(new Dimension(5,5)));
		mathParamPane.add(txtNY);
		
		mathParamPane.setMaximumSize(new Dimension(10240,25));
		
		mathParamPane.setBackground(color);          
		mathParamPane.setAlignmentX(LEFT_ALIGNMENT); 
		
		
		
		
		// OK & Cancel Buttons
		
		JPanel OKCancel = new JPanel();
		OKCancel.setLayout(new BoxLayout(OKCancel, BoxLayout.LINE_AXIS));
	
		JButton OKButton = new JButton("OK");
		JButton CancelButton = new JButton("Cancel");
	
		
		OKCancel.add(Box.createHorizontalGlue());
		OKCancel.add(OKButton);
		OKCancel.add(Box.createRigidArea(new Dimension(5,5)));
		OKCancel.add(CancelButton);
	
		
		OKCancel.setBackground(color);          
		OKCancel.setAlignmentX(LEFT_ALIGNMENT); 
		
		
		
		// Adding Components
		
		this.add(Box.createVerticalStrut(5));
		this.add(setSizePane);
		this.add(Box.createVerticalStrut(5));
		
		this.add(Box.createVerticalGlue());
		
		this.add(setFromTO);
		this.add(Box.createVerticalStrut(5));

		this.add(Box.createVerticalGlue());
		
		this.add(quantityAndComp);
		this.add(Box.createVerticalStrut(5));
		
		this.add(Box.createVerticalGlue());
		
		this.add(filters);
		this.add(Box.createVerticalStrut(5));
		
		this.add(Box.createVerticalGlue());
		
		this.add(gcpane);
		this.add(Box.createVerticalStrut(5));
		
		this.add(Box.createVerticalGlue());
		
		this.add(mathParamPane);
		this.add(Box.createVerticalStrut(5));
		
		this.add(Box.createVerticalGlue());
		
		this.add(OKCancel);
		this.add(Box.createVerticalStrut(5));

	}
	
	
	public static void main(String[] arg) {
		JFrame newframe = new JFrame();
		JPanel optionpane =new SearchOptionsPane();
		

		
		newframe.add(optionpane);
		newframe.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE );

		
		Dimension maxSize = new Dimension(400,400);
		
//		System.out.println(optionpane.getSize());
		newframe.pack();
//		System.out.println(optionpane.getSize());
		
		Insets inset = newframe.getInsets();
		Dimension size = optionpane.getSize();
		newframe.setMinimumSize(new Dimension(size.width+ inset.left + inset.right, size.height+ inset.top +inset.bottom));
		Dimension minSize = newframe.getMinimumSize();

		newframe.setPreferredSize(maxSize);
		newframe.setMaximumSize(maxSize);
		
		
		newframe.pack();
		
		newframe.setLocationRelativeTo(null);
		newframe.setVisible(true);
	}
	
	// Inner Classes
	
	@SuppressWarnings("serial")
	class FilterRendered extends JCheckBox implements ListCellRenderer {

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {

			this.setText(value.toString());
			this.setSelected(isSelected);
			this.setBackground(null);
			
			
			
			return this;

			
		};
	};
	
	/**
	 * ActionListener to open de Genetic Code File Chooser
	 * 
	 * @author jiserte
	 *
	 */
	class gcButtonPressed implements ActionListener {
		@Override public void actionPerformed(ActionEvent e) {
			JFileChooser iFile = new JFileChooser();
			
//			FileFilter ff = new FileFilter() {
//				@Override public String getDescription() { return "Fasta files"; }
//				@Override public boolean accept(File f) { return f.isDirectory() || (new FastaFilter()).accept(f); }
//			};
//			iFile.setFileFilter(ff);
			
			
			iFile.showOpenDialog(getParent());
		}
	};

}
