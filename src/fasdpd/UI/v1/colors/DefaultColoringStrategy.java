package fasdpd.UI.v1.colors;
import java.awt.Color;
public class DefaultColoringStrategy implements ColoringStrategy {
  @Override
  public Color getColor(char c) {
    return Color.BLACK;
  }
}
