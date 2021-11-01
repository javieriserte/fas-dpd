package fasdpd.UI.v1.alignmentExplorer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

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
import fasdpd.UI.v1.colors.DefaultColoringStrategy;
import fasdpd.UI.v1.colors.DnaColoringStrategy;
import fasdpd.UI.v1.colors.ProteinColoringStrategy;
import fastaIO.FastaMultipleReader;
import fastaIO.Pair;

/**
 * Swing component to show DNA or Protein Alignment.
 */
public class AlignmentExplorer extends javax.swing.JPanel {
	private static final long serialVersionUID = 7978892495301236840L;
	private Alignment alignment;
	protected GeneticCode geneticCode;
	private Color backgroundColor = new Color(255, 255, 255);
	private Set<AlignmentRegion> hightlightedRegions;
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
		this.hightlightedRegions = new HashSet<>();
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

	public boolean isMolTypeProtein() {
		return this.alignment.getSeq().get(0).getClass() == ProtSeq.class;
	}

	public boolean isMolTypeDNA() {
		return this.alignment.getSeq().get(0).getClass() == DNASeq.class;
	}

	/**
	 * Shows selected region of alignment differentially. <b>Currently not
	 * implemented</b>
	 *
	 * @param from start of selected region
	 * @param to   end of selected region
	 */
	public void highlight(int from, int to, Color color) {
		AlignmentRegion e = new AlignmentRegion(from, to, color);
		this.hightlightedRegions.add(e);
		this.updateUI();
	}

	public void clearHighlightedRegions() {
		this.hightlightedRegions.clear();
		this.updateUI();
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
		String[] fontNames = new String[]{
			"Courier New",
			"FreeMono",
			"Arial"
		};
		GraphicsEnvironment env = GraphicsEnvironment
			.getLocalGraphicsEnvironment();
		String[] fonts = env.getAvailableFontFamilyNames();
		Set<String> fontSet = new HashSet<>();
		Collections.addAll(fontSet, fonts);
		fontNames = Arrays
			.stream(fontNames)
			.filter(f -> fontSet.contains(f))
			.toArray(String[]::new);
		String fontName = fontNames.length>0?fontNames[0]:fonts[0];
		monoSpaceFont = new Font(fontName, Font.PLAIN, 18);
		this.setFont(monoSpaceFont);

	}

	private void addCornersToMainScrollPane(JScrollPane pane) {
		String[] corners = new String[] { ScrollPaneConstants.UPPER_RIGHT_CORNER,
			ScrollPaneConstants.LOWER_LEFT_CORNER,
			ScrollPaneConstants.UPPER_LEFT_CORNER };
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
		header = new Header(this.alignment, this);
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
	//
	@SuppressWarnings("unused")
	private boolean isProtein() {
		return this.alignment.getSeq().get(0).getClass() == ProtSeq.class;
	}

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
			descriptions.setPreferredSize(
				new Dimension(
					descriptions.getSize().width + dx,
					descriptions.getSize().height));
			descriptions.updateUI();
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			x_i = null;
		}
		@Override public void mouseMoved(MouseEvent e) {}
		@Override public void mouseClicked(MouseEvent e) { }
		@Override public void mousePressed(MouseEvent e) { }
		@Override public void mouseEntered(MouseEvent e) { }
		@Override public void mouseExited(MouseEvent e) {}
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
			if (s != null) {
				return s.getDescription();
			} else {
				return "";
			}
		}
	}

	// private class Header extends JLabel {
	// 	private static final long serialVersionUID = 1221448808240337613L;
	// 	private Alignment alignment;
	// 	private BufferedImage biHeader = null;
	// 	public Header(Alignment alignment) {
	// 		super();
	// 		this.alignment = alignment;
	// 		createImage();
	// 	}
	// 	public void paint(Graphics g) {
	// 		super.paint(g);
	// 		if (biHeader == null) {
	// 			createImage();
	// 		}
	// 		g.drawImage((Image) biHeader, 0, 0, null);
	// 	}
	// 	private void createImage() {
	// 		this.biHeader = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
	// 		Graphics2D g = (Graphics2D) this.biHeader.getGraphics();
	// 		g.setFont(AlignmentExplorer.this.getFont());
	// 		int textHeight = g.getFontMetrics().getHeight();
	// 		String s = null;
	// 		if (AlignmentExplorer.this.geneticCode != null) {
	// 			s = (alignment.pileUp(AlignmentExplorer.this.geneticCode))
	// 				.getSequence();
	// 		}
	// 		int size = s.length();
	// 		g.setFont(AlignmentExplorer.this.getFont());
	// 		int textwidth = g.getFontMetrics().stringWidth("A") * size * 2;
	// 		int imageHeight = 3 * textHeight + 8;
	// 		int imageWidth = textwidth + 10;
	// 		StringBuilder line1 = new StringBuilder();
	// 		StringBuilder line2 = new StringBuilder();
	// 		this.biHeader = new BufferedImage(
	// 			imageWidth,
	// 			imageHeight,
	// 			BufferedImage.TYPE_INT_RGB);
	// 		g = (Graphics2D) this.biHeader.getGraphics();
	// 		g.setFont(AlignmentExplorer.this.getFont());
	// 		g.setColor(Color.white);
	// 		String base = "''''|";
	// 		int nb = ((size - 1) / 5) + 1;
	// 		while (nb-- > 0) {
	// 			line1.append(base);
	// 		}
	// 		int d = ((size - 1) / 10) + 1;
	// 		int i = 1;
	// 		while (i++ < d) {
	// 			String n = String.valueOf((i - 1) * 10);
	// 			line2.append(("          " + n).substring(n.length()));
	// 		}
	// 		line1.delete(size, line1.length());
	// 		g.fillRect(0, 0, imageWidth, imageHeight);
	// 		g.setColor(Color.black);
	// 		g.drawString(line1.toString(), 5, textHeight);
	// 		g.drawString(line2.toString(), 5, 2 * textHeight);
	// 		ColoringStrategy color = new DnaColoringStrategy();
	// 		ColoredSequencePrinter.print(5, 2 * textHeight, (Graphics2D) g, s, color);
	// 		this.setPreferredSize(new Dimension(imageWidth, imageHeight));
	// 	}

	// };

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
			if (biMainView == null || !hasMsaImage) {
				createImage();
			}
			g.drawImage((Image) biMainView, 0, 0, null);
		}
		public void updateAlignment(Alignment aln) {
			this.alignment = aln;
			createImage();
		}
		private void createImage() {
			boolean emptyAln = this.alignment.lenght() == 0;
			AbstractMsaPainter painter = emptyAln?
				new EmptyMsaPainter():
				new MsaPainter();
			Rectangle visible = this.getVisibleRect();
			painter = painter
				.withAlignment(this.alignment)
				.withFont(AlignmentExplorer.this.getFont())
				.defaultDimension(new Dimension(
					visible.width, visible.height
				));
			biMainView = painter.paint();
			if (biMainView != null) {
				this.setPreferredSize(
					new Dimension(
						biMainView.getWidth(),
						biMainView.getHeight()
					)
				);
				hasMsaImage = !emptyAln;
			}
			return;
		}
	}
}
