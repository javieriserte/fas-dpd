package fasdpd.UI.v1;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import fasdpd.PrimerPair;

import sequences.dna.Primer;

public class ResultViewer extends JPanel {

	// INSTANCE VAIRABLES
	
	private static final long 	serialVersionUID = -5492613920686356173L;
	private JButton 			jbSaveList;
	private JButton 			jbSavePlot;
	private JScrollPane			scPane;
	private ResultTable 		resultTable;
	private MainFASDPD 			mainframe;
	private boolean 			isShowingSinglePrimerData;
	private Object				primerData;
	private int					lenOfAlignment;

	// CONSTRUCTOR
	public 			ResultViewer		(MainFASDPD mainframe, int lenOfAlignment) {
		super();
		this.mainframe = mainframe;
		this.lenOfAlignment = lenOfAlignment;
		this.createGUI();
	}
	
	// PUBLIC INTERFACE
	public void 	setdata				(List<Primer> primers) {
		resultTable.setData(primers);
		this.primerData = primers;
		this.isShowingSinglePrimerData = true;
		this.resultTable.updateUI();
	}
	
	public void 	setPairdata			(List<PrimerPair> primers) {
		resultTable.setPairData(primers);
		this.primerData = primers;		
		this.isShowingSinglePrimerData = false;
		this.resultTable.updateUI();
	}

	// PRIVATE METHODS
	
	private void 	createGUI			() {
		
		GridBagLayout thisLayout = new GridBagLayout();
		thisLayout.columnWeights = new double[] {1,1}; 
		thisLayout.columnWidths = new int[] {100, 100}; 
		thisLayout.rowWeights = new double[] {1, 0};
		thisLayout.rowHeights = new int[] {170, 30}; 
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(thisLayout);
		this.setOpaque(true);
		
		resultTable = new ResultTable(null,null);
		
		resultTable.setOpaque(true);

		scPane = new JScrollPane();
		scPane.setViewportView(resultTable);
		scPane.setOpaque(true);
		
		
		c.fill = GridBagConstraints.BOTH ;
		c.gridwidth =2;
		c.gridx = 0;
		c.gridy = 0;
		this.add(scPane,c);
		c.gridwidth =1;
		
		jbSaveList = new JButton();
		jbSaveList.setText("Save List");
		jbSaveList.setMargin(new Insets(2, 2,2,2));
		jbSaveList.setPreferredSize(new Dimension(100, 50));
		jbSaveList.addActionListener(new SaveListActionListener());

	
		c.fill = GridBagConstraints.HORIZONTAL ;
		c.gridx = 0;
		c.gridy = 1;
		this.add(jbSaveList,c);

		
		jbSavePlot = new JButton();
		jbSavePlot.setText("Save Plot");
		jbSavePlot.setMargin(new Insets(2, 2,2,2));
		jbSavePlot.setPreferredSize(new Dimension(100, 50));
		jbSavePlot.addActionListener(new SavePlotActionListener());

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		this.add(jbSavePlot,c);
	}
	
	// AUXILIARY CLASSES
	
	private class 	SaveListActionListener 		implements 	ActionListener {
		
		@SuppressWarnings("unchecked")
		@Override public void 		actionPerformed		(ActionEvent e) {

			// Create a saveFile dialog
			JFileChooser iFile = new JFileChooser();

			iFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
			iFile.setMultiSelectionEnabled(false);
			iFile.setDialogTitle("Select a File to Sale Primers Data");
			iFile.setDialogType(JFileChooser.SAVE_DIALOG);
			iFile.setCurrentDirectory(new java.io.File( "." ));
			iFile.showOpenDialog(ResultViewer.this);

			try {
				if (ResultViewer.this.isShowingSinglePrimerData) {
					ResultViewer.this.mainframe.getControl().exportPrimers(iFile.getSelectedFile().getCanonicalPath() , (List<Primer>) primerData );
				} else {
					ResultViewer.this.mainframe.getControl().exportPairs(iFile.getSelectedFile().getCanonicalPath(), (List<PrimerPair>) primerData );
				}
			} catch (IOException e1) { e1.printStackTrace(); }			
		}
		
	}
	
	private class 	SavePlotActionListener 		implements 	ActionListener {

		@SuppressWarnings("unchecked")
		@Override public void 		actionPerformed		(ActionEvent e) {

			// Create a saveFile dialog
			JFileChooser iFile = new JFileChooser();

			iFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
			iFile.setMultiSelectionEnabled(false);
			iFile.setDialogTitle("Select a File to Sale Primers Data");
			iFile.setDialogType(JFileChooser.SAVE_DIALOG);
			iFile.setCurrentDirectory(new java.io.File( "." ));
			iFile.showOpenDialog(ResultViewer.this);

			List<Primer> exported = null;
			if (ResultViewer.this.isShowingSinglePrimerData) {
				
				exported = (List<Primer>) primerData;
				
			} else {
				exported = new Vector<Primer>();
				for (PrimerPair pair : ((List<PrimerPair>) primerData)) {
					exported.add(pair.getForward());
					exported.add(pair.getReverse());
				}
			}
			
			try {
				ResultViewer.this.mainframe.getControl().exportDistributionProfile(iFile.getSelectedFile().getCanonicalPath(),exported, lenOfAlignment);
				} catch (IOException e1) { e1.printStackTrace(); }			
		}
	}
}
