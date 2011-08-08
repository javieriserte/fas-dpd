package fasdpd.UI.v1;

import java.awt.Color;

public class ProteinColoringStrategy extends ColoringStrategy{

	@Override
	public Color getColor(char c) {
		Color result = null;
		switch (Character.toUpperCase(c)) {
			case 'G':
			case 'P':
			case 'S':
			case 'T': result = Color.orange; 			break;

			case 'H':
			case 'K':
			case 'R': result = Color.RED;			break;

			case 'F':
			case 'W':
			case 'Y': result = Color.BLUE;			break;
			
			case 'I':
			case 'L':
			case 'M':
			case 'V': result = new Color(0,127,0); 			break;
			
			case '-': result = Color.black; 			break;
			case '·': result = new Color (191,191,191); 			break;

		default:
			result = Color.black; break;
		}
		return result;
	}
	
}
