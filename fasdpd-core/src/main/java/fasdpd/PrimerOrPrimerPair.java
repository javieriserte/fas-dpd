package fasdpd;

import java.util.Optional;

import sequences.dna.Primer;

public class PrimerOrPrimerPair {
  private Optional<Primer> primer;
  private Optional<PrimerPair> pair;

  public PrimerOrPrimerPair(Primer p) {
    this.primer = Optional.of(p);
    this.pair = Optional.empty();
  }

  public PrimerOrPrimerPair(PrimerPair p) {
    this.primer = Optional.empty();
    this.pair = Optional.of(p);
  }

  public boolean isPrimer() {
    return primer.isPresent();
  }

  public boolean isPrimerPair() {
    return pair.isPresent();
  }

  public Primer getPrimer(){
    return primer.get();
  }

  public PrimerPair getPrimerPair() {
    return pair.get();
  }

}
