package filters.primerpair;

import sequences.dna.Primer;

public class FilterOverlapping extends FilterPrimerPair {

	/**
	 * Two possible cases can be true.<br>
	 * If primers are:
	 * <pre>
	 * Primer 1 : |--------->
	 *            s1       f1
	 *
	 * Primer 2 : |--------->
	 *            s2       f2
	 * </pre>
	 * The first case is:
	 * <pre>
	 * |--------->             <---------|
	 * s1       f1             f2       s2
	 * </pre>
	 * Then: s2>f2; f2>f1; f1>s1;<br>
	 *
	 * The second case is:
	 * <pre>
	 * |--------->             <---------|
	 * s2       f2             f1       s1
	 * </pre>
	 * Then: s1>f1; f1>f2; f2>s2;
	 */
	@Override
	public boolean filter(Primer p1, Primer p2) {
		int s1 = p1.getStart();
		int s2 = p2.getStart();
		int f1 = p1.getEnd();
		int f2 = p2.getEnd();
		return s1 > f1 && f1 > f2 && f2 > s2  ||
			s2 > f2 && f2 > f1 && f1 > s1;
	}

	@Override public String toString() {
		return "FilterOverlapping []";
	}
}
