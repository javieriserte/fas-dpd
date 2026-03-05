package fasdpd.UI.v1.alignmentExplorer;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Arrays;
import java.awt.Color;

public class ArrowPainter implements ShapePainter {
  int start;
  int end;
  Color color;
  private boolean onTopBar;
  private boolean leftToRight;

  public ArrowPainter(boolean leftToRight) {
    this.start = 1;
    this.end = 1;
    this.color = Color.BLACK;
    this.onTopBar = true;
    this.leftToRight = leftToRight;

  }

  @Override
  public void
    paintShape(
      Graphics2D g,
      int offsetX,
      int offsetY,
      int height
    ) {
      g.setColor(color);
      int charWidth = g.getFontMetrics().charWidth(' ');
      int charHeight = g.getFontMetrics().getHeight();
      int[] xPoints = new int[]{
        (start-1) * charWidth + offsetX,
        (end-1) * charWidth + offsetX,
        (end-1) * charWidth + offsetX,
        (end) * charWidth + offsetX,
        (end-1) * charWidth + offsetX,
        (end-1) * charWidth + offsetX,
        (start-1) * charWidth + offsetX
    };
    if (!leftToRight) {
      xPoints = flipHorizontal(xPoints);
    }
    int[] yPoints = new int[]{
        offsetY + charHeight/3,
        offsetY + charHeight/3,
        offsetY + 2,
        offsetY + charHeight/2,
        offsetY + charHeight-2,
        offsetY + 2*charHeight/3,
        offsetY + 2*charHeight/3
    };
    g.setRenderingHint(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON
    );
    g.fillPolygon(xPoints, yPoints, 7);
  }

  public int[] flipHorizontal(int[] xPoints) {
    double minX = Double.MAX_VALUE;
    double maxX = Double.MIN_VALUE;
    for (int x : xPoints) {
      minX = Double.min((double)x, minX);
      maxX = Double.max((double)x, maxX);
    }
    final double midX = (int) (maxX + minX)/2;
    return Arrays.stream(xPoints).map(
      i -> (int) (2*midX - (double)i)
    ).toArray();
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
