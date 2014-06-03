package edu.oit.cst236.lab2.parser;

import java.util.List;

import edu.oit.cst236.lab2.model.IBook;

/**
 * An interface for a BookParser. Book parsers handle creating Book objects
 * from different formats (i.e. json, xml)
 * 
 * @author nferraro
 *
 */
public interface IBookParser {
	/**
	 * Parse a list of books from the string parameter. The list of books returned
	 * will never be NULL.
	 * 
	 * @param bookString The string representation of the books.
	 * @return A list of books parsed from the string.
	 * @throws ParseException thrown when the string cannot be parsed into an array of complete Book objects.
	 */
	public List<IBook> parseBooks(String bookString) throws ParseException;
	
	/**
	 * Parse a book from the string parameter. The returned book will never be null.
	 * 
	 * @param bookString The string representation of the book.
	 * @return The parsed book.
	 * @throws ParseException thrown when the string cannot be parsed into a complete Book object.
	 */
	public IBook parseBook(String bookString) throws ParseException;
}