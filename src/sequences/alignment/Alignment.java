package sequences.alignment;

import java.util.List;
import java.util.Vector;

import sequences.Sequence;
import sequences.dna.DNASeq;

import degeneration.GeneticCode;

/**
 * This class represents an alignment of sequences.
 * A invariant of representation is that all the sequences had the same length.
 *
 * @author Javier Iserte <jiserte@unq.edu.ar>
 */
public class Alignment {
  protected List<Sequence> seq;

  /**
   * Creates a new alignment from a list of sequences.
   */
  public Alignment(List<Sequence> seq) {
    super();
    this.setSeq(seq);
  }

  /**
   * Creates an empty alignment
   */
  public Alignment() {
    super();
    this.setSeq(new Vector<Sequence>());
  }

  public void addSequence(Sequence seq) {
    this.getSeq().add(seq);
  }

  /**
   * Removes a sequence from the alignment. Assumes that there is not two or
   * more sequences with the same description
   *
   * @param Description is a String to identify a sequence.
   */
  public void removeSequence(String Description) {
    List<Sequence> l = new Vector<Sequence>();
    l.addAll(this.getSeq());
    for (Sequence seq : this.getSeq()) {
      if (seq.getDescription().equals(Description)) {
        l.remove(seq);
      }
    }
    this.setSeq(l);
  }

  /**
   * Checks that all sequences have a different description.
   *
   * @return true. If all of them are different, false otherwise.
   */
  public boolean verifyDifferentDescriptions() {
    boolean result = true;
    outer:
    for (int x = 0; x < this.getSeq().size() - 1; x++) {
      for (int y = x + 1; y < this.getSeq().size(); y++) {
        String seq_x = this.getSeq().get(x).getDescription();
        String seq_y = this.getSeq().get(y).getDescription();
        if (seq_x == seq_y) {
          result = false;
          break outer;
        }
      }
    }
    return result;
  }

  /**
   * This method is used to create a single DNA sequence that is the consensus of
   * all the sequences in the alignment.
   * When the alignment is of Proteins, each sequence is back translated to DNA.
   *
   * @param myGC a genetic code. Used when proteins must be back translated to
   *             DNA.
   * @return a DNASeq representing the consensus of the alignment.
   */
  public DNASeq pileUp(GeneticCode myGC) {
    if (this.getSeq().size() == 0) {
      return new DNASeq("", "");
    }
    Sequence result = this.getSeq().get(0);
    for (Sequence sec : this.getSeq()) {
      result = result.pileUpWith(sec, myGC);
    }
    return (DNASeq) result;
  }

  public Alignment toDnaAlignment() {
    Alignment aln = new Alignment();
    for (Sequence s : this.seq) {
      aln.addSequence(s.toDNA());
    }
    return aln;
  }

  public Alignment toProteinAlignment() {
    Alignment aln = new Alignment();
    for (Sequence s : this.seq) {
      aln.addSequence(s.toProtein());
    }
    return aln;
  }

  /**
   * @return int the length of all sequences in the alignment.
   */
  public int lenght() {
    if (this.getSeq().size() == 0) {
      return 0;
    }
    return this.getSeq().get(0).getLength();
    // all the sequences must have the same length
  }

  // Getters & Setter
  public List<Sequence> getSeq() {
    return seq;
  }

  public void setSeq(List<Sequence> seq) {
    this.seq = seq;
  }

}
