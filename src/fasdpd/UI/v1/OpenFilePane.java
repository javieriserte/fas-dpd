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
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
		jbContinue.setEnabled(false);

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
			
			JFileChooser iFile = new JFileChooser(System.getProperty("user.dir"));
				// Open a Dialog Box for to load a fasta file.

			
			
			iFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
			iFile.setMultiSelectionEnabled(false);
			iFile.setDialogTitle("Select Fasta Alignment");
			iFile.setDialogType(JFileChooser.OPEN_DIALOG);
			iFile.setCurrentDirectory(new java.io.File( "." ));
			iFile.setFileFilter(fastaFilter);
			int dialogResponse = iFile.showOpenDialog(OpenFilePane.this);
				// Set parameters to File Open Dialog Box
			File file = null;
			if (dialogResponse == JFileChooser.APPROVE_OPTION) file = iFile.getSelectedFile();
			OpenFilePane.this.selectedFile = file;
			if (file != null && file.exists()) OpenFilePane.this.jbContinue.setEnabled(true);
			else OpenFilePane.this.jbContinue.setEnabled(false);

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
