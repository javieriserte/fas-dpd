package fasdpd.UI.v1;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;

import degeneration.GeneticCode;

import fastaIO.FastaMultipleReader;
import fastaIO.Pair;

import sequences.Sequence;
import sequences.alignment.Alignment;
import sequences.alignment.htmlproducer.AlignmentHTMLProducer;
import sequences.dna.DNASeq;


public class AlignmentExplorer extends javax.swing.JPanel {

	/////////////////////
	// Instance Variables
	
	private static final long 		serialVersionUID = 7978892495301236840L;
	private Alignment 				alignment;
	private GeneticCode 			geneticCode;

	/////////////
	// Components
	private JScrollPane 			mainScrollPane;
	private JLabel 					mainView;
	private JLabel 					header;
	private JLabel 					descriptions;


	
	public 					AlignmentExplorer				(Alignment alin1, GeneticCode geneticCode) {
		super();
		this.alignment = alin1;
		this.geneticCode = geneticCode;
		try { createGUI();
		} catch (BadLocationException e) { e.printStackTrace(); }
		  catch (IOException e) { e.printStackTrace();
		}
	}
	
	private void 			createGUI						() throws BadLocationException, IOException {
		BorderLayout thisLayout = new BorderLayout();
		this.setLayout(thisLayout);
		{
			mainScrollPane = new JScrollPane();
			this.add(mainScrollPane, BorderLayout.CENTER);

			Font myFont = new Font("Monospaced", Font.BOLD, 14);
			
			mainView = new JLabel();
			header = new JLabel();
			
//			header.setContentType("text/html");
			header.setText(this.createTextRuler(this.alignment.getSeq().get(0).getLength()));
//			header.setEditable(false);
//			header.setDisabledTextColor(new Color(0));
//			header.setEnabled(false);
			header.setFont(myFont);
			header.setFocusable(false);
			header.setInputVerifier(null);
			header.addMouseListener(null);
			header.addInputMethodListener(null);
			header.addMouseMotionListener(null);
			header.setOpaque(true);
			header.setBackground(new Color(255,255,255));			
//			header.setDragEnabled(false);
			
			descriptions = new JLabel();
//			descriptions.setContentType("text/html");
			descriptions.setText(getHTMLforDecriptions());
//			descriptions.setSize(200, descriptions.getSize().height);
			descriptions.setPreferredSize(new Dimension(150,descriptions.getSize().height));
			descriptions.setFont(myFont);
			descriptions.setOpaque(true);
			descriptions.setBackground(new Color(255,255,255));
			descriptions.setVerticalAlignment(SwingConstants.TOP);
			
			
			
//		    mainView.setContentType("text/html");
		    mainView.setText(this.getHTMLforSequences(null, null));
//		    mainView.setEditable(false);
		    
		    mainView.setFont(myFont);
		    mainView.setVerticalAlignment(SwingConstants.TOP);
		    
		    mainView.setOpaque(true);
		    mainView.setBackground(new Color(255,255,255));
		    
		    mainScrollPane.setColumnHeaderView(header);
//


		    JPanel rowHeaderPanel = new JPanel();
		    
		    
		    descriptions.setOpaque(true);
		    descriptions.setVisible(true);
		    
		    rowHeaderPanel.setOpaque(true);
		    rowHeaderPanel.setVisible(true);
		    rowHeaderPanel.setBackground(Color.white);
		    
		    GridBagLayout rhpL = new GridBagLayout();
		    rowHeaderPanel.setLayout(rhpL);

		    rhpL.columnWeights 	= new double[] 	{   1,  0 }; 
		    rhpL.columnWidths 	= new int[] 	{ 140, 15 }; // total width of panel is 310
		    rhpL.rowWeights 	= new double[] 	{   1 };
		    rhpL.rowHeights 	= new int[] 	{ 100 }; // total width of panel is 200
		    
		    GridBagConstraints c = new GridBagConstraints();

		    c.gridy = 0;
		    c.gridx = 0;
		    c.fill = GridBagConstraints.BOTH;
		    c.anchor = GridBagConstraints.NORTHWEST;
		
		    rowHeaderPanel.add(descriptions,c);
		    
		    c.gridy = 0;
		    c.gridx = 1;
		    c.fill = GridBagConstraints.VERTICAL;
		    c.anchor = GridBagConstraints.CENTER;
		    
		    
		    		    
		    
		    JButton split = new JButton();
		    split.setPreferredSize(new Dimension(5, 100));
		    split.setAlignmentX(CENTER_ALIGNMENT);
		    
		    SplitBarMouseListener sbml = new SplitBarMouseListener();
		    
		    split.addMouseMotionListener((MouseMotionListener) sbml );
		    split.addMouseListener((MouseListener) sbml );
		    
		    rowHeaderPanel.add(split,c);
		    mainScrollPane.setRowHeaderView(rowHeaderPanel);

		    mainScrollPane.setViewportView(mainView);
			
		    JLabel lowerleftcorner = new JLabel();
		    lowerleftcorner.setOpaque(true);
		    lowerleftcorner.setBackground(Color.white);
		    
			JLabel upperleftcorner = new JLabel();
			upperleftcorner.setBackground(Color.white);
			upperleftcorner.setOpaque(true);
			
			mainScrollPane.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, lowerleftcorner);
			mainScrollPane.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, upperleftcorner);
		}
		this.setPreferredSize(new Dimension (500,400));
		
	}
	
	private String getHTMLforSequences(Integer from, Integer to) {
		AlignmentHTMLProducer ahp = new AlignmentHTMLProducer();
		return ahp.produceHTML(alignment, from, to, new Color(200, 200, 100));

	}
	
	private String getHTMLforDecriptions(){
		StringBuilder r = new StringBuilder();
		r.append("<HTML><TT><PRE>");
		for (Sequence s : this.alignment.getSeq()) {
//			r.append(s.getDescription().substring(0, Math.min(15, s.getDescription().length()))+"<BR>");
			
			r.append(s.getDescription()+"<BR>");
		}
		r.append("</PRE></TT></HTML>");
		return r.toString();
	}
	
	private String createTextRuler(int size) {
		String s = null;
		if (this.geneticCode!=null) {
			s = (alignment.pileUp(this.geneticCode)).getSequence();
		}
		StringBuilder line1 = new StringBuilder();
		StringBuilder line2 = new StringBuilder();
		String base = "''''|";
		int nb = ((size-1) /5)+1;
		while (nb-- >0) {line1.append(base);}
		
		int d = ((size-1) /10)+1;
		int i=1;
		while (i++ <d) {
			String n = String.valueOf((i-1)*10);
			
			line2.append(("          "+n).substring(n.length()));
			}
		
		
		line1.delete(size,line1.length());
		if (this.geneticCode!=null) return "<HTML><TT><PRE>" + line1.toString() + "<BR>" + line2.toString() + "</PRE></TT></HTML>";
		return "<HTML><TT><PRE>" + s + "<BR>" + line1.toString() + "<BR>" + line2.toString() + "</PRE></TT></HTML>";
	}
	
	public void highlight (Integer from, Integer to) {
		this.mainView.setText(this.getHTMLforSequences(from,to));
	}
	
	////////////////////////////////////
	// Auxiliary Classes
	
	
	private class SplitBarMouseListener implements MouseMotionListener, MouseListener {
		private Integer x_i = null;
		
		
		@Override public void mouseDragged	(MouseEvent e) {
			int dx=0;
			if (x_i!=null) { dx = e.getX() - x_i;} 
			          else { x_i = e.getX(); }

			AlignmentExplorer.this.descriptions.setPreferredSize(new Dimension(descriptions.getSize().width + dx,descriptions.getSize().height));
			descriptions.updateUI();
			
		}
		
		@Override public void mouseReleased	(MouseEvent e) {
			x_i = null;
		}
		
		@Override public void mouseMoved  	(MouseEvent e) {}
		@Override public void mouseClicked	(MouseEvent e) {}
		@Override public void mousePressed	(MouseEvent e) {}
		@Override public void mouseEntered	(MouseEvent e) {}
		@Override public void mouseExited	(MouseEvent e) {}
	}
	
	
	//////////////////////////////////
	// Executable Main. DO NOT USE IT.
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		List<Pair<String,String>> l = null;
		FastaMultipleReader mfr = new FastaMultipleReader();
		GeneticCode geneticCode =null;
		Alignment alin1 = new Alignment();
		try {
			
//			geneticCode = new GeneticCode("C:\\Javier\\Informatica\\Proyectos\\FASDPD\\JavaWorkspace\\FAS-DPD\\StandardCode");
			geneticCode = new GeneticCode("StandardCode");			
			l = mfr.readFile("C:\\Javier\\Informatica\\Proyectos\\FASDPD\\JavaWorkspace\\FAS-DPD\\example\\Cyto_c_ox.fas");
		} catch (FileNotFoundException e) { e.printStackTrace(); }
//
		if (l!=null) {
			for (Pair<String, String> pair : l) {
				alin1.addSequence(new DNASeq( pair.getSecond(),pair.getFirst()));
			}
		} else {
			return;
		}
		AlignmentExplorer ae = new AlignmentExplorer(alin1,geneticCode);
		frame.getContentPane().add(ae);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		ae.highlight(5, 10);
//		frame.pack();
	
		frame.setVisible(true);
	}

}
