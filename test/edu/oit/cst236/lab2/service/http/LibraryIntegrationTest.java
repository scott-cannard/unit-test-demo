package edu.oit.cst236.lab2.service.http;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
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
import edu.oit.cst236.lab2.service.http.LibraryHttpService;

public class LibraryIntegrationTest {
	private LibraryHttpService sut;
	private final FakeWebClient mockWebClient = Mockito.mock(FakeWebClient.class);
	
	private static final List<ILibrary> LIBRARIES = new ArrayList<ILibrary>();
	private static final ILibrary LIBRARY = new Library("1234", "Tigard Library");
	private static final List<IBook> BOOKS = new ArrayList<IBook>();
	private static final Book BOOK = new Book("0210", "Neuro Web Design");
	
	
	
	@BeforeClass
	public static void init() {
		Library lib = new Library("001A", "Hillsboro Library");
		lib.setAvailableBooks(1);
		lib.setUnavailableBooks(2);
		LIBRARIES.add(lib);
		
		lib = new Library("XYZ4", "OIT Library");
		lib.setAvailableBooks(3);
		lib.setUnavailableBooks(4);
		LIBRARIES.add(lib);
		
		lib = new Library("005C", "Portland Library");
		lib.setAvailableBooks(5);
		lib.setUnavailableBooks(6);
		LIBRARIES.add(lib);
		
		((Library)LIBRARY).setAvailableBooks(7);
		((Library)LIBRARY).setAvailableBooks(8);
		
		Book bk = new Book("0001", "Peter Pan");
		bk.setDescription("Great book about a boy that never grows up");
		BOOKS.add(bk);
		
		bk = new Book("0002", "The Time Machine");
		BOOKS.add(bk);
		
		bk = new Book("0010", "Alice's Adventures in Wonderland");
		bk.setDescription("An acid trip by a teenager");
		BOOKS.add(bk);
		
		bk = new Book("0014", "Artificial Intelligence for Beginners");
		BOOKS.add(bk);
		
		((Book)BOOK).setDescription("How the brain responds to web design");
	}
	
	@Before
	public void setup() {
		try {
			sut = new LibraryHttpService(mockWebClient);
		} catch (ConnectionException ex) {
			System.err.println("Mockito.mock(Class) returned null!");
		}
	}
	
	@Test
	public void testGetLibraries() throws InvalidRequestException, ConnectionException {
		Mockito.when(mockWebClient.query("http://www.fake.com/libraries")).thenReturn(getJSONLibraries());
		List<ILibrary> libraries = sut.getLibraries();
		Assert.assertEquals(libraries.size(), LIBRARIES.size());
		for (int i = 0; i < libraries.size(); i++) {
			Assert.assertEquals(libraries.get(i).getId(), LIBRARIES.get(i).getId());
			Assert.assertEquals(libraries.get(i).getName(), LIBRARIES.get(i).getName());
			Assert.assertEquals(libraries.get(i).getAvailableBooks(), LIBRARIES.get(i).getAvailableBooks());
			Assert.assertEquals(libraries.get(i).getUnavailableBooks(), LIBRARIES.get(i).getUnavailableBooks());
		}
	}
	
	@Test
	public void testGetLibraries_invalidQueryResponses() throws InvalidRequestException, ConnectionException {
		Mockito.when(mockWebClient.query("http://www.fake.com/libraries")).thenReturn(null);
		List<ILibrary> libraries = sut.getLibraries();
		Assert.assertNotNull(libraries);
		Assert.assertEquals(libraries.size(), 0);
		
		Mockito.when(mockWebClient.query("http://www.fake.com/libraries")).thenReturn("bad_JSON");
		libraries = sut.getLibraries();
		Assert.assertNotNull(libraries);
		Assert.assertEquals(libraries.size(), 0);
	}
	
	@Test
	public void testGetLibrary() throws InvalidRequestException, ConnectionException {
		Mockito.when(mockWebClient.query(Mockito.startsWith("http://www.fake.com/library/"))).thenReturn(getJSONLibrary());
		ILibrary library = sut.getLibrary("someLibraryId");
		Assert.assertEquals(library.getId(), LIBRARY.getId());
		Assert.assertEquals(library.getName(), LIBRARY.getName());
		Assert.assertEquals(library.getAvailableBooks(), LIBRARY.getAvailableBooks());
		Assert.assertEquals(library.getUnavailableBooks(), LIBRARY.getUnavailableBooks());
	}
	
	@Test
	public void testGetLibrary_invalidQueryResponses() throws InvalidRequestException, ConnectionException {
		Mockito.when(mockWebClient.query(Mockito.startsWith("http://www.fake.com/library/"))).thenReturn(null);
		Assert.assertNull(sut.getLibrary("someLibrary"));
		
		Mockito.when(mockWebClient.query(Mockito.startsWith("http://www.fake.com/library/"))).thenReturn("bad_JSON");
		Assert.assertNull(sut.getLibrary("someLibrary"));
	}
	
	@Test
	public void testGetBooks() throws InvalidRequestException, ConnectionException {
		Mockito.when(mockWebClient.query(Mockito.startsWith("http://www.fake.com/books/"))).thenReturn(getJSONBooks());
		List<IBook> books = sut.getLibraryBooks("someLibraryId");
		Assert.assertEquals(books.size(), BOOKS.size());
		for (int i = 0; i < books.size(); i++) {
			Assert.assertEquals(books.get(i).getId(), BOOKS.get(i).getId());
			Assert.assertEquals(books.get(i).getTitle(), BOOKS.get(i).getTitle());
			Assert.assertEquals(books.get(i).getDescription(), BOOKS.get(i).getDescription());
		}
	}
	
	@Test
	public void testGetBooks_invalidQueryResponse() throws InvalidRequestException, ConnectionException {
		Mockito.when(mockWebClient.query(Mockito.startsWith("http://www.fake.com/books/"))).thenReturn(null);
		List<IBook> books = sut.getLibraryBooks("someLibrary");
		Assert.assertNotNull(books);
		Assert.assertEquals(books.size(), 0);
		
		Mockito.when(mockWebClient.query(Mockito.startsWith("http://www.fake.com/books/"))).thenReturn("bad_JSON");
		books = sut.getLibraryBooks("someLibrary");
		Assert.assertNotNull(books);
		Assert.assertEquals(books.size(), 0);
	}
	
	@Test
	public void testGetBook() throws InvalidRequestException, ConnectionException {
		Mockito.when(mockWebClient.query(Mockito.startsWith("http://www.fake.com/book/"))).thenReturn(getJSONBook());
		IBook book = sut.getLibraryBook("someBookId");
		Assert.assertEquals(book.getId(), BOOK.getId());
		Assert.assertEquals(book.getTitle(), BOOK.getTitle());
		Assert.assertEquals(book.getDescription(), BOOK.getDescription());
	}
	
	@Test
	public void testGetBook_invalidQueryResponse() throws InvalidRequestException, ConnectionException {
		Mockito.when(mockWebClient.query(Mockito.startsWith("http://www.fake.com/book/"))).thenReturn(null);
		Assert.assertNull(sut.getLibraryBook("someBook"));
		
		Mockito.when(mockWebClient.query(Mockito.startsWith("http://www.fake.com/book/"))).thenReturn("bad_JSON");
		Assert.assertNull(sut.getLibraryBook("someBook"));
	}
	
	
	
	
	
	private String getJSONLibraries() {
		JSONArray jArr = new JSONArray();
		for (ILibrary lib : LIBRARIES) {
			JSONObject jObj = new JSONObject().put("id", lib.getId());
			jObj.put("name", lib.getName());
			jObj.put("availableBooks", lib.getAvailableBooks());
			jObj.put("unavailableBooks", lib.getUnavailableBooks());
			jArr.put(jObj);
		}
		return jArr.toString();
	}
	
	private String getJSONLibrary() {
		JSONObject jObj = new JSONObject().put("id", LIBRARY.getId());
		jObj.put("name", LIBRARY.getName());
		jObj.put("availableBooks", LIBRARY.getAvailableBooks());
		jObj.put("unavailableBooks", LIBRARY.getUnavailableBooks());
		return jObj.toString();
	}
	
	private String getJSONBooks() {
		JSONArray jArr = new JSONArray();
		for (IBook bk : BOOKS) {
			JSONObject jObj = new JSONObject().put("id", bk.getId());
			jObj.put("title", bk.getTitle());
			if (!bk.getDescription().equals(""))
				jObj.put("description", ((Book)bk).getDescription());
			jArr.put(jObj);
		}
		return jArr.toString();
	}
	
	private String getJSONBook() {
		JSONObject jObj = new JSONObject().put("id", BOOK.getId());
		jObj.put("title", BOOK.getTitle());
		jObj.put("description", BOOK.getDescription());
		return jObj.toString();
	}
}
