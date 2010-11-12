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
package fastaIO;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Vector;
/**
 * This class is used to read a Fasta file containing multiple sequences.
 * 
 * @author "Javier Iserte <jiserte@unq.edu.ar>"
 * @version 1.3.1
 */
public class FastaMultipleReader {
	
	public List<Pair<String, String>> readFile(String filepath) throws FileNotFoundException {
		
		File f = new File(filepath);
		
		return this.readFile(f);
	}
	
	/**
	 * Reads a fasta file from a File Object 
	 * @param filepath
	 * @return 
	 * @throws FileNotFoundException 
	 */
	public List<Pair<String, String>> readFile(File file) throws FileNotFoundException {
		return this.readBuffer(new BufferedReader( new FileReader(file)));
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
		
		List<Pair<String, String>> result = new Vector<Pair<String, String>>(); 
		
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
