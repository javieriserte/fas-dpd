package fasdpd.UI.v1.filterCreators;
import filters.Filter;
import filters.primerpair.FilterOverlapping;
public class FilterOverlappingCreator extends FilterCreator{
	@Override public Filter create() {
		return new FilterOverlapping();
	}
	@Override public String toString() { return "Primer Pair - Remove Overlapping Primers";}	
} 

