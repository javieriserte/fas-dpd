package sequences.util.compare;

import sequences.dna.DNASeq;
import degeneration.BaseDeg;

public class DegeneratedDNAMatchingStrategy implements MatchingStrategy {

	static private double[][] matchingValues = DegeneratedDNAMatchingStrategy.getMatchingMatrix();

	
	@Override
	public double matches(char a, char b) {
		int p1 = BaseDeg.getIntFromChar(a);
		int p2 = BaseDeg.getIntFromChar(b);
		
		
		return DegeneratedDNAMatchingStrategy.matchingValues[p1][p2];
		
		
		
	}
	
	public static void printMatchingMatrix() {
	
		StringBuilder line= new StringBuilder();
		
		line.append("\t");
		for (int i=1;i<16;i++) line.append(BaseDeg.getCharFromInt(i)+"\t");
		
		line.append("\r\n");
		
		
		for (int i=1;i<16;i++) {
			// start in 1, because 0 is for '-'
				line.append(BaseDeg.getCharFromInt(i));
			for (int j=1;j<16;j++) {
				line.append("\t"+matchingValues[i][j]);
			}
			line.append("\r\n");
		}
		
		System.out.println(line);
		
	}
	
	
	static private double[][] getMatchingMatrix() {
		double values[][] = new double[16][16];
		for (int i=1;i<16;i++) {
			// start in 1, because 0 is for '-' 
			for (int j=i;j<16;j++) {
				
				char[] b1 = BaseDeg.getCharArrayFromChar(DNASeq.getComplementaryBase(BaseDeg.getCharFromInt(i)));
				char[] b2 = BaseDeg.getCharArrayFromInt(j);
				
				double cases= 0;
				for (char c1 : b1) {
					for (char c2 : b2) {
						if (c1==c2) cases++;
					}
				}
				values[i][j] = cases / (b1.length*b2.length);
				values[j][i] = values[i][j];
			}
		}
		return values;
	}

}
