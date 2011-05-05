package fasdpd.UI.v1;
import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextPane;
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
	private JTextPane mainView;
	private JTextPane header;
	private JTextPane descriptions;
	private Alignment alignment;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		List<Pair<String,String>> l = null;
		FastaMultipleReader mfr = new FastaMultipleReader();
		Alignment alin1 = new Alignment();
		try {
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
		frame.getContentPane().add(new AlignmentExplorer(alin1));
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public AlignmentExplorer(Alignment alin1) {
		super();
		this.alignment = alin1;
		try {
			initGUI();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initGUI() throws BadLocationException, IOException {
		BorderLayout thisLayout = new BorderLayout();
		this.setLayout(thisLayout);
		{
			mainScrollPane = new JScrollPane();
			this.add(mainScrollPane, BorderLayout.CENTER);

			// TODO hacer que alignment exporte el alineamiento como html.
			// TODO Agregar otro text area para los nombre de las secuencias.
			// TODO ver como se puede hacer que la barra para los nombres de las secuencias pueda cambiar de largo.
			// TODO asociar el AlignmentExplorer a un Alignment.
			
			
			mainView = new JTextPane();
			
			header = new JTextPane();
			header.setContentType("text/html");
			header.setText(this.createTextRuler(this.alignment.getSeq().get(0).getLength()));
			header.setEditable(false);
			header.setDisabledTextColor(new Color(0));
			header.setEnabled(false);
			header.setFocusable(false);
			header.setInputVerifier(null);
			header.addMouseListener(null);
			header.addInputMethodListener(null);
			header.addMouseMotionListener(null);
			header.setDragEnabled(false);
			

			descriptions = new JTextPane();
			descriptions.setContentType("text/html");
			descriptions.setText(getHTMLforDecriptions());
//			descriptions.setSize(200, descriptions.getSize().height);
//			descriptions.setPreferredSize(new Dimension(40,descriptions.getSize().height));

		    mainView.setContentType("text/html");
		    mainView.setText(this.getHTMLforSequences(null, null));
		    mainView.setEditable(false);

		    mainScrollPane.setColumnHeaderView(header);
			mainScrollPane.setRowHeaderView(descriptions);
			mainScrollPane.setViewportView(mainView);
			
			
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
			r.append(s.getDescription().substring(0, Math.min(15, s.getDescription().length()))+"<BR>");
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