package edu.oit.cst236.lab2.model;

/**
 * The interface for a Book model object
 * 
 * @author Spidy
 *
 */
public interface IBook {
	/**
	 * Get the id of the book. The id should never be null or empty.
	 * @return a string representing the book's id.
	 */
	public String getId();
	
	/**
	 * Get the title of the book. The title should never be null or empty.
	 * @return a string representing the book's title.
	 */
	public String getTitle();
	
	/**
	 * Get the description of the book. The description should never be null.
	 * @return a string representing the book's description.
	 */
	public String getDescription();
}
