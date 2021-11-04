package fasdpd.UI.v1.alignmentExplorer;

import java.awt.Color;
import java.awt.Graphics2D;

public interface ShapePainter {
  public void paintShape(
    Graphics2D g,
    int offsetX,
    int offsetY,
    int height
  );
  public ShapePainter setStart(int start);
  public ShapePainter setEnd(int end);
  public ShapePainter setColor(Color color);
  public ShapePainter setOnTopBar(boolean onTopBar);
  public boolean isOnTopBar();
}
