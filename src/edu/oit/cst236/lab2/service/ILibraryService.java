package edu.oit.cst236.lab2.service;

import java.util.List;

import edu.oit.cst236.lab2.model.IBook;
import edu.oit.cst236.lab2.model.ILibrary;

/**
 * The interface for a Library service. This service handles getting libraries,
 * library books, and checkin/checkout of books.
 * 
 * @author nferraro
 *
 */
public interface ILibraryService {
	/**
	 * Get a list of libraries from the datasource. The list should never be NULL.
	 * @return A list of retrieved libraries.
	 */
	public List<ILibrary> getLibraries();
	
	/**
	 * Get a library from the datasource based on the id.
	 * @param libraryId The id of the library to retrieve
	 * @return The library object if found, otherwise NULL
	 */
	public ILibrary getLibrary(String libraryId);
	
	/**
	 * Get a list of books from a library from the datasource. The list should never be NULL.
	 * @param libraryId The id of the library to retrieve books from
	 * @return A list of retrieved books
	 */
	public List<IBook> getLibraryBooks(String libraryId);
	
	/**
	 * Get a book from the datasource based on the id.
	 * @param bookId The id of the book to retrieve
	 * @return The book object if found, otherwise NULL
	 */
	public IBook getLibraryBook(String bookId);
}