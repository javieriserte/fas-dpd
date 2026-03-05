package fasdpd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;

import sequences.dna.Primer;
import sequences.util.tmcalculator.SantaluciaTmEstimator;

/**
 * Exports a .tsv file for a list of primers or primer pairs.
 */
public class PrimerListExporter {
  public static void exportPrimersToFile(
      File outfile,
      List<PrimerOrPrimerPair> primerData) throws IOException{
    String[] header = getHeader(primerData);
    List<String[]> content = getContent(primerData);
    writeContents(header, content, outfile);
  }

  private static String[] getContentLineForPrimer(Primer p){
    return new String[]{
      p.getSequence(),
      String.valueOf(p.getScore()),
      String.valueOf(p.getStart()),
      String.valueOf(p.getEnd()),
      String.valueOf(p.isDirectStrand())
    };
  }

  private static String[] getContentLineForPrimerPair(PrimerPair p){
    SantaluciaTmEstimator tmCalc = new SantaluciaTmEstimator();
    Primer forward = p.getForward();
    Primer reverse = p.getReverse();
    tmCalc.calculateTM(forward);
    double forwardTm = tmCalc.mean();
    tmCalc.calculateTM(reverse);
    double reverseTm = tmCalc.mean();
    return new String[]{
      forward.getSequence(),
      String.valueOf(forward.getScore()),
      String.valueOf(forward.getStart()),
      String.valueOf(forward.getEnd()),
      String.valueOf(forward.isDirectStrand()),
      String.valueOf(forwardTm),
      reverse.getSequence(),
      String.valueOf(reverse.getScore()),
      String.valueOf(reverse.getStart()),
      String.valueOf(reverse.getEnd()),
      String.valueOf(reverse.isDirectStrand()),
      String.valueOf(reverseTm),
    };
  }

  private static void writeContents(
      String[] header,
      List<String[]> content,
      File outfile) throws IOException {
    PrintWriter writer = new PrintWriter(outfile ,"UTF8");
    writer.println(String.join("\t", header));
    for (String[] line : content) {
      writer.println(String.join("\t", line));
    }
    writer.flush();
    writer.close();
  }

  private static List<String[]> getContent(List<PrimerOrPrimerPair> primerData) {
    List<String[]> content = new ArrayList<String[]>();
    for (PrimerOrPrimerPair p : primerData) {
      if (p.isPrimer()) {
        content.add(getContentLineForPrimer(p.getPrimer()));
      } else {
        content.add(getContentLineForPrimerPair(p.getPrimerPair()));
      }
    }
    return content;
  }

  private static String[] getHeader(List<PrimerOrPrimerPair> primerData) {
    if (primerData.size()>0) {
      PrimerOrPrimerPair firstElement = primerData.get(0);
      if (!firstElement.isPrimer()) {
        return new String[]{
          "Sequence",
          "Score",
          "Start",
          "End",
          "DirectStrand",
          "Sequence",
          "Score",
          "Start",
          "End",
          "DirectStrand"};
      } else {
        return new String[]{
        "Sequence",
        "Score",
        "Start",
        "End",
        "DirectStrand"
        };
      }
    }
    return new String[]{};
  }
}
