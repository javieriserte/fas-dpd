package fasdpd.cli;

import cmdGA2.returnvalues.ReturnValueParser;

public class End5v3Value extends ReturnValueParser<End5v3Value.Result> {

	@Override
	public End5v3Value.Result parse(String token) {
		String[] p = token.split(",");

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
