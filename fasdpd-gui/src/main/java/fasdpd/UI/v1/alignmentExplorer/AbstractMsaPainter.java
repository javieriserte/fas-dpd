package fasdpd.UI.v1.alignmentExplorer;

import sequences.alignment.Alignment;
import java.awt.image.BufferedImage;
import java.util.Optional;
import java.util.Set;
import java.awt.Dimension;
import java.awt.Font;

public interface AbstractMsaPainter {
    public AbstractMsaPainter withFont(Font font);
    public AbstractMsaPainter withAlignment(Alignment aln);
    public AbstractMsaPainter withHightlightedRegions(
        Set<ShapePainter> hightlightedRegions);
    public AbstractMsaPainter defaultDimension(Dimension dim);
    public AbstractMsaPainter withSelectedRow(Optional<Integer> selectedRow);
    public BufferedImage paint();
}
