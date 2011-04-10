package tests.sequences;


import sequences.util.gccontent.GCContent;

import junit.framework.TestCase;

public class GCContentTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testCalculateGCContent() {
		
		assertEquals( 0f     	, GCContent.calculateGCContent( "ATTT" ) );
		assertEquals( 1f/4		, GCContent.calculateGCContent( "ATTC" ) );
		assertEquals( 1f/3		, GCContent.calculateGCContent( "ATC"  ) ) ;
		assertEquals( 0.50f		, GCContent.calculateGCContent( "ATCG" ) );
		assertEquals( 0.75f		, GCContent.calculateGCContent( "AGCG" ) );
		assertEquals( 1f		, GCContent.calculateGCContent( "CGCG" ) );
		
	}
	
}
