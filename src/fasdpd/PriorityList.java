package fasdpd;

import java.util.List;
import java.util.Vector;

/**
 * PriorityList is a kind of list that stores values until a maximum quantity.
 * When this quantity is reached, the new values can displace older if they had
 * a greater priority. Priority is given by natural order. Object stored in this
 * list must implement comparable interface.
 *
 * @param <T> A class that implements Comparable interface.
 */
public class PriorityList<T> {
	// INSTANCE VARIABLES
	private List<Comparable<T>> priority;
	private Comparable<T> min;
	private int capacity;
	private int currentFilled;

	// CONSTRUCTOR
	/**
	 * Creates a new instance of PriorityList
	 * 
	 * @param size is the number of values stored.
	 */
	public PriorityList(int size) {
		this.setMin(null);
		this.setPriority(new Vector<Comparable<T>>(size));
		this.setCapacity(size);
		this.setCurrentFilled(0);
	}

	// Public Interface
	/**
	 * Adds a new value to the list.
	 */
	@SuppressWarnings("unchecked")
	public void addValue(Comparable<T> newValue) {
		if (this.getCurrentFilled() == this.getCapacity()) {
			// If the list is full
			if (newValue.compareTo((T) this.getMin()) > 0) {
				this.getPriority().remove(this.getMin());
				this.getPriority().add(newValue);
				this.setMin(this.searchMin());
				// the greater is on, the lesser is off.
			}
		} else {
			// If the list is not full
			this.getPriority().add(newValue);
			this.setMin(this.searchMin());
			this.setCurrentFilled(this.getCurrentFilled() + 1);
			// add the new value with any inconvenient.
		}
	}

	/**
	 * Returns the content of the list in another sorted list. The element are
	 * erased from the original list. The sorting algorithm is a kind of selection
	 * sort.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> ExtractSortedList() {
		List<T> result = new Vector<T>(this.getCurrentFilled());
		for (int x = 0; x < this.getCurrentFilled(); x++) {
			result.add(null);
		}
		for (int x = this.getCurrentFilled() - 1; x >= 0; x--) {
			T min = (T) this.searchMin();
			// Selects the minimum from original list
			result.set(x, min);
			// Put the minimum in he new list.
			this.getPriority().remove(min);
			// remove minimum from original list.
		}
		return result;
	}

	// Private Methods
	/**
	 * search the minimum in the elements of the list and returns it.
	 * 
	 * @return a Comparable T that is the minimum in the natural order or T.
	 */
	@SuppressWarnings("unchecked")
	private Comparable<T> searchMin() {
		Comparable<T> min = this.getPriority().get(0);

		for (Comparable<T> element : this.getPriority()) {
			if (min.compareTo((T) element) > 0)
				min = element;
		}
		return min;

	}

	// GETTERS & SETTERS
	public void setPriority(List<Comparable<T>> priority) {
		this.priority = priority;
	}

	public List<Comparable<T>> getPriority() {
		return priority;
	}

	public void setMin(Comparable<T> min) {
		this.min = min;
	}

	public Comparable<T> getMin() {
		return min;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getCurrentFilled() {
		return currentFilled;
	}

	public void setCurrentFilled(int currentFilled) {
		this.currentFilled = currentFilled;
	}
}
