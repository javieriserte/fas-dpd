package fasdpd.UI.v1;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

import fasdpd.SearchParameter;
import fastaIO.FastaFilter;

public class OpenFilePane extends javax.swing.JPanel {

	private static final long serialVersionUID = -6739781310056008614L;
	private JButton jBopen;
	private ButtonGroup dnaorprotein;
	private JRadioButton dna;
	private JRadioButton protein;
	private JButton jbContinue;
	private File selectedFile;
	private JLabel jlSelectedFileName;
	private MainFASDPD control;
	
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new OpenFilePane(null));
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	//  CONSTRUCTOR
	public OpenFilePane(MainFASDPD mainFASDPD) {
		super();
		this.setControl(mainFASDPD);
		this.selectedFile = null;
		createGUI();
	}

	// Private Methods

	private void createGUI() {
	
		GridBagLayout thisLayout = new GridBagLayout();
		thisLayout.columnWeights = new double[] {1, 0,0,0,1}; 
		thisLayout.columnWidths = new int[] {50, 100, 10,100,50}; // total width of panel is 310
		thisLayout.rowWeights = new double[] {1, 0, 0, 0, 0, 1};
		thisLayout.rowHeights = new int[] {50, 30, 30, 20, 20, 50}; // total width of panel is 200
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(thisLayout);
			// Layout Settings
		
		jBopen = new JButton();
		jBopen.setText("<HTML><CENTER>Open Alignment File</CENTER></html>");
		jBopen.setMargin(new Insets(2, 2,2,2));
		jBopen.setPreferredSize(new Dimension(100, 60));
		jBopen.setHorizontalAlignment(SwingConstants.CENTER );
		jBopen.addActionListener(new jbOpenFileAction());
	
		c.gridheight = 2;
		c.fill = GridBagConstraints.HORIZONTAL ;
		c.gridx = 1;
		c.gridy = 1;
		this.add(jBopen,c);
			// Open File Button settings

		jbContinue = new JButton();
		jbContinue.addActionListener(new jbConAction());
		jbContinue.setText("<HTML><CENTER>Continue</CENTER></html>");
		jbContinue.setMargin(new Insets(2, 2,2,2));
		jbContinue.setPreferredSize(new Dimension(100, 20));
		jbContinue.setHorizontalAlignment(SwingConstants.CENTER );

		c.gridheight = 1;
		c.gridwidth = 3;
		c.fill = GridBagConstraints.HORIZONTAL ;
		c.gridx = 1;
		c.gridy = 4;
		this.add(jbContinue,c);
			// Continue Button Settings
		
		jlSelectedFileName = new JLabel(this.getSelectedFileName());
		jlSelectedFileName.setToolTipText("Selected File Name");
		jlSelectedFileName.setPreferredSize(new Dimension(100,20));
		c.gridwidth = 3;
		c.fill = GridBagConstraints.HORIZONTAL ;
		c.gridx = 1;
		c.gridy = 3;
		this.add(jlSelectedFileName,c);
			// File Name Label Button Settings
		
		
		dna = new JRadioButton();
		dna.setText("from DNA ");
		dna.setMargin(new Insets(2, 2,2,2));
		dna.setPreferredSize(new Dimension(100, 30));
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 3;
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(dna,c);
			// Select DNA type Radio Button settings
		
		protein = new JRadioButton();
		protein.setText("from protein ");
		protein.setMargin(new Insets(2, 2,2,2));
		protein.setPreferredSize(new Dimension(100, 30));
		c.fill = GridBagConstraints.NONE;
		c.gridx = 3;
		c.gridy = 2;
		this.add(protein,c);
			// Select protein type Radio Button settings
		
		this.dnaorprotein = new ButtonGroup();
		this.dnaorprotein.add(dna);
		this.dnaorprotein.add(protein);
		dna.setSelected(true);
			// Select protein or DNA buttongroup settings
		
	}

	private String getSelectedFileName() {
		if (this.selectedFile==null) return "No File Selected";
		return this.selectedFile.getName();
	}
	
	protected void updateSelectedFile() {
		String selectedFileName = this.getSelectedFileName();
		this.jlSelectedFileName.setText(selectedFileName);
		this.jlSelectedFileName.setToolTipText("<HTML>Selected File: " + selectedFileName + "</HTML>");
	}

	
	
	// GETTERS AND SETTERS
	
	public void setControl(MainFASDPD control) {
		this.control = control;
	}

	public MainFASDPD getControl() {
		return control;
	}
	
	// LISTENERS AND EXTRA CLASSES
	
	private class jbOpenFileAction implements ActionListener {

		@Override public void actionPerformed(ActionEvent e) {
			FileFilter fastaFilter = new FileFilter() {
				@Override public String getDescription() { return "Fasta files"; }
				@Override public boolean accept(File f) { return f.isDirectory() || (new FastaFilter()).accept(f); }
			}; 
				// Creates a filter to choose only fasta files.
			
			JFileChooser iFile = new JFileChooser();
				// Open a Dialog Box for to load a fasta file.
			
			iFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
			iFile.setMultiSelectionEnabled(false);
			iFile.setDialogTitle("Select Fasta Alignment");
			iFile.setDialogType(JFileChooser.OPEN_DIALOG);
			iFile.setCurrentDirectory(new java.io.File( "." ));
			iFile.setFileFilter(fastaFilter);
			iFile.showOpenDialog(OpenFilePane.this);
				// Set parameters to File Open Dialog Box
			OpenFilePane.this.selectedFile = iFile.getSelectedFile();
			OpenFilePane.this.updateSelectedFile();
			
		}
	}
	
	private class jbConAction implements ActionListener {

		@Override public void actionPerformed(ActionEvent e) {
			
			if (!selectedFile.exists()) {JOptionPane.showMessageDialog(
					                       getParent(),
					                       "Selected File Does Not Exists", 
					                       "Error", 
					                       JOptionPane.ERROR_MESSAGE );
										 return;
			};
				// check that file selected is valid.
			
			SearchParameter searchParameter = OpenFilePane.this.control.getSearchParameter();
			searchParameter.setInfile(OpenFilePane.this.selectedFile.getAbsolutePath());
			searchParameter.setDNA(dna.isSelected());
				// Modify Search Parameter Options
			
			control.loadOptionsPane();
			
		}
	}
}
