package tests.sequences;

import java.util.List;

import sequences.util.compare.DegeneratedDNAMatchingStrategy;
import sequences.util.compare.SequenceComparator;
import junit.framework.TestCase;

public class SequenceComparatorTest extends TestCase {
	private String seq1 = "AGT";
	private String seq1a = "AAGT";
	private String seq1b = "AAGTA";
	private String seq2 = "ACT";
	private String seq2a = "AACT";
	
	private String seq3a = "AAAAAAAAACT";
	private String seq3b = "GGGGGGGGAGT";
	
	private String seq4a = "ACTAAAAAAAA";
	private String seq4b = "GGGGGGGGAGT";
	
	private String seq5a = "ACTAAAAAAAA";
	private String seq5b = "AGTGGGGGGGG";

	private String seq6a = "ACTAAAAGTAC";
	private String seq6b = "AGTAAAGTACT";
	
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testGetListOfNonGappedComplementaryRegions() {

		List<String> result = SequenceComparator.getListOfNonGappedComplementaryRegions(seq1,seq2, 12, new DegeneratedDNAMatchingStrategy());
		assertEquals(0, result.size());
		
		result = SequenceComparator.getListOfNonGappedComplementaryRegions(seq1,seq2, 2, new DegeneratedDNAMatchingStrategy());
		assertEquals(1, result.size());
		
		assertEquals("AGT\r|||\rTCA", result.get(0));
		
		result = SequenceComparator.getListOfNonGappedComplementaryRegions(seq1a,seq2, 2, new DegeneratedDNAMatchingStrategy());
		assertEquals(1, result.size());

		assertEquals("AAGT\r |||\r-TCA", result.get(0));
		
		result = SequenceComparator.getListOfNonGappedComplementaryRegions(seq1b,seq2, 2, new DegeneratedDNAMatchingStrategy());
		assertEquals(1, result.size());

		assertEquals("AAGTA\r ||| \r-TCA-", result.get(0));

		result = SequenceComparator.getListOfNonGappedComplementaryRegions(seq1,seq2a, 2, new DegeneratedDNAMatchingStrategy());
		assertEquals(1, result.size());

		assertEquals("AGT-\r||| \rTCAA", result.get(0));

		
		result = SequenceComparator.getListOfNonGappedComplementaryRegions(seq1a,seq2a, 2, new DegeneratedDNAMatchingStrategy());
		assertEquals(1, result.size());

		assertEquals("AAGT-\r ||| \r-TCAA", result.get(0));
		
		result = SequenceComparator.getListOfNonGappedComplementaryRegions(seq3a,seq3b, 2, new DegeneratedDNAMatchingStrategy());
		assertEquals(1, result.size());
		
		assertEquals("AAAAAAAAACT--------\r        |||        \r--------TGAGGGGGGGG", result.get(0));
		
		result = SequenceComparator.getListOfNonGappedComplementaryRegions(seq4a,seq4b, 2, new DegeneratedDNAMatchingStrategy());
		assertEquals(1, result.size());
		
		assertEquals("ACTAAAAAAAA\r|||        \rTGAGGGGGGGG", result.get(0));
		
		result = SequenceComparator.getListOfNonGappedComplementaryRegions(seq5a,seq5b, 2, new DegeneratedDNAMatchingStrategy());
		assertEquals(1, result.size());
		
		
		
		assertEquals("--------ACTAAAAAAAA\r        |||        \rGGGGGGGGTGA--------", result.get(0));

		
		result = SequenceComparator.getListOfNonGappedComplementaryRegions(seq6a,seq6b, 2, new DegeneratedDNAMatchingStrategy());
//		System.out.println(result);
		assertEquals(4, result.size());
		
		
		
	}

	public void testHaveNonGappedComplementaryRegions() {

		assertEquals(true, SequenceComparator.haveNonGappedComplementaryRegions(seq1, seq2, 2, new DegeneratedDNAMatchingStrategy()));
		assertEquals(false, SequenceComparator.haveNonGappedComplementaryRegions(seq1, seq2, 3, new DegeneratedDNAMatchingStrategy()));
		
		assertEquals(true, SequenceComparator.haveNonGappedComplementaryRegions(seq6a,seq6b, 4, new DegeneratedDNAMatchingStrategy()));
		assertEquals(false, SequenceComparator.haveNonGappedComplementaryRegions(seq6a,seq6b, 5, new DegeneratedDNAMatchingStrategy()));		
		
	}

	public void testGetListOfNonGappedComplementaryRegionsWithFixed3End() {

		List<String> result = null;
		
		result = SequenceComparator.getListOfNonGappedComplementaryRegionsWithFixed3End("TACTGGGT", "GAAACCCA", 4, new DegeneratedDNAMatchingStrategy());
		
		assertEquals(1, result.size());
		assertEquals("TACTGGGT---\r   |||||   \r---ACCCAAAG", result.get(0));
		
		result = SequenceComparator.getListOfNonGappedComplementaryRegionsWithFixed3End("TACTGGGTA", "GAAACCCA", 4, new DegeneratedDNAMatchingStrategy());
		
		assertEquals(1, result.size());
		assertEquals("TACTGGGTA--\r   |||||   \r---ACCCAAAG", result.get(0));
		
		result = SequenceComparator.getListOfNonGappedComplementaryRegionsWithFixed3End("TACTGGGTA", "GAAACCCAA", 4, new DegeneratedDNAMatchingStrategy());
		
		assertEquals(0, result.size());
		
	}

	public void testHaveNonGappedComplementaryRegionsWithFixed3End() {
		boolean result;
		
		result = SequenceComparator.haveNonGappedComplementaryRegionsWithFixed3End("TACTGGGT", "GAAACCCA", 4, new DegeneratedDNAMatchingStrategy());
		
		assertEquals(true, result);
		
		result = SequenceComparator.haveNonGappedComplementaryRegionsWithFixed3End("TACTGGGTA", "GAAACCCA", 4, new DegeneratedDNAMatchingStrategy());
		
		assertEquals(true, result);
		
		result = SequenceComparator.haveNonGappedComplementaryRegionsWithFixed3End("TACTGGGTA", "GAAACCCAA", 4, new DegeneratedDNAMatchingStrategy());
		
		assertEquals(false, result);
		
	}

}
