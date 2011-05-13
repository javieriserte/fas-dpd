package fasdpd.UI.v1.filterCreators;
import cmdGA.exceptions.IncorrectParameterTypeException;
import cmdGA.parameterType.FloatParameter;
import cmdGA.parameterType.ParameterType;
import filters.Filter;
import filters.primerpair.FilterOverlapping;
import filters.singlePrimer.FilterCGContent;
public class FilterOverlappingCreator extends FilterCreator{
	@Override protected Filter create(){ 
		return new FilterOverlapping();
	}
} 
