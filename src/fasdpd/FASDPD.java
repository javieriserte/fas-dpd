/*
 * You may not change or alter any portion of this comment or credits
 * of supporting developers from this source code or any supporting source code
 * which is considered copyrighted (c) material of the original comment or credit authors.
 *
 * THERE IS NO WARRANTY FOR THE PROGRAM, TO THE EXTENT PERMITTED BY APPLICABLE LAW. 
 * EXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT HOLDERS AND/OR OTHER PARTIES 
 * PROVIDE THE PROGRAM “AS IS” WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, 
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS 
 * FOR A PARTICULAR PURPOSE. THE ENTIRE RISK AS TO THE QUALITY AND PERFORMANCE OF THE 
 * PROGRAM IS WITH YOU. SHOULD THE PROGRAM PROVE DEFECTIVE, YOU ASSUME THE COST OF ALL 
 * NECESSARY SERVICING, REPAIR OR CORRECTION.
 
 * IN NO EVENT UNLESS REQUIRED BY APPLICABLE LAW OR AGREED TO IN WRITING WILL ANY COPYRIGHT 
 * HOLDER, OR ANY OTHER PARTY WHO MODIFIES AND/OR CONVEYS THE PROGRAM AS PERMITTED ABOVE, 
 * BE LIABLE TO YOU FOR DAMAGES, INCLUDING ANY GENERAL, SPECIAL, INCIDENTAL OR CONSEQUENTIAL
 * DAMAGES ARISING OUT OF THE USE OR INABILITY TO USE THE PROGRAM (INCLUDING BUT NOT LIMITED 
 * TO LOSS OF DATA OR DATA BEING RENDERED INACCURATE OR LOSSES SUSTAINED BY YOU OR THIRD 
 * PARTIES OR A FAILURE OF THE PROGRAM TO OPERATE WITH ANY OTHER PROGRAMS), EVEN IF SUCH 
 * HOLDER OR OTHER PARTY HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
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

package fasdpd;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import degeneration.GeneticCode;

import fastaIO.FastaMultipleReader;
import fastaIO.Pair;
import sequences.alignment.Alignment;
import sequences.dna.DNASeq;
import sequences.dna.Primer;
import sequences.protein.ProtSeq;

/**
 * Executable Class For Command line FAS-DPD program.
 * @author Javier Iserte <jiserte@unq.edu.ar>
 */
public class FASDPD {


	// Public Interface
	/**
	 * Performs the search of degenerated primers.
	 * The results are sent to a file. The file name and path are defined in <code>mySp</code>.
	 * 
	 * @see SearchParameter
	 * @param mySp contains all the parameters for the search.
	 */
	public void doSearchAndExportResults(SearchParameter mySp) {
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
			// creates a new analyzer with standard parameters

		if (!mySp.isSearchPair()) {
		
			PriorityList<Primer> result = myAn.searchBestPrimers(mySp.getQuantity(), consense, mySp.getLenMin(), mySp.getLenMax(), mySp.isDirectStrand(),mySp.getFilter(),mySp.getStartPoint(),mySp.getEndPoint());
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
		
		} else {
			
			PriorityList<Primer> resultforward = myAn.searchBestPrimers(mySp.getQuantity(), consense, mySp.getLenMin(), mySp.getLenMax(), true, mySp.getFilter(),mySp.getStartPoint(),mySp.getEndPoint());
			PriorityList<Primer> resultreverse = myAn.searchBestPrimers(mySp.getQuantity(), consense, mySp.getLenMin(), mySp.getLenMax(), true, mySp.getFilter(),mySp.getStartPoint(),mySp.getEndPoint());
			
			List<PrimerPair> pairs = myAn.searchPrimerPairs(resultforward.ExtractSortedList(), resultreverse.ExtractSortedList(), mySp.getFilterpair());
			
			exportPairs(mySp.getOutfile(),pairs);
			  // send primers pairs list to file

		}
	}
	/**
	 * Performs the search of degenerated primers.
	 * The file name and path are defined in <code>mySp</code>.
	 * 
	 * @see SearchParameter
	 * @param mySp contains all the parameters for the search.
	 * @return a <code>ResultOfSearch</code> that contains the designed primers.
	 */
	public ResultOfSearch doSearch(SearchParameter mySp) {

		ResultOfSearch results;
		
		Alignment al = new Alignment();
		FastaMultipleReader fmr = new FastaMultipleReader();
		List<Pair<String, String>> ps =null;
		try {
			ps = fmr.readFile(mySp.getInfile());	
		} catch (FileNotFoundException e) {
			System.out.println("\r\nFASDPD exit with errors\r\nFile: " + mySp.getInfile() + " not found.\r\n");
			System.out.println(FASDPD.getHelp());
			System.exit(0);
			// TODO do not show error, instead propagate an exception
			
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
			// creates a new analyzer with standard parameters

		if (!mySp.isSearchPair()) {
		
			PriorityList<Primer> result = myAn.searchBestPrimers(mySp.getQuantity(), consense, mySp.getLenMin(), mySp.getLenMax(), mySp.isDirectStrand(),mySp.getFilter(),mySp.getStartPoint(),mySp.getEndPoint());
				// Do the search !!
	
			
			List<Primer> sorted = result.ExtractSortedList();
			
			results = new ResultOfSearch();
			results.primers = sorted;

	
		} else {
			
			PriorityList<Primer> resultforward = myAn.searchBestPrimers(mySp.getQuantity(), consense, mySp.getLenMin(), mySp.getLenMax(), true, mySp.getFilter(),mySp.getStartPoint(),mySp.getEndPoint());
			PriorityList<Primer> resultreverse = myAn.searchBestPrimers(mySp.getQuantity(), consense, mySp.getLenMin(), mySp.getLenMax(), false, mySp.getFilter(),mySp.getStartPoint(),mySp.getEndPoint());
			
			List<PrimerPair> result = myAn.searchPrimerPairs(resultforward.ExtractSortedList(), resultreverse.ExtractSortedList(), mySp.getFilterpair());
			results = new ResultOfSearch();
			results.primerPairs = result;
			
		}
		return results;
		
	}
	
	/**
	 * Exports a distribution profile of primers to a text file, and also exports a simple script to view the profile with gnu-plot.
	 * 
	 * @param outfile is the path to the output file
	 * @param list is the List or Primers to export
	 * @param lastPos is the number of the last position of the alignment used to design the primers. 
	 * Is required for drawing purposes.
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
	 * @param list is the list of primers used to make  
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
	public void exportPairs(String outfile, List<PrimerPair> list){
		
		try {
			FileWriter fr = new FileWriter(outfile);
			fr.write("Sequence\tScore\tStart\tEnd\tDirectStrand\tSequence\tScore\tStart\tEnd\tDirectStrand\t\n");
			for (PrimerPair primer : list) {
				fr.write(primer.getForward().getSequence()+"\t");
				fr.write(primer.getForward().getScore()+"\t");
				fr.write(primer.getForward().getStart()+"\t");
				fr.write(primer.getForward().getEnd()+"\t");
				fr.write(primer.getForward().isDirectStrand()+"\n");
				
				fr.write(primer.getReverse().getSequence()+"\t");
				fr.write(primer.getReverse().getScore()+"\t");
				fr.write(primer.getReverse().getStart()+"\t");
				fr.write(primer.getReverse().getEnd()+"\t");
				fr.write(primer.getReverse().isDirectStrand()+"\n");
				
			}
			fr.flush();
			fr.close();
			
		} catch (IOException e) {
			System.out.println("There was an error in the file. No primer list file was generated.");
		}
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
	 * Gets the the text of help.
	 */
	public static String getHelp() {
		return "Usage:\r\n	java -cp \\bin;\\lib\\* fasdpd.FASDPD 'OPTIONS'\r\n\r\n	Options:\r\n		Required:\r\n			Infile: '/infile' : Path to a Fasta file with the starting alignment.\r\n			Outfile: '/outfile' : Path to a file where resulting primers will be stored.\r\n			GCfile: '/gcfile' : Path to a file containg the genetic code that will be used.\r\n		Optional:\r\n			Length: '/len' : The length of resulting primers.\r\n			Quantity: '/q' : The number of primers to search.\r\n			Staring Point: '/startingpoint' : The position of the alignment where start the search.\r\n			Ending Point: '/endpoint' : The position of the alignment where finish the search.\r\n			Is DNA: '/isdna' : Treat the sequences in input alignment as DNA sequences.\r\n			Is Protein: '/isprotein' : Treat the sequences in input alignment as protein sequences.\r\n			Filter Repeated End: '/frep' : Discard primers with the last two bases repeated.\r\n			Filter Degenerated End: '/fdeg' : Discar Primers with the last base degenerated.\r\n			Complementary Strand: '/ComplementaryStrand' : Search the primers in the complementary strand.\r\n			Profile: '/profile' : Generates an histogram of sites of the alignment occupied by primers. Also provides a simple script to generate '.png' and '.ps' graphic output with Gnu-Plot.\r\n";
	
	}
	// Executable Main
	/**
	 * Executable main method for console interface of FAS-DPD.
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
		myProgram.doSearchAndExportResults(sp);
			// start the search of primers
	}
	// Auxiliary Classes
	/**
	 * ResultOfSearch object are used to store the results of the search of primers.
	 * It could contain a list of Primer or a list PrimerPair.
	 * 
	 * @author Javier Iserte <jiserte@unq.edu.ar>
	 */
	public class ResultOfSearch {
		public List<Primer> primers = null;
		public List<PrimerPair> primerPairs=null;
	}

}
