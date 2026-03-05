package tests.sequences;

import java.util.HashMap;

import junit.framework.TestCase;
import sequences.dna.DegeneratedPrimerIterator;

public class DegeneratedPrimerIteratorTest extends TestCase {
    protected void setUp() throws Exception {
		super.setUp();
	}

    public void testIterateOverOneCharNonDegenerateSequence() {
        DegeneratedPrimerIterator dpi = new DegeneratedPrimerIterator("A");
        HashMap<String, Integer> collected = new HashMap<String, Integer>();
        dpi.start();
        while (dpi.hasNext()) {
            String current = dpi.next();
            Integer oldValue = collected.getOrDefault(current, 0);
            collected.put(current, oldValue + 1);
        }
        assertTrue(collected.size() == 1);
        assertTrue(collected.containsKey("A"));
    }

    public void testIterateOverOneCharDegenerateSequence() {
        DegeneratedPrimerIterator dpi = new DegeneratedPrimerIterator("N");
        HashMap<String, Integer> collected = new HashMap<String, Integer>();
        dpi.start();
        while (dpi.hasNext()) {
            String current = dpi.next();
            Integer oldValue = collected.getOrDefault(current, 0);
            collected.put(current, oldValue + 1);
        }
        assertTrue(collected.size() == 4);
        assertTrue(collected.containsKey("A"));
        assertTrue(collected.containsKey("C"));
        assertTrue(collected.containsKey("G"));
        assertTrue(collected.containsKey("T"));
    }


    public void testIterateOverTwoCharDegenerateSequence() {
        DegeneratedPrimerIterator dpi = new DegeneratedPrimerIterator("NR");
        HashMap<String, Integer> collected = new HashMap<String, Integer>();
        dpi.start();
        while (dpi.hasNext()) {
            String current = dpi.next();
            Integer oldValue = collected.getOrDefault(current, 0);
            collected.put(current, oldValue + 1);
        }
        assertTrue(collected.size() == 8);
        assertTrue(collected.containsKey("AA"));
        assertTrue(collected.containsKey("CA"));
        assertTrue(collected.containsKey("GA"));
        assertTrue(collected.containsKey("TA"));
        assertTrue(collected.containsKey("AG"));
        assertTrue(collected.containsKey("CG"));
        assertTrue(collected.containsKey("GG"));
        assertTrue(collected.containsKey("TG"));
    }

}
