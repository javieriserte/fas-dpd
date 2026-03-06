
package fastaIO;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
/**
 * This class is used to read sequences files with just one sequence.
 * By the moment, this class can not handle sequences with more than one line.  
 *
 */
public class FastaReader {

	/**
	 * Reads a fasta file from a filepath 
	 * @param filepath
	 * @return 
	 */
	public Pair<String, String> readFile(String filepath) {
		return this.readFile(Path.of(filepath));
	}
	
	/**
	 * Reads a fasta file from a File Object 
	 * @param filepath
	 * @return 
	 */
	public Pair<String, String> readFile(File file) {
		return this.readFile(file.toPath());
	}

	public Pair<String, String> readFile(Path path) {
		try {
			try (BufferedReader reader = Files.newBufferedReader(
				path,
				StandardCharsets.UTF_8
			)) {
				return this.readBuffer(reader);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Reads fasta Content From a String
	 * @param string
	 * @return
	 */
	public Pair<String, String> readString(String string) {
		return this.readBuffer(new BufferedReader(new CharArrayReader(string.toCharArray())));
	}
	
	/**
	 * Reads fasta Content from a BufferedReader
	 * @param buffer
	 * @return
	 */
	public Pair<String, String> readBuffer(BufferedReader buffer) {
		
		String d="";
		String s="";
		
		TextConsumer tx = new TextConsumer();
		tx.buffer = buffer;
		
		try {

			tx.consumeUntil( ">");
			d = tx.consumeLine();
			s = tx.consumeUntil(">");
			s = s.replaceAll("[\r\n]", "");
			return (Pair<String, String>) new Pair<String,String>(d.substring(1),s);				
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}

