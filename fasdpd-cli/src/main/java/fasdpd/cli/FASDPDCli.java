package fasdpd.cli;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import cmdGA2.exceptions.IncorrectCommandLineException;
import fasdpd.SearchParameter;
import fasdpd.FASDPDController;


/**
 * Executable Class For Command line FAS-DPD program.
 */
public class FASDPDCli {
	/**
	 * Gets the the text of help.
	 */
	public static String getHelp() {
		var stream = FASDPDCli.class.getResourceAsStream("/help");
		if (stream == null) {
			stream = FASDPDCli.class.getClassLoader().getResourceAsStream("help");
		}
		try (var is = stream) {
			if (is == null) {
				System.err.println("Cannot find help file in classpath/module-path");
				return "";
			}
			return new String(is.readAllBytes(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println("Cannot read help file");
			return "";
		}
	}

	// Executable Main
	/**
	 * Executable main method for console interface of FAS-DPD.
	 */
	public static void main(String[] arg) {

    FASDPDController controller = new FASDPDController();
		FASDPDCommandLine cmd = FASDPDCommandLine.create_default();

		try {
			cmd.validate();
			cmd.parse(arg);
		} catch (IncorrectCommandLineException e) {
			System.out.println(e.getLocalizedMessage());
			System.out.println(FASDPDCli.getHelp());
			return;
		} catch (InvalidCommandLineException e) {
			System.out.println(e.getLocalizedMessage());
			System.out.println(FASDPDCli.getHelp());
			return;
		}

		SearchParameter sp = null;

    try {
      sp = SearchParameterBuilder.getSearchParameter(cmd);
    } catch (IOException e) {
			System.err.println(
				"There was an error reading the genetic code file:"
			);
      System.exit(1);
    }
		// sp will store all the parameters for the search
		try {
			controller.doSearchAndExportResults(sp);
		} catch (FileNotFoundException e) {
			System.err.println("FASDPD exit with errors");
			System.err.println("Input file not found");
			System.err.println(e.getLocalizedMessage());
			System.out.println(FASDPDCli.getHelp());
			System.exit(1);
		} catch (IOException e) {
			System.err.println(
				"There was an error reading the genetic code file:"
			);
			System.err.println(
				e.getLocalizedMessage()
			);
			System.out.println(FASDPDCli.getHelp());
			System.exit(1);
		}
		// start the search of primers
	}
}
