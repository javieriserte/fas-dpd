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
