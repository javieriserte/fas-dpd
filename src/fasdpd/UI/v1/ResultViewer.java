package fasdpd.UI.v1;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ResultViewer extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5492613920686356173L;
	private JButton jbSaveList;
	private JButton jbSavePlot;
	private JScrollPane scPane;
	final private ResultTable resultTable;

	public ResultViewer() {
		super();
		
		GridBagLayout thisLayout = new GridBagLayout();
		thisLayout.columnWeights = new double[] {1,1}; 
		thisLayout.columnWidths = new int[] {100, 100}; 
		thisLayout.rowWeights = new double[] {1, 0};
		thisLayout.rowHeights = new int[] {170, 30}; 
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(thisLayout);
		this.setOpaque(true);
		
		resultTable = new ResultTable();
		

		
		
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

	
		c.fill = GridBagConstraints.HORIZONTAL ;
		c.gridx = 0;
		c.gridy = 1;
		this.add(jbSaveList,c);

		
		jbSavePlot = new JButton();
		jbSavePlot.setText("Save Plot");
		jbSavePlot.setMargin(new Insets(2, 2,2,2));
		jbSavePlot.setPreferredSize(new Dimension(100, 50));

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		this.add(jbSavePlot,c);
	}
	
	
	
}
