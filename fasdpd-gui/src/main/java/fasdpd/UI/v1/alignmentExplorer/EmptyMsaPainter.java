package fasdpd.UI.v1.alignmentExplorer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Optional;
import java.util.Set;

import sequences.alignment.Alignment;

public class EmptyMsaPainter implements AbstractMsaPainter {
    private Dimension dimension;
    @Override
    public AbstractMsaPainter withAlignment(Alignment aln) {
        return this;
    }

    @Override
    public BufferedImage paint() {
        if (dimension == null) {
            return null;
        }
        int w = dimension.width;
        int h = dimension.height;
        if (!(w > 0 && h > 0)) {
            return null;
        }
        BufferedImage image = new BufferedImage(
            w,
            h,
            BufferedImage.TYPE_INT_RGB
        );
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setFont(new Font("Verdana", 0, 20));
        String text = "No MSA data";
        int textHeight = g.getFontMetrics().getHeight();
        int textWidth = g.getFontMetrics().stringWidth(text);
        g.setColor(
            new Color(1.0f, 1.0f, 1.0f)
        );
        g.fillRect(0, 0, w, h);
        g.setColor(
            new Color(127, 127, 127)
        );
        g.drawString(
            text,
            (w - textWidth) / 2,
            (h - textHeight) / 2
        );
        return image;
    }

    @Override
    public AbstractMsaPainter defaultDimension(Dimension dimension) {
        this.dimension = dimension;
        return this;
    }

    @Override
    public AbstractMsaPainter withFont(Font font) {
        return this;
    }

    @Override
    public AbstractMsaPainter withHightlightedRegions(Set<ShapePainter> hightlightedRegions) {
        return this;
    }

    @Override
    public AbstractMsaPainter withSelectedRow(Optional<Integer> selectedRow) {
        return this;
    }
}
