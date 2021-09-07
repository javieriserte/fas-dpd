package filters.validator;


/**
 * This class represents the boolean operation OR to use with Validator Objects
 * 
 * @author "Javier Iserte <jiserte@unq.edu.ar>"
 * 
 */
public class Validate_OR extends ValidatorBoolean {

	private Validator op1;
	private Validator op2;
	/**
	 * Creates a new instance of Validate_OR.
	 * Two Validators are necessary.
	 */	
	// CONSTRUCTOR
	public Validate_OR(Validator op1, Validator op2) {
		super();
		this.op1 = op1;
		this.op2 = op2;
	}
	/**
	 * This methods performs the boolean evaluation 'OR' of the two Validators. 
	 */	
	@Override
	public boolean validate(Validable p) {
		return (this.op1.validate(p) || this.op2.validate(p));
	}
	@Override
	public String toString() {
		return "Validate_OR [op1=" + op1 + ", op2=" + op2 + "]";
	}

	

}
