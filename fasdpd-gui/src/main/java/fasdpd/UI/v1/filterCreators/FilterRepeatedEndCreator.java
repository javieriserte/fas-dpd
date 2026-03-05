package fasdpd.UI.v1.filterCreators;
import filters.Filter;
import filters.singlePrimer.FilterRepeatedEnd;
public class FilterRepeatedEndCreator extends FilterCreator{
	@Override public Filter create() {
		return new FilterRepeatedEnd();
	}
	@Override public String toString() { return "Single Primer - Remove Primers With The 3' End Repeated";}	
} 

