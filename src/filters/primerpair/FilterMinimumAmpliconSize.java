package filters.primerpair;

import sequences.dna.Primer;

public class FilterMinimumAmpliconSize extends FilterPrimerPair {
  int minAcceptedSize;
  
  public FilterMinimumAmpliconSize(int minAcceptedSize) {
    this.minAcceptedSize = minAcceptedSize;
  }
  
  @Override
  public boolean filter(Primer p1, Primer p2) {
    if (p1.isDirectStrand() == p2.isDirectStrand()) {
      return false;
    }
    int s1 = p1.getStart();
    int s2 = p2.getStart();
    int f1 = p1.getEnd();
    int f2 = p2.getEnd();
  
    boolean primer1IsLeftToRight = s1 < f1;
    boolean primer2IsRightToLeft = s2 > f2;
    boolean primer1StartsBeforePrimer2Start = s2 > s1;
        
    boolean correctOrientation = (
        primer2IsRightToLeft &&
        primer1IsLeftToRight &&
        primer1StartsBeforePrimer2Start
      ) || (
        !primer2IsRightToLeft &&
        !primer1IsLeftToRight &&
        !primer1StartsBeforePrimer2Start
      );
    return correctOrientation && (Math.abs(s1 - s2) + 1 >= minAcceptedSize);
  }
  
  @Override public String toString() {
    return "FilterMinimumAmpliconSize [minAcceptedSize=" + minAcceptedSize+ "]";
  }

}
