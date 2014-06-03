package edu.oit.cst236.lab2.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.oit.cst236.lab2.model.IBook;
import edu.oit.cst236.lab2.model.ILibrary;
import edu.oit.cst236.lab2.model.core.Book;
import edu.oit.cst236.lab2.model.core.Library;

public class FakeWebClient implements IWebClient {
	private static final String getLibrariesUrl = "http://www.fake.com/libraries";
	private static final String getLibraryUrl = "http://www.fake.com/library/";
	private static final String getLibraryBooksUrl = "http://www.fake.com/books/";
	private static final String getLibraryBookUrl = "http://www.fake.com/book/";
	private static final String checkinLibraryBookUrl = "http://www.fake.com/checkin/";
	private static final String checkoutLibraryBookUrl = "http://www.fake.com/checkout/";
	private static final Random random = new Random();
	
	private HashMap<String, Library> libraryDB = new HashMap<String, Library>();
	private HashMap<String, Book> bookDB = new HashMap<String, Book>();
	private HashMap<String, LibraryBooks> libraryBooksDB = new HashMap<String, LibraryBooks>();
	
	public FakeWebClient() throws ConnectionException {
		// Initialize libraries
		Library library1 = new Library("001A", "Hillsboro Library");
		libraryDB.put(library1.getId(), library1);
		
		Library library2 = new Library("XYZ4", "OIT Library");
		libraryDB.put(library2.getId(), library2);
		
		Library library3 = new Library("005C", "Portland Library");
		libraryDB.put(library3.getId(), library3);
		
		// Initialize books
		Book book1 = new Book("0001", "Peter Pan");
		book1.setDescription("Great book about a boy that never grows up");
		bookDB.put(book1.getId(), book1);
		
		Book book2 = new Book("0002", "The Time Machine");
		book2.setDescription("");
		bookDB.put(book2.getId(), book2);
		
		Book book3 = new Book("0010", "Alice's Adventures in Wonderland");
		book3.setDescription("An acid trip by a teenager");
		bookDB.put(book3.getId(), book3);
		
		Book book4 = new Book("0014", "Artificial Intelligence for Beginners");
		book4.setDescription("");
		bookDB.put(book4.getId(), book4);
		
		Book book5 = new Book("0210", "Neuro Web Design");
		book5.setDescription("How the brain responds to web design");
		bookDB.put(book5.getId(), book5);
		
		Book book6 = new Book("0150", "Tom Sawyer");
		book6.setDescription("");
		bookDB.put(book6.getId(), book6);
		
		// Initialize library books
		try {
			ArrayList<String> libraryBooksList = new ArrayList<String>();
			libraryBooksList.add(book1.getId());
			libraryBooksList.add(book1.getId());
			libraryBooksList.add(book1.getId());
			libraryBooksList.add(book1.getId());
			libraryBooksList.add(book2.getId());
			libraryBooksList.add(book2.getId());
			libraryBooksList.add(book4.getId());
			libraryBooksList.add(book5.getId());
			LibraryBooks libraryBooks1 = new LibraryBooks(libraryBooksList);
			libraryBooks1.checkoutBook(book1.getId());
			libraryBooks1.checkoutBook(book1.getId());
			libraryBooks1.checkoutBook(book4.getId());
			library1.setAvailableBooks(libraryBooks1.getAvailableBooks().size());
			library1.setUnavailableBooks(libraryBooks1.getUnavailableBooks().size());
			libraryBooksDB.put(library1.getId(), libraryBooks1);
			
			libraryBooksList = new ArrayList<String>();
			libraryBooksList.add(book1.getId());
			libraryBooksList.add(book1.getId());
			libraryBooksList.add(book2.getId());
			libraryBooksList.add(book2.getId());
			libraryBooksList.add(book2.getId());
			libraryBooksList.add(book4.getId());
			libraryBooksList.add(book4.getId());
			libraryBooksList.add(book3.getId());
			libraryBooksList.add(book5.getId());
			libraryBooksList.add(book5.getId());
			libraryBooksList.add(book5.getId());
			LibraryBooks libraryBooks2 = new LibraryBooks(libraryBooksList);
			libraryBooks2.checkoutBook(book2.getId());
			library2.setAvailableBooks(libraryBooks2.getAvailableBooks().size());
			library2.setUnavailableBooks(libraryBooks2.getUnavailableBooks().size());
			libraryBooksDB.put(library2.getId(), libraryBooks2);
			
			libraryBooksList = new ArrayList<String>();
			libraryBooksList.add(book1.getId());
			libraryBooksList.add(book1.getId());
			libraryBooksList.add(book2.getId());
			libraryBooksList.add(book2.getId());
			libraryBooksList.add(book3.getId());
			libraryBooksList.add(book3.getId());
			libraryBooksList.add(book3.getId());
			libraryBooksList.add(book4.getId());
			libraryBooksList.add(book4.getId());
			libraryBooksList.add(book5.getId());
			libraryBooksList.add(book5.getId());
			libraryBooksList.add(book5.getId());
			libraryBooksList.add(book5.getId());
			libraryBooksList.add(book6.getId());
			libraryBooksList.add(book6.getId());
			LibraryBooks libraryBooks3 = new LibraryBooks(libraryBooksList);
			libraryBooks3.checkoutBook(book1.getId());
			libraryBooks3.checkoutBook(book1.getId());
			libraryBooks3.checkoutBook(book2.getId());
			libraryBooks3.checkoutBook(book2.getId());
			libraryBooks3.checkoutBook(book4.getId());
			libraryBooks3.checkoutBook(book5.getId());
			libraryBooks3.checkoutBook(book5.getId());
			libraryBooks3.checkoutBook(book6.getId());
			libraryBooks3.checkoutBook(book6.getId());
			library3.setAvailableBooks(libraryBooks3.getAvailableBooks().size());
			library3.setUnavailableBooks(libraryBooks3.getUnavailableBooks().size());
			libraryBooksDB.put(library3.getId(), libraryBooks3);
		} catch (InvalidRequestException e) {
			throw new ConnectionException();
		}
		
		// Simulating connection time
		query();
	}
	
	@Override
	public String query(String url) throws InvalidRequestException, ConnectionException {
		query();
		
		if( url.equals(getLibrariesUrl) ) {
			return getLibraries();
		} else if( url.startsWith(getLibraryUrl) ) {
			return getLibrary(url.substring(url.lastIndexOf("/") + 1));
		} else if( url.startsWith(getLibraryBooksUrl) ) {
			return getLibraryBooks(url.substring(url.lastIndexOf("/") + 1));
		} else if( url.startsWith(getLibraryBookUrl) ) {
			return getLibraryBook(url.substring(url.lastIndexOf("/") + 1));
		} else if( url.startsWith(checkinLibraryBookUrl) ) {
			try {
				String bookId = url.substring(url.lastIndexOf("/") + 1);
				String newUrl = url.substring(0, url.lastIndexOf("/"));
				String libraryId = newUrl.substring(newUrl.lastIndexOf("/") + 1);
				return checkinLibraryBook(libraryId, bookId);
			} catch(Exception e ) {
				throw new InvalidRequestException();
			}
		} else if( url.startsWith(checkoutLibraryBookUrl) ) {
			try {
				String bookId = url.substring(url.lastIndexOf("/") + 1);
				String newUrl = url.substring(0, url.lastIndexOf("/"));
				String libraryId = newUrl.substring(newUrl.lastIndexOf("/") + 1);
				return checkoutLibraryBook(libraryId, bookId);
			} catch(Exception e ) {
				throw new InvalidRequestException();
			}
		}
		
		throw new InvalidRequestException();
	}
	
	private String getLibraries() {
		JSONArray jsonLibraries = new JSONArray();
		
		for(String keyId : libraryDB.keySet()) {
			ILibrary library = libraryDB.get(keyId);
			
			JSONObject jsonLibrary = new JSONObject();
			jsonLibrary.put("id", library.getId());
			jsonLibrary.put("name", library.getName());
			jsonLibrary.put("availableBooks", library.getAvailableBooks());
			jsonLibrary.put("unavailableBooks", library.getUnavailableBooks());
			
			jsonLibraries.put(jsonLibrary);
		}
		
		return jsonLibraries.toString();
	}
	
	private String getLibrary(String libraryId) {
		JSONObject jsonLibrary = new JSONObject();
		
		ILibrary library = libraryDB.get(libraryId);
		if( library != null ) {
			jsonLibrary.put("id", library.getId());
			jsonLibrary.put("name", library.getName());
			jsonLibrary.put("availableBooks", library.getAvailableBooks());
			jsonLibrary.put("unavailableBooks", library.getUnavailableBooks());
		}
		
		return jsonLibrary.toString();
	}
	
	private String getLibraryBooks(String libraryId) {
		JSONArray jsonLibraryBooks = new JSONArray();
		
		ILibrary library = libraryDB.get(libraryId);
		if( library != null ) {
			LibraryBooks libraryBooks = libraryBooksDB.get(library.getId());
			for(String bookId : libraryBooks.getAvailableBooks()) {
				IBook book = bookDB.get(bookId);
				
				JSONObject bookJson = new JSONObject();
				bookJson.put("id", book.getId());
				bookJson.put("title", book.getTitle());
				bookJson.put("description", book.getDescription());
				
				jsonLibraryBooks.put(bookJson);
			}
		}
		
		return jsonLibraryBooks.toString();
	}
	
	private String getLibraryBook(String bookId) {
		JSONObject bookJson = new JSONObject();
		
		IBook book = bookDB.get(bookId);
		if( book != null ) {
			bookJson.put("id", book.getId());
			bookJson.put("title", book.getTitle());
			bookJson.put("description", book.getDescription());
		}
		
		return bookJson.toString();
	}
	
	private String checkinLibraryBook(String libraryId, String bookId) throws InvalidRequestException {
		JSONObject checkinStatus = new JSONObject();
		checkinStatus.put("libraryId", libraryId);
		checkinStatus.put("bookId", bookId);
		
		try {
			LibraryBooks libraryBooks = libraryBooksDB.get(libraryId);
			if( libraryBooks != null ) {
				libraryBooks.checkinBook(bookId);
			}
			checkinStatus.put("status", "success");
		} catch(InvalidRequestException e) {
			checkinStatus.put("status", "failure");
		}
		
		return checkinStatus.toString();
	}
	
	private String checkoutLibraryBook(String libraryId, String bookId) throws InvalidRequestException {
		JSONObject checkoutStatus = new JSONObject();
		checkoutStatus.put("libraryId", libraryId);
		checkoutStatus.put("bookId", bookId);
		
		try {
			LibraryBooks libraryBooks = libraryBooksDB.get(libraryId);
			if( libraryBooks != null ) {
				libraryBooks.checkoutBook(bookId);
			}
			checkoutStatus.put("status", "success");
		} catch(InvalidRequestException e) {
			checkoutStatus.put("status", "failure");
		}
		
		return checkoutStatus.toString();
	}
	
	private void query() throws ConnectionException {
		try {
			Thread.sleep(1000 + random.nextInt(1000));
			
			if( random.nextInt(1000) < 50) {
				throw new ConnectionException();
			}
		} catch (InterruptedException e) {
			System.out.println("Network query interrupted");
		}
	}
	
	private class LibraryBooks {
		private List<String> availableBooks = new ArrayList<String>();
		private List<String> unavailableBooks = new ArrayList<String>();
		
		public LibraryBooks(List<String> availableBooks) {
			this.availableBooks = availableBooks;
		}
		public List<String> getAvailableBooks() {
			return this.availableBooks;
		}
		public List<String> getUnavailableBooks() {
			return this.unavailableBooks;
		}
		public void checkinBook(String bookId) throws InvalidRequestException {
			if( !unavailableBooks.contains(bookId) ) {
				throw new InvalidRequestException();
			}
			
			unavailableBooks.remove(bookId);
			availableBooks.add(bookId);
		}
		public void checkoutBook(String bookId) throws InvalidRequestException {
			if( !availableBooks.contains(bookId) ) {
				throw new InvalidRequestException();
			}
			
			unavailableBooks.add(bookId);
			availableBooks.remove(bookId);
		}
	}
}
