package sequences.util.gccontent;

import degeneration.BaseDeg;

public class GCContent {

	/**
	 * Calculates the G+C content of a DNA sequence.
	 * 
	 * @param sequence. Is a DNA sequence in uppercase. Can be a degenerated sequence.
	 * @return
	 */
	static public float calculateGCContent(String sequence) {
		float[] TotalBases = new float[4];
		int[] Currentbases = new int[4];
			// each position in bases array is used to count the number of each base in the primer.
		    // First position is A, second is C, third is T, fourth is G.
		
		for (int l=0; l<sequence.length();l++) {
			// Loop for every base in the primer
			for( int i=0;i<4;i++) {
					// loop for each posible non-degenerated base.
				boolean c = BaseDeg.containsBaseIntInChar(i, sequence.charAt(l));
					// looks the current base in the primer contains each posible base.
				if(c) Currentbases[i]=1; else Currentbases[i]=0; 
					// if the base is present in a given position of a primer, puts one in Currentbases array.
			}
			int total = 0; for (int i=0;i<4;i++) total+=Currentbases[i];
				// get the degeneration value for a given position.
				
			for (int i=0;i<4;i++) {
				TotalBases[i]+=Currentbases[i]/total;
					// TotalBases stores the cummulated frequencie for each non-degenerated base.
			}
		}	
		float totalACTG = TotalBases[0]+ TotalBases[1]+ TotalBases[2]+ TotalBases[3];

		float totalCG = TotalBases[1]+ TotalBases[3];
		
		return totalCG/totalACTG;
	}

	
}
