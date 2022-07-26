package fasdpd.cli;

import java.util.Optional;

import cmdGA2.CommandLine;
import cmdGA2.NoArgumentOption;
import cmdGA2.SingleArgumentOption;
import cmdGA2.exceptions.IncorrectCommandLineException;
import cmdGA2.returnvalues.FloatValue;
import cmdGA2.returnvalues.IntegerValue;
import cmdGA2.returnvalues.StringValue;
import fasdpd.cli.End5v3Value.Result;

public class FASDPDCommandLine {
  public Optional<CommandLine> cmd = Optional.empty();
  public SingleArgumentOption<Integer> lenMin;
  public SingleArgumentOption<Integer> lenMax;
  public SingleArgumentOption<String> infile;
  public SingleArgumentOption<String> outfile;
  public SingleArgumentOption<String> gcfile;
  public SingleArgumentOption<String> profile;
  public SingleArgumentOption<Integer> quantity;
  public SingleArgumentOption<Integer> start;
  public SingleArgumentOption<Integer> end;
  public SingleArgumentOption<Float> nx;
  public SingleArgumentOption<Float> ny;
  public SingleArgumentOption<Float> pa;
  public NoArgumentOption isDna;
  public NoArgumentOption isProtein;
  public NoArgumentOption complementary;
  public NoArgumentOption pair;
  public NoArgumentOption tmSL;
  public NoArgumentOption tmsimple;
  public NoArgumentOption filterRep;
  public NoArgumentOption filterDeg;
  public NoArgumentOption notmOpt;
  public NoArgumentOption noend5v3;
  public NoArgumentOption nobaserun;
  public NoArgumentOption nohomodimer;
  public NoArgumentOption nohomodimerfixedEnd;
  public NoArgumentOption nogccontent;
  public NoArgumentOption noscore;
  public NoArgumentOption noampsize;
  public NoArgumentOption nosmallampsize;
  public NoArgumentOption nogccomp;
  public NoArgumentOption noheterodimer;
  public NoArgumentOption noheterodimerfixedEnd;
  public NoArgumentOption notmcomp;
  public SingleArgumentOption<Result> end5v3;
  public SingleArgumentOption<Integer> baserun;
  public SingleArgumentOption<Integer> homodimer;
  public SingleArgumentOption<Integer> homodimerfixedEnd;
  public SingleArgumentOption<Float[]> gccontent;
  public SingleArgumentOption<Float> score;
  public SingleArgumentOption<Integer> ampsize;
  public SingleArgumentOption<Integer> smallampsize;
  public SingleArgumentOption<Float> gccomp;
  public SingleArgumentOption<Float[]> tmOpt;
  public SingleArgumentOption<Integer> heterodimer;
  public SingleArgumentOption<Integer> heterodimerfixedEnd;
  public SingleArgumentOption<Float> tmcomp;

  public static FASDPDCommandLine create_default() {
    CommandLine cmd = new CommandLine();
    FASDPDCommandLine fdpCmd = new FASDPDCommandLine();
    fdpCmd.cmd = Optional.of(cmd);
    fdpCmd.lenMin = new SingleArgumentOption<Integer>(
      cmd, "/lenMin", new IntegerValue(), 20);
    fdpCmd.lenMax = new SingleArgumentOption<Integer>(
      cmd, "/lenMax", new IntegerValue(), 25);
    fdpCmd.infile = new SingleArgumentOption<String>(
      cmd, "/infile", new StringValue(), null);
    fdpCmd.outfile = new SingleArgumentOption<String>(
      cmd, "/outfile", new StringValue(), null);
    fdpCmd.gcfile = new SingleArgumentOption<String>(
      cmd, "/gcfile", new StringValue(), null);
    fdpCmd.profile = new SingleArgumentOption<String>(
      cmd, "/profile", new StringValue(), null);
    fdpCmd.quantity = new SingleArgumentOption<Integer>(
      cmd, "/q", new IntegerValue(), 20);
    fdpCmd.start = new SingleArgumentOption<Integer>(
      cmd, "/startingpoint", new IntegerValue(), 1);
    fdpCmd.end = new SingleArgumentOption<Integer>(
      cmd, "/endpoint", new IntegerValue(), -1);
    fdpCmd.nx = new SingleArgumentOption<Float>(
      cmd, "/nx", new FloatValue(), (float)1);
    fdpCmd.ny = new SingleArgumentOption<Float>(
      cmd, "/ny", new FloatValue(), (float)1);
    fdpCmd.pa = new SingleArgumentOption<Float>(
      cmd, "/pa", new FloatValue(), (float)0);
    fdpCmd.isDna = new NoArgumentOption(cmd, "/isDNA");
    fdpCmd.isProtein = new NoArgumentOption(cmd, "/isProtein");
    fdpCmd.complementary = new NoArgumentOption(
      cmd, "/ComplementaryStrand"
    );
    fdpCmd.pair = new NoArgumentOption(cmd, "/pair");
    fdpCmd.tmSL = new NoArgumentOption(cmd, "/tmsantalucia");
    fdpCmd.tmsimple = new NoArgumentOption(cmd, "/tmsimple");
    // DEFINE OPTIONS FOR FILTERS
    fdpCmd.filterRep = new NoArgumentOption(cmd, "/frep");
    fdpCmd.filterDeg = new NoArgumentOption(cmd, "/fdeg");
    fdpCmd.tmOpt = new SingleArgumentOption<Float[]>(
        cmd, "/tm", new FloatArrayValue(), new Float[]{50f,65f});
    fdpCmd.notmOpt = new NoArgumentOption(cmd, "/notm");
    fdpCmd.end5v3 = new SingleArgumentOption<End5v3Value.Result>(
        cmd,
        "/end5v3",
        new End5v3Value(),
        new End5v3Value.Result(1.5, 273 + 37, 0.05, 5)
      );
    fdpCmd.noend5v3 = new NoArgumentOption(cmd, "/noend5v3");
    fdpCmd.baserun = new SingleArgumentOption<Integer>(
      cmd, "/baserun", new IntegerValue(), 4);
    fdpCmd.nobaserun = new NoArgumentOption(cmd, "/nobaserun");
    fdpCmd.homodimer = new SingleArgumentOption<Integer>(
      cmd, "/homodimer", new IntegerValue(), 5);
    fdpCmd.nohomodimer = new NoArgumentOption(cmd, "/nohomodimer");
    fdpCmd.homodimerfixedEnd = new SingleArgumentOption<Integer>(
      cmd, "/homodimer3", new IntegerValue(), 3);
    fdpCmd.nohomodimerfixedEnd = new NoArgumentOption(cmd, "/nohomodimer3");
    fdpCmd.gccontent = new SingleArgumentOption<Float[]>(
      cmd, "/gc", new FloatArrayValue(), new Float[]{40f,60f});
    fdpCmd.nogccontent = new NoArgumentOption(cmd, "/nogccontent");
    fdpCmd.score = new SingleArgumentOption<Float>(
      cmd, "/score", new FloatValue(), 0.8f);
    fdpCmd.noscore = new NoArgumentOption(cmd, "/noscore");
    fdpCmd.ampsize = new SingleArgumentOption<Integer>(
      cmd, "/size", new IntegerValue(), 200);
    fdpCmd.noampsize = new NoArgumentOption(cmd, "/nosize");
    fdpCmd.smallampsize = new SingleArgumentOption<Integer>(
      cmd, "/minsize", new IntegerValue(), 100);
    fdpCmd.nosmallampsize = new NoArgumentOption(cmd, "/nominsize");
    fdpCmd.gccomp = new SingleArgumentOption<Float>(
      cmd, "/gccomp", new FloatValue(), 10f);
    fdpCmd.nogccomp = new NoArgumentOption(cmd, "/nogccomp");
    fdpCmd.heterodimer = new SingleArgumentOption<Integer>(
      cmd, "/hetdimer", new IntegerValue(), 5);
    fdpCmd.noheterodimer = new NoArgumentOption(cmd, "/nohetdimer");
    fdpCmd.heterodimerfixedEnd = new SingleArgumentOption<Integer>(
      cmd, "/hetdimer3", new IntegerValue(), 3);
    fdpCmd.noheterodimerfixedEnd = new NoArgumentOption(cmd, "/nohetdimer3");
    fdpCmd.tmcomp = new SingleArgumentOption<Float>(
      cmd, "/tmcomp", new FloatValue(), 5f);
    fdpCmd.notmcomp = new NoArgumentOption(cmd, "/notmcomp");
    return fdpCmd;
  }

  public void parse(String[] arg) throws IncorrectCommandLineException {
    if (this.cmd.isEmpty()) {
      return;
    }
    this.cmd.get().read(arg);
  }

  /**
   * @return
   * @throws InvalidCommandLineException
   */
  public boolean validate() throws InvalidCommandLineException{
		if (! (infile.isPresent()&&outfile.isPresent()&&gcfile.isPresent())) {
			// infile, outfile and gcfile are required!
			// if one of them is not present then the command line is not well formed.
			throw new InvalidCommandLineException(
				"Infile, Outfile and GCfile are required arguments"
			);
		}

		if (isDna.isPresent()&&isProtein.isPresent()) {
			// both option can not be in the command line at the same moment.
			throw new InvalidCommandLineException(
				"isDna option and isProtein Option can not "
        + "be in the command line at the same time."
			);
		}

		if (tmSL.isPresent() && tmsimple.isPresent() ) {
			// These options can not be in the commandline at the same time.
			throw new InvalidCommandLineException(
				"Can not use both Tm estimators at the same time. Choose one."
			);
		}

		if((Integer)lenMin.getValue() > (Integer)lenMax.getValue() ) {
			// Primer Min can not be greater than Primer Max.
			throw new InvalidCommandLineException(
				"Max length is lesser than Min length"
			);
		}

		if (tmOpt.isPresent() && notmOpt.isPresent() ) {
 			// This options can't be in the command line at the same time
			throw new InvalidCommandLineException(
				"/tm and /notm options can not appear in the command "
        + "line simoultaneously"
			);
		}
		if (end5v3.isPresent() && noend5v3.isPresent() ) {
 			// This options can't be in the command line at the same time
			throw new InvalidCommandLineException(
				"/end5v3 and /noend5v3 options can not appear in the command "
        + "line simoultaneously"
			);
		}
		if (baserun.isPresent() && nobaserun.isPresent() ) {
 			// This options can't be in the command line at the same time
			throw new InvalidCommandLineException(
				"/baserun and /nobaserun options can not appear in the "
        + "command line simoultaneously"
			);
		}
		if (homodimer.isPresent() && nohomodimer.isPresent() ) {
 			// This options can't be in the command line at the same time
			throw new InvalidCommandLineException(
				"/homodimer and /nohomodimer options can not appear in "
        + " the command line simoultaneously"
			);
		}
		if (homodimerfixedEnd.isPresent() && nohomodimerfixedEnd.isPresent() ) {
 			// This options can't be in the command line at the same time
			throw new InvalidCommandLineException(
        "/homodimer3 and /nohomodimer3 options can not appear in the "
        + "command line simoultaneously"
      );
		}
		if (gccontent.isPresent() && nogccontent.isPresent() ) {
 			// This options can't be in the command line at the same time
			throw new InvalidCommandLineException(
        "/gccontent and /nogccontent options can not appear in the"
        + " command line simoultaneously"
      );
		}
		if (score.isPresent() && noscore.isPresent() ) {
 			// This options can't be in the command line at the same time
			throw new InvalidCommandLineException(
        "/score and /noscore options can not appear in the command "
        + "line simoultaneously"
      );
		}
		if (ampsize.isPresent() && noampsize.isPresent() ) {
 			// This options can't be in the command line at the same time
			throw new InvalidCommandLineException(
        "/ampsize and /noampsize options can not appear in the command line "
        + "simoultaneously"
      );
		}
		if (gccomp.isPresent() && nogccomp.isPresent() ) {
 			// This options can't be in the command line at the same time
			throw new InvalidCommandLineException(
        "/gccomp and /nogccomp options can not appear in the "
        + "command line simoultaneously"
      );
		}
		if (heterodimer.isPresent() && noheterodimer.isPresent() ) {
 			// This options can't be in the command line at the same time
			throw new InvalidCommandLineException(
        "/heterodimer and /noheterodimer options can not appear in "
        + "the command line simoultaneously"
      );
		}
		if (heterodimerfixedEnd.isPresent() && noheterodimerfixedEnd.isPresent() ) {
 			// This options can't be in the command line at the same time
			throw new InvalidCommandLineException(
        "/heterodimer3 and /noheterodimer3 options can not appear "
        + "in the command line simoultaneously"
      );
		}
		if (tmcomp.isPresent() && notmcomp.isPresent() ) {
 			// This options can't be in the command line at the same time
			throw new InvalidCommandLineException(
        "/tmcomp and /notmcomp options can not appear in the "
        + "command line simoultaneously"
      );
		}

		if (!pair.isPresent()) {
			boolean isAnyPrimerPairParameter = false;
			isAnyPrimerPairParameter |= noampsize.isPresent();
			isAnyPrimerPairParameter |= ampsize.isPresent();
			isAnyPrimerPairParameter |= smallampsize.isPresent();
			isAnyPrimerPairParameter |= nogccomp.isPresent();
			isAnyPrimerPairParameter |= gccomp.isPresent();
			isAnyPrimerPairParameter |= noheterodimer.isPresent();
			isAnyPrimerPairParameter |= heterodimer.isPresent();
			isAnyPrimerPairParameter |= noheterodimerfixedEnd.isPresent();
			isAnyPrimerPairParameter |= heterodimerfixedEnd.isPresent();
			isAnyPrimerPairParameter |= notmcomp.isPresent();
			isAnyPrimerPairParameter |= tmcomp.isPresent();
			if (isAnyPrimerPairParameter) {
	 			// there is some options for primer pair search,
				// but /pair option is not present.
				throw new InvalidCommandLineException(
					"/pair option is not present in the command line, "
          + "but there is one or more parameters for primer pair search."
				);
			}
		}
    return true;
  }
}
