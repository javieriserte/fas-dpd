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

package sequences.alignment.htmlproducer;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import sequences.Sequence;
import sequences.alignment.Alignment;
import sequences.dna.DNASeq;

public class AlignmentHTMLProducer {

	
	/**
  	 *			Aspartic Acid	2,98	red
 	 *			Glutamic Acid	3,08	red
	 *			Cysteine	5,02	amarillo
	 *			Asparagine	5,41	amarillo
	 *			Threonine	5,6	amarillo
	 *			Tyrosine	5,63	amarillo
	 *			Glutamine	5,65	amarillo
	 *			Serine	5,68	amarillo
	 *			Methionine	5,74	amarillo
	 *			Tryptophan	5,88	amarillo
	 *			Phenylalanine	5,91	amarillo
	 *			Valine	6,02	verde
	 *			Isoleucine	6,04	verde
	 *			Leucine	6,04	verde
	 *			Glycine	6,06	verde
	 *			alanine	6,11	verde
	 *			Proline	6,3	verde
	 *			Histidine	7,64	verde
	 *			Lysine	9,47	blue
	 *			Arginine	10,76	blue
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public String produceHTML(Alignment alin, Integer from, Integer to, Color col) {
		Export e=null;
		HighLight hl=null;

		String header = "<html><tt><PRE>";
		String end = "</PRE></tt></html>";
		String endlin = "<br>";
		StringBuilder result= new StringBuilder();
		
		int l = alin.getSeq().size();
		
		Sequence sequence = alin.getSeq().get(0);
		String bgcolor = Integer.toHexString(col.getRGB()).substring(2);
		
		if (sequence.getClass()==DNASeq.class) e = new ExportDNA(); else 	e = new ExportAA();
		if (from==null|| to==null) hl = new WithOutHighLight(); else hl = new WithHighLight(bgcolor);

		result.append(header);

		for (int i=0;i<l;i++) {
			
			sequence = alin.getSeq().get(i);
			for (int j=0;j<sequence.getLength();j++) {
				
				char c = sequence.getSequence().charAt(j);
				String fontColor = e.getFontColor(c);
				
				String temp = hl.highlight(j,from,to);
				
				temp = temp.replaceAll("#1", fontColor);
				temp = e.getLetters(temp, c);
				
				result.append(temp);
			}
			result.append(endlin);
		}
		result.append(end);
		return result.toString();
	}

}
