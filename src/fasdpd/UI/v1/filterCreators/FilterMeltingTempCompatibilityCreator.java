package fasdpd.UI.v1.filterCreators;
import sequences.util.tmcalculator.SantaluciaTmEstimator;
import cmdGA.exceptions.IncorrectParameterTypeException;
import cmdGA.parameterType.DoubleParameter;
import cmdGA.parameterType.ParameterType;
import filters.Filter;
import filters.primerpair.FilterMeltingTempCompatibility;
public class FilterMeltingTempCompatibilityCreator extends FilterCreator{
	public FilterMeltingTempCompatibilityCreator() {
		parametersComments = new String[] {
			"max Value:"
		};
		parametersTypes = new ParameterType[] {
			new DoubleParameter()
		};
		parametersValues = new String[] {"5"};
	}
	@Override public Filter create() {
		Double p0=null;
		try {
			p0 = (Double) parametersTypes[0]
				.parseParameter(parametersValues[0]);
		} catch (IncorrectParameterTypeException e) {
			e.printStackTrace();
		}
		return new FilterMeltingTempCompatibility(
			p0,
			new SantaluciaTmEstimator()
		);
	}
	@Override public String toString() {
		return "Primer Pair - Keep Primers Whose Difference In Melting Temperature Is Less Than "
		+ this.parametersValues[0]
		+ "°C";
	}
}
