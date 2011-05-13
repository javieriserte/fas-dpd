package fasdpd.UI.v1.filterCreators;

import filters.Filter;
import filters.singlePrimer.FilterDegeneratedEnd;

public class FilterDegeneratedEndCreator extends FilterCreator{
	@Override protected Filter create() { return new FilterDegeneratedEnd();}
}