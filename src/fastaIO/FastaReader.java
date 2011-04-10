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
/**
 * This class is used to read sequences files with just one sequence.
 * By the moment, this class can not handle sequences with more than one line.  
 * 
 * @author "Javier Iserte <jiserte@unq.edu.ar>"
 * @version 1.3.1
 */
public class FastaReader {

	/**
	 * Reads a fasta file from a filepath 
	 * @param filepath
	 * @return 
	 */
	public Pair<String, String> readFile(String filepath) {
		
		File f = new File(filepath);
		
		return this.readFile(f);
	}
	
	/**
	 * Reads a fasta file from a File Object 
	 * @param filepath
	 * @return 
	 */
	public Pair<String, String> readFile(File file) {
		try {
			return this.readBuffer(new BufferedReader( new FileReader(file)));
		} catch (FileNotFoundException e) {
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

