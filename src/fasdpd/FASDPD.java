package fasdpd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
 */
public class FASDPD {
	/**
	 * Performs the search of degenerated primers. The results are sent to a file.
	 * The file name and path are defined in <code>mySp</code>.
	 *
	 * @see SearchParameter
	 * @param mySp contains all the parameters for the search.
	 */
	public void doSearchAndExportResults(SearchParameter mySp)
			throws IOException, FileNotFoundException {
		if (!mySp.getInfile().isPresent()) {
			return;
		}
		Alignment al = new Alignment();
		FastaMultipleReader fmr = new FastaMultipleReader();
		List<Pair<String, String>> ps =  fmr.readFile(mySp.getInfile().get());
		List<PrimerOrPrimerPair> exportData =
			new ArrayList<PrimerOrPrimerPair>();
		for (Pair<String, String> pair : ps) {
			if (mySp.isDNA()) {
				// Working with DNA sequences
				al.addSequence(new DNASeq(pair.getSecond(), pair.getFirst()));
			} else {
				// Working with protein sequences
				al.addSequence(new ProtSeq(pair.getSecond(), pair.getFirst()));
			}
		}
		GeneticCode myGC = new GeneticCode(mySp.getGCfile());;
		// Creates a genetic Code
		DNASeq consense = al.pileUp(myGC);
		// Generates the degenerated consensus
		Analyzer myAn = new Analyzer(
			mySp.getpA(), mySp.getNy(), mySp.getNx(), myGC
		);
		// creates a new analyzer with standard parameters
		if (!mySp.isSearchPair()) {
			PriorityList<Primer> result = myAn.searchBestPrimers(
				mySp.getQuantity(),
				consense,
				mySp.getLenMin(),
				mySp.getLenMax(),
				mySp.isDirectStrand(),
				mySp.getFilter(),
				mySp.getStartPoint(),
				mySp.getEndPoint()
			);
			List<Primer> sorted = result.ExtractSortedList();
			// Get the results
			if (mySp.getProfile() != null) {
				// if profile == null means that no profile generations is
				// needed
				exportDistributionProfile(
					mySp.getProfile(), sorted, al.lenght()
				);
				// creates and exports a histogram distribution
			}
			for (Primer s: sorted) {
				exportData.add(new PrimerOrPrimerPair(s));
			}
			// send primers list to file
		} else {
			PriorityList<Primer> resultforward = myAn.searchBestPrimers(
				mySp.getQuantity(), consense, mySp.getLenMin(),
				mySp.getLenMax(), true, mySp.getFilter(),
				mySp.getStartPoint(), mySp.getEndPoint()
			);
			PriorityList<Primer> resultreverse = myAn.searchBestPrimers(
				mySp.getQuantity(), consense, mySp.getLenMin(),
				mySp.getLenMax(), true, mySp.getFilter(),
				mySp.getStartPoint(), mySp.getEndPoint()
			);
			List<PrimerPair> pairs = myAn.searchPrimerPairs(
				resultforward.ExtractSortedList(),
				resultreverse.ExtractSortedList(),
				mySp.getFilterpair()
			);
			for (PrimerPair s: pairs) {
				exportData.add(new PrimerOrPrimerPair(s));
			}
		}
		try {
			PrimerListExporter.exportPrimersToFile(
				new File(mySp.getOutfile()),
				exportData
			);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Performs the search of degenerated primers. The file name and path are
	 * defined in <code>mySp</code>.
	 *
	 * @see SearchParameter
	 * @param mySp contains all the parameters for the search.
	 * @return a <code>ResultOfSearch</code> that contains the designed primers.
	 */
	public ResultOfSearch doSearch(SearchParameter mySp) throws IOException, FileNotFoundException {

		ResultOfSearch results;

		Alignment al = new Alignment();
		FastaMultipleReader fmr = new FastaMultipleReader();
		List<Pair<String, String>> ps = null;
		if (mySp.getInfile().isPresent()) {
			ps = fmr.readFile(mySp.getInfile().get());
		} else {
			ps = new ArrayList<Pair<String,String>>();
		}
		for (Pair<String, String> pair : ps) {
			if (mySp.isDNA()) {
				// Working with DNA sequences
				al.addSequence(new DNASeq(pair.getSecond(), pair.getFirst()));
			} else {
				// Working with protein sequences
				al.addSequence(new ProtSeq(pair.getSecond(), pair.getFirst()));
			}
		}

		GeneticCode myGC = new GeneticCode(mySp.getGCfile());

		// Creates a genetic Code
		DNASeq consense = al.pileUp(myGC);
		// Generates the degenerated consensus
		Analyzer myAn = new Analyzer(mySp.getpA(), mySp.getNy(), mySp.getNx(), myGC);
		// creates a new analyzer with standard parameters

		if (!mySp.isSearchPair()) {

			PriorityList<Primer> result = myAn.searchBestPrimers(mySp.getQuantity(), consense, mySp.getLenMin(),
					mySp.getLenMax(), mySp.isDirectStrand(), mySp.getFilter(), mySp.getStartPoint(),
					mySp.getEndPoint());
			// Do the search !!

			List<Primer> sorted = result.ExtractSortedList();

			results = new ResultOfSearch();
			results.primers = sorted;

		} else {

			PriorityList<Primer> resultforward = myAn.searchBestPrimers(mySp.getQuantity(), consense, mySp.getLenMin(),
					mySp.getLenMax(), true, mySp.getFilter(), mySp.getStartPoint(), mySp.getEndPoint());
			PriorityList<Primer> resultreverse = myAn.searchBestPrimers(mySp.getQuantity(), consense, mySp.getLenMin(),
					mySp.getLenMax(), false, mySp.getFilter(), mySp.getStartPoint(), mySp.getEndPoint());

			List<PrimerPair> result = myAn.searchPrimerPairs(resultforward.ExtractSortedList(),
					resultreverse.ExtractSortedList(), mySp.getFilterpair());
			results = new ResultOfSearch();
			results.primerPairs = result;

		}
		return results;

	}

	/**
	 * Exports a distribution profile of primers to a text file, and also exports a
	 * simple script to view the profile with gnu-plot.
	 * 
	 * @param outfile is the path to the output file
	 * @param list    is the List or Primers to export
	 * @param lastPos is the number of the last position of the alignment used to
	 *                design the primers. Is required for drawing purposes.
	 */
	public void exportDistributionProfile(String outfile, List<Primer> list, int lastPos) {
		int[] pos = distributionProfile(list, lastPos);
		// create a new distribution profile from a list of primers.
		int max = pos[0];

		for (int i = 0; i < lastPos; i = i + 1) {
			max = Math.max(max, pos[i]);
		}
		// store in max the maximum value of profile. In order to scale the graph.

		try {
			// Tries to write the profile
			FileWriter fr = new FileWriter(outfile);

			for (int i = 0; i < lastPos; i = i + 1) {
				fr.write((i + 1) + "\t" + pos[i] + "\n");
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
			fr.write("set yrange [0:" + ((int) max * 1.2) + "]\n");
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
	 * @param list    is the list of primers used to make
	 * @param lastPos indicates the last position of the sequence. Is used to know
	 *                where the profile ends.
	 * @return distributionProfile object representing a histogram of number of
	 *         primer per position.
	 */
	private int[] distributionProfile(List<Primer> list, int lastPos) {
		int pos[] = new int[lastPos];
		// each value of pos[] will store the number of primer of the list that cover
		// that position in the sequence.

		// Initialization of matrix
		for (int x = 0; x < lastPos; x = x + 1) {
			pos[x] = 0;
		}

		for (Primer primer : list) {
			int min = Math.min(primer.getStart(), primer.getEnd());
			int max = Math.max(primer.getStart(), primer.getEnd());
			for (int i = min; i <= max; i = i + 1) {
				pos[i - 1] = pos[i - 1] + 1;
			}
		}
		return pos;
	}
	/**
	 * Gets the the text of help.
	 */
	public static String getHelp() {
		try {
			var is = FASDPD
				.class
				.getClassLoader()
				.getResourceAsStream("fasdpd/help");
			String text = new String(
				is.readAllBytes(),
				StandardCharsets.UTF_8
			);
			return text;
		} catch (IOException e) {
			System.err.println("Cannot read help file");
			return "";
		}
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
			if (arg.length == 0) {
				throw new InvalidCommandLineException();
			}
			sp.retrieveFromCommandLine(arg);
			// tries to get all the parameters from command line.
		} catch (InvalidCommandLineException e) {

			System.out.println(FASDPD.getHelp());
			System.out.println(e.getMessage());

			return;
		}
		try {
			myProgram.doSearchAndExportResults(sp);
		} catch (FileNotFoundException e) {
			System.err.println("FASDPD exit with errors");
			System.err.println("Input file not found");
			System.err.println(e.getLocalizedMessage());
			System.out.println(FASDPD.getHelp());
			System.exit(1);
		} catch (IOException e) {
			System.err.println(
				"There was an error reading the genetic code file:"
			);
			System.err.println(
				e.getLocalizedMessage()
			);
			System.out.println(FASDPD.getHelp());
			System.exit(1);
		}
		// start the search of primers
	}

	// Auxiliary Classes
	/**
	 * ResultOfSearch object are used to store the results of the search of primers.
	 * It could contain a list of Primer or a list PrimerPair.
	 */
	public class ResultOfSearch {
		public List<Primer> primers = null;
		public List<PrimerPair> primerPairs = null;
	}

}
