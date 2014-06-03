package edu.oit.cst236.lab2.model.core;

import edu.oit.cst236.lab2.model.IBook;

/**
 * A Book POJO that follows the IBook interface.
 * 
 * @author nferraro
 *
 */
public class Book implements IBook {
	private final String id;
	private final String title;
	private String description = "";
	
	/**
	 * Create an instance of a book object.
	 * @param id the id of the book.
	 * @param title the title of the book.
	 */
	public Book(String id, String title) {
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("Book ID must be not be null or empty.");
		this.id = id;
		
		if (title == null || title.isEmpty())
			throw new IllegalArgumentException("Book Title must not be null or empty.");
		this.title = title;
	}
	
	@Override
	public String getId() {
		return this.id;
	}
	
	@Override
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Set the book's description.
	 * @param description the description of the book.
	 */
	public void setDescription(String description) {
		if (description == null)
			throw new IllegalArgumentException("Description must not be null.");
		this.description = description;
	}
	
	@Override
	public String getDescription() {
		return this.description;
	}
}
