package tests.degenaration;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import degeneration.GeneticCode;

public class GeneticCode2Test {

  @Test
  void shouldPileUpTwoIdenticalSequences() {
    try {
      GeneticCode gc = new GeneticCode("StandardCode");
      Assertions.assertEquals(
          gc.pileUp(
            "ACTGACTGACTGACTG",
            "ACTGACTGACTGACTG"
          ),
          "ACTGACTGACTGACTG"
      );
    } catch (IOException e) {
      fail(e.getLocalizedMessage());
    }
  }

  @Test
  void shouldPileUpTwoNonDegeneratedSequences() {
    try {
      GeneticCode gc = new GeneticCode("StandardCode");
      Assertions.assertEquals(
        gc.pileUp(
          "AAAACCCCGGGGTTTT",
          "ACTGACTGACTGACTG"
        ),
          "AMWRMCYSRSKGWYTK"
      );
    } catch (IOException e) {
      fail(e.getLocalizedMessage());
    }
  }

  @Test
  void shouldPileUpTwoDegeneratedSequences() {
    try {
      GeneticCode gc = new GeneticCode("StandardCode");
      Assertions.assertEquals(
          gc.pileUp(
            "MMMMKKKKWWWWSSSSYYYYRRRR",
            "BDHVBDHVBDHVBDHVBDHVBDHV"
          ),
            "NNHVBDNNNDHNBNNVBNHNNDNV"
      );
    } catch (IOException e) {
      fail(e.getLocalizedMessage());
    }
  }

  @Test
  void shouldPileUpTwoSequencesWithGaps() {
    try {
      GeneticCode gc = new GeneticCode("StandardCode");
      Assertions.assertEquals(
          gc.pileUp(
            "ACTG",
            "----"
          ),
            "NNNN"
      );
    } catch (IOException e) {
      fail(e.getLocalizedMessage());
    }
  }

  @Test
  void shouldPileUpTwoSequenceIgnoringTerminalGaps() {
    try {
      GeneticCode gc = new GeneticCode("StandardCode");
      Assertions.assertEquals(
          gc.pileUpIgnoreTerminalGaps(
            "ACGTG",
            "--C--"
          ),
            "ACSTG"
      );
    } catch (IOException e) {
      fail(e.getLocalizedMessage());
    }
  }
}
