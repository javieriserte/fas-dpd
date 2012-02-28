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

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainPane extends JPanel {
	private JButton jbSetIndividual;
	private JButton jbSetPairs;
	private MainFASDPD mainFrame;
	// TODO implement singleton
	
	public MainPane(MainFASDPD mainFASDPD) {
		super();
		// TODO Separate GUI creation of Constructor
		this.mainFrame = mainFASDPD;
		
		GridBagLayout thisLayout = new GridBagLayout();
		thisLayout.columnWeights = new double[] {1, 0,0,0,1}; 
		thisLayout.columnWidths = new int[] {50, 100, 10,100,50}; // total width of panel is 310
		thisLayout.rowWeights = new double[] {1, 0, 1};
		thisLayout.rowHeights = new int[] {50, 60, 50}; // total width of panel is 160
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(thisLayout);
		
		jbSetIndividual = new JButton();
		jbSetIndividual.setText("<HTML><CENTER>Design Individual Primers</CENTER></html>");
		jbSetIndividual.setMargin(new Insets(2, 2,2,2));
		jbSetIndividual.setPreferredSize(new Dimension(100, 60));
		jbSetIndividual.setHorizontalAlignment(SwingConstants.CENTER );
		jbSetIndividual.addActionListener(new jbSetIndAction());
	
	
		c.fill = GridBagConstraints.HORIZONTAL ;
		c.gridx = 1;
		c.gridy = 1;
		this.add(jbSetIndividual,c);

		
		jbSetPairs = new JButton();
		jbSetPairs.setText("<HTML><CENTER>Design PCR primer pairs</CENTER></html>");
		jbSetPairs.setMargin(new Insets(2, 2,2,2));
		jbSetPairs.setHorizontalAlignment(SwingConstants.TRAILING );
		jbSetPairs.setPreferredSize(new Dimension(100, 60));
		jbSetPairs.addActionListener(new jbSetPairAction());
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 1;
		this.add(jbSetPairs,c);
	}
	
	// LISTENERS & EXTRA CLASSES

	private class jbSetIndAction implements ActionListener {

		@Override public void actionPerformed(ActionEvent e) {
			System.out.println("Individual Set");
			MainPane.this.mainFrame.getSearchParameter().setSearchPair(false);
			MainPane.this.mainFrame.loadOpenFilePane();
		}
	}

	private class jbSetPairAction implements ActionListener {

		@Override public void actionPerformed(ActionEvent e) {
			System.out.println("Pair Set");
			MainPane.this.mainFrame.getSearchParameter().setSearchPair(true);
			MainPane.this.mainFrame.loadOpenFilePane();
		}
	}
	
	
	
}
