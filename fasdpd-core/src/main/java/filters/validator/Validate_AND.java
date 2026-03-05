
package filters.validator;

/**
 * This class represents the boolean operation AND to use with Validator Objects
 * 
 * @author "Javier Iserte <jiserte@unq.edu.ar>"
 * 
 */
public class Validate_AND extends ValidatorBoolean {

	private Validator op1;
	private Validator op2;
	
	// COSNTRUCTOR
	/**
	 * Creates a new instance of Validate_AND.
	 * Two Validators are necessary.
	 */
	public Validate_AND(Validator op1, Validator op2) {
		super();
		this.op1 = op1;
		this.op2 = op2;
	}
	
	@Override
	/**
	 * This methods performs the boolean evaluation 'AND' of the two Validators. 
	 */
	public boolean validate(Validable p) {
		return (this.op1.validate(p) && this.op2.validate(p));
	}

	@Override
	public String toString() {
		return "Validate_AND [op1=" + op1 + ", op2=" + op2 + "]";
	}
	
	


}
