package edu.oit.cst236.lab2.model;

/**
 * The interface for a Library model object
 * 
 * @author nferraro
 *
 */
public interface ILibrary {
	/**
	 * Get the id of the library. The id should never be null or empty.
	 * @return a string representing the library's id.
	 */
	public String getId();
	
	/**
	 * Get the name of the library. The name should never be null or empty.
	 * @return a string representing the library's name.
	 */
	public String getName();
	
	/**
	 * Get the number of available books at this library.
	 * This number should not be less than 0.
	 * @return the number of available books at this library.
	 */
	public int getAvailableBooks();
	
	/**
	 * Get the number of unavailable books at this library.
	 * This number should not be less than 0.
	 * @return the number of unavailable books at this library.
	 */
	public int getUnavailableBooks();
}