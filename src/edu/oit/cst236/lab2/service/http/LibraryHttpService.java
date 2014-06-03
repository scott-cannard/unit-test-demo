package edu.oit.cst236.lab2.service.http;

import java.util.ArrayList;
import java.util.List;

import edu.oit.cst236.lab2.lib.ConnectionException;
import edu.oit.cst236.lab2.lib.IWebClient;
import edu.oit.cst236.lab2.lib.InvalidRequestException;
import edu.oit.cst236.lab2.model.IBook;
import edu.oit.cst236.lab2.model.ILibrary;
import edu.oit.cst236.lab2.parser.IBookParser;
import edu.oit.cst236.lab2.parser.ILibraryParser;
import edu.oit.cst236.lab2.parser.ParseException;
import edu.oit.cst236.lab2.parser.json.BookJsonParser;
import edu.oit.cst236.lab2.parser.json.LibraryJsonParser;
import edu.oit.cst236.lab2.service.ILibraryService;

/**
 * An http service that handles communicating with an http data source.
 * 
 * @author nferraro
 *
 */
public class LibraryHttpService implements ILibraryService {
	private ILibraryParser libraryParser = new LibraryJsonParser();
	private IBookParser bookParser = new BookJsonParser();
	private IWebClient fakeWebClient;
	
	/**
	 * Create an instance of the LibraryHttpService
	 * 
	 * @throws ConnectionException thrown when a connection cannot be established
	 */
	public LibraryHttpService(IWebClient webClient) throws ConnectionException {
		setWebClient(webClient);
	}
	
	/**
	 * Set the library parser
	 * 
	 * @param libraryParser should not be NULL
	 */
	public void setLibraryParser(ILibraryParser libraryParser) {
		if (libraryParser != null)
			this.libraryParser = libraryParser;
	}
	
	/**
	 * Set the book parser
	 * 
	 * @param bookParser should not be NULL
	 */
	public void setBookParser(IBookParser bookParser) {
		if (bookParser != null)
			this.bookParser = bookParser;
	}
	
	/**
	 * Set the web client object
	 * 
	 * @param webClient should not be NULL
	 * @throws ConnectionException 
	 */
	public void setWebClient(IWebClient webClient) throws ConnectionException {
		if (webClient == null)
			throw new ConnectionException();
		
		this.fakeWebClient = webClient;
	}
	
	@Override
	public List<ILibrary> getLibraries() {
		List<ILibrary> libraries = new ArrayList<ILibrary>();
		
		try {
			String librariesJsonString = fakeWebClient.query("http://www.fake.com/libraries");
			List<ILibrary> parsedList = (libraryParser.parseLibraries(librariesJsonString));
			if (parsedList != null)
				libraries.addAll(parsedList);
		} catch(InvalidRequestException e) {
			System.err.println("Bad request");
		} catch(ConnectionException e) {
			System.err.println("Could not connect to server");
		} catch (ParseException e) {
			System.err.println("Error parsing libraries");
		}
		
		return libraries;
	}

	@Override
	public ILibrary getLibrary(String libraryId) {
		ILibrary library = null;
		
		try {
			String libraryJsonString = fakeWebClient.query("http://www.fake.com/library/" + libraryId);
			library = libraryParser.parseLibrary(libraryJsonString);
		} catch(InvalidRequestException e) {
			System.err.println("Bad request");
		} catch(ConnectionException e) {
			System.err.println("Could not connect to server");
		} catch (ParseException e) {
			System.err.println("Error parsing library");
		}

		return library;
	}

	@Override
	public List<IBook> getLibraryBooks(String libraryId) {
		List<IBook> books = new ArrayList<IBook>();
		
		try {
			String libraryBooksJsonString = fakeWebClient.query("http://www.fake.com/books/" + libraryId);
			List<IBook> parsedList = bookParser.parseBooks(libraryBooksJsonString);
			if (parsedList != null)
				books.addAll(parsedList);
		} catch(InvalidRequestException e) {
			System.err.println("Bad request");
		} catch(ConnectionException e) {
			System.err.println("Could not connect to server");
		} catch (ParseException e) {
			System.err.println("Error parsing library books");
		}
		
		return books;
	}

	@Override
	public IBook getLibraryBook(String bookId) {
		IBook book = null;
		
		try {
			String libraryBookJsonString = fakeWebClient.query("http://www.fake.com/book/" + bookId);
			book = bookParser.parseBook(libraryBookJsonString);
		} catch(InvalidRequestException e) {
			System.err.println("Bad request");
		} catch(ConnectionException e) {
			System.err.println("Could not connect to server");
		} catch (ParseException e) {
			System.err.println("Error parsing book");
		}
		
		return book;
	}
}
