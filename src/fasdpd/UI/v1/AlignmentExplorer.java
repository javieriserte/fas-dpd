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
import javax.swing.border.EmptyBorder;

import sequences.Sequence;
import sequences.alignment.Alignment;
import sequences.dna.DNASeq;
import sequences.protein.ProtSeq;
import degeneration.GeneticCode;
import fasdpd.UI.v1.colors.ColoringStrategy;
import fasdpd.UI.v1.colors.DnaColoringStrategy;
import fasdpd.UI.v1.colors.ProteinColoringStrategy;
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
	private Font monoSpaceFont;

	public AlignmentExplorer(Alignment alingment, GeneticCode geneticCode) {
		super();
		this.alignment = alingment;
		this.geneticCode = geneticCode;
		this.createGUI();
	}

	public void updateAlignment(Alignment aln) {
		this.alignment = aln;
		setUpMainScrollPane();
		mainView.updateAlignment(aln);
		this.updateUI();
	}


	public void updateGeneticCode(GeneticCode code) {
		this.geneticCode = code;
	}

	public boolean hasNonEmptyMsa() {
		return alignment.lenght() > 0;
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


	private JPanel createRowHeaderPanel() {
		setUpDescriptions();

		JPanel rowHeaderPanel = new JPanel();

		rowHeaderPanel.setOpaque(true);
		rowHeaderPanel.setVisible(true);
		rowHeaderPanel.setBackground(Color.white);

		GridBagLayout rhpL = new GridBagLayout();
		rowHeaderPanel.setLayout(rhpL);

		rhpL.columnWeights = new double[] { 1, 0 };
		rhpL.columnWidths = new int[] { 140, 15 };
		rhpL.rowWeights = new double[] { 1 };
		rhpL.rowHeights = new int[] { 100 };

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
		return rowHeaderPanel;
	}

	private void setUpLayout() {
		BorderLayout thisLayout = new BorderLayout();
		this.setLayout(thisLayout);
	}

	private void setUpMonoSpaceFont() {
		monoSpaceFont = new Font("Courier New", Font.PLAIN, 14);
		this.setFont(monoSpaceFont);
	}

	private void addCornersToMainScrollPane(JScrollPane pane) {
		String[] corners = new String[]{
			ScrollPaneConstants.UPPER_RIGHT_CORNER,
			ScrollPaneConstants.LOWER_LEFT_CORNER,
			ScrollPaneConstants.UPPER_LEFT_CORNER
		};
		for (String c : corners) {
			JLabel lb = new JLabel();
			lb.setBackground(this.backgroundColor);
			lb.setOpaque(true);
			lb.setVisible(true);
			mainScrollPane.setCorner(c, lb);
		}
	}

	private void setUpMainScrollPane() {
		mainScrollPane = new JScrollPane();
		mainScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setUpMainView();
		mainScrollPane.setViewportView(mainView);
		setUpHeader();
		mainScrollPane.setColumnHeaderView(header);
		if (hasNonEmptyMsa()) {
			JPanel rowHeaderPanel = createRowHeaderPanel();
			mainScrollPane.setRowHeaderView(rowHeaderPanel);
		}
		addCornersToMainScrollPane(mainScrollPane);
		this.removeAll();
		this.add(mainScrollPane, BorderLayout.CENTER);
	}

	private void setUpDescriptions() {
		descriptions = new Description(this.alignment);
		descriptions.setFont(monoSpaceFont);
		descriptions.setBackground(this.backgroundColor);
		descriptions.setOpaque(true);
	}

	private void setUpHeader() {
		header = new Header(this.alignment);
		header.setFont(monoSpaceFont);
		header.setBackground(this.backgroundColor);
		header.setOpaque(true);
	}

	private void setUpMainView() {
		mainView = new MainView(this.alignment);
		mainView.setFont(monoSpaceFont);
		mainView.setOpaque(true);
		mainView.setBackground(this.backgroundColor);
	}

	private void createGUI() {
		setUpLayout();
		setUpMonoSpaceFont();
		setUpMainScrollPane();
		this.setPreferredSize(new Dimension(400, 300));
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

		public Description(Alignment alignment) {
			super();
			this.alignment = alignment;
		}

		public void paint(Graphics g) {
			super.paint(g);
			if (biDescriptions == null)
				createImage();
			g.drawImage((Image) this.biDescriptions, 0, 0, null);
		}

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
			if (s!=null) {
				return s.getDescription();
			} else {
				return "";
			}
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
		private boolean hasMsaImage = false;

		public MainView(Alignment alignment) {
			super();
			this.alignment = alignment;
			this.createImage();
		}

		public void paint(Graphics g) {
			super.paint(g);
			if (biMainView == null) {
				createImage();
			}
			if (!hasMsaImage) {
				createImage();
				g.drawImage((Image) biMainView, 0, 0, null);
			}
			g.drawImage((Image) biMainView, 0, 0, null);
		}

		public void updateAlignment(Alignment aln) {
			this.alignment = aln;
			createImage();
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

		private void createImageForAlignment(){
			List<Sequence> sequences = this.alignment.getSeq();
			biMainView = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = (Graphics2D) biMainView.getGraphics();
			g.setFont(AlignmentExplorer.this.getFont());
			int textWidth = g.getFontMetrics()
				.stringWidth(sequences.get(0).getSequence());
			int textHeight = g.getFontMetrics().getHeight();
			int imageWidth = textWidth + 10;
			int imageHeight = textHeight * sequences.size();
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
		}

		private void createImageWhenNoAlignment() {
			if (mainView!=null) {
				int w = mainView.getWidth();
				int h = mainView.getHeight();
				if (!(w>0&&h>0)) {
					biMainView = null;
					return;
				}
				biMainView = new BufferedImage(w, h,
					BufferedImage.TYPE_INT_RGB
				);
				Graphics2D g = (Graphics2D) biMainView.getGraphics();
				g.setFont(new Font("Verdana", 0, 20));
				String text = "No MSA data";
				int textHeight = g.getFontMetrics().getHeight();
				int textWidth = g.getFontMetrics().stringWidth(text);
				g.setColor(new Color(1.0f, 1.0f, 1.0f));
				g.fillRect(0, 0, w, h);
				g.setColor(new Color(127,127,127));
				g.drawString(text, (w - textWidth)/2, (h-textHeight)/2);
			} else {
				biMainView = null;
			}
		}

		private void createImage() {
			if (this.alignment.lenght()==0) {
				createImageWhenNoAlignment();
				return;
			}
			createImageForAlignment();
			this.setPreferredSize(
				new Dimension(
					biMainView.getWidth(),
					biMainView.getHeight()
				)
			);
			hasMsaImage = true;
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
