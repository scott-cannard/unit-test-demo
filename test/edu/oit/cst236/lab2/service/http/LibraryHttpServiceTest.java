package edu.oit.cst236.lab2.service.http;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import edu.oit.cst236.lab2.lib.ConnectionException;
import edu.oit.cst236.lab2.lib.FakeWebClient;
import edu.oit.cst236.lab2.lib.InvalidRequestException;
import edu.oit.cst236.lab2.model.IBook;
import edu.oit.cst236.lab2.model.ILibrary;
import edu.oit.cst236.lab2.model.core.Book;
import edu.oit.cst236.lab2.model.core.Library;
import edu.oit.cst236.lab2.parser.IBookParser;
import edu.oit.cst236.lab2.parser.ILibraryParser;
import edu.oit.cst236.lab2.parser.ParseException;
import edu.oit.cst236.lab2.parser.json.BookJsonParser;
import edu.oit.cst236.lab2.parser.json.LibraryJsonParser;
import edu.oit.cst236.lab2.service.http.LibraryHttpService;

public class LibraryHttpServiceTest {
	private LibraryHttpService sut;
	
	private final ILibraryParser mockLibraryParser = Mockito.mock(LibraryJsonParser.class);
	private final IBookParser mockBookParser = Mockito.mock(BookJsonParser.class);
	private final FakeWebClient mockWebClient = Mockito.mock(FakeWebClient.class);
	
	private static final List<ILibrary> VALID_LIBRARIES = new ArrayList<ILibrary>();
	private static final Library VALID_LIBRARY =  new Library("libraryID", "libraryName");
	private static final List<IBook> VALID_BOOKS = new ArrayList<IBook>();
	private static final Book VALID_BOOK = new Book("bookID", "bookTitle"); 
	
	
	
	@BeforeClass
	public static void init() {
		VALID_LIBRARY.setAvailableBooks(1);
		VALID_LIBRARY.setUnavailableBooks(1);
		VALID_BOOK.setDescription("bookDescription");
	}
	
	

	@Before
	public void setup() throws ConnectionException {
		sut = new LibraryHttpService(mockWebClient);
		sut.setBookParser(mockBookParser);
		sut.setLibraryParser(mockLibraryParser);
	}

	
	
	// Test accessors	
	
	@Test
	public void testSetLibraryParser() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field parser = reflectSUT("libraryParser");
		Assert.assertEquals(parser.get(sut), mockLibraryParser);
	}
	
	
	@Test
	public void testSetLibraryParser_nullArgument() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field parser = reflectSUT("libraryParser");
		Assert.assertNotNull(parser.get(sut));
		sut.setLibraryParser(null);
		Assert.assertNotNull(parser.get(sut));
	}
	
	
	@Test
	public void testSetBookParser() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field parser = reflectSUT("bookParser");
		Assert.assertEquals(parser.get(sut), mockBookParser);
	}
	
	
	@Test
	public void testSetBookParser_nullArgument() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field parser = reflectSUT("bookParser");
		Assert.assertNotNull(parser.get(sut));
		sut.setBookParser(null);
		Assert.assertNotNull(parser.get(sut));
	}
	
	
	@Test
	public void testSetWebClient_nullArgument() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, ConnectionException {
		Field client = reflectSUT("fakeWebClient");
		Assert.assertNotNull(client.get(sut));
		sut.setWebClient(null);
		Assert.assertNotNull(client.get(sut));
	}
	
	
	
	// Test getLibraries()
	
	@Test
	public void testGetLibraries() throws InvalidRequestException, ConnectionException, ParseException {
		Mockito.when(mockWebClient.query("http://www.fake.com/libraries")).thenReturn("validJSON");
		Mockito.when(mockLibraryParser.parseLibraries("validJSON")).thenReturn(VALID_LIBRARIES);
		Assert.assertEquals(sut.getLibraries(), VALID_LIBRARIES);
	}
	
	
	@Test
	public void testGetLibraries_clientReturnsBadData() throws InvalidRequestException, ConnectionException, ParseException {
		Mockito.when(mockWebClient.query("http://www.fake.com/libraries")).thenReturn("badJSONorNull");
		Mockito.when(mockLibraryParser.parseLibraries("badJSONorNull")).thenThrow(new ParseException());
		Assert.assertNotNull(sut.getLibraries());
	}
	
	
	@Test
	public void testGetLibraries_clientThrowsExceptions() throws InvalidRequestException, ConnectionException {		
		Mockito.when(mockWebClient.query("http://www.fake.com/libraries")).thenThrow(new InvalidRequestException()).thenThrow(new ConnectionException());
		Assert.assertNotNull(sut.getLibraries());  //InvalidRequestException
		Assert.assertNotNull(sut.getLibraries());  //ConnectionException
	}
	
	
	@Test
	public void testGetLibraries_parserReturnsNull() throws InvalidRequestException, ConnectionException, ParseException {
		Mockito.when(mockWebClient.query("http://www.fake.com/libraries")).thenReturn("validJSON");
		Mockito.when(mockLibraryParser.parseLibraries("validJSON")).thenReturn(null);
		Assert.assertNotNull(sut.getLibraries());
	}
	
	
	
	// Test getLibrary(id)
	
	@Test
	public void testGetLibrary() throws InvalidRequestException, ConnectionException, ParseException {
		Mockito.when(mockWebClient.query(Mockito.startsWith("http://www.fake.com/library/"))).thenReturn("validJSON");
		Mockito.when(mockLibraryParser.parseLibrary("validJSON")).thenReturn(VALID_LIBRARY);
		Assert.assertEquals(sut.getLibrary("libraryID"), VALID_LIBRARY);
	}
	
	
	@Test
	public void testGetLibrary_clientReturnsBadData() throws InvalidRequestException, ConnectionException, ParseException {
		Mockito.when(mockWebClient.query(Mockito.startsWith("http://www.fake.com/library/"))).thenReturn("badJSONorNull");
		Mockito.when(mockLibraryParser.parseLibrary("badJSONorNull")).thenThrow(new ParseException());
		Assert.assertNull(sut.getLibrary("libraryID"));
	}
	
	
	@Test
	public void testGetLibrary_clientThrowsExceptions() throws InvalidRequestException, ConnectionException {
		Mockito.when(mockWebClient.query(Mockito.startsWith("http://www.fake.com/library/"))).thenThrow(new InvalidRequestException()).thenThrow(new ConnectionException());
		Assert.assertNull(sut.getLibrary("libraryID"));		//InvalidRequestException
		Assert.assertNull(sut.getLibrary("libraryID"));		//ConnectionException
	}
	
	
	@Test
	public void testGetLibrary_parserReturnsNull() throws InvalidRequestException, ConnectionException, ParseException {
		Mockito.when(mockWebClient.query(Mockito.startsWith("http://www.fake.com/library/"))).thenReturn("validJSON");
		Mockito.when(mockLibraryParser.parseLibrary("validJSON")).thenReturn(null);
		Assert.assertNull(sut.getLibrary("libraryID"));
	}
	
	
	
	// Test getLibraryBooks(libraryID)
	
	@Test
	public void testGetLibraryBooks() throws InvalidRequestException, ConnectionException, ParseException {
		Mockito.when(mockWebClient.query(Mockito.startsWith("http://www.fake.com/books"))).thenReturn("validJSON");
		Mockito.when(mockBookParser.parseBooks("validJSON")).thenReturn(VALID_BOOKS);
		Assert.assertEquals(sut.getLibraryBooks("libraryID"), VALID_BOOKS);
	}
	
	
	@Test
	public void testGetLibraryBooks_clientReturnsBadData() throws InvalidRequestException, ConnectionException, ParseException {
		Mockito.when(mockWebClient.query(Mockito.startsWith("http://www.fake.com/books"))).thenReturn("badJSONorNull");
		Mockito.when(mockBookParser.parseBooks("badJSONorNull")).thenThrow(new ParseException());
		Assert.assertNotNull(sut.getLibraryBooks("libraryID"));
	}
	
	
	@Test
	public void testGetLibraryBooks_clientThrowsExceptions() throws InvalidRequestException, ConnectionException {
		Mockito.when(mockWebClient.query(Mockito.startsWith("http://www.fake.com/books"))).thenThrow(new InvalidRequestException()).thenThrow(new ConnectionException());
		Assert.assertNotNull(sut.getLibraryBooks("libraryID"));		//InvalidRequestException
		Assert.assertNotNull(sut.getLibraryBooks("libraryID"));		//ConnectionException
	}
	
	
	@Test
	public void testGetLibraryBooks_parserReturnsNull() throws InvalidRequestException, ConnectionException, ParseException {
		Mockito.when(mockWebClient.query(Mockito.startsWith("http://www.fake.com/books"))).thenReturn("validJSON");
		Mockito.when(mockBookParser.parseBooks("validJSON")).thenReturn(null);
		Assert.assertNotNull(sut.getLibraryBooks("libraryID"));
	}
	
	
	
	// Test getLibraryBook(id)
	
	@Test
	public void testGetLibraryBook() throws InvalidRequestException, ConnectionException, ParseException {
		Mockito.when(mockWebClient.query(Mockito.startsWith("http://www.fake.com/book/"))).thenReturn("validJSON");
		Mockito.when(mockBookParser.parseBook("validJSON")).thenReturn(VALID_BOOK);
		Assert.assertEquals(sut.getLibraryBook("bookID"), VALID_BOOK);
	}
	
	
	@Test
	public void testGetLibraryBook_clientReturnsBadData() throws InvalidRequestException, ConnectionException, ParseException {
		Mockito.when(mockWebClient.query(Mockito.startsWith("http://www.fake.com/book/"))).thenReturn("badJSONorNull");
		Mockito.when(mockBookParser.parseBook("badJSONorNull")).thenThrow(new ParseException());
		Assert.assertNull(sut.getLibraryBook("bookID"));
	}
	
	
	@Test
	public void testGetLibraryBook_clientThrowsExceptions() throws InvalidRequestException, ConnectionException {
		Mockito.when(mockWebClient.query(Mockito.startsWith("http://www.fake.com/book/"))).thenThrow(new InvalidRequestException()).thenThrow(new ConnectionException());
		Assert.assertNull(sut.getLibraryBook("bookId"));		//InvalidRequestException
		Assert.assertNull(sut.getLibraryBook("bookId"));		//ConnectionException
	}
	
	
	@Test
	public void testGetLibraryBook_parserReturnsNull() throws InvalidRequestException, ConnectionException, ParseException {
		Mockito.when(mockWebClient.query(Mockito.startsWith("http://www.fake.com/book/"))).thenReturn("validJSON");
		Mockito.when(mockBookParser.parseBook("validJSON")).thenReturn(null);
		Assert.assertNull(sut.getLibraryBook("bookID"));
	}
	
	
	// Tests added or modified for 100% coverage
	
	@Test
	public void testConstructor() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field client = reflectSUT("fakeWebClient");
		Assert.assertEquals(client.get(sut), mockWebClient);
	}
	
	@Test (expected = ConnectionException.class)
	public void testConstructor_nullArgument() throws ConnectionException {
		sut = new LibraryHttpService(null);
	}
	
	@Test
	public void testSetWebClient() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, ConnectionException {
		Field client = reflectSUT("fakeWebClient");
		Assert.assertNotNull(client.get(sut));
		
		FakeWebClient mockClient2 = Mockito.mock(FakeWebClient.class);
		sut.setWebClient(mockClient2);
		Assert.assertEquals(client.get(sut), mockClient2);
	}
	
	
	
	
	/**
	 * Exposes a field of the sut instance via reflection
	 * @param fieldName Name of the field to be reflected
	 * @return Identifier for one of the sut instance's fields (regardless of accessibility level)
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	private Field reflectSUT(String fieldName) throws NoSuchFieldException, SecurityException {
		Field reflection = sut.getClass().getDeclaredField(fieldName);
		reflection.setAccessible(true);
		return reflection;
	}
}