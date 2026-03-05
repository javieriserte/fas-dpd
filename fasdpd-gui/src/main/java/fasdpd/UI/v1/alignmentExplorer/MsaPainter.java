package fasdpd.UI.v1.alignmentExplorer;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import fasdpd.UI.v1.colors.ColoringStrategy;
import fasdpd.UI.v1.colors.DefaultColoringStrategy;
import fasdpd.UI.v1.colors.DnaColoringStrategy;
import fasdpd.UI.v1.colors.ProteinColoringStrategy;
import sequences.Sequence;
import sequences.alignment.Alignment;
import sequences.dna.DNASeq;
import sequences.protein.ProtSeq;

public class MsaPainter implements AbstractMsaPainter {
    private static final int Y_OFFSET = 20;
    private Alignment alignment;
    private Font font;
    private Set<ShapePainter> highlights = new HashSet<ShapePainter>();
    private Optional<Integer> selectedRow = Optional.empty();
    private Dimension imageDimension;

    @Override
    public AbstractMsaPainter withAlignment(Alignment aln) {
        this.alignment = aln;
        return this;
    }

    @Override
    public AbstractMsaPainter defaultDimension(Dimension dim) {
        return this;
    }

    @Override
    public BufferedImage paint() {
        Dimension dim = computeImageDimension();
        imageDimension = dim;
        BufferedImage image = createEmptyImage(dim);
        Graphics2D g = createGraphics(image);
        ColoringStrategy color = this.getColorStrategy();
        paintBackground(g, dim);
        paintSelectedRow(g, selectedRow);
        paintHighlighting(g);
        paintSequence(g, color);
        return image;
    }


    private void paintSelectedRow(
            Graphics2D g,
            Optional<Integer> selectedRow) {
        if (!selectedRow.isPresent()) {
            return;
        }
        g.setColor(new Color(212, 235, 245));
        int index = selectedRow.get();
        int textHeight = g.getFontMetrics().getHeight();
        g.fillRect(0, Y_OFFSET + textHeight*index, imageDimension.width, textHeight);
    }

    private Dimension computeImageDimension() {
        List<Sequence> sequences = this.alignment.getSeq();
        BufferedImage biMainView = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) biMainView.getGraphics();
        g.setFont(font);
        int textWidth = g.getFontMetrics()
            .stringWidth(sequences.get(0).getSequence());
        int textHeight = g.getFontMetrics().getHeight();
        int imageWidth = textWidth * sequences.get(0).sizeInBases() + 10;
        int imageHeight = textHeight * sequences.size() + Y_OFFSET;
        return new Dimension(imageWidth, imageHeight);
    }

    private BufferedImage createEmptyImage(Dimension dim) {
        BufferedImage image = new BufferedImage(
            dim.width,
            dim.height,
            BufferedImage.TYPE_INT_RGB
        );
        return image;
    }

    private Graphics2D createGraphics(BufferedImage image) {
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setFont(font);
        return g;
    }

    private ColoringStrategy getColorStrategy() {
        if (isMolTypeDNA()) {
            return new DnaColoringStrategy();
        } else if (isMolTypeProtein()) {
            return new ProteinColoringStrategy();
        }
        return new DefaultColoringStrategy();
    }

	public boolean isMolTypeProtein() {
		return this.alignment
            .getSeq()
            .get(0)
            .getClass() == ProtSeq.class;
	}

	public boolean isMolTypeDNA() {
		return this.alignment
            .getSeq()
            .get(0)
            .getClass() == DNASeq.class;
	}

    private void paintBackground(Graphics2D g, Dimension dim) {
        g.setColor(Color.white);
        g.fillRect(0, 0, dim.width, dim.height);
    }

    private void paintHighlighting(Graphics2D g) {
        int charHeight = g.getFontMetrics().getHeight();
        int height = alignment.getSeq().size() * charHeight-1;
        highlights.stream().forEach(
            (region) -> {
                int offset = region.isOnTopBar()?0:Y_OFFSET;
                region.paintShape(g, 5, offset, height);
            }
        );
    }

    private void paintSequence(
            Graphics2D g,
            ColoringStrategy color) {
        int counter = 0;
        int textHeight = g.getFontMetrics().getHeight();
        for (Sequence sequence : this.alignment.getSeq()) {
            String desc = sequence.getPrintableSequencePaddedToNucleotide();
            counter++;
            ColoredSequencePrinter.print(
                5,
                Y_OFFSET + textHeight * (counter - 1),
                g,
                desc,
                color
            );
        }
    }

    @Override
    public AbstractMsaPainter withFont(Font font) {
        this.font = font;
        return this;
    }

    @Override
    public AbstractMsaPainter withHightlightedRegions(Set<ShapePainter> hightlightedRegions) {
        this.highlights.clear();
        this.highlights.addAll(hightlightedRegions);
        return this;
    }

    @Override
    public AbstractMsaPainter withSelectedRow(Optional<Integer> selectedRow) {
        this.selectedRow = selectedRow;
        return this;
    }
}
