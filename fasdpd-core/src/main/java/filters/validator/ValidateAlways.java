
package filters.validator;

/**
 * This class represents a dummy condition that is always true. 
 * @author Javier Iserte <jiserte@unq.edu.ar>
 * 
 */
public class ValidateAlways extends ValidatorSimple {

	@Override
	/**
	 * Do not perform any task, just return true.
	 */
	public boolean validate(Validable p) {
		return true;
	}

	@Override
	public String toString() {
		return "ValidateAlways []";
	}

	
	
}
