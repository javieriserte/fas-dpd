package sequences.util.compare;

import java.util.List;
import java.util.Vector;

import sequences.dna.DNASeq;

public class SequenceComparator {
	
	/**
	 * Looks for complementary between two sequences.
	 * Asumes that the sequences are Uppercase and non-degenerated. 
	 * The two sequences are compared straightforward. 
	 * 
	 * @return a list of subsequences that are complementary.
	 * @since 1.1.2
	 */
	static public List<String> getListOfNonGappedComplementaryRegions(String seq1, String seq2, int largerthan, MatchingStrategy ms) {
		// STEP TWO : LOOK FOR THE VALUES GREATER THAN 'largerthan'
		
		char[] s1 = seq1.toCharArray();
		char[] s2 = DNASeq.reverse(seq2).toCharArray();
		return analizeMatrixAndRetrieveList(s1, s2, largerthan, fillMatrix(s1, s2, ms));
	}

	/**
	 * Looks for complementary between two sequences.
	 * Asumes that the sequences are Uppercase and non-degenerated. 
	 * @return true if exist a segment of complementarity larger than 'largerthan'.
	 * @since 1.1.2
	 */
	static public boolean haveNonGappedComplementaryRegions(String seq1, String seq2, int largerthan, MatchingStrategy ms) {
		// STEP TWO : LOOK FOR THE VALUES GREATER THAN 'largerthan'
		
		char[] s1 = seq1.toCharArray();
		char[] s2 = DNASeq.reverse(seq2).toCharArray();		
		return analizeMatrix(s1, s2, largerthan, fillMatrix(s1, s2, ms));
	}
	
	/**
	 * Looks for complementary between two sequences with the 3' end of one secuence fixed.
	 * Asumes that the sequences are Uppercase and non-degenerated. 
	 * @return a list of subsequences that are complementary.
	 * @since 1.1.2
	 */
	static public List<String> getListOfNonGappedComplementaryRegionsWithFixed3End(String seq1, String seq2, int largerthan, MatchingStrategy ms) {
		// STEP TWO : LOOK FOR THE VALUES GREATER THAN 'largerthan'
		char[] s1 = seq1.toCharArray();
		char[] s2 = DNASeq.reverse(seq2).toCharArray();		
		return analizeMatrixWithFixed3EndAndRetrieveList(s1, s2, largerthan, fillMatrix(s1, s2, ms));
	}
	
	/**
	 * Looks for complementary between two sequences with the 3' end of one secuence fixed.
	 * Asumes that the sequences are Uppercase and non-degenerated. 
	 * @return true if exist a segment of complementarity larger than 'largerthan'.
	 * @since 1.1.2
	 */
	static public boolean haveNonGappedComplementaryRegionsWithFixed3End(String seq1, String seq2, int largerthan, MatchingStrategy ms) {
		
		// STEP TWO : LOOK FOR THE VALUES GREATER THAN 'largerthan'
		char[] s1 = seq1.toCharArray();
		char[] s2 = DNASeq.reverse(seq2).toCharArray();		
		return analizeMatrixWithFixed3End(s1, s2, largerthan, fillMatrix(s1, s2, ms));
	}
	
	
	// PRIVATE METHODS
	
	/**
	 * Creates a matrix for represent the segments of complementarity between two sequences.
	 * 
	 */
	private static double[][] fillMatrix(char[] seq1, char[] seq2, MatchingStrategy ms) {
		double[][] m = new double[seq1.length+1][seq2.length+1];
		
		// STEP ZERO : INITIALIZE
		for(int i=0;i<=seq1.length;i++) m[i][0] = 0;
		for(int i=0;i<=seq2.length;i++) m[0][i] = 0;

		
		// STEP ONE : FILL THE MATRIX
		for(int i=1;i<=seq1.length;i++) {
			for(int j=1;j<=seq2.length;j++) {
				
				double v = ms.matches(seq1[i-1],seq2[j-1]);
				m[i][j] = (v != 0 ? m[i-1][j-1] + v : 0);
			}
		}
		return m;
	}
	
	/**
	 * Given a matrix with matching values for two sequences, retrieves a list of String.
	 * Each String represents a alignment of the two subsequences in which the two sequences has more than 'largerThan' consecutive matching bases.
	 *  
	 * @param seq1 First sequence.
	 * @param seq2 Second sequence.
	 * @param largerthan cut-off value.
	 * @param ds a matrix containing matching values. 
	 * @return a list of String repreting each one an alignment.
	 */
	private static List<String> analizeMatrixAndRetrieveList(char[] seq1, char[] seq2, int largerthan, double[][] ds) {
		List<String> result = new Vector<String>();
		
		for(int i=seq1.length;i>0;i--) {
			for(int j=seq2.length;j>0;j--) {
				if (ds[i][j] > largerthan) {
					
					int k = i; int l = j;
					
					while (ds[k][l]>0) ds[k--][l--]=0; l++; k++;
					result.add(retrieveSegment(seq1, seq2, k, l, i, j));
				}
			}
		}
		return result;
	}

	/**
	 * Given a matrix with matching values for two sequences searchs for a common matching subsequence. 
	 * Each String represents a alignment of the two subsequences in which the two sequences has more than 'largerThan' consecutive matching bases.
	 *  
	 * @param seq1 First sequence.
	 * @param seq2 Second sequence.
	 * @param largerthan cut-off value.
	 * @param matchMatrix a matrix containing matching values. 
	 * @return True is a common matching subsequence is found, False otherwise.
	 */
	private static boolean analizeMatrix(char[] seq1, char[] seq2, int largerthan, double[][] ds) {
		for(int i=seq1.length;i>largerthan;i--) {
			for(int j=seq2.length;j>largerthan;j--) {
				if (ds[i][j] > largerthan) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Given a matrix with matching values for two sequences, retrieves a list of String.
	 * Each String represents a alignment of the two subsequences in which the two sequences has more than 'largerThan' consecutive matching bases and one of them includes the 3' end.
	 *  
	 * @param seq1 First sequence.
	 * @param seq2 Second sequence.
	 * @param largerthan cut-off value.
	 * @param ds a matrix containing matching values. 
	 * @return  a list of String repreting each one an alignment.
	 */
	private static List<String> analizeMatrixWithFixed3EndAndRetrieveList(char[] seq1, char[] seq2, int largerthan, double[][] ds) {
		List<String> result = new Vector<String>();
		
		// look from 3' of first sequence

		/*
		 * seq1 = [A ,B ,C ,D ,E ,F]
		 * seq2 = [A',B',C',D',E']
		 * 
		 *                        ___
		 *   | A | B | C | D | E | F |    
		 * E'| 0 | 0 | 1 | 0 | 0 | 0 |
		 * D'| 0 | 0 | 0 | 2 | 0 | 0 |
		 * C'| 0 | 0 | 0 | 0 | 3 | 0 |
		 * B'| 0 | 0 | 0 | 0 | 0 | 4 |
		 * A'| 0 | 0 | 0 | 0 | 0 | 0 |
		 *                        ---
		 *                         ^  
		 * Search here ------------|		 
		 * 
		 *                           
		 *   | A | B | C | D | E | F |    
		 * E'| 0 | 0 | 1 | 0 | 0 | 0 |
		 * D'| 0 | 0 | 0 | 2 | 0 | 0 |
		 * C'| 0 | 0 | 0 | 0 | 3 | 0 |
		 *                        ___   
		 * B'| 0 | 0 | 0 | 0 | 0 # 4 #  <-- this position is found = (i,lastPosOfFirstSeq)
		 *                        --- 
		 * A'| 0 | 0 | 0 | 0 | 0 | 0 |
		 *                           
		 * Backtrack from the found position until a postion with no match (value == 0) or the end of sequence.
		 *
		 *   | A | B | C | D | E | F |
		 *                this position is found = (k,l)
		 *            ___/            
		 * E'| 0 | 0 # 1 # 0 | 0 | 0 |
		 *            ---\
		 * D'| 0 | 0 | 0 # 2 # 0 | 0 |
		 *                   \
		 * C'| 0 | 0 | 0 | 0 # 3 # 0 |
		 *                       \___   
		 * B'| 0 | 0 | 0 | 0 | 0 # 4 #  <-- this position is found = (i,lastPosOfFirstSeq)
		 *                        --- 
		 * A'| 0 | 0 | 0 | 0 | 0 | 0 |
		 *                           
		 */

		
		for (int i=seq2.length;i>0;i--) {
			int lastPosOfFirstSeq=seq1.length;
			if (ds [lastPosOfFirstSeq][i] > largerthan) {


				int k = i;
				int l = lastPosOfFirstSeq;
				while (ds[l][k]>0) ds[l--][k--]=0; l++; k++;
					
				result.add(retrieveSegment(seq1,seq2,l,k,lastPosOfFirstSeq,i ));
			}
			
		}
		
		// look from 3' of second sequence

		/*
		 * seq1 = [A ,B ,C ,D ,E ,F]
		 * seq2 = [A',B',C',D',E']
		 * 
		 *                        
		 *   | A | B | C | D | E | F |
		 *    ___ ___ ___ ___ ___ ___    
		 * E'| 0 | 0 | 1 | 0 | 0 | 0 |  <-- search here.
		 *    --- --- --- --- --- ---
		 * D'| 0 | 0 | 0 | 2 | 0 | 0 |
		 * C'| 0 | 0 | 0 | 0 | 3 | 0 |
		 * B'| 0 | 0 | 0 | 0 | 0 | 4 |
		 * A'| 0 | 0 | 0 | 0 | 0 | 0 |
		 * 
		 */
		
		for (int i=1;i<seq1.length;i++) {
			
			if (ds [i][1] > 0) {

				int k = i;
				int l = 1;
				
				while(k<seq1.length && l<seq2.length && ds[k++][l++]>0);
				k--;l--;
				if (ds[k][l]>largerthan) {
					result.add(retrieveSegment(seq1, seq2,i,1,k,l));
				}
			}
		}
		
		return result;
	}

	/**
	 * Given a matrix with matching values for two sequences searchs for a common matching subsequence. 
	 * Each String represents a alignment of the two subsequences in which the two sequences has more than 'largerThan' consecutive matching bases and one of them includes the 3' end.
	 * 
	 * @param seq1 First sequence.
	 * @param seq2 Second sequence.
	 * @param largerthan cut-off value.
	 * @param matchMatrix a matrix containing matching values. 
	 * @return True is a common matching subsequence is found, False otherwise.
	 */
	private static boolean analizeMatrixWithFixed3End(char[] seq1, char[] seq2, int largerthan, double[][] ds) {
		for(int i=1;i<=seq2.length;i++) {
			if (ds[seq1.length][i] > largerthan) return true;
		}
		
		for(int i=1;i<seq1.length;i++) {
			int k=i; int l=1;
			if (ds[i][1] > 0) {
				while(k<seq1.length && l<seq2.length && ds[k++][l++]>0);
				k--;l--;
				if (ds[k][l]>largerthan) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	
	private static String retrieveSegment(char[] seq1, char[] seq2, int x1, int x2, int x3, int x4 ) {
		
		int s = x3-x1;
		int f = x2-x1;
		
		int gapsInit1 = Math.max(0, f);
		int gapsInit2 = Math.max(0,-f);
		int gapsInitm = Math.max(x1, x2)-1;
		
		int h1 = seq1.length - x3;
		int h2 = seq2.length - x4;
		
		int fl = Math.min(h1, h2);
		
		int gapsTerminal1 = h2 - fl;
		int gapsTerminal2 = h1 - fl;
		int gaspTerminalm = Math.max(h1, h2);
		
		String upperLine = putgaps(gapsInit1) + String.valueOf(seq1) + putgaps(gapsTerminal1);
		String middleLine = putspaces(gapsInitm) + putsim(s+1) + putspaces(gaspTerminalm);
		String bottomLine = putgaps(gapsInit2) + String.valueOf(seq2) + putgaps(gapsTerminal2);
		
		return upperLine + (char)Character.LINE_SEPARATOR + middleLine + (char)Character.LINE_SEPARATOR + bottomLine;
	}

	private static String putgaps(int number) {
		return putChar(number,"-");
	}

	private static String putsim(int number) {
		return putChar(number,"|");
	}

	private static String putspaces(int number) {
		return putChar(number," ");
	}
	
	private static String putChar(int number, String character) {
		StringBuilder s = new StringBuilder(number);
		while(number-->0) s.append(character);
		return s.toString();
	}
	
}
