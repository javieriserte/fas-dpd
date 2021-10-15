package fasdpd.UI.v1;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JPanel;

import degeneration.GeneticCode;
import fasdpd.PrimerPair;
import sequences.alignment.Alignment;
import sequences.dna.Primer;

public class OptionsPane extends JPanel {
	private static final long serialVersionUID = -5923205806932143474L;
	private Alignment align;
	private GeneticCode geneticCode;
	// COMPONENTS
	private AlignmentExplorer alignmentExplorer;
	private ResultViewer resultViewer;

	// CONSTRUCTORS
	public OptionsPane(Alignment align, GeneticCode gc) {
		super();
		this.geneticCode = gc;
		this.align = align;
		this.alignmentExplorer = new AlignmentExplorer(
			this.align,
			this.geneticCode);
		this.createGUI();
	}

	public OptionsPane(Alignment align, GeneticCode gc, MainFASDPD mainframe) {
		super();
		this.geneticCode = gc;
		this.align = align;
		this.alignmentExplorer = new AlignmentExplorer(
			this.align,
			this.geneticCode);
		this.createGUI();
	}

	public void updateAlignment(Alignment alignment) {
		this.align = alignment;
		this.alignmentExplorer.updateAlignment(alignment);
		this.updateUI();
	}

	public void updateDna(boolean isDNA) {
		Alignment aln;
		if (isDNA) {
			aln = this.align.toDnaAlignment();
		} else {
			aln = this.align.toProteinAlignment();
		}
		updateAlignment(aln);
	}

	private void createGUI() {
		GridBagLayout thisLayout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		this.setOpaque(true);
		this.setBackground(Color.WHITE);
		thisLayout.rowWeights = new double[] { 1, 0 };
		thisLayout.rowHeights = new int[] { 450, 250 };
		thisLayout.columnWeights = new double[] { 1 };
		thisLayout.columnWidths = new int[] { 250 };
		this.setLayout(thisLayout);

		resultViewer = new ResultViewer();
		resultViewer.setOpaque(true);

		c.insets = new Insets(5, 5, 5, 5);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;
		this.add(alignmentExplorer, c);

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(resultViewer, c);
	}

	public void setPairData(List<PrimerPair> primers) {
		resultViewer.setPairdata(primers);
		this.updateUI();
	}

	public void setPrimerData(List<Primer> primers) {
		resultViewer.setdata(primers);
		this.updateUI();
	}
	
	public void addExportFiltersActionListener(ActionListener listener) {
	  resultViewer.addExportFiltersActionListener(listener);
	}
}
