package fasdpd.UI.v1.alignmentExplorer;

import sequences.alignment.Alignment;
import java.awt.image.BufferedImage;
import java.util.Set;
import java.awt.Dimension;
import java.awt.Font;

public interface AbstractMsaPainter {
    public AbstractMsaPainter withFont(Font font);
    public AbstractMsaPainter withAlignment(Alignment aln);
    public AbstractMsaPainter withHightlightedRegions(
        Set<ShapePainter> hightlightedRegions);
    public AbstractMsaPainter defaultDimension(Dimension dim);
    public BufferedImage paint();
}
