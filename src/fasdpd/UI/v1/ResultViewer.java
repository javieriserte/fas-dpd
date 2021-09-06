
package fasdpd.UI.v1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import sequences.dna.Primer;
import fasdpd.PrimerListExporter;
import fasdpd.PrimerOrPrimerPair;
import fasdpd.PrimerPair;

public class ResultViewer extends JPanel {

	private static final long serialVersionUID = -5492613920686356173L;
	private JButton jbSaveList;
	private ResultTable resultTable;
	private List<PrimerOrPrimerPair> primerData;

	public ResultViewer() {
		super();
		this.createGUI();
	}

	public void setdata(List<Primer> primers) {
		resultTable.setData(primers);
		this.primerData = new ArrayList<PrimerOrPrimerPair>();
		for (Primer p : primers) {
			this.primerData.add(new PrimerOrPrimerPair(p));
		}
		this.resultTable.updateUI();
	}

	public void setPairdata(List<PrimerPair> primers) {
		resultTable.setPairData(primers);
		this.primerData = new ArrayList<PrimerOrPrimerPair>();
		for (PrimerPair p : primers) {
			this.primerData.add(new PrimerOrPrimerPair(p));
		}
		this.resultTable.updateUI();
	}

	private void createGUI() {
		GridBagLayout thisLayout = new GridBagLayout();
		thisLayout.columnWeights = new double[] { 1, 1 };
		thisLayout.columnWidths = new int[] { 100, 100 };
		thisLayout.rowWeights = new double[] { 1, 0 };
		thisLayout.rowHeights = new int[] { 200, 30 };
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(thisLayout);
		this.setOpaque(true);
		this.setBackground(Color.WHITE);

		resultTable = new ResultTable(null, null);
		resultTable.setOpaque(true);
		resultTable.setPreferredSize(new Dimension(200, 200));

		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(2, 2, 2, 2);
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		this.add(resultTable, c);

		c.gridwidth = 1;
		jbSaveList = new JButton();
		jbSaveList.setText("Save List");
		jbSaveList.addActionListener(new SaveListActionListener());
		c.gridx = 1;
		c.gridy = 1;
		this.add(jbSaveList, c);

	}

	private class SaveListActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser iFile = setUpFileChooser();
			List<PrimerOrPrimerPair> pdata = new ArrayList<PrimerOrPrimerPair>();
			try {
				PrimerListExporter.exportPrimersToFile(
					iFile.getSelectedFile(),
					pdata
				);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		private JFileChooser setUpFileChooser() {
			JFileChooser iFile = new JFileChooser();
			iFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
			iFile.setMultiSelectionEnabled(false);
			iFile.setDialogTitle("Select a File to Sale Primers Data");
			iFile.setDialogType(JFileChooser.SAVE_DIALOG);
			iFile.setCurrentDirectory(new java.io.File("."));
			iFile.showOpenDialog(ResultViewer.this);
			return iFile;
		}
	}
}
