package tests.degenaration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import degeneration.GeneticCode;

public class GeneticCode2Test {
  
  @Test
  void shouldPileUpTwoIdenticalSequences() {
    GeneticCode gc = new GeneticCode("StandardCode");
    Assertions.assertEquals(
        gc.pileUp(
          "ACTGACTGACTGACTG",
          "ACTGACTGACTGACTG"
        ),
        "ACTGACTGACTGACTG"
    );
  }

  @Test
  void shouldPileUpTwoNonDegeneratedSequences() {
    GeneticCode gc = new GeneticCode("StandardCode");
    Assertions.assertEquals(
        gc.pileUp(
          "AAAACCCCGGGGTTTT",
          "ACTGACTGACTGACTG"
        ),
          "AMWRMCYSRSKGWYTK"
    );
  }

  @Test
  void shouldPileUpTwoDegeneratedSequences() {
    GeneticCode gc = new GeneticCode("StandardCode");
    Assertions.assertEquals(
        gc.pileUp(
          "MMMMKKKKWWWWSSSSYYYYRRRR",
          "BDHVBDHVBDHVBDHVBDHVBDHV"
        ),
          "NNHVBDNNNDHNBNNVBNHNNDNV"
    );
  }

  @Test
  void shouldPileUpTwoSequencesWithGaps() {
    GeneticCode gc = new GeneticCode("StandardCode");
    Assertions.assertEquals(
        gc.pileUp(
          "ACTG",
          "----"
        ),
          "NNNN"
    );
  }
  
  @Test
  void shouldPileUpTwoSequenceIgnoringTerminalGaps() {
    GeneticCode gc = new GeneticCode("StandardCode");
    Assertions.assertEquals(
        gc.pileUpIgnoreTerminalGaps(
          "ACGTG",
          "--C--"
        ),
          "ACSTG"
    );
    
  }
  
}
