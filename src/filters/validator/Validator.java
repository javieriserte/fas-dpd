package filters.validator;


/**
 * This class represents conditions that can be satisfied by a Primer.
 * There is simple conditions, that test one thing.
 * And multiple conditions that performs boolean operations of one or between any two conditions.
 * It is implemented using a 'composite' pattern.  
 * 
 * @author Javier Iserte <jiserte@unq.edu.ar>
 * 
 */
public abstract class Validator {
	/**
	 * Validate method is used to verify if a primer satisfy a condition.
	 * @param p the primer that is tested.
	 * @return a boolean. True if the condition is satisfied, false otherwise.
	 */
	public abstract boolean validate(Validable p);

}
