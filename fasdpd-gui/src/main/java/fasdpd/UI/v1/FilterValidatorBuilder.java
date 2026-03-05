package fasdpd.UI.v1;

import java.util.List;

import fasdpd.UI.v1.filterCreators.FilterCreator;
import filters.Filter;
import filters.primerpair.FilterOverlapping;
import filters.primerpair.FilterPrimerPair;
import filters.singlePrimer.FilterSinglePrimer;
import filters.validator.ValidateAlways;
import filters.validator.ValidateForFilterPrimerPair;
import filters.validator.ValidateForFilterSinglePrimer;
import filters.validator.Validate_AND;
import filters.validator.Validator;

public class FilterValidatorBuilder {
  Validator filterSinglePrimers;
  Validator filterPrimerPairs;

  public FilterValidatorBuilder() {
    filterSinglePrimers = new ValidateAlways();
    filterPrimerPairs = new ValidateAlways();
  };

  public FilterValidatorBuilder add(List<FilterCreator> filterCreators) {
    for (FilterCreator fc : filterCreators) {
      add(fc);
    }
    return this;
  }

  public FilterValidatorBuilder add(FilterCreator filterCreator) {
    Filter filter = filterCreator.create();
    if (filter.isSinglePrimerFilter()) {
      filterSinglePrimers = new Validate_AND(
        filterSinglePrimers,
        new ValidateForFilterSinglePrimer((FilterSinglePrimer) filter));
    } else {
      filterPrimerPairs = new Validate_AND(
        filterPrimerPairs,
        new ValidateForFilterPrimerPair((FilterPrimerPair) filter));
    }
    return this;
  }

  public Validator getSinglePrimerValidator() {
    return filterSinglePrimers;
  }

  public Validator getPrimerPairValidator() {
    return new Validate_AND(
      filterPrimerPairs,
      new ValidateForFilterPrimerPair(new FilterOverlapping()));
  }

}
