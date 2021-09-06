package fasdpd;

import cmdGA.parameterType.ParameterType;

public class End5v3ParameterType extends ParameterType {

	/**
	 * Parses a string fragment from a command line into an array of float values.
	 */
	protected static End5v3ParameterType singleton = new End5v3ParameterType();

	/**
	 * No instance variables are used, so there is no need of more than one
	 * instance. A 'singleton' pattern is implemented.
	 * 
	 * @return the only one instance BooleanParameter
	 */
	public static End5v3ParameterType getParameter() {
		return (End5v3ParameterType) singleton;
	}

	@Override
	protected Object parse(String parameter) {
		String[] p = parameter.split(",");

		return new Result(
			Double.parseDouble(p[0]),
			Double.parseDouble(p[1]),
			Double.parseDouble(p[2]),
			Integer.parseInt(p[3]));
	}

	public static class Result {
		public double dg;
		public double ktemp;
		public double monov;
		public int len;

		public Result(double dg, double ktemp, double monov, int len) {
			this.dg = dg;
			this.ktemp = ktemp;
			this.monov = monov;
			this.len = len;
		}

	}
}
