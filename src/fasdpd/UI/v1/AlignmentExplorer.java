package fasdpd.UI.v1;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;

import java.awt.Dimension;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTMLDocument.HTMLReader;
import javax.swing.text.html.parser.Element;

import degeneration.GeneticCode;

import fastaIO.FastaMultipleReader;
import fastaIO.Pair;

import sequences.Sequence;
import sequences.alignment.Alignment;
import sequences.alignment.htmlproducer.AlignmentHTMLProducer;
import sequences.dna.DNASeq;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class AlignmentExplorer extends javax.swing.JPanel {
	private JScrollPane mainScrollPane;
	private JLabel mainView;
//	private JTextPane mainView;
//	private JTextPane header;
	private JLabel header;
	private JLabel descriptions;
	private JLabel concense;
	private Alignment alignment;
	private GeneticCode geneticCode;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		List<Pair<String,String>> l = null;
		FastaMultipleReader mfr = new FastaMultipleReader();
		GeneticCode geneticCode =null;
		Alignment alin1 = new Alignment();
		try {
			
			geneticCode = new GeneticCode("C:\\javier\\Proyectos\\FAS-DPD\\Workspace\\FAS-DPD\\StandardCode");
			l = mfr.readFile("C:\\javier\\Proyectos\\FAS-DPD\\Workspace\\FAS-DPD\\example\\Cyto_c_ox.fas");
		} catch (FileNotFoundException e) { e.printStackTrace(); }
//
		if (l!=null) {
			for (Pair<String, String> pair : l) {
				alin1.addSequence(new DNASeq( pair.getSecond(),pair.getFirst()));
			}
		} else {
			return;
		}
		frame.getContentPane().add(new AlignmentExplorer(alin1,geneticCode));
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public AlignmentExplorer(Alignment alin1, GeneticCode geneticCode) {
		super();
		this.alignment = alin1;
		this.geneticCode = geneticCode;
		try { initGUI();
		} catch (BadLocationException e) { e.printStackTrace(); }
		  catch (IOException e) { e.printStackTrace();
		}
	}
	
	private void initGUI() throws BadLocationException, IOException {
		BorderLayout thisLayout = new BorderLayout();
		this.setLayout(thisLayout);
		{
			mainScrollPane = new JScrollPane();
			this.add(mainScrollPane, BorderLayout.CENTER);

			// TODO ver como se puede hacer que la barra para los nombres de las secuencias pueda cambiar de largo.
			
			Font myFont = new Font("Monospaced", Font.BOLD, 14);
			
			mainView = new JLabel() {

				private static final long serialVersionUID = 1L;

				public void paint(Graphics g) {
//  				Shape circle = new Ellipse2D.Float(100.0f, 100.0f, 100.0f, 100.0f);
//					Shape square = new Rectangle2D.Double(100, 100,100, 100);
					super.paint(g);
//				    Graphics2D ga = (Graphics2D)g;
//				    ga.draw(circle);
//				    ga.setPaint(Color.green);
//				    ga.fill(circle);
//				    ga.setPaint(Color.red);
//				    ga.draw(square);
//					ga.drawString("HOLA", 10, 10);
				};
			};
			
			
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
			
			concense = new JLabel() ;
			concense.setFont(myFont);
			concense.setOpaque(true);
			concense.setBackground(new Color(255,255,255));
			concense.setVerticalAlignment(SwingConstants.TOP);
			
			String s = (alignment.pileUp(this.geneticCode)).getSequence();
			
//		    mainView.setContentType("text/html");
		    mainView.setText(this.getHTMLforSequences(null, null));
//		    mainView.setEditable(false);
		    
		    mainView.setFont(myFont);
		    mainView.setVerticalAlignment(SwingConstants.TOP);
		    
		    mainView.setOpaque(true);
		    mainView.setBackground(new Color(255,255,255));
		    
		    mainScrollPane.setColumnHeaderView(header);
			mainScrollPane.setRowHeaderView(descriptions);
			mainScrollPane.setViewportView(mainView);
//			mainScrollPane.setCorner(key, corner)
			
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
		return "<HTML><TT><PRE>" + line1.toString() + "<BR>" + line2.toString() + "</PRE></TT></HTML>";
	}
}
