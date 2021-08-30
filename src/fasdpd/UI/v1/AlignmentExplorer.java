/*
 * You may not change or alter any portion of this comment or credits
 * of supporting developers from this source code or any supporting source code
 * which is considered copyrighted (c) material of the original comment or credit authors.
 *
 * THERE IS NO WARRANTY FOR THE PROGRAM, TO THE EXTENT PERMITTED BY APPLICABLE LAW. 
 * EXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT HOLDERS AND/OR OTHER PARTIES 
 * PROVIDE THE PROGRAM �AS IS� WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, 
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
 * 	"Microbiolog�a molecular b�sica y aplicaciones biotecnol�gicas"
 * 		(Basic Molecular Microbiology and biotechnological applications)
 * 
 * And is being conducted in:
 * 	LIGBCM: Laboratorio de Ingenier�a Gen�tica y Biolog�a Celular y Molecular.
 *		(Laboratory of Genetic Engineering and Cellular and Molecular Biology)
 *	Universidad Nacional de Quilmes.
 *		(National University Of Quilmes)
 *	Quilmes, Buenos Aires, Argentina.
 *
 * The complete team for this project is formed by:
 *	Lic.  Javier A. Iserte.
 *	Lic.  Betina I. Stephan.
 * 	ph.D. Sandra E. Go�i.
 * 	ph.D. P. Daniel Ghiringhelli.
 *	ph.D. Mario E. Lozano.
 *
 * Corresponding Authors:
 *	Javier A. Iserte. <jiserte@unq.edu.ar>
 *	Mario E. Lozano. <mlozano@unq.edu.ar>
 */

package fasdpd.UI.v1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import sequences.Sequence;
import sequences.alignment.Alignment;
import sequences.dna.DNASeq;
import sequences.protein.ProtSeq;
import degeneration.GeneticCode;
import fastaIO.FastaMultipleReader;
import fastaIO.Pair;

/**
 * Swing component to show DNA or Protein Alignment.
 *
 * @author Javier Iserte <jiserte@unq.edu.ar>
 */
public class AlignmentExplorer extends javax.swing.JPanel {
	private static final long serialVersionUID = 7978892495301236840L;
	private Alignment alignment;
	private GeneticCode geneticCode;
	private Color backgroundColor = new Color(255, 255, 255);
	// Components
	private JScrollPane mainScrollPane;
	private MainView mainView;
	private JLabel header;
	private Description descriptions;

	public AlignmentExplorer(Alignment alingment, GeneticCode geneticCode) {
		super();
		this.alignment = alingment;
		this.geneticCode = geneticCode;
		this.createGUI();
	}

	/**
	 * Shows selected region of alignment differentially. <b>Currently not
	 * implemented</b>
	 * 
	 * @param from start of selected region
	 * @param to   end of selected region
	 */
	public void highlight(Integer from, Integer to) {
		// TODO Implement highlighting of selected regions.
	}

	private void createGUI() {
		BorderLayout thisLayout = new BorderLayout();
		this.setLayout(thisLayout);
		mainScrollPane = new JScrollPane();
		this.add(mainScrollPane, BorderLayout.CENTER);
		Font font = new Font("Courier New", Font.PLAIN, 14);
		this.setFont(font);
		mainView = new MainView(this.alignment);
		mainView.setFont(font);
		mainView.setOpaque(true);
		mainView.setBackground(this.backgroundColor);
		header = new Header(this.alignment);
		header.setFont(font);
		header.setBackground(this.backgroundColor);
		header.setOpaque(true);

		descriptions = new Description(this.alignment);
		descriptions.setFont(font);
		descriptions.setBackground(this.backgroundColor);
		descriptions.setOpaque(true);
		descriptions.setVisible(true);

		mainScrollPane.setColumnHeaderView(header);
		mainScrollPane.add(mainView);

		this.add(mainScrollPane);

		this.setPreferredSize(new Dimension(400, 300));

		JPanel rowHeaderPanel = new JPanel();

		rowHeaderPanel.setOpaque(true);
		rowHeaderPanel.setVisible(true);
		rowHeaderPanel.setBackground(Color.white);

		GridBagLayout rhpL = new GridBagLayout();
		rowHeaderPanel.setLayout(rhpL);

		rhpL.columnWeights = new double[] { 1, 0 };
		rhpL.columnWidths = new int[] { 140, 15 }; // total width of panel is 310
		rhpL.rowWeights = new double[] { 1 };
		rhpL.rowHeights = new int[] { 100 }; // total width of panel is 200

		GridBagConstraints c = new GridBagConstraints();

		c.gridy = 0;
		c.gridx = 0;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.NORTHWEST;

		rowHeaderPanel.add(descriptions, c);

		c.gridy = 0;
		c.gridx = 1;
		c.fill = GridBagConstraints.VERTICAL;
		c.anchor = GridBagConstraints.CENTER;

		JButton split = new JButton();
		split.setPreferredSize(new Dimension(5, 100));
		split.setAlignmentX(CENTER_ALIGNMENT);

		SplitBarMouseListener sbml = new SplitBarMouseListener();

		split.addMouseMotionListener((MouseMotionListener) sbml);
		split.addMouseListener((MouseListener) sbml);

		rowHeaderPanel.add(split, c);
		mainScrollPane.setRowHeaderView(rowHeaderPanel);

		mainScrollPane.setViewportView(mainView);

		JLabel lowerleftcorner = new JLabel();
		lowerleftcorner.setOpaque(true);
		lowerleftcorner.setBackground(this.backgroundColor);
		lowerleftcorner.setVisible(true);

		JLabel upperleftcorner = new JLabel();
		upperleftcorner.setBackground(this.backgroundColor);
		upperleftcorner.setOpaque(true);
		upperleftcorner.setVisible(true);

		JLabel upperrightcorner = new JLabel();
		upperrightcorner.setBackground(this.backgroundColor);
		upperrightcorner.setOpaque(true);
		upperrightcorner.setVisible(true);

		mainScrollPane
			.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, upperrightcorner);
		mainScrollPane
			.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, lowerleftcorner);
		mainScrollPane
			.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, upperleftcorner);

	}

	private void printColoredSequence(
		int x,
		int y,
		Graphics2D g,
		String sequence,
		ColoringStrategy color) {
		int textHeight = g.getFontMetrics().getHeight();
		int charWidth = g.getFontMetrics().stringWidth("A");

		for (int i = 0; i < sequence.length(); i++) {
			String charbase = sequence.substring(i, i + 1);
			g.setColor(color.getColor(charbase.charAt(0)));
			g.drawString(charbase, x + charWidth * i, y + textHeight);
		}
	}

	//
	@SuppressWarnings("unused")
	private boolean isProtein() {
		return this.alignment.getSeq().get(0).getClass() == ProtSeq.class;
	}

	////////////////////////////////////
	// Auxiliary Classes

	private class SplitBarMouseListener
		implements MouseMotionListener, MouseListener {
		private Integer x_i = null;

		@Override
		public void mouseDragged(MouseEvent e) {
			int dx = 0;
			if (x_i != null) {
				dx = e.getX() - x_i;
			} else {
				x_i = e.getX();
			}

			AlignmentExplorer.this.descriptions.setPreferredSize(
				new Dimension(
					descriptions.getSize().width + dx,
					descriptions.getSize().height));
			descriptions.updateUI();

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			x_i = null;
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}

	private class Description extends JLabel {
		private static final long serialVersionUID = 3066870404629353044L;
		private Alignment alignment = null;
		private BufferedImage biDescriptions = null;

		// Constructor
		public Description(Alignment alignment) {
			super();
			this.alignment = alignment;

		}

		// Public Interface
		public void paint(Graphics g) {
			// Redefinition of paint method.
			super.paint(g);

			if (biDescriptions == null)
				createImage();

			g.drawImage((Image) this.biDescriptions, 0, 0, null);

		}

		// Private Methods
		private void createImage() {

			List<Sequence> sequences = this.alignment.getSeq();
			int size = sequences.size();
			String maxLengthDesc = "";

			maxLengthDesc = getMaxLengthSequence(sequences);

			biDescriptions = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);

			Graphics2D g = (Graphics2D) biDescriptions.getGraphics();
			g.setFont(AlignmentExplorer.this.getFont());

			int textWidth = g.getFontMetrics().stringWidth(maxLengthDesc);
			int textHeight = g.getFontMetrics().getHeight() * size;

			// System.out.println("textWidth : " + textWidth);
			// System.out.println("textHeight: " + textHeight);
			//
			biDescriptions = new BufferedImage(
				textWidth + 10,
				textHeight + 10,
				BufferedImage.TYPE_INT_RGB);
			g = (Graphics2D) biDescriptions.getGraphics();

			g.setColor(Color.white);
			g.fillRect(0, 0, textWidth + 10, textHeight + 10);

			g.setColor(Color.black);
			g.setFont(AlignmentExplorer.this.getFont());

			int textLineHeight = g.getFontMetrics().getHeight();
			int counter = 0;
			for (Sequence sequence : this.alignment.getSeq()) {
				String desc = sequence.getDescription();
				counter++;
				g.drawString(desc, 5, textLineHeight * (counter));
			}

			// this.setPreferredSize(new Dimension(textWidth + 10, textHeight + 10));
			this.setPreferredSize(new Dimension(150, textHeight + 10));

		}

		protected String getMaxLengthSequence(List<Sequence> sequences) {
			int max = 0;
			Sequence s = null;
			for (Sequence seq : sequences) {
				if (seq.getDescription().length() > max) {
					max = seq.getDescription().length();
					s = seq;
				}
			}
			return s.getDescription();
		}
	}

	private class Header extends JLabel {

		private static final long serialVersionUID = 1221448808240337613L;
		private Alignment alignment;
		private BufferedImage biHeader = null;

		public Header(Alignment alignment) {
			super();
			this.alignment = alignment;
			createImage();
		}

		public void paint(Graphics g) {
			super.paint(g);

			if (biHeader == null) {
				createImage();
			}

			g.drawImage((Image) biHeader, 0, 0, null);

			// this.setPreferredSize(new Dimension(w+10,textHeight*3+8));

		}

		private void createImage() {

			this.biHeader = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = (Graphics2D) this.biHeader.getGraphics();

			g.setFont(AlignmentExplorer.this.getFont());

			int textHeight = g.getFontMetrics().getHeight();

			// int size =
			// AlignmentExplorer.this.alignment.getSeq().get(0).getLength();
			String s = null;

			if (AlignmentExplorer.this.geneticCode != null) {
				s = (alignment.pileUp(AlignmentExplorer.this.geneticCode))
					.getSequence();
			}
			int size = s.length();

			// int textwidth = g.getFontMetrics().stringWidth(s);
			int textwidth = g.getFontMetrics().stringWidth("A") * size * 2;

			int imageHeight = 3 * textHeight + 8;
			int imageWidth = textwidth + 10;

			StringBuilder line1 = new StringBuilder();
			StringBuilder line2 = new StringBuilder();

			this.biHeader = new BufferedImage(
				imageWidth,
				imageHeight,
				BufferedImage.TYPE_INT_RGB);
			g = (Graphics2D) this.biHeader.getGraphics();
			g.setFont(AlignmentExplorer.this.getFont());

			g.setColor(Color.white);

			String base = "''''|";
			int nb = ((size - 1) / 5) + 1;
			while (nb-- > 0) {
				line1.append(base);
			}
			int d = ((size - 1) / 10) + 1;
			int i = 1;
			while (i++ < d) {
				String n = String.valueOf((i - 1) * 10);
				line2.append(("          " + n).substring(n.length()));
			}
			line1.delete(size, line1.length());
			g.fillRect(0, 0, imageWidth, imageHeight);
			g.setColor(Color.black);
			g.drawString(line1.toString(), 5, textHeight);
			g.drawString(line2.toString(), 5, 2 * textHeight);
			ColoringStrategy color = new DnaColoringStrategy();
			AlignmentExplorer.this
				.printColoredSequence(5, 2 * textHeight, (Graphics2D) g, s, color);
			this.setPreferredSize(new Dimension(imageWidth, imageHeight));
			// String s1 = alignment.getSeq().get(0).getSequence();
			// int w = g.getFontMetrics().stringWidth(s1);
		}

	};

	private class MainView extends JLabel {
		private static final long serialVersionUID = -3012075092592318198L;
		private Alignment alignment = null;
		private BufferedImage biMainView = null;

		// Constructor
		public MainView(Alignment alignment) {
			super();
			this.alignment = alignment;
			this.createImage();
		}

		// Public Interface
		public void paint(Graphics g) {
			// Redefinition of paint method.
			super.paint(g);

			if (biMainView == null) {
				createImage();
			}
			g.drawImage((Image) biMainView, 0, 0, null);
		}

		private String expandProteinSequence(String s) {
			StringBuilder result = new StringBuilder();

			for (int i = 0; i < s.length(); i++) {
				result.append(' ');
				result.append(s.charAt(i));
				result.append(' ');
			}
			return result.toString();
		}

		private void createImage() {
			List<Sequence> sequences = this.alignment.getSeq();
			int size = sequences.size();
			biMainView = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = (Graphics2D) biMainView.getGraphics();
			g.setFont(AlignmentExplorer.this.getFont());
			int textWidth = g.getFontMetrics()
				.stringWidth(sequences.get(0).getSequence());
			int textHeight = g.getFontMetrics().getHeight();
			int imageWidth = textWidth + 10;
			int imageHeight = textHeight * size;
			int counter = 0;
			ColoringStrategy color = null;
			boolean isProtein = false;
			if (this.alignment.getSeq().get(0).getClass() == DNASeq.class) {
				color = new DnaColoringStrategy();
			} else if (this.alignment.getSeq().get(0).getClass() == ProtSeq.class) {
				color = new ProteinColoringStrategy();
				imageWidth = imageWidth * 3;
				isProtein = true;
			}
			biMainView = new BufferedImage(
				imageWidth,
				imageHeight,
				BufferedImage.TYPE_INT_RGB);
			g = (Graphics2D) biMainView.getGraphics();
			g.setFont(AlignmentExplorer.this.getFont());
			g.setColor(Color.white);
			g.fillRect(0, 0, imageWidth, imageHeight);
			for (Sequence sequence : this.alignment.getSeq()) {
				String desc = sequence.getSequence();
				counter++;
				if (isProtein)
					desc = this.expandProteinSequence(desc);
				AlignmentExplorer.this.printColoredSequence(
					5,
					textHeight * (counter - 1),
					(Graphics2D) g,
					desc,
					color);
			}
			this.setPreferredSize(new Dimension(imageWidth, imageHeight));
		}
	}

	//////////////////////////////////
	// Executable Main. DO NOT USE IT.
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		List<Pair<String, String>> l = null;
		FastaMultipleReader mfr = new FastaMultipleReader();
		GeneticCode geneticCode = null;
		Alignment alin1 = new Alignment();
		try {
			geneticCode = new GeneticCode("StandardCode");
			l = mfr.readFile(
				"C:\\JAvier\\DropBox\\My Dropbox\\Investigacion\\Sandra\\Filogenia SLEV - Mayo 2011\\Datos de Partida\\slev.fas");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (l != null) {
			for (Pair<String, String> pair : l) {
				alin1.addSequence(new DNASeq(pair.getSecond(), pair.getFirst()));
			}
		} else {
			return;
		}
		AlignmentExplorer ae = new AlignmentExplorer(alin1, geneticCode);
		frame.getContentPane().add(ae);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		ae.highlight(5, 10);
		frame.setVisible(true);
	}
}
