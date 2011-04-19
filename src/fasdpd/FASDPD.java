package fasdpd;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import degeneration.GeneticCode;

import fastaIO.FastaMultipleReader;
import fastaIO.Pair;
/*
 * You may not change or alter any portion of this comment or credits
 * of supporting developers from this source code or any supporting source code
 * which is considered copyrighted (c) material of the original comment or credit authors.
 * This program is distributed WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * FAS-DPD project, including algorithms design, software implementation and experimental laboratory work, is being developed as a part of the Research Program:
 * 	"Microbiología molecular básica y aplicaciones biotecnológicas"
 * 		(Basic Molecular Microbiology and biotechnological applications)
 * 
 * And is being conducted in:
 * 	LIGBCM: Laboratorio de Ingeniería Genética y Biología Celular y Molecular.
 *		(Laboratory of Genetic Engineering and Cellular and Molecular Biology)
 *	Universidad Nacional de Quilmes.
 *		(National University Of Quilmes)
 *	Quilmes, Buenos Aires, Argentina.
 *
 * The complete team for this project is formed by:
 *	Lic.  Javier A. Iserte.
 *	Lic.  Betina I. Stephan.
 * 	ph.D. Sandra E. Goñi.
 * 	ph.D. P. Daniel Ghiringhelli.
 *	ph.D. Mario E. Lozano.
 *
 * Corresponding Authors:
 *	Javier A. Iserte. <jiserte@unq.edu.ar>
 *	Mario E. Lozano. <mlozano@unq.edu.ar>
 */
import sequences.alignment.Alignment;
import sequences.dna.DNASeq;
import sequences.dna.Primer;
import sequences.protein.ProtSeq;

/**
 * Executable Class For FAS-DPD program
 * @author Javier Iserte <jiserte@unq.edu.ar>
 * @version 1.1.2
 */

public class FASDPD {

	/**
	 * Main method. 
	 */
	public static void main(String[] arg) {

		
		FASDPD myProgram = new FASDPD();
			
		SearchParameter sp = new SearchParameter();
			// sp will store all the parameters for the search 

		// Interpret command line
		try {
			if (arg.length==0) {throw new InvalidCommandLineException();}	
			sp.retrieveFromCommandLine(arg);
				// tries to get all the parameters from command line.
		} catch (InvalidCommandLineException e) {

			System.out.println(FASDPD.getHelp());
			System.out.println(e.getMessage());
			
			return;
		}
		myProgram.DoSearch(sp);
			// start the search of primers
	}

	////////////////////////////
	// New Search Strategy
	//
	// 1) Look if in the searchParameters indicates that must be search pair of primer or single primers.
	// 2) If single primers are asked, do the search like before.
	// 3) If primer pairs are searched, a list of primers must be submitted. 
	//               In command line this list is made searching single primers first.
	//               But, can be given as a file. ( TODO modify input parameters to read a list of primers from a file)
	//               Search primerpairs, 
	//
	////////////////////////////
	
	public void DoSearch(SearchParameter mySp) {
		Alignment al = new Alignment();
		FastaMultipleReader fmr = new FastaMultipleReader();
		List<Pair<String, String>> ps =null;
		try {
			ps = fmr.readFile(mySp.getInfile());	
		} catch (FileNotFoundException e) {
			System.out.println("\r\nFASDPD exit with errors\r\nFile: " + mySp.getInfile() + " not found.\r\n");
			System.out.println(FASDPD.getHelp());
			System.exit(0);
		}
		
		
		for (Pair<String, String> pair : ps) {
			if (mySp.isDNA()) {
				// Working with DNA sequences
				al.addSequence(new DNASeq(pair.getSecond(),pair.getFirst()));
			} else {
				// Working with protein sequences
				al.addSequence(new ProtSeq(pair.getSecond(),pair.getFirst()));
			}
		}
		
		GeneticCode myGC = new GeneticCode(mySp.getGCfile());
			// Creates a genetic Code
		DNASeq consense = al.pileUp(myGC);
			// Generates the degenerated consensus 
		Analyzer myAn = new Analyzer(mySp.getpA(), mySp.getNy(), mySp.getNx(),  myGC);
		
		// TODO modify to search a range of primer lenght
		
			// creates a new analyzer with standard parameters
		PriorityList<Primer> result = myAn.searchBestPrimers(mySp.getQuantity(), consense, mySp.getLen(), mySp.isDirectStrand(),mySp.getFilter(),mySp.getStartPoint(),mySp.getEndPoint());
			// Do the search !!
		List<Primer> sorted = result.ExtractSortedList();
			// Get the results
		if (mySp.getProfile()!=null) {
			// if profile == null means that no profile generations is needed  
			exportDistributionProfile(mySp.getProfile(),sorted, al.lenght());
				// creates and exports a histogram distribution
		}
		exportPrimers(mySp.getOutfile(),sorted);
		  // send primers list to file
		
		if (mySp.isSearchPair()) {
			// TODO modify Analyzer class in order to perform searches of primer pairs.
			// TODO maybe a filter for primer score is needed in this step. otherwise, an scoring strategy for primers pair is needed.
			// For pair search, the list of primers must be separated in two lists: Forward and reverse.
			// The cartesian product of the two sets is done. (this step may be expensive if the primer list is large.)
			// Each resulting pair is filtered.
			// 
		}
	}
	
	/**
	 * export a distribution profile of primers to a text file, and also exports a simple script to view the profile with gnu-plot.
	 * 
	 * @param outfile
	 * @param list
	 * @param lastPos
	 */
	public void exportDistributionProfile(String outfile, List<Primer> list,int lastPos) {
		int[] pos = distributionProfile(  list, lastPos);
			// create a new distribution profile from a list of primers.
		int max=pos[0];
		
		for (int i=0;i<lastPos;i=i+1) {
			max = Math.max(max, pos[i]);
		}
			// store in max the maximum value of profile. In order to scale the graph.
		
		
		try {
			// Tries to write the profile
			FileWriter fr = new FileWriter(outfile);
		
			for (int i=0;i<lastPos;i=i+1) {
				fr.write((i+1) + "\t" +pos[i] +"\n");
					// write each line to file.
			}
			fr.flush();
			fr.close();

			} catch (IOException e) {
			System.out.println("There was an error in the file. No Profile file was generated.");
		}
		
		try {
			// Tries to write the script file for Gnu-plot
			FileWriter fr = new FileWriter(outfile + ".plt");
			fr.write("set terminal postscript eps font \"Helvetica,20\"\n");
			fr.write("set output \"" + outfile + ".ps\"\n");
			fr.write("set yrange [0:"+ ((int) max*1.2) +"]\n");
			fr.write("plot '" + outfile + "' with filledcurves below notitle lc rgb \"#000000\"\n");
			fr.write("set terminal png font verdana 10 size 1024,768\n");
			fr.write("set output \"" + outfile + ".png\"\n");
			fr.write("replot");

			fr.flush();
			fr.close();
			} catch (IOException e) {
			System.out.println("There was an error in the file. No Profile file was generated.");
		}
	}
	
	/**
	 * Creates a distribution profile of a collection of primers for a DNAseq.
	 * 
	 * @param list
	 * @param lastPos indicates the last position of the sequence. Is used to know where the profile ends. 
	 * @return distributionProfile object representing a histogram of number of primer per position. 
	 */
	private int[] distributionProfile(List<Primer> list,int lastPos) {
		int pos[] = new int[lastPos];
			// each value of pos[] will store the number of primer of the list that cover that position in the sequence.

		// Initialization of matrix 
		for(int x=0;x<lastPos;x=x+1) {
			pos[x]=0;
		}
		
		for (Primer primer : list) {
			int min = Math.min(primer.getStart(),primer.getEnd());
			int max = Math.max(primer.getStart(),primer.getEnd());
			for (int i=min;i<=max;i=i+1) {
				pos[i-1]= pos[i-1]+1;
			}
		}
		return pos;
	}
	
	/**
	 * Send a primer list to a file.
	 * @param outfile a string containing the path of the file
	 * @param list a List of Primer objects
	 */
	public void exportPrimers(String outfile, List<Primer> list){
		
		try {
			FileWriter fr = new FileWriter(outfile);
			fr.write("Sequence\tScore\tStart\tEnd\tDirectStrand\n");
			for (Primer primer : list) {
				fr.write(primer.getSequence()+"\t");
				fr.write(primer.getScore()+"\t");
				fr.write(primer.getStart()+"\t");
				fr.write(primer.getEnd()+"\t");
				fr.write(primer.isDirectStrand()+"\n");
			}
			fr.flush();
			fr.close();
			
		} catch (IOException e) {
			System.out.println("There was an error in the file. No primer list file was generated.");
		}
	}
	/**
	 * 
	 */
	
	public static String getHelp() {
		return "Usage:\r\n	java -cp \\bin;\\lib\\* fasdpd.FASDPD 'OPTIONS'\r\n\r\n	Options:\r\n		Required:\r\n			Infile: '/infile' : Path to a Fasta file with the starting alignment.\r\n			Outfile: '/outfile' : Path to a file where resulting primers will be stored.\r\n			GCfile: '/gcfile' : Path to a file containg the genetic code that will be used.\r\n		Optional:\r\n			Length: '/len' : The length of resulting primers.\r\n			Quantity: '/q' : The number of primers to search.\r\n			Staring Point: '/startingpoint' : The position of the alignment where start the search.\r\n			Ending Point: '/endpoint' : The position of the alignment where finish the search.\r\n			Is DNA: '/isdna' : Treat the sequences in input alignment as DNA sequences.\r\n			Is Protein: '/isprotein' : Treat the sequences in input alignment as protein sequences.\r\n			Filter Repeated End: '/frep' : Discard primers with the last two bases repeated.\r\n			Filter Degenerated End: '/fdeg' : Discar Primers with the last base degenerated.\r\n			Complementary Strand: '/ComplementaryStrand' : Search the primers in the complementary strand.\r\n			Profile: '/profile' : Generates an histogram of sites of the alignment occupied by primers. Also provides a simple script to generate '.png' and '.ps' graphic output with Gnu-Plot.\r\n";
	
	}
	
}
