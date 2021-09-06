package filters.singlePrimer;

import sequences.dna.Primer;

public class FilterGCClamp extends FilterSinglePrimer{

  @Override
  public boolean filter(Primer p) {
    return p
      .getSequence()
      .toUpperCase()
      .endsWith("C") ||
      p.getSequence()
      .toUpperCase()
      .endsWith("G");
  }
}
