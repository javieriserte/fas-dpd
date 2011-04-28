package sequences.dna;

import degeneration.BaseDeg;

public class DegeneratedPrimerIterator {
	private String initialSeq;
	private int skipFrom;
	private int max;
	private int counter=0;
	
	private char[][] chars;
	private int[] deg;
	private int[] current;

	// CONSTRUCTOR
	public DegeneratedPrimerIterator(String initialSeq, int skipFrom) {
		super();
		this.initialSeq = initialSeq;
		this.skipFrom = skipFrom;
	}

	public DegeneratedPrimerIterator(String initialSeq) {
		super();
		this.initialSeq = initialSeq;
		this.skipFrom = 0;
	}
	
	// PUBLIC INSTANCE METHODS
	
	public void start() {
		
		this.counter=0;
		this.max = BaseDeg.getDegValueFromString(this.initialSeq);
		if (this.skipFrom==0) this.skipFrom =  this.max;
		
		int pos= this.initialSeq.length();
		
		this.chars = new char[pos][];
		this.deg = new int[pos];
		this.current = new int[pos];

		
		for(int i=0;i<pos;i++) {
			this.chars[i] = BaseDeg.getCharArrayFromChar(this.initialSeq.charAt(i)); 
			this.deg[i] = this.chars[i].length;
			this.current[i] = 0;
		}
		
	}
	
	public String next(){
		StringBuilder result = new StringBuilder(this.initialSeq.length());
		
		// Construct result
		for (int i=0;i < this.current.length;i++) {
			result.append(this.chars[i][this.current[i]]);
		}
		
		// Increase & Update current
		this.current[0]++;
		this.counter++;

		
		if(this.hasNext()) {
			for (int i=0;i < this.current.length;i++) {
				if (this.current[i]==this.deg[i]) {
					this.current[i+1]++;
					this.current[i]=0;
				}
			}
		} 
		
		return result.toString();
	}
	
	public boolean hasNext() {
		return (this.skipFrom>this.counter);
	}
	
	
}
