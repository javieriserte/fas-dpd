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

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import fasdpd.PrimerPair;
import fastaIO.Pair;
import sequences.dna.Primer;
import java.awt.Dimension;
import fasdpd.PrimerPair;
import sequences.dna.Primer;
import java.awt.GridLayout;
import java.util.List;

public class ResultTable extends JPanel {

	// INSTANCE VARIABLES
	
	private AbstractTableModel model;
	private JTable table;
	private JScrollPane scrollPane; 
	private static final long serialVersionUID = 1L;

	// CONSTRUCTOR
	public 				ResultTable		(List<Primer> primers, List<PrimerPair> pairs) {
        
		super(new GridLayout(1,0));
        this.model = new SinglePrimerTableModel(null);
//        table.setPreferredScrollableViewportSize(new Dimension(400, 300));

        if (primers==null && pairs!= null) { this.setPairData(pairs); } 
        else 
        if (primers!=null && pairs== null) { this.setData(primers)  ; } 
        else 
        if (primers==null && pairs== null) { this.model = new SinglePrimerTableModel(null) ; }
        
        table = new JTable(this.model);

        table.setFillsViewportHeight(true);
        
        scrollPane = new JScrollPane(table);
        
        this.add(scrollPane);
        
    }
	
	// PUBLIC INTERFACE 
	
	public void 		setValueAt		(Object value, int row, int col) {

		this.model.setValueAt(value, row, col);
		
	}
	
	public Object 		getValueAt		(int row, int col) {
		
		return this.model.getValueAt(row, col);
		
	}

	public void 		setData			(List<Primer> primers) {
		Object[][] data= new Object[primers.size()][4];
		int i=0;
		for (Primer primer : primers) {
			
			data[i]  [0] = primer.getSequence();
			data[i]  [1] = primer.getScore();
			data[i]  [2] = String.valueOf(primer.getStart() + " to " + primer.getEnd());
			data[i++][3] = primer.isDirectStrand() == true? "forward": "reverse";
			
		}
		
		this.model = new SinglePrimerTableModel(data);
		
		this.table.setModel(this.model);
		this.table.updateUI();
		
	}	
	
	public void 		setPairData		(List<PrimerPair> primerpairs) {
		Object[][] data= new Object[primerpairs.size()][8];
		int i=0;
		for (PrimerPair pair : primerpairs) {
			
			Primer f = pair.getForward();
			Primer r = pair.getReverse();
			
			data[i]  [0] = f.getSequence();
			data[i]  [1] = f.getScore();
			data[i]  [2] = String.valueOf(f.getStart() + " to " + f.getEnd());
			data[i]  [3] = f.isDirectStrand() == true? "forward": "reverse";
			
			data[i]  [4] = r.getSequence();
			data[i]  [5] = r.getScore();
			data[i]  [6] = String.valueOf(r.getStart() + " to " + r.getEnd());
			data[i++][7] = r.isDirectStrand() == true? "forward": "reverse";
			
		}
		
		this.model = new PrimerPairTableModel(data);
		this.table.setModel(this.model);
		this.table.updateUI();
	}
	

	// AUXILIARY CLASSES

	class SinglePrimerTableModel extends AbstractTableModel {

		// INSTANCE VARIABLES

		private static final long 		serialVersionUID 	= 1194534744911746424L;
		
		private	String[] 				columnNames 		= {"Sequence","Score",  "Position", "Strand"};
        
		private Object[][] 				data 				= {{"Empty List", "","",""}};

        // CONSTRUCTOR
        public 								SinglePrimerTableModel		(Object[][] data) 	{ if(data!=null) this.data = data; }
        
        // PUBLIC INTERFACE
        public int 							getColumnCount				() 					{ return columnNames.length; }

        public int 							getRowCount					() 					{ return data.length; }

        public String 						getColumnName				(int col) 			{ return columnNames[col]; }

        public Object 						getValueAt					(int row, int col) 	{ return data[row][col] ; }

		public Class<? extends Object> 		getColumnClass				(int c) 			{ return getValueAt(0, c).getClass(); }

        public void 						setValueAt					(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }

    }
	
	class PrimerPairTableModel extends AbstractTableModel {

		// INSTANCE VARIABLES	
		private static final long serialVersionUID = 1194534744911746424L;

		private String[] columnNames = {"Sequence","Score",  "Position", "Strand","Sequence","Score",  "Position", "Strand"};

		private Object[][] data = {{"Empty List", "","","","","","",""}};

        
        // CONSTRUCTOR
        
        public 								PrimerPairTableModel		(Object[][] data) 	{ if(data!=null) this.data = data; }
        
        
        // PUBLIC INTERFACE
        
        public int 							getColumnCount		() 					{ return columnNames.length; }

        public int 							getRowCount			() 					{ return data.length; }

        public String 						getColumnName		(int col) 			{ return columnNames[col]; }

        public Object 						getValueAt			(int row, int col) 	{ return data[row][col]; }

		public Class<? extends Object> 		getColumnClass		(int c) 			{ return getValueAt(0, c).getClass(); }

        public void 						setValueAt			(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }
    }
	
}
