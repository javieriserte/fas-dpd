package fasdpd.UI.v1.alignmentExplorer;

import java.awt.Color;
import java.awt.Graphics2D;

public class BoxPainter implements ShapePainter {
  int start;
  int end;
  Color color;
  private boolean onTopBar;

  @Override
  public void
    paintShape(
      Graphics2D g,
      int offsetX,
      int offsetY,
      int height
      ) {
        g.setColor(this.color);
        int charWidth = g.getFontMetrics().stringWidth(" ");
        g.fillRect(
            (start-1) * charWidth + offsetX,
            offsetY,
            (end - start+1) * charWidth,
            height
        );
        g.setColor(new Color(200,200,200));
        g.drawRect(
            (start-1) * charWidth + offsetX,
            offsetY,
            (end - start+1) * charWidth,
            height
        );
    }

  @Override
  public ShapePainter setStart(int start) {
    this.start = start;
    return this;
  }

  @Override
  public ShapePainter setEnd(int end) {
    this.end = end;
    return this;
  }

  @Override
  public ShapePainter setColor(Color color) {
    this.color = color;
    return this;
  }

  @Override
  public ShapePainter setOnTopBar(boolean onTopBar) {
    this.onTopBar = onTopBar;
    return this;
  }

  @Override
  public boolean isOnTopBar() {
    return this.onTopBar;
  }
}
