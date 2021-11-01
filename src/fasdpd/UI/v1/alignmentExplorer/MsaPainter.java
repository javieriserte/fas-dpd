package fasdpd.UI.v1.alignmentExplorer;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import fasdpd.UI.v1.colors.ColoringStrategy;
import fasdpd.UI.v1.colors.DefaultColoringStrategy;
import fasdpd.UI.v1.colors.DnaColoringStrategy;
import fasdpd.UI.v1.colors.ProteinColoringStrategy;
import sequences.Sequence;
import sequences.alignment.Alignment;
import sequences.dna.DNASeq;
import sequences.protein.ProtSeq;

public class MsaPainter implements AbstractMsaPainter {

    private Alignment alignment;
    private Font font;
    private Set<AlignmentRegion> hightlightedRegions = new HashSet<AlignmentRegion>();

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
        BufferedImage image = createEmptyImage(dim);
        Graphics2D g = createGraphics(image);
        ColoringStrategy color = this.getColorStrategy();
        paintBackground(g, dim);
        hightlightedRegions.add(new AlignmentRegion(
            5, 10, new Color(245,255,245))
        );
        paintHighlighting(g);
        paintSequence(g, color);
        return image;
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
        int imageHeight = textHeight * sequences.size();
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
        hightlightedRegions.stream().forEach(
            (region) -> {
                g.setColor(region.getColor());
                int charWidth = g.getFontMetrics().stringWidth(" ");
                int charHeight = g.getFontMetrics().getHeight();
                int height = alignment.getSeq().size() * charHeight-1;
                g.fillRect(
                    (region.getStartPoint()) * charWidth + 5,
                    0,
                    (region.getEndPoint() - region.getStartPoint()) * charWidth,
                    height
                );
                g.setColor(new Color(200,200,200));
                g.drawRect(
                    (region.getStartPoint()) * charWidth + 5,
                    0,
                    (region.getEndPoint() - region.getStartPoint()) * charWidth,
                    height
                );
                // Draw Arraw Shape
                int[] xPoints = new int[]{
                    (region.getStartPoint()) * charWidth + 5,
                    (region.getEndPoint()-1) * charWidth + 5,
                    (region.getEndPoint()-1) * charWidth + 5,
                    (region.getEndPoint()) * charWidth + 5,
                    (region.getEndPoint()-1) * charWidth + 5,
                    (region.getEndPoint()-1) * charWidth + 5,
                    (region.getStartPoint()) * charWidth + 5
                };
                int[] yPointsBase = new int[]{
                    charHeight/3,
                    charHeight/3,
                    0,
                    charHeight/2,
                    charHeight,
                    2*charHeight/3,
                    2*charHeight/3
                };
                IntStream.range(0, alignment.getSeq().size()).forEach(
                    i -> {
                        int[] yPoints = new int[7];
                        for (int j = 0; j < yPoints.length; j++) {
                            yPoints[j] = i*charHeight + yPointsBase[j];
                        }
                        g.fillPolygon(xPoints, yPoints, 7);
                    }
                );
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
                textHeight * (counter - 1),
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
    public AbstractMsaPainter withHightlightedRegions(Set<AlignmentRegion> hightlightedRegions) {
        this.hightlightedRegions.clear();
        this.hightlightedRegions.addAll(hightlightedRegions);
        return this;
    }
}
