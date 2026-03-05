package sequences.alignment.htmlproducer;

import sequences.Sequence;
import sequences.dna.DNASeq;

@Deprecated
public final class ExportFactory {
  private boolean isDNA = true;
  public ExportFactory forSequence(Sequence sequence) {
    isDNA = sequence.getClass() == DNASeq.class;
    return this;
  }
  public Export getExporter() {
    if (isDNA) {
      return new ExportDNA();
    }
    return new ExportAA();
  }
}
