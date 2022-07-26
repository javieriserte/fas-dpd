package fasdpd.cli;
/**
 * This exception is thrown when the command line in not well formed.
 * Usually this is given by incompatibility of arguments. 
 *
 */
public class InvalidCommandLineException extends Exception {

	private static final long serialVersionUID = 1L;

	// CONSTRUCTORS
	/**
	 * Creates a new InvalidCommandLineException with the default error message.
	 */
	public InvalidCommandLineException() {
		super("Command Line is incorrect");
	}
	/**
	 * Creates a new InvalidCommandLineException with the custom error message.
	 * @param message is the custom error message.
	 */
	public InvalidCommandLineException(String message) {
		super(message);
	}
}
