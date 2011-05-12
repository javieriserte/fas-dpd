package fasdpd.UI.v1;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import java.awt.Dimension;
import java.awt.GridLayout;

public class ResultTable extends JPanel {
	private MyTableModel model;

	private static final long serialVersionUID = 1L;

	public ResultTable() {
        super(new GridLayout(1,0));

        this.model = new MyTableModel();
        JTable table = new JTable(this.model);
//        table.setPreferredScrollableViewportSize(new Dimension(400, 300));
        table.setFillsViewportHeight(true);
        
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);
        
    }
	
	public void setValueAt(Object value, int row, int col) {
		this.model.setValueAt(value, row, col);
	}
	
	public Object getValueAt(int row, int col) {
		return this.model.getValueAt(row, col);
	}
	

	class MyTableModel extends AbstractTableModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1194534744911746424L;
		private String[] columnNames = {"Sequence","Score",  "Position", "Strand"};
        private Object[][] data = {{"Empty List", "","",""}};

        public int getColumnCount() { return columnNames.length; }

        public int getRowCount() { return data.length; }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

		public Class<? extends Object> getColumnClass(int c) { return getValueAt(0, c).getClass(); }

        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }

    }
}
