package tests.fasdpd;

import java.util.List;

import fasdpd.PriorityList;

import junit.framework.TestCase;

/**
 * Test Case.
 * Unfinished.
 * @author "Javier Iserte <jiserte@unq.edu.ar>"
 * 
 */
public class PriorityListTest extends TestCase {
	private PriorityList<Integer> lp1;
	
	protected void setUp() throws Exception {
		super.setUp();
		lp1 = new PriorityList<Integer>(10);
	}

	public void testListaDePrioridad() {
		assertSame(PriorityList.class,lp1.getClass());
	}


	public void testAddValue() {
		lp1.addValue(1);
		lp1.addValue(2);
		lp1.addValue(3);
		lp1.addValue(4);
		lp1.addValue(5);
		lp1.addValue(6);
		lp1.addValue(7);
		lp1.addValue(8);
		lp1.addValue(9);
		lp1.addValue(10);
		lp1.addValue(11);
		lp1.addValue(0);
		lp1.addValue(12);
		
		System.out.println(lp1.getCurrentFilled());
		
		assertEquals(10, lp1.getCurrentFilled());
	}

	public void testExtractSortedList() {
		lp1.addValue(1);
		lp1.addValue(2);
		lp1.addValue(3);
		lp1.addValue(4);
		lp1.addValue(5);
		lp1.addValue(6);
		lp1.addValue(7);
		lp1.addValue(8);
		lp1.addValue(9);
		lp1.addValue(10);
		lp1.addValue(11);
		lp1.addValue(0);
		lp1.addValue(12);
		
		List<Integer> l = lp1.ExtractSortedList(); 
		 
		assertEquals(12, (int) l.get(0));
		assertEquals(11, (int) l.get(1));
		assertEquals(10, (int) l.get(2));
		assertEquals(9, (int) l.get(3));
		assertEquals(8, (int) l.get(4));
		assertEquals(7, (int) l.get(5));
		assertEquals(6, (int) l.get(6));
		assertEquals(5, (int) l.get(7));
		assertEquals(4, (int) l.get(8));
		assertEquals(3, (int) l.get(9));
		
		
	}

}
