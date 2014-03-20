package fasdpd;

import cmdGA2.returnvalues.ReturnValueParser;

public class FloatArrayValue extends ReturnValueParser<Float[]> {

	@Override
	public Float[] parse(String token) {
		String[] elements = token.trim().split(",");
		Float[] returnValues = new Float[elements.length];
		
		for (int i =0; i<elements.length;i++) {
			returnValues[i] = Float.valueOf(elements[i].trim());
		}
		return returnValues;
		
	}

}
