package fasdpd.UI.v1;
import java.awt.BorderLayout;

import java.awt.Dimension;
import java.io.IOException;

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
	private JScrollPane jScrollPane1;
	private JTextPane jTextArea1;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new AlignmentExplorer());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public AlignmentExplorer() {
		super();
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
			jScrollPane1 = new JScrollPane();
			this.add(jScrollPane1, BorderLayout.CENTER);

			jScrollPane1.setColumnHeaderView(new JTextArea("Hola a todoss"));
			jTextArea1 = new JTextPane();
			String a = "<html>\n" +
	                "Color and font test:\n" +
	                "<ul>\n" +
	                "<li><font color=red>red</font>\n" +
	                "<li><font color=blue>blue</font>\n" +
	                "<li><font color=green>green</font>\n" +
	               /* "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font size=-2>small</font>\n" +
	                "<li><font size=+2>large</font>\n" +*/
	                "<li><i>italic</i>\n" +
	                "<li><b>bold</b>\n" +
	                "</ul>\n";
			
			HTMLEditorKit htmlKit = new HTMLEditorKit();
		    HTMLDocument htmlDoc = (HTMLDocument) htmlKit.createDefaultDocument();
		    javax.swing.text.Element e = htmlDoc.getElement(htmlDoc.getDefaultRootElement(), StyleConstants.NameAttribute, HTML.Tag.BODY);
		    htmlDoc.insertAfterEnd(e,a);
		    jTextArea1.setContentType("text/html");
		    jTextArea1.setText(a);
//			jTextArea1.setStyledDocument(htmlDoc);
			jScrollPane1.setViewportView(jTextArea1);
		}
		this.setPreferredSize(new Dimension (500,400));
		
	}

}
