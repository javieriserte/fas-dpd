package fastaIO;

import java.io.File;
import java.io.FileFilter;
/**
 * This class is a subclass a FileFilter that recognizes common extensions for Fasta files.
 *
 */
public class FastaFilter implements FileFilter {
	@Override
	public boolean accept(File arg0) {
		String a = arg0.getName().toLowerCase();
		return (a.endsWith  (".fasta") || a.endsWith(".fas") || a.endsWith(".fa"));
	}
	public String getDescription(File arg0) {
		return "Fasta Files (*.fas, *.fasta, *.fa)";
	}
}
