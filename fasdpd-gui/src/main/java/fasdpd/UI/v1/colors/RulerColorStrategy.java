package fasdpd.UI.v1.colors;
import java.awt.Color;
public class RulerColorStrategy implements ColoringStrategy {
  @Override
  public Color getColor(char c) {
    Color color;
    switch (c) {
      case '|' : color = Color.black;
      break;
      case '·' : color = Color.gray;
      break;
      default : color = Color.black;
    }
    return color;
  }
}
