package fasdpd.UI;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.filechooser.FileFilter;

import fastaIO.FastaFilter;

public class FasDPDUI extends JFrame {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
			
		FasDPDUI i = new FasDPDUI();
		i.setVisible(true);
		float goldenRatio = (float) (1 + Math.pow(5, (double)1/2));
		System.out.println(goldenRatio);
		float  v =  (float) 2 *  (float) 600 / goldenRatio;
		Dimension fixed = new Dimension(600, (int) v);
		Dimension semifixed = new Dimension(600, (int) (v/3));

		for (LookAndFeelInfo lf : UIManager.getInstalledLookAndFeels()) {
			System.out.println(lf.getClassName());
		}
		
		
		
	    try {
		    // Set System L&F
//	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    	UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
	            //;
	        	

	        		
	    } 
	    catch (UnsupportedLookAndFeelException e) {
	       // handle exception
	    }
	    catch (ClassNotFoundException e) {
	       // handle exception
	    }
	    catch (InstantiationException e) {
	       // handle exception
	    }
	    catch (IllegalAccessException e) {
	       // handle exception
	    }

		
		// Step One: Load a alignmen/t.
		// Select Sequence Type (DNA o Protein)
		// Step Two: Define Parameters:
		// Step Three: Select an output file. & Draw Profile.
		
		// This Panel must have components for input file loading:
		// 		A label to show : "Loaded File : xxxx" where xxx is the name of the file.
		//		A Button to launch a JFileChooser open dialog
		//		A combobox to choose between DNA and Protein.
		
		
		Label lbOpenFile = new Label("Loaded File: no file...");
			lbOpenFile.setBounds(10, 10, 120, 25);
		
		
		JButton btOpenFile = new JButton("Load file");
			btOpenFile.setBounds(140, 10, 60, 60);
			
		JCheckBox cbMolType = new JCheckBox("is DNA: ");
			cbMolType.setBounds(10, 40, 70, 25);
		
		
		
		
		// This Panel must have components to choose parameters.
		// 		Genetic Code: JFileChooser, a button and Label. 
		//		Start Point:  inputbox & label
		//		End Point:    Inputbox & label
		//		Ny, Nx y pA:  inputboc & label
		//		Complementary Chain: a Togglebutton? & a label. 
		//		Quantity: inputboc & label
		//		A label to say that "parameter xxx change" if any changed.
		
		// This Panel must have components for Outfile choosing and profile choosing.
		// 		A label & Jfilechooser to choose outfile
		//		A Toggle button, a label & a Jfilechooser for profile.
		//		A big Button to Start!!!
		
		i.setSize(fixed);
		i.setResizable(false);
		GridBagLayout l = new GridBagLayout();
		int[] wi = new int[] {600};
		int[] he = new int[]{200,200,200};
		
		l.columnWidths = wi;
		l.rowHeights = he;
		i.setLayout(new GridBagLayout());

		
		JPanel mainPanel1 = new JPanel();
		JPanel mainPanel2 = new JPanel();
		JPanel mainPanel3 = new JPanel();
		
		JPanel refPanel1 = new JPanel();
		JPanel refPanel2 = new JPanel();
		JPanel refPanel3 = new JPanel();

		
		
		refPanel1.add( new JTextArea(((Integer) fixed.height).toString()));
		refPanel1.setVisible(true);
		refPanel2.setVisible(true);
		
		refPanel1.setBackground(new Color(255,0,0));
		refPanel2.setBackground(new Color(0,255,0));
		refPanel3.setBackground(new Color(0,0,255));
		
		refPanel1.setPreferredSize(semifixed);
		refPanel2.setPreferredSize(semifixed);
		refPanel3.setPreferredSize(semifixed);
		
		mainPanel1 = refPanel1;
		mainPanel2 = refPanel2;
		mainPanel3 = refPanel3;
		
		
//		i.add(mainPanel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		i.add(mainPanel2,new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		i.add(mainPanel3,new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

//		i.add(mainPanel1);
//		i.add(mainPanel2);
//		i.add(mainPanel3);
		

//		i.remove(mainPanel1);
//		
		mainPanel1 = i.getStepOnePanel();
//		mainPanel3 = refPanel3;
		
		
		i.add(mainPanel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		mainPanel2.add(i.getButton("Tsy"));
		mainPanel1.add(new JButton("JEJE"));
		
		
		i.pack();
	}


	private JButton getButton(String text) {
		return new JButton(text);
	}

	private void initializeUI() {
		
	}
	
	private JPanel getStepOnePanel() {


		
		
		final JPanel rPanel = new JPanel();
		rPanel.setLayout(null);
		

		
		ActionListener al = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				JFileChooser iFile = new JFileChooser();
				FileFilter ff = new FileFilter() {
					@Override public String getDescription() { return "Fasta files"; }
					@Override public boolean accept(File f) { return f.isDirectory() || (new FastaFilter()).accept(f); }
				};
				iFile.setFileFilter(ff);
				iFile.showOpenDialog(getParent());
				System.out.println(getParent());
			}
		};

		Label label = new Label("No Input File Selected");
		JButton bt = new JButton("Load Alignment");
		bt.setVisible(true);
		bt.setPreferredSize(new Dimension(50,20));
		bt.setBounds(10, 10, 50, 20);
		bt.addActionListener(al);
		
		rPanel.setVisible(true);
		rPanel.setPreferredSize(new Dimension(600, 200));
		rPanel.setBackground(new Color(0,0,255));
		
		rPanel.add(bt);
		rPanel.add(label);

		
		
		
		
//		JFileChooser d = new JFileChooser();
//		System.out.println(d.showOpenDialog(this));
//		System.out.println(d.getSelectedFile());

		return rPanel;
	
	}

	private JPanel getStepTwoPanel() {

		return null;
	}
	
	private JPanel getStepThreePanel() {

		return null;
	}
	
	
}
