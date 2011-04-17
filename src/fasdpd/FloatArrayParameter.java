package fasdpd;

import cmdGA.parameterType.ParameterType;

public class FloatArrayParameter extends ParameterType {

	/**
	 * Mod1
	 */
	protected static FloatArrayParameter  singleton = new FloatArrayParameter();
	/**
	 * No instance variables are used, so there is no need of more than one instance.
	 * A 'singleton' pattern is implemented. 

	 * @return the only one instance BooleanParameter
	 */	
	public static FloatArrayParameter getParameter() {
		return (FloatArrayParameter) singleton;
	}
	/**
	 * Parse method
	 * 
	 * @return an array of Float.
	 */
	@Override
	protected Object parse(String parameter) {
		String[] res = parameter.split(",");
		Float[] res2 = new Float[res.length];
		int counter=0;
		for (String string : res) {
			res2[counter++] = Float.parseFloat(string);
		}
		return res;
	}
}
