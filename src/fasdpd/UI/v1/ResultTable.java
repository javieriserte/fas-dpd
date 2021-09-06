package fasdpd.UI.v1;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import fasdpd.PrimerPair;
import sequences.dna.Primer;
import java.awt.GridLayout;
import java.util.List;

public class ResultTable extends JPanel {

	private AbstractTableModel model;
	private JTable table;
	private JScrollPane scrollPane;
	private static final long serialVersionUID = 1L;

	public ResultTable(List<Primer> primers, List<PrimerPair> pairs) {
		super(new GridLayout(1, 0));
		this.model = new SinglePrimerTableModel(null);
		if (primers == null && pairs != null) {
			this.setPairData(pairs);
		} else if (primers != null && pairs == null) {
			this.setData(primers);
		} else if (primers == null && pairs == null) {
			this.model = new SinglePrimerTableModel(null);
		}

		table = new JTable(this.model);
		table.setFillsViewportHeight(true);
		scrollPane = new JScrollPane(table);
		this.add(scrollPane);

	}

	public void setValueAt(Object value, int row, int col) {
		this.model.setValueAt(value, row, col);
	}

	public Object getValueAt(int row, int col) {
		return this.model.getValueAt(row, col);
	}

	public void setData(List<Primer> primers) {
		Object[][] data = new Object[primers.size()][4];
		int i = 0;
		for (Primer primer : primers) {
			data[i][0] = primer.getSequence();
			data[i][1] = primer.getScore();
			data[i][2] = String.valueOf(primer.getStart() + " to " + primer.getEnd());
			data[i++][3] = primer.isDirectStrand() == true ? "forward" : "reverse";
		}
		this.model = new SinglePrimerTableModel(data);
		this.table.setModel(this.model);
		this.table.updateUI();
	}

	public void setPairData(List<PrimerPair> primerpairs) {
		Object[][] data = new Object[primerpairs.size()][8];
		int i = 0;
		for (PrimerPair pair : primerpairs) {
			Primer f = pair.getForward();
			Primer r = pair.getReverse();
			data[i][0] = f.getSequence();
			data[i][1] = f.getScore();
			data[i][2] = String.valueOf(f.getStart() + " to " + f.getEnd());
			data[i][3] = f.isDirectStrand() == true ? "forward" : "reverse";
			data[i][4] = r.getSequence();
			data[i][5] = r.getScore();
			data[i][6] = String.valueOf(r.getStart() + " to " + r.getEnd());
			data[i++][7] = r.isDirectStrand() == true ? "forward" : "reverse";
		}
		this.model = new PrimerPairTableModel(data);
		this.table.setModel(this.model);
		this.table.updateUI();
	}

	class SinglePrimerTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1194534744911746424L;
		private String[] columnNames = { "Sequence", "Score", "Position",
			"Strand" };
		private Object[][] data = { { "Empty List", "", "", "" } };

		public SinglePrimerTableModel(Object[][] data) {
			if (data != null)
				this.data = data;
		}

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.length;
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			return data[row][col];
		}

		public Class<? extends Object> getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		public void setValueAt(Object value, int row, int col) {
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}

	}

	class PrimerPairTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1194534744911746424L;
		private String[] columnNames = { "Sequence", "Score", "Position", "Strand",
			"Sequence", "Score", "Position", "Strand" };
		private Object[][] data = { { "Empty List", "", "", "", "", "", "", "" } };

		public PrimerPairTableModel(Object[][] data) {
			if (data != null)
				this.data = data;
		}

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.length;
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			return data[row][col];
		}

		public Class<? extends Object> getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		public void setValueAt(Object value, int row, int col) {
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}
	}

}
