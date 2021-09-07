package fastaIO;
/**
 * This class represents a pair of elements.
 *
 * @param <First> the first component of the pair.
 * @param <Second> the second component of the pair.
 */
public class Pair <First, Second> {
	private First first;
	private Second second;
   
	/**
	 * Creates a new pair a objects.
	 * @param first the first component of the pair.
	 * @param second the second component of the pair.
	 */
	public Pair( First first, Second second ) {
		this.setFirst(first);
		this.setSecond(second);

	}


	// PUBLIC METHODS
	/**
	 * @return hash code for this object.
	 */
	@Override
	public int hashCode() {
		if (this.getFirst() == null) {
			if (this.getSecond() == null) return 0;
			return this.getSecond().hashCode();
		} else {
			if (this.getSecond() == null) return this.getFirst().hashCode() * 43;
			return this.getFirst().hashCode() * 43 *  this.getSecond().hashCode();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * Re - implementation of equals method inherited from object. 
	 */
	public boolean equals( Object object ) {
		if ( this == object ) return true;
    
		if ( object == null ) return false;
    
		if (!object.getClass().equals(this.getClass())) return false;
    
		Pair<First, Second> otherObject = (Pair<First, Second> ) object ;
    
		boolean f = ((otherObject.getFirst() == null  && (this.getFirst() == null )) || otherObject.getFirst().equals(this.getFirst()));
		return f && ((otherObject.getSecond() == null  && (this.getSecond() == null )) || otherObject.getSecond().equals(this.getSecond()));

	}

	/**
	 * This is a string representation of the Pair.
	 */
	@Override
	public String toString() {
		return "[ " + this.getFirst().toString() + " , " + this.getSecond().toString() + " ]";
	}
	
	// GETTERS AND SETTERS
	public First getFirst() {
	   return first;
	}
	public Second getSecond() {
		return second;
	}
	public void setFirst(First first) {
		this.first = first;
	}
	public void setSecond(Second second) {
		this.second = second;
	}
   
} 