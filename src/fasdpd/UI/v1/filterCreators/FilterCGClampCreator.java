package fasdpd.UI.v1.filterCreators;

import filters.Filter;
import filters.singlePrimer.FilterGCClamp;

public class FilterCGClampCreator extends FilterCreator {
  @Override
  public Filter create() {
    return new FilterGCClamp();
  }

  @Override public String toString() {
    return "Single Primer - Keep primers with G or C at 3' end.";
  }
}
