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
import java.io.IOException;
/**
 * This class reads a buffer and permits do more actions than a simple bufferedReader.
 * 
 * @author Javier Iserte <jiserte@unq.edu.ar>
 * @version 1.1.1
 */
public class TextConsumer {

	
	protected String prebuffer="";
	protected BufferedReader buffer = null;
	protected boolean readFromStart=true;
	
	/**
	 * This method ask if there are remaining bytes to read; 
	 * 
	 * @return
	 */
	public boolean ready() {
		try {
			
			return this.buffer.ready() || this.prebuffer!="";
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * this methods removes from TextConsumer everything before the next end of line symbol 
	 * and returns it as a String.
	 * 
	 * @return Everything before the next end of line symbol.
	 * @throws IOException
	 */
	public String consumeLine() throws IOException {

		String winEOF = "\r\n";
		String linuxEOF = "\n";
		String macEOF = "\r";
		String result = "";
		int loc;
		this.transfer();
		
		if (this.prebuffer.contains(winEOF)) {
			loc = this.prebuffer.indexOf(winEOF);
			result = this.prebuffer.substring(0,Math.max(0,loc));
			this.prebuffer = this.prebuffer.substring(Math.min(loc+2, this.prebuffer.length()));
			
		} else if (this.prebuffer.contains(linuxEOF)) {
			loc = this.prebuffer.indexOf(linuxEOF);
			result = this.prebuffer.substring(0,Math.max(0,loc));
			this.prebuffer = this.prebuffer.substring(Math.min(loc+1, this.prebuffer.length()));
			
		} else if (this.prebuffer.contains(macEOF)) {
			loc = this.prebuffer.indexOf(macEOF);
			result = this.prebuffer.substring(0,Math.max(0,loc));
			this.prebuffer = this.prebuffer.substring(Math.min(loc+1, this.prebuffer.length()));
			
		} else {
			result = this.prebuffer;
			this.prebuffer="";
		}

		this.readFromStart =true;
		return result.trim();		
		
	}
	
	/**
	 * This method removes everything before the next 'until' String and returns it as a String.
	 * The first time the object is called, the search begins from position 0, the subsequent 
	 * searches will begin from position 1 to avoid looping when searching the same string many times.    
	 * 
	 * @param until
	 * @return
	 * @throws IOException
	 */
	public String consumeUntil(String until) throws IOException {
		String result ="";
		int loc;

		loc = this.searchFor(until);
		if (loc==-1) {
			// 'until' string not found. Must return everything.
			result = this.prebuffer;
			this.prebuffer= "";

		} else if (loc==0) {
			// Special case. Found at beginning. return nothing.
			result="";
		} else {
			result = this.prebuffer.substring(0,loc);
			this.prebuffer= this.prebuffer.substring(loc);
		}
				
			// loop if until String is not found or there is not any more bytes to read
		this.readFromStart =false;
		return result;
	}
	
	/**
	 * Returns the first appearance of 'string' in prebuffer and buffer.
	 * Warnings: this method has collateral effect changing the values of buffer and prebuffer.
	 * 
	 * @param string
	 * @return
	 */
	protected int searchFor(String string) {
		int searchFrom;
		if (this.readFromStart) searchFrom=0; else searchFrom=1;
				
		int p = this.prebuffer.indexOf(string, searchFrom);
		
		if (p==-1) {
			boolean EOB;
			do {
				searchFrom = this.prebuffer.length() - string.length()+1; 
				EOB = this.transfer();
				p = this.prebuffer.indexOf(string, searchFrom);
			} while (p==-1 && EOB);
		}
		
		return p;
			// -1 if not found in the entire buffer.
			// position otherwise
	}
	
	/**
	 * Transfers a part of BufferedReader 'buffer' to prebuffer.
	 * Transfer until any End of line characters 
	 * 
	 * return true is the end of buffer has been reached
	 */
	protected boolean transfer() {
	
		StringBuilder transfered = new StringBuilder();
		int i=0;
		char l = 0;
		int c=0;
		boolean exit = true;
		do {
			try {
				l = (char)c;
				c = this.buffer.read();
				if (c>=0) transfered.append((char) c);
				if(c==-1) exit = false;
			} catch (IOException e) {}
			i++; 
		} while(c>=0 && (l !='\r' && l !='\n') );
		this.prebuffer = this.prebuffer + transfered;
		return exit;
	}
	
	/**
	 * Transfers a part of BufferedReader 'buffer' to prebuffer.
	 * @param bytes
	 */
	protected void transfer(int bytes) {
		StringBuilder transfered = new StringBuilder();
		int i=0;
		int c=0;
		do {
			try {
				c = this.buffer.read();
				if (c>=0) transfered.append((char) c);
			} catch (IOException e) {}
			i++; 
		} while(i<bytes && c>=0);
		this.prebuffer = this.prebuffer + transfered;
	}
	
}
