package fasdpd.UI.v1;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.ScrollPane;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
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
import sequences.protein.ProtSeq;


public class AlignmentExplorer extends javax.swing.JPanel {

	
	/////////////////////
	// Instance Variables
	
	private static final long 		serialVersionUID = 7978892495301236840L;
	private Alignment 				alignment;
	private GeneticCode 			geneticCode;
	private Color					backgroundColor = new Color(255,255,255);

	
	/////////////
	// Components
	
	private JScrollPane 			mainScrollPane;
//	private JLabel 					mainView;
	private MainView 				mainView;
	private JLabel 					header;
	private Description             descriptions;
//	private JLabel 					descriptions;

	
	///////////////////
	// Public Interface
	
	public 					AlignmentExplorer				(Alignment alin1, GeneticCode geneticCode) {
		super();
		this.alignment = alin1;
		this.geneticCode = geneticCode;
		
		createGUI();
//		
//		try { createGUI();
//		} catch (BadLocationException e) { e.printStackTrace(); }
//		  catch (IOException e) { e.printStackTrace();
//		}
	}

	public void highlight (Integer from, Integer to) {
//		this.mainView.setText(this.getHTMLforSequences(from,to));
	}
	
	
	//////////////////
	// Private Methods
	
	private void 			createGUI						() {
		BorderLayout thisLayout = new BorderLayout();
		this.setLayout(thisLayout);

		mainScrollPane = new JScrollPane();
		this.add(mainScrollPane, BorderLayout.CENTER);
		Font font = new Font("Courier New", Font.PLAIN, 14);

		
		mainView = new MainView(this.alignment);
		mainView.setFont(font);
		mainView.setOpaque(true);
		mainView.setBackground(this.backgroundColor);
		
		header = new JLabel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1221448808240337613L;

			public void paint (Graphics g) {
				super.paint(g);
				
				int textHeight    = g.getFontMetrics().getHeight();
				
				int size = AlignmentExplorer.this.alignment.getSeq().get(0).getLength();
				String s = null;
				
				if (AlignmentExplorer.this.geneticCode!=null) {
					s = (alignment.pileUp(AlignmentExplorer.this.geneticCode)).getSequence();
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

				g.drawString(line1.toString(), 5, textHeight);
				g.drawString(line2.toString(), 5, 2*textHeight);
				
				ColoringStrategy color = new DnaColoringStrategy();
				
				
				AlignmentExplorer.this.printColoredSequence(5,2*textHeight,(Graphics2D)g,s,color);
				
				
				String s1 = alignment.getSeq().get(0).getSequence();
				int w = g.getFontMetrics().stringWidth(s1);
				
				this.setPreferredSize(new Dimension(w+10,textHeight*3+8));
				
			}
		};
		
		
		header.setFont(font);
		header.setBackground(this.backgroundColor);
		header.setOpaque(true);
		header.setPreferredSize(new Dimension(0,70));
		
		descriptions = new Description(0, this.alignment);
		descriptions.setFont(font);
		descriptions.setBackground(this.backgroundColor);
		descriptions.setOpaque(true);
	    descriptions.setVisible(true);

		mainScrollPane.setColumnHeaderView(header);
		mainScrollPane.add(mainView);
		
		
		this.add(mainScrollPane);
		
		this.setPreferredSize(new Dimension(200,300));
		
	    JPanel rowHeaderPanel = new JPanel();
	    
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
	    lowerleftcorner.setBackground(this.backgroundColor);
	    
		JLabel upperleftcorner = new JLabel();
		upperleftcorner.setBackground(this.backgroundColor);
		upperleftcorner.setOpaque(true);
		
		mainScrollPane.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, lowerleftcorner);
		mainScrollPane.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, upperleftcorner);
		
		
	}
	


//	private void 			createGUI						() throws BadLocationException, IOException {
//		BorderLayout thisLayout = new BorderLayout();
//		this.setLayout(thisLayout);
//		{
//			mainScrollPane = new JScrollPane();
//			this.add(mainScrollPane, BorderLayout.CENTER);
//
//			Font myFont = new Font("Monospaced", Font.BOLD, 14);
//			
//			mainView = new JLabel();
//			header = new JLabel();
//			
////			header.setContentType("text/html");
//			header.setText(this.createTextRuler(this.alignment.getSeq().get(0).getLength()));
////			header.setEditable(false);
////			header.setDisabledTextColor(new Color(0));
////			header.setEnabled(false);
//			header.setFont(myFont);
//			header.setFocusable(false);
//			header.setInputVerifier(null);
//			header.addMouseListener(null);
//			header.addInputMethodListener(null);
//			header.addMouseMotionListener(null);
//			header.setOpaque(true);
//			header.setBackground(new Color(255,255,255));			
////			header.setDragEnabled(false);
//			
//			descriptions = new JLabel();
////			descriptions.setContentType("text/html");
//			descriptions.setText(getHTMLforDecriptions());
////			descriptions.setSize(200, descriptions.getSize().height);
//			descriptions.setPreferredSize(new Dimension(150,descriptions.getSize().height));
//			descriptions.setFont(myFont);
//			descriptions.setOpaque(true);
//			descriptions.setBackground(new Color(255,255,255));
//			descriptions.setVerticalAlignment(SwingConstants.TOP);
//			
////		    mainView.setContentType("text/html");
//		    mainView.setText(this.getHTMLforSequences(null, null));
////		    mainView.setEditable(false);
//		    
//		    mainView.setFont(myFont);
//		    mainView.setVerticalAlignment(SwingConstants.TOP);
//		    
//		    mainView.setOpaque(true);
//		    mainView.setBackground(new Color(255,255,255));
//		    
//		    mainScrollPane.setColumnHeaderView(header);
////
//
//
//		    JPanel rowHeaderPanel = new JPanel();
//		    
//		    
//		    descriptions.setOpaque(true);
//		    descriptions.setVisible(true);
//		    
//		    rowHeaderPanel.setOpaque(true);
//		    rowHeaderPanel.setVisible(true);
//		    rowHeaderPanel.setBackground(Color.white);
//		    
//		    GridBagLayout rhpL = new GridBagLayout();
//		    rowHeaderPanel.setLayout(rhpL);
//
//		    rhpL.columnWeights 	= new double[] 	{   1,  0 }; 
//		    rhpL.columnWidths 	= new int[] 	{ 140, 15 }; // total width of panel is 310
//		    rhpL.rowWeights 	= new double[] 	{   1 };
//		    rhpL.rowHeights 	= new int[] 	{ 100 }; // total width of panel is 200
//		    
//		    GridBagConstraints c = new GridBagConstraints();
//
//		    c.gridy = 0;
//		    c.gridx = 0;
//		    c.fill = GridBagConstraints.BOTH;
//		    c.anchor = GridBagConstraints.NORTHWEST;
//		
//		    rowHeaderPanel.add(descriptions,c);
//		    
//		    c.gridy = 0;
//		    c.gridx = 1;
//		    c.fill = GridBagConstraints.VERTICAL;
//		    c.anchor = GridBagConstraints.CENTER;
//		    
//		    
//		    		    
//		    
//		    JButton split = new JButton();
//		    split.setPreferredSize(new Dimension(5, 100));
//		    split.setAlignmentX(CENTER_ALIGNMENT);
//		    
//		    SplitBarMouseListener sbml = new SplitBarMouseListener();
//		    
//		    split.addMouseMotionListener((MouseMotionListener) sbml );
//		    split.addMouseListener((MouseListener) sbml );
//		    
//		    rowHeaderPanel.add(split,c);
//		    mainScrollPane.setRowHeaderView(rowHeaderPanel);
//
//		    mainScrollPane.setViewportView(mainView);
//			
//		    JLabel lowerleftcorner = new JLabel();
//		    lowerleftcorner.setOpaque(true);
//		    lowerleftcorner.setBackground(Color.white);
//		    
//			JLabel upperleftcorner = new JLabel();
//			upperleftcorner.setBackground(Color.white);
//			upperleftcorner.setOpaque(true);
//			
//			mainScrollPane.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, lowerleftcorner);
//			mainScrollPane.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, upperleftcorner);
//		}
//		this.setPreferredSize(new Dimension (500,400));
//	}
//	
//	private String 			getHTMLforSequences				(Integer from, Integer to) {
//		AlignmentHTMLProducer ahp = new AlignmentHTMLProducer();
//		return ahp.produceHTML(alignment, from, to, new Color(200, 200, 100));
//
//	}
//	
//	private String 			getHTMLforDecriptions			(){
//		StringBuilder r = new StringBuilder();
//		r.append("<HTML><TT><PRE>");
//		for (Sequence s : this.alignment.getSeq()) {
////			r.append(s.getDescription().substring(0, Math.min(15, s.getDescription().length()))+"<BR>");
//			
//			r.append(s.getDescription()+"<BR>");
//		}
//		r.append("</PRE></TT></HTML>");
//		return r.toString();
//	}
//	
//	private String 			createTextRuler					(int size) {
//		String s = null;
//		if (this.geneticCode!=null) {
//			s = (alignment.pileUp(this.geneticCode)).getSequence();
//		}
//		StringBuilder line1 = new StringBuilder();
//		StringBuilder line2 = new StringBuilder();
//		String base = "''''|";
//		int nb = ((size-1) /5)+1;
//		while (nb-- >0) {line1.append(base);}
//		
//		int d = ((size-1) /10)+1;
//		int i=1;
//		while (i++ <d) {
//			String n = String.valueOf((i-1)*10);
//			
//			line2.append(("          "+n).substring(n.length()));
//			}
//		
//		
//		line1.delete(size,line1.length());
//		if (this.geneticCode!=null) return "<HTML><TT><PRE>" + line1.toString() + "<BR>" + line2.toString() + "</PRE></TT></HTML>";
//		return "<HTML><TT><PRE>" + s + "<BR>" + line1.toString() + "<BR>" + line2.toString() + "</PRE></TT></HTML>";
//	}
//	
	
	private void printColoredSequence(int x, int y, Graphics2D g, String sequence, ColoringStrategy color) {
		int textHeight = g.getFontMetrics().getHeight();
		int charWidth = g.getFontMetrics().stringWidth("A");
		
		for( int i=0;i<sequence.length();i++) {
			String charbase = sequence.substring(i,i+1);
			g.setColor(color.getColor(charbase.charAt(0)));
			g.drawString(charbase,x + charWidth*i , y + textHeight);
		}
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
	
	private class Description extends JLabel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 3066870404629353044L;
		private Alignment alignment = null;
		private double textWidth;
		// Constructor
		public Description (int rulerLenght, Alignment alignment) {
			super();
			this.alignment = alignment;
//			modifyPreferredSize(null);
		}

//		protected void modifyPreferredSize(Graphics2D g) {
//
//			for (Sequence sequence : this.alignment.getSeq()) {
//				String desc = sequence.getDescription();
//				this.textWidth = Math.max(this.getTextBounds(desc,g).getWidth(),this.textWidth);
//				
//			}
//			this.setPreferredSize(new Dimension((int) this.textWidth+10, 100));
//		}
		
		// Public Interface 
		public void paint( Graphics g ) {
			// Redefinition of paint method.
			super.paint(g);
			int textHeight = g.getFontMetrics().getHeight();
			int counter=0;
//			this.modifyPreferredSize((Graphics2D) g);
			for (Sequence sequence : this.alignment.getSeq()) {
				String desc = sequence.getDescription();
				counter++;
				g.drawString(desc, 5, textHeight*(counter));
			}
		}
		
		private Rectangle2D getTextBounds(String text, Graphics2D g) {
			if (g!=null) 
				return g.getFontMetrics().getStringBounds(text, g);
			else {
				FontRenderContext frc = new FontRenderContext(null, false, true);
				TextLayout layout = new TextLayout(text, this.getFont(), frc);
				return layout.getBounds();
			}
		}
	}
	

	
	private class MainView extends JLabel {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3012075092592318198L;
		private Alignment alignment = null;
		private BufferedImage bi = null;
		// Constructor
		public MainView (Alignment alignment) {
			super();
			this.alignment = alignment;
			this.setPreferredSize(new Dimension(100,100));
		}

		// Public Interface 
		public void paint( Graphics g ) {
			// Redefinition of paint method.
			super.paint(g);
				
			
			if (bi==null) {
			
				
				

				
			int textHeight = g.getFontMetrics().getHeight();
			int counter=0;
			ColoringStrategy color = null;
			boolean isProtein=false;
			
			String s = this.alignment.getSeq().get(0).getSequence();
			int w = g.getFontMetrics().stringWidth(s);
			
			bi = new BufferedImage(w+10, textHeight * this.alignment.getSeq().size(), BufferedImage.TYPE_INT_RGB);
			
			Graphics2D g1 = (Graphics2D) bi.getGraphics();
			g1.setFont(g.getFont());
			
			g1.setColor(Color.white);
			g1.fillRect(0, 0, w+10, textHeight * this.alignment.getSeq().size());
			
			if (this.alignment.getSeq().get(0).getClass() == DNASeq.class) {
				color = new DnaColoringStrategy();
			} else 
			if (this.alignment.getSeq().get(0).getClass() == ProtSeq.class) {
				color = new ProteinColoringStrategy();
				isProtein = true;
			}
			
			for (Sequence sequence : this.alignment.getSeq()) {
				String desc = sequence.getSequence();
				counter++;
				if (isProtein) desc = this.expandProteinSequence(desc);
				AlignmentExplorer.this.printColoredSequence(5, textHeight* (counter-1), (Graphics2D)g1, desc, color);
//				g.drawString(desc, 5, textHeight*(counter));
				
				
			}
//			String s = this.alignment.getSeq().get(0).getSequence();
//			int w = g.getFontMetrics().stringWidth(s);
			
			this.setPreferredSize(new Dimension(w+10,textHeight*this.alignment.getSeq().size()));
			}
			g.drawImage((Image) bi, 0, 0, null);
			
		}
		
		private String expandProteinSequence(String s) {
			StringBuilder result = new StringBuilder();
			
			for (int i=0;i<s.length();i++) {
				result.append('·');
				result.append(s.charAt(i));
				result.append('·');
				
			}
			return result.toString();
			
		}
		
	}
	
	//////////////////////////////////
	// Executable Main. DO NOT USE IT.
	
	public static void 		main							(String[] args) {
		JFrame frame = new JFrame();
		List<Pair<String,String>> l = null;
		FastaMultipleReader mfr = new FastaMultipleReader();
		GeneticCode geneticCode =null;
		Alignment alin1 = new Alignment();
		try {
			
//			geneticCode = new GeneticCode("C:\\Javier\\Informatica\\Proyectos\\FASDPD\\JavaWorkspace\\FAS-DPD\\StandardCode");
			geneticCode = new GeneticCode("StandardCode");			
//			l = mfr.readFile("C:\\JAvier\\JavaWorkspace\\fas-dpd\\example\\Cyto_c_ox.fas");
			l = mfr.readFile("C:\\JAvier\\DropBox\\My Dropbox\\Investigacion\\Sandra\\Filogenia SLEV - Mayo 2011\\Datos de Partida\\slev.fas");
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
