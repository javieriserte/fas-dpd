package tests.sequences;

import sequences.dna.Primer;
import sequences.util.tmcalculator.SimpleTmEstimator;
import junit.framework.TestCase;

public class SimpleTmEstimatorTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testMean() {
		
		SimpleTmEstimator stme = new SimpleTmEstimator();
		
		Primer p1 = new Primer("A","",1,1,true);
		Primer p2 = new Primer("C","",1,1,true);
		Primer p3 = new Primer("T","",1,1,true);
		Primer p4 = new Primer("G","",1,1,true);
		
		Primer p11 = new Primer("AA","",1,1,true);
		Primer p21 = new Primer("AC","",1,1,true);
		Primer p31 = new Primer("AT","",1,1,true);
		Primer p41 = new Primer("AG","",1,1,true);
		
		Primer p12 = new Primer("AN","",1,1,true);
		Primer p22 = new Primer("AW","",1,1,true);
		Primer p32 = new Primer("AB","",1,1,true);
		Primer p42 = new Primer("AS","",1,1,true);

		
		stme.calculateTM(p1); assertEquals(2f,stme.mean());
		stme.calculateTM(p2); assertEquals(4f,stme.mean());
		stme.calculateTM(p3); assertEquals(2f,stme.mean());
		stme.calculateTM(p4); assertEquals(4f,stme.mean());

		stme.calculateTM(p11); assertEquals(4f,stme.mean());
		stme.calculateTM(p21); assertEquals(6f,stme.mean());
		stme.calculateTM(p31); assertEquals(4f,stme.mean());
		stme.calculateTM(p41); assertEquals(6f,stme.mean());
		
		stme.calculateTM(p12); assertEquals(5f,stme.mean());
		stme.calculateTM(p22); assertEquals(4f,stme.mean());
		stme.calculateTM(p32); assertEquals((8f/3+2f/3+2),stme.mean());
		stme.calculateTM(p42); assertEquals(6f,stme.mean());

	}

	public void testMax() {
		SimpleTmEstimator stme = new SimpleTmEstimator();
		
		Primer p1 = new Primer("A","",1,1,true);
		Primer p2 = new Primer("C","",1,1,true);
		Primer p3 = new Primer("T","",1,1,true);
		Primer p4 = new Primer("G","",1,1,true);
		
		Primer p11 = new Primer("AA","",1,1,true);
		Primer p21 = new Primer("AC","",1,1,true);
		Primer p31 = new Primer("AT","",1,1,true);
		Primer p41 = new Primer("AG","",1,1,true);
		
		Primer p12 = new Primer("AN","",1,1,true);
		Primer p22 = new Primer("AW","",1,1,true);
		Primer p32 = new Primer("AB","",1,1,true);
		Primer p42 = new Primer("AS","",1,1,true);

		
		stme.calculateTM(p1); assertEquals(2f,stme.max());
		stme.calculateTM(p2); assertEquals(4f,stme.max());
		stme.calculateTM(p3); assertEquals(2f,stme.max());
		stme.calculateTM(p4); assertEquals(4f,stme.max());

		stme.calculateTM(p11); assertEquals(4f,stme.max());
		stme.calculateTM(p21); assertEquals(6f,stme.max());
		stme.calculateTM(p31); assertEquals(4f,stme.max());
		stme.calculateTM(p41); assertEquals(6f,stme.max());
		
		stme.calculateTM(p12); assertEquals(6f,stme.max());
		stme.calculateTM(p22); assertEquals(4f,stme.max());
		stme.calculateTM(p32); assertEquals(6f,stme.max());
		stme.calculateTM(p42); assertEquals(6f,stme.max());
	}

	public void testMin() {
		SimpleTmEstimator stme = new SimpleTmEstimator();
		
		Primer p1 = new Primer("A","",1,1,true);
		Primer p2 = new Primer("C","",1,1,true);
		Primer p3 = new Primer("T","",1,1,true);
		Primer p4 = new Primer("G","",1,1,true);
		
		Primer p11 = new Primer("AA","",1,1,true);
		Primer p21 = new Primer("AC","",1,1,true);
		Primer p31 = new Primer("AT","",1,1,true);
		Primer p41 = new Primer("AG","",1,1,true);
		
		Primer p12 = new Primer("AN","",1,1,true);
		Primer p22 = new Primer("AW","",1,1,true);
		Primer p32 = new Primer("AB","",1,1,true);
		Primer p42 = new Primer("AS","",1,1,true);

		
		stme.calculateTM(p1); assertEquals(2f,stme.min());
		stme.calculateTM(p2); assertEquals(4f,stme.min());
		stme.calculateTM(p3); assertEquals(2f,stme.min());
		stme.calculateTM(p4); assertEquals(4f,stme.min());

		stme.calculateTM(p11); assertEquals(4f,stme.min());
		stme.calculateTM(p21); assertEquals(6f,stme.min());
		stme.calculateTM(p31); assertEquals(4f,stme.min());
		stme.calculateTM(p41); assertEquals(6f,stme.min());
		
		stme.calculateTM(p12); assertEquals(4f,stme.min());
		stme.calculateTM(p22); assertEquals(4f,stme.min());
		stme.calculateTM(p32); assertEquals(4f,stme.min());
		stme.calculateTM(p42); assertEquals(6f,stme.min());

	}

}
