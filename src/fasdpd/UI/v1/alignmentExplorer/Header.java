package fasdpd.UI.v1.alignmentExplorer;

import javax.swing.JLabel;

import fasdpd.UI.v1.colors.ColoringStrategy;
import fasdpd.UI.v1.colors.DnaColoringStrategy;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import sequences.alignment.Alignment;

class Header extends JLabel {
    private static final long serialVersionUID = 1221448808240337613L;
    private Alignment alignment;
    private BufferedImage biHeader = null;
    private AlignmentExplorer parent;
    private int textHeight;
    private int textWidth;

    public Header(
            Alignment alignment,
            AlignmentExplorer parent
            ) {
        super();
        this.alignment = alignment;
        this.parent = parent;
        createImage();
    }
    public void paint(Graphics g) {
        super.paint(g);
        if (biHeader == null) {
            createImage();
        }
        g.drawImage(biHeader, 0, 0, null);
    }

    private void createImage() {
        String sequence = getPiledUpSequence();
        createEmptyImage(sequence.length());
        paintBackground();
        paintRuler(sequence.length());
        paintSequence(sequence);
        this.setPreferredSize(
            new Dimension(
                biHeader.getWidth(),
                biHeader.getHeight()
            )
        );
    }

    private String getPiledUpSequence() {
        String s = null;
        if (parent.geneticCode != null) {
            s = alignment
                .pileUp(parent.geneticCode)
                .getSequence();
        }
        return s;
    }

    private int getTextHeight() {
        return textHeight;
    }

    private int getTextWidth() {
        return textWidth;
    }

    private void computeTextMetrics() {
        BufferedImage tmpImage = new BufferedImage(
            10,
            10,
            BufferedImage.TYPE_INT_RGB
        );
        Graphics2D g = (Graphics2D) tmpImage.getGraphics();
        g.setFont(parent.getFont());
        textHeight = g.getFontMetrics().getHeight() + 5;
        textWidth = g.getFontMetrics().stringWidth("A");
    }

    private Dimension computeImageDimension(int sequenceLength) {
        computeTextMetrics();
        int imageHeight = 3 * getTextHeight() + 8;
        int imageWidth = getTextWidth() * sequenceLength;
        return new Dimension(imageWidth, imageHeight);
    }

    private void createEmptyImage(int sequenceLength) {
        Dimension dim = computeImageDimension(sequenceLength);
        this.biHeader = new BufferedImage(
            dim.width,
            dim.height,
            BufferedImage.TYPE_INT_RGB
        );
    }

    private void printRulerFirstLine(int rulerLength) {
        StringBuilder line = new StringBuilder();
        Graphics2D g = (Graphics2D) biHeader.getGraphics();
        g.setFont(parent.getFont());
        g.setColor(Color.white);
        String base = "''''|";
        int nb = ((rulerLength - 1) / 5) + 1;
        while (nb-- > 0) {
            line.append(base);
        }
        line.delete(rulerLength, line.length());
        g.setFont(parent.getFont());
        g.setColor(Color.black);
        g.drawString(line.toString(), 5, getTextHeight());
    }

    private void printRulerSecondLine(int rulerLength) {
        StringBuilder line = new StringBuilder();
        Graphics2D g = (Graphics2D) this.biHeader.getGraphics();
        int d = ((rulerLength - 1) / 10) + 1;
        int i = 1;
        while (i++ < d) {
            String n = String.valueOf((i - 1) * 10);
            line.append(("          " + n).substring(n.length()));
        }
        g.setFont(parent.getFont());
        g.setColor(Color.black);
        g.drawString(line.toString(), 5, 2*getTextHeight());
    }

    private void paintRuler(int rulerLength) {
        printRulerFirstLine(rulerLength);
        printRulerSecondLine(rulerLength);
    }

    private void paintBackground() {
        Graphics2D g = (Graphics2D) biHeader.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, biHeader.getWidth(), biHeader.getHeight());
    }

    private void paintSequence(String sequence) {
        ColoringStrategy color = new DnaColoringStrategy();
        Graphics2D g = (Graphics2D) biHeader.getGraphics();
        g.setFont(parent.getFont());
        ColoredSequencePrinter.print(
            5,
            2 * getTextHeight(),
            (Graphics2D) g,
            sequence,
            color
        );
    }
};
