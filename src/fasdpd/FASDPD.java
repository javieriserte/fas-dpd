/*
 * You may not change or alter any portion of this comment or credits
 * of supporting developers from this source code or any supporting source code
 * which is considered copyrighted (c) material of the original comment or credit authors.
 *
 * THERE IS NO WARRANTY FOR THE PROGRAM, TO THE EXTENT PERMITTED BY APPLICABLE LAW. 
 * EXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT HOLDERS AND/OR OTHER PARTIES 
 * PROVIDE THE PROGRAM �AS IS� WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, 
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
 * 	"Microbiolog�a molecular b�sica y aplicaciones biotecnol�gicas"
 * 		(Basic Molecular Microbiology and biotechnological applications)
 * 
 * And is being conducted in:
 * 	LIGBCM: Laboratorio de Ingenier�a Gen�tica y Biolog�a Celular y Molecular.
 *		(Laboratory of Genetic Engineering and Cellular and Molecular Biology)
 *	Universidad Nacional de Quilmes.
 *		(National University Of Quilmes)
 *	Quilmes, Buenos Aires, Argentina.
 *
 * The complete team for this project is formed by:
 *	Lic.  Javier A. Iserte.
 *	Lic.  Betina I. Stephan.
 * 	ph.D. Sandra E. Go�i.
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
		return "FAS-DPD:\nFamily Specific Degenerate Primer Design program: \n\nVersion 1.1.1\n\nFAS-DPD is a command line software that designs degenerated oligonucleotides to use in PCR in order to amplify new members of a family of sequences\nFAS-DPD is written in Java, so you will need a Java Virtual Machine to run it.\n\nUsage:\n    java -cp \\bin;\\lib\\* fasdpd.FASDPD 'OPTIONS'\n\n    Options:\n    \n        Required:\n    \n            Infile: '/infile' : Path to a Fasta file with the starting alignment.\n    \n            Outfile: '/outfile' : Path to a file where resulting primers will be stored.\n    \n            GCfile: '/gcfile' : Path to a file containg the genetic code that will be used.\n        \n        Optional:\n        \n            Quantity: '/q' : The number of primers to search.\n            \n            Staring Point: '/startingpoint' : The position of the alignment where start the search.\n            \n            Ending Point: '/endpoint' : The position of the alignment where finish the search.\n            \n            Is DNA: '/isdna' : Treat the sequences in input alignment as DNA sequences. \n                By default: is assumed as DNA.\n            \n            Is Protein: '/isprotein' : Treat the sequences in input alignment as protein sequences.\n                By default: is assumed as DNA.\n            \n            Filter Repeated End: '/frep' : Discard primers with the last two bases repeated.\n            \n            Filter Degenerated End: '/fdeg' : Discar Primers with the last base degenerated.\n            \n            Complementary Strand: '/ComplementaryStrand' : Search the primers in the complementary strand.\n            \n            Profile: '/profile' : Generates an histogram of sites of the alignment occupied by primers. Also provides \n                a simple script to generate '.png' and '.ps' graphic output with Gnu-Plot." + "\n            \n            Primer Minimum Length: '/lenMin' : With '/lenMax' option permits evaluate primers of different sizes.\n                Default value: 20 \n            \n            Primer Maximum Length: '/lenMax' : With '/lenMin' option permits evaluate primers of different sizes.\n                Default value: 25 \n            \n            Melting Point Filter: '/tm' : Keep primers with a melting temperature in a given range.\n                Example: '/tm 55.0,70.0', primers with 55.0<=tm<=70.0 Celcius are used, discarding the rest.\n                Default value: 50ºC - 65ºC.\n                Use '/notm' option to do not filter by melting Point. \n            \n            SantaLucia Melting Point: '/tmsantalucia' : Estimates the melting point of primers using Santa Lucia method.\n                Reference: SantaLucia J Jr. A unified view of polymer, dumbbell, and oligonucleotide DNA \n                nearest-neighbor thermodynamics. Proc Natl Acad Sci U S A. 1998 Feb 17;95(4):1460-5.\n                By default: /tmsantalucia is used.\n            \n            Simple Melting Point: '/tmsimple' : Estimates the melting point of primers using the formula Tm = 2*AT+4*GC.\n                Reference: Joseph Sambrook, E. F. Fritsch, Tom Maniatis. Molecular cloning: a laboratory manual. \n                Cold Spring Harbor Laboratory, 1989.\n                By default: /tmsantalucia is used.\n                                \n            5' vs. 3' Thermodynamic Stability: '/end5v3' : Calculates the Delta_G values for the first (5') and last (3')\n                five nucleotides of the primer and verifies that 5' is more stable than 3', according to a given Delta_G \n                threshold value and experimental conditions. Delta_G-5' + delta_G_Limit < Delta_G-3'.\n                Example: '/end5v3 1.5, 310, 0.05, 5', \n                  1.5 is the Delta_G limit (in Kcal/mol), \n                  310 is the Kelvin temperature used to estimate Delta_G values. \n                  0.05 is the concetration of monovalent ions. Typically Na(+).\n                  5 is the number of bases from each end that will be used to estimate delta_G difference.\n                The values shown above are the dafault values.\n                Use '/noend5v3' to do not filter by 5' vs 3' thermodynamic stability. \n                \n            Base Runs Filter: '/baserun' : Discard primers that repeats the last base at least a given number of times.\n                Example: '/baserun 4', discard primers that repeats 4, 5, or more times the last 3' base.\n                Default value: 4.\n                Use '/nobaserun' to do not filter by base runs. \n                \n            Homodimer Filter: '/homodimer' : Eliminate primers that can form a homodimer structure of a given number of bases \n                in any part of the primer.\n                Default value: 5.\n                Use '/nohomodimer' to do not filter homodimer structures. \n                \n            Homodimer At The 3' Filter: '/homodimer3' : Discard primers that can form homodimeric structures of a given size \n                containing the 3' end.\n                Dafault value: 3.\n                Use '/nohomodimer3' to do not filter homodimer structures at 3'. \n                \n            G+C Content Filter: '/gc' :  Keep primer primers whose G+C percent content ​​are in a given range.\n                Example: '/gc 30, 70' , primers with 30<=G+C<=70 are kept.\n                Default value: 40 - 60 G+C %.\n                Use '/nogc' to do not filter primers by its G+C content.\n                \n            Score Filter: '/score' : Discard primers whose score is lower than a given value.\n                Default value: 0.8.\n                Use '/noscore' to do not filter primers by score.\n                \n            Perform PCR Primer Pair Search: '/pair' : Searches primers in both strands of a given sequence in order to find primer pairs \n                for PCR. This option allows the use of extra filters for primer pairs.\n                By Default, single primer search are performed.\n                \n            Maximum Amplicon Size: '/size' : Search primers that can give a PCR product smaller or equal than a given size.\n                Default value: 200.\n                Use '/nosize' to do not limit the maximum size of a PCR product.\n            \n            Minimum Amplicon Size: '/minsize' : Search primers that can give a PCR product greater or equal than a given size.\n                Default value: 100.\n                Use '/nominsize' to do not limit the manimum size of a PCR product.\n               \n            G+C content compatibility: '/gccomp' : Calculates the difference of G+C content of the two primers and discard the pair if \n                it is greater than a given value.\n                Example: '/gccomp 20'. Keep pairs whose G+C content is smaller or equal to 20 %.\n                Default value: 10 G+C %.\n                Use '/nogccomp' to do not filter primer pairs by its G+C content.\n            \n            Hetero Dimer Filter: '/hetdimer' : Eliminate primer pairs that can form a heterodimer structure of a given number of bases \n                in any part of the primer.\n                Default value: 5.\n                Use '/nohetdimer' to do not filter primer pairs that can form heterodimer structures.\n                \n            Heterodimer At The 3' Filter: '/hetdimer3' : Eliminate primer pairs that can form a heterodimer structure of a given number \n                of bases containing the 3' end of any of the two primers.\n                Default value: 3.\n                Use '/nohetdimer3' to do not filter primer pairs that can form heterodimer structures at the 3' end.\n                \n            Tm Compatibility Filter: '/tmcomp' : Eliminate primer pairs with a difference in whose melting temperatures greater than a given value.\n                Default value: 5 ºC.\n                Use '/notmcomp' to do not check compatibility of melting points.";
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
