package fasdpd.UI.v1.alignmentExplorer;

import java.awt.Color;

public class AlignmentRegion {
  private int startPoint;
  private int endPoint;
  private Color color;

  public AlignmentRegion(int startPoint, int endPoint, Color color) {
    this.startPoint = startPoint;
    this.endPoint = endPoint;
    this.color = color;
  }

  public int getStartPoint() {
    return startPoint;
  }

  public void setStartPoint(int startPoint) {
    this.startPoint = startPoint;
  }

  public int getEndPoint() {
    return endPoint;
  }

  public void setEndPoint(int endPoint) {
    this.endPoint = endPoint;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

}
