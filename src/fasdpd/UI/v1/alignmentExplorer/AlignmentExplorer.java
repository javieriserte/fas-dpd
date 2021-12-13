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
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.awt.GradientPaint;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import sequences.Sequence;
import sequences.alignment.Alignment;
import sequences.dna.DNASeq;
import sequences.protein.ProtSeq;
import degeneration.GeneticCode;

/**
 * Swing component to show DNA or Protein Alignment.
 */
public class AlignmentExplorer extends javax.swing.JPanel {
	private static final long serialVersionUID = 7978892495301236840L;
	public Alignment alignment;
	protected GeneticCode geneticCode;
	private Color backgroundColor = new Color(255, 255, 255);
	private Set<ShapePainter> hightlightedRegions;
	// Components
	public JScrollPane mainScrollPane;
	private MainView mainView;
	private JLabel header;
	private Description descriptions;
	private Font monoSpaceFont;
	private PropertyChangeSupport properties = new PropertyChangeSupport(this);

	public AlignmentExplorer(
			Alignment alingment,
			GeneticCode geneticCode) {
		super();
		this.alignment = alingment;
		this.geneticCode = geneticCode;
		this.hightlightedRegions = new HashSet<>();
		this.createGUI();
	}

	public void focusOnMsaRegion(int start, int end) {
		int maxScroll = mainScrollPane
			.getHorizontalScrollBar()
			.getMaximum();
		int alnLength = alignment.lenght() * alignment.getSeq().get(0).sizeInBases();
		int viewportSize = mainScrollPane
			.getViewport()
			.getWidth();
		int correctedValue = (end+start-1)/2 * maxScroll / alnLength - viewportSize / 2;
		correctedValue = Math.max(0, correctedValue);
		mainScrollPane
			.getHorizontalScrollBar()
			.setValue(correctedValue);
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
	 * Add a new highlight region to the list of regions to be painted.
	 * @param start
	 * @param end
	 * @param color
	 */
	public void highlightMsaBox(int start, int end, Color color) {
		ShapePainter shape = new BoxPainter()
			.setColor(color)
			.setOnTopBar(false)
			.setStart(start)
			.setEnd(end);
		hightlightedRegions.add(shape);
		mainView.updateImage();
		this.updateUI();
	}


	/**
	 * Add a new highlight arrow to the list of regions to
	 *
	 * @param from
	 * @param to
	 * @param color
	 * @param leftToRight
	 */
	public void highlightArrow(
			int from,
			int to,
			Color color,
			boolean leftToRight
			) {
		ShapePainter shape = new ArrowPainter(leftToRight)
			.setColor(color)
			.setOnTopBar(true)
			.setStart(from)
			.setEnd(to);
		this.hightlightedRegions.add(shape);
		this.mainView.updateImage();
		this.updateUI();
	}

	public void clearHighlightedRegions() {
		this.hightlightedRegions.clear();
		this.mainView.updateImage();
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
			"Arial",
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
		mainScrollPane.setBorder(
			new EmptyBorder(0, 0, 0, 0)
		);
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
		AlignmentExplorer.this.properties.addPropertyChangeListener(
			"selectedRow",
			new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				mainView.setSelectedRow(
					(Optional<Integer>) evt.getNewValue()
				);
				mainView.updateImage();
				mainView.updateUI();
			}
		});
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
		private static final int Y_OFFSET = 20;
		private int charHeight = 0;
		private Alignment alignment = null;
		private BufferedImage biDescriptions = null;
		private int numberOfSequences;
		protected Optional<Integer> selectedRow = Optional.empty();
		protected boolean requiresImageUpdate = true;

		public Description(Alignment alignment) {
			super();
			this.alignment = alignment;
			properties.addPropertyChangeListener(
				"selectedRow",
				new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					requiresImageUpdate = true;
					updateUI();
				}
			});
			this.addComponentListener(new ComponentListener() {
				@Override
				public void componentResized(ComponentEvent e) {
					requiresImageUpdate = true;
					Description.this.updateUI();
				}
				@Override
				public void componentMoved(ComponentEvent e) {}
				@Override
				public void componentShown(ComponentEvent e) {}
				@Override
				public void componentHidden(ComponentEvent e) {}
			});
			this.addMouseListener(
				new MouseListener() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (MouseEvent.BUTTON1 == e.getButton()) {
							var y = e.getY();
							var index = (y - Y_OFFSET) / charHeight;
							var old = selectedRow;
							selectedRow = Optional.of(
								Math.min(index, numberOfSequences-1)
							);
							properties.firePropertyChange(
								"selectedRow",
								old,
								selectedRow
							);
						}
					}
					@Override
					public void mousePressed(MouseEvent e) {}
					@Override
					public void mouseReleased(MouseEvent e) {}
					@Override
					public void mouseEntered(MouseEvent e) {}
					@Override
					public void mouseExited(MouseEvent e) {}
				}
			);
		}
		public void paint(Graphics g) {
			super.paint(g);
			if (requiresImageUpdate) {
				createImage();
				requiresImageUpdate = false;
			}
			g.drawImage((Image) this.biDescriptions, 0, 0, null);
		}

		private Dimension computeImageMetrics() {
			var tmpImage  = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = (Graphics2D) tmpImage.getGraphics();
			g.setFont(AlignmentExplorer.this.getFont());
			var sequences = alignment.getSeq();
			numberOfSequences = sequences.size();
			String maxLengthDesc = getMaxLengthSequence(sequences);
			var textAreaWidth = g.getFontMetrics().stringWidth(maxLengthDesc);
			charHeight = g.getFontMetrics().getHeight();
			var textAreaHeight = charHeight * numberOfSequences;
			return new Dimension(textAreaWidth, textAreaHeight);
		}

		private Dimension initiliazesEmptyImage() {
			Dimension imageDimension = computeImageMetrics();
			biDescriptions = new BufferedImage(
				this.getWidth(),
				Y_OFFSET + imageDimension.height + 10,
				BufferedImage.TYPE_INT_RGB
			);
			return imageDimension;
		}

		private void paintBackground(Dimension imageDimension) {
			Graphics2D g = (Graphics2D) biDescriptions.getGraphics();
			g.setColor(Color.white);
			g.fillRect(
				0,
				0,
				this.getWidth(),
				Y_OFFSET + imageDimension.height + 10
			);
		}

		private void paintText() {
			Graphics2D g = (Graphics2D) biDescriptions.getGraphics();
			g.setColor(Color.black);
			g.setFont(AlignmentExplorer.this.getFont());
			int descent = g.getFontMetrics().getDescent();
			int counter = 0;
			for (Sequence sequence : this.alignment.getSeq()) {
				String desc = sequence.getDescription();
				counter++;
				g.drawString(desc, 5, Y_OFFSET + charHeight * (counter) - descent);
			}
		}

		private void paintSelection(Dimension imageDimension) {
			if (selectedRow.isEmpty()) {
				return;
			}
			Graphics2D g = (Graphics2D) biDescriptions.getGraphics();
			var gradient = new GradientPaint(
				0, 0, new Color(212, 235, 245),
				75, 0, new Color(212, 235, 245)
			);
			g.setPaint(gradient);
			int row = selectedRow.get();
			g.fillRect(
				0, row*charHeight+Y_OFFSET, biDescriptions.getWidth(), charHeight
			);
		}

		private void createImage() {
			var imageDimension = initiliazesEmptyImage();
			paintBackground(imageDimension);
			paintSelection(imageDimension);
			paintText();
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

	private class MainView extends JLabel {
		private static final long serialVersionUID = -3012075092592318198L;
		private Alignment alignment = null;
		private BufferedImage biMainView = null;
		private boolean hasMsaImage = false;
		private Optional<Integer> selectedRow = Optional.empty();

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

		public void updateImage() {
			hasMsaImage = false;
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
				.withHightlightedRegions(hightlightedRegions)
				.withSelectedRow(selectedRow)
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

		public void setSelectedRow(Optional<Integer> selectedRow) {
			this.selectedRow = selectedRow;
		}
	}
}

