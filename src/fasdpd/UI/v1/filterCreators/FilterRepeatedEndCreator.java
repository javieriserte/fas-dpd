package fasdpd.UI.v1.filterCreators;
import cmdGA.exceptions.IncorrectParameterTypeException;
import cmdGA.parameterType.FloatParameter;
import cmdGA.parameterType.ParameterType;
import filters.Filter;
import filters.singlePrimer.FilterCGContent;
import filters.singlePrimer.FilterRepeatedEnd;
public class FilterRepeatedEndCreator extends FilterCreator{
	@Override protected Filter create(){ 
		return new FilterRepeatedEnd();
	}
} 