package fasdpd.cli;

import cmdGA.parameterType.ParameterType;
/**
 * FloatArrayParameter is an custom extension for ParameterType.
 * Recognizes an array of float value.
 *
 * @see cmdGA.parameterType.ParameterType
 */
public class FloatArrayParameter extends ParameterType {
	// Private Class Variables
	/**
	 * Parses a string fragment from a command line into an array of float values.
	 */
	protected static FloatArrayParameter  singleton = new FloatArrayParameter();

	// Constructor
	/**
	 * No instance variables are used, so there is no need of more than one instance.
	 * A 'singleton' pattern is implemented.

	 * @return the only one instance BooleanParameter
	 */
	public static FloatArrayParameter getParameter() {
		return (FloatArrayParameter) singleton;
	}

	// Protected Method
	/**
	 * Performs the parse operation.
	 * It expect a well formed input String. Does not check if the input is correct.<br>
	 * The expected input is a series of number separated by comma.
	 *
	 * @return an array of Float values. The user of this result
	 * must cast the returned value to the corresponding Class.
	 */
	@Override protected Object parse(String parameter) {
		String[] res = parameter.split(",");
		Float[] res2 = new Float[res.length];
		int counter=0;
		for (String string : res) {
			res2[counter++] = Float.parseFloat(string);
		}
		return res;
	}
}
