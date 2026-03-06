package fasdpd.cli;

import java.io.FileNotFoundException;
import java.io.IOException;

import cmdGA2.exceptions.IncorrectCommandLineException;
import fasdpd.SearchParameter;
import fasdpd.FASDPDController;
import fasdpd.ResourceLoader;


/**
 * Executable Class For Command line FAS-DPD program.
 */
public class FASDPDCli {
	private static final System.Logger LOGGER = System.getLogger(
		FASDPDCli.class.getName()
	);

	/**
	 * Gets the the text of help.
	 */
	public static String getHelp() {
		var helpText = ResourceLoader.readUtf8Resource(FASDPDCli.class, "help");
		if (helpText.isPresent()) {
			return helpText.get();
		}
		LOGGER.log(
			System.Logger.Level.ERROR,
			"Cannot load help resource from classpath/module-path."
		);
		return "";
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
