package fasdpd.UI.v1.filterCreators;

import filters.Filter;
import filters.singlePrimer.FilterDegeneratedEnd;

public class FilterDegeneratedEndCreator extends FilterCreator{
	@Override
	public Filter create() { return new FilterDegeneratedEnd();}
	@Override public String toString() { return "Filter Degenerated End";}	
}