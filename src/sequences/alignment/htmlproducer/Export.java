package sequences.alignment.htmlproducer;

import java.util.HashMap;
import java.util.Map;

@Deprecated
public abstract class Export {
	protected String defaultcolor;
	protected Map<Character,String> seqTocol = new HashMap<Character,String>();
	protected abstract String getLetters(String temp, char c);
	protected String getFontColor(char c) {
		String col = seqTocol.get(c);
		if (col==null) col = defaultcolor;
		return col;
	}
}