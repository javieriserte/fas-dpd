package fasdpd.UI.v1.alignmentExplorer;
import java.awt.Graphics2D;

import fasdpd.UI.v1.colors.ColoringStrategy;


public class ColoredSequencePrinter {
    public static void print(
            int x,
            int y,
            Graphics2D g,
            String sequence,
            ColoringStrategy color) {
        int textHeight = g.getFontMetrics().getHeight();
		int charWidth = g.getFontMetrics().stringWidth("A");
		for (int i = 0; i < sequence.length(); i++) {
			String charbase = sequence.substring(i, i + 1);
			g.setColor(color.getColor(charbase.charAt(0)));
			g.drawString(charbase, x + charWidth * i, y + textHeight);
		}
    }
}
