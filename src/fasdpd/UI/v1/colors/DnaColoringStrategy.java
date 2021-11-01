package fasdpd.UI.v1.colors;

import java.awt.Color;

public class DnaColoringStrategy implements ColoringStrategy{

	@Override public Color getColor(char c) {
		Color result = null;
		switch (c) {
		case 'A':
		case 'a': result = new Color(0,127,0); break;
		case 'C':
		case 'c': result = new Color(0,0,255); break;
		case '-':
		case 'G':
		case 'g': result = new Color(0,0,0); break;
		case 'T':
		case 't': result = new Color(255,0,0); break;
		default: result = new Color(255,0,255); break;
		};
		return result;
	}
}
