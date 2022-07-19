package sequences.alignment.htmlproducer;

import java.awt.Color;
import sequences.Sequence;
import sequences.alignment.Alignment;

@Deprecated
public class AlignmentHTMLProducer {
	/**
	 * Aspartic Acid 2,98 red
	 * Glutamic Acid 3,08 red
	 * Cysteine 5,02 amarillo
	 * Asparagine 5,41 amarillo
	 * Threonine 5,6 amarillo
	 * Tyrosine 5,63 amarillo
	 * Glutamine 5,65 amarillo
	 * Serine 5,68 amarillo
	 * Methionine 5,74 amarillo
	 * Tryptophan 5,88 amarillo
	 * Phenylalanine 5,91 amarillo
	 * Valine 6,02 verde
	 * Isoleucine 6,04 verde
	 * Leucine 6,04 verde
	 * Glycine 6,06 verde
	 * alanine 6,11 verde
	 * Proline 6,3 verde
	 * Histidine 7,64 verde
	 * Lysine 9,47 blue
	 * Arginine 10,76 blue
	 *
	 * @param from
	 * @param to
	 * @return
	 */
	public String produceHTML(
        Alignment alin,
        Integer from,
        Integer to,
        Color col
      ) {
		Export exporter = null;
		HighLight hl = null;
		String header = "<html><tt><PRE>";
		String end = "</PRE></tt></html>";
		String endlin = "<br>";
		StringBuilder result = new StringBuilder();
		Sequence sequence = alin.getSeq().get(0);
		String bgcolor = Integer
      .toHexString(col.getRGB())
      .substring(2);
    exporter = (new ExportFactory()).forSequence(sequence).getExporter();
		if (from == null || to == null)
			hl = new WithOutHighLight();
		else
			hl = new WithHighLight(bgcolor);
		result.append(header);
		int l = alin.getSeq().size();
		for (int i = 0; i < l; i++) {
			sequence = alin.getSeq().get(i);
			for (int j = 0; j < sequence.getLength(); j++) {
				char c = sequence.getSequence().charAt(j);
				String fontColor = exporter.getFontColor(c);
				String temp = hl.highlight(j, from, to);
				temp = temp.replaceAll("#1", fontColor);
				temp = exporter.getLetters(temp, c);
			}
			result.append(endlin);
		}
		result.append(end);
		return result.toString();
	}
}
