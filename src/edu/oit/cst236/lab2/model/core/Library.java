package edu.oit.cst236.lab2.model.core;

import edu.oit.cst236.lab2.model.ILibrary;

/**
 * A Library POJO that follows the ILibrary interface
 * 
 * @author nferraro
 *
 */
public class Library implements ILibrary {
	private final String id;
	private String name;
	private int availableBooks = 0;
	private int unavailableBooks = 0;
	
	/**
	 * Creates an instance of Library.
	 * @param id the id of the library.
	 * @param name the name of the library.
	 */
	public Library(String id, String name) {
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("Library ID must not be null or empty.");
		this.id = id;
		
		if (name == null || name.isEmpty())
			throw new IllegalArgumentException("Library Name must not be null or empty.");
		setName(name);
	}

	@Override
	public String getId() {
		return this.id;
	}
	
	/**
	 * Set the name of the library.
	 * @param name the library's name.
	 */
	public void setName(String name) {
		if (name == null || name.isEmpty())
			throw new IllegalArgumentException("Library Name must not be null or empty.");
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set the number of available books at this library.
	 * @param availableBooks the number of available books.
	 */
	public void setAvailableBooks(int availableBooks) {
		if (availableBooks < 0)
			throw new IllegalArgumentException("Number of available books must not be negative.");
		this.availableBooks = availableBooks;
	}

	@Override
	public int getAvailableBooks() {
		return this.availableBooks;
	}
	
	/**
	 * Set the number of unavailable books at this library.
	 * @param unavailableBooks the number of unavailable books.
	 */
	public void setUnavailableBooks(int unavailableBooks) {
		if (unavailableBooks < 0)
			throw new IllegalArgumentException("Number of unavailable books must not be negative.");
		this.unavailableBooks = unavailableBooks;
	}

	@Override
	public int getUnavailableBooks() {
		return this.unavailableBooks;
	}
}
