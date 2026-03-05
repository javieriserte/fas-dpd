package sequences.alignment.htmlproducer;

@Deprecated
public class ExportDNA extends Export {
	ExportDNA() {
		seqTocol.put('A', "green");
		seqTocol.put('C', "blue");
		seqTocol.put('T', "red");
		seqTocol.put('G', "black");
		seqTocol.put('-', "gray");
		defaultcolor = "pink";
	}

	@Override
	protected String getLetters(String temp, char c) {
		return temp.replaceAll("#L", String.valueOf(c));
	}
}