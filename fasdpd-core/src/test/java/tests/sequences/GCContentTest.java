package tests.sequences;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;


import sequences.util.gccontent.GCContent;

public class GCContentTest {

	@BeforeEach

	protected void setUp() throws Exception {
	}

	@Test

	
	public void testCalculateGCContent() {
		
		assertEquals( 0f     	, GCContent.calculateGCContent( "ATTT" ) );
		assertEquals( 1f/4		, GCContent.calculateGCContent( "ATTC" ) );
		assertEquals( 1f/3		, GCContent.calculateGCContent( "ATC"  ) ) ;
		assertEquals( 0.50f		, GCContent.calculateGCContent( "ATCG" ) );
		assertEquals( 0.75f		, GCContent.calculateGCContent( "AGCG" ) );
		assertEquals( 1f		, GCContent.calculateGCContent( "CGCG" ) );
		
	}
	
}
