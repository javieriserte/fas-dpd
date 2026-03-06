package fastaIO;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
/**
 * This class is used to read a Fasta file containing multiple sequences.
 *
 */
public class FastaMultipleReader {

	public List<Pair<String, String>> readFile(
			String filepath)
			throws IOException {
		return this.readFile(Path.of(filepath));
	}

	/**
	 * Reads a fasta file from a File Object
	 * @param filepath
	 * @return
	 * @throws IOException
	 */
	public List<Pair<String, String>> readFile(File file) throws IOException {
		return this.readFile(file.toPath());
	}

	public List<Pair<String, String>> readFile(Path path) throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(
			path,
			StandardCharsets.UTF_8
		)) {
			return this.readBuffer(reader);
		}
	}

	/**
	 * Reads fasta Content From a String
	 * @param string
	 * @return
	 */
	public List<Pair<String, String>> readString(String string) {
		return this.readBuffer(new BufferedReader(new CharArrayReader(string.toCharArray())));
	}
	
	/**
	 * Reads fasta Content from a BufferedReader
	 * @param buffer
	 * @return
	 */
	public List<Pair<String, String>> readBuffer(BufferedReader buffer) {
		
		String d="";
		String s="";
		
		List<Pair<String, String>> result = new ArrayList<Pair<String, String>>(); 
		
		TextConsumer textconsumer = new TextConsumer();
		textconsumer.buffer = buffer;
		
		try {
			textconsumer.consumeUntil( ">"); 
				// discard everything before the first '>' symbol.
			do { 
				d = textconsumer.consumeLine(); // Description is the first line
				s = textconsumer.consumeUntil(">"); // sequence is everything until the next '>'
				s = s.replaceAll("[\r\n]", ""); // remove any  
				result.add(new Pair<String,String>(d.substring(1),s));
			} while (textconsumer.ready());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
//	public static void main(String[] arg) {
//		String fs = ">seq1\r\nAAAAAAA\r\nAAAAAAA\r\nAAAAAAAA\r\n>seq2\r\nTTTTTTT\r\nTTTTTTT\r\nTTTTTTTT\r\n>seq3\r\nGGGGGGG\r\nGGGGGGG\r\nGGGGGGGG\r\n>seq4\r\nCCCCCCC\r\nCCCCCCC\r\nCCCCCCCC";
//		
//		FastaMultipleReader fr = new FastaMultipleReader();
//		System.out.println(fr.readString(fs));
//		
//		
//	}
}
