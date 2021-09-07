package filters.validator;

/**
 * This class represents the boolean operation NOT to use with a Validator Object
 * 
 * @author "Javier Iserte <jiserte@unq.edu.ar>"
 * 
 */
public class Validate_NOT extends ValidatorBoolean {

	private Validator op1;
	/**
	 * Creates a new instance of Validate_NOT.
	 * One Validator is necessary.
	 */
	// CONSTRUTOR
	public Validate_NOT(Validator op1) {
		super();
		this.op1 = op1;
	}
	
	
	@Override
	public boolean validate(Validable p) {
		return (!this.op1.validate(p));
	}


	@Override
	public String toString() {
		return "Validate_NOT [op1=" + op1 + "]";
	}
	
	


}
