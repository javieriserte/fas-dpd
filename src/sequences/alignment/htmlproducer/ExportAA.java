package sequences.alignment.htmlproducer;

@Deprecated
public class ExportAA extends Export {
	ExportAA() {
		seqTocol.put('D', "red");
		seqTocol.put('E', "red");
		seqTocol.put('C', "yellow");
		seqTocol.put('N', "yellow");
		seqTocol.put('T', "yellow");
		seqTocol.put('Y', "yellow");
		seqTocol.put('Q', "yellow");
		seqTocol.put('M', "yellow");
		seqTocol.put('W', "yellow");
		seqTocol.put('F', "yellow");
		seqTocol.put('V', "green");
		seqTocol.put('I', "green");
		seqTocol.put('L', "green");
		seqTocol.put('G', "green");
		seqTocol.put('A', "green");
		seqTocol.put('P', "green");
		seqTocol.put('H', "green");
		seqTocol.put('K', "blue");
		seqTocol.put('R', "blue");
		seqTocol.put('-', "gray");
		defaultcolor = "black";
	}

	@Override
	protected String getLetters(String temp, char c) {
		return temp.replaceAll("#L", String.valueOf(c) + "  ");
	}
}