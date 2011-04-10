package filters.primerpair;

import sequences.dna.Primer;

public class FilterOverlapping extends FilterPrimerPair {

	@Override
	public boolean filter(Primer p1, Primer p2) {

		int s1 = p1.getStart();
		int s2 = p2.getStart();
		int f1 = p1.getEnd();
		int f2 = p2.getEnd();
		
		int maxp1 = Math.max(s1, f1);
		int maxp2 = Math.max(s2, f2);
		int minp1 = Math.min(s1, f1);
		int minp2 = Math.min(s2, f2);
		
		return (minp2>maxp1)||(minp1>maxp2);		
	}

}
