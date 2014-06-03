package edu.oit.cst236.lab2.parser.json;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.oit.cst236.lab2.model.IBook;
import edu.oit.cst236.lab2.parser.ParseException;
import edu.oit.cst236.lab2.parser.json.BookJsonParser;

public class BookJsonParserTest {
	private BookJsonParser sut;
	private JSONArray jsArray;
	private JSONObject jsObject;
	
	private final String[] ids = { "101", "202" };
	private final String[] titles = { "The Hitchhiker's Guide to the Galaxy", "The Princess Bride" };
	private final String[] descriptions = { "Mostly harmless", "As you wish" };
	
	
	
	@Before
	public void setup() {
		sut = new BookJsonParser();
		jsArray = new JSONArray();
		jsObject = new JSONObject();
	}
	
	
	
	// Test parseBooks(JSON_style_string)
	
	@Test
	public void testParseBooks() throws ParseException {
		jsArray.put(validJsonBook(0, true));
		jsArray.put(validJsonBook(1, false));
		List<IBook> bookList = sut.parseBooks(jsArray.toString());
		
		Assert.assertNotNull(bookList);
		
		Assert.assertEquals(bookList.get(0).getId(), ids[0]);
		Assert.assertEquals(bookList.get(0).getTitle(), titles[0]);
		Assert.assertEquals(bookList.get(0).getDescription(), descriptions[0]);
		
		Assert.assertEquals(bookList.get(1).getId(), ids[1]);
		Assert.assertEquals(bookList.get(1).getTitle(), titles[1]);
		Assert.assertEquals(bookList.get(1).getDescription(), "");
	}
	
	
	@Test (expected = ParseException.class)
	public void testParseBooks_invalidStringFormat() throws ParseException {
		sut.parseBooks("{ error");
	}
	
	
	@Test (expected = ParseException.class)
	public void testParseBooks_missingId() throws ParseException {
		jsObject.put("title", titles[0]);
		jsObject.put("description", descriptions[0]);
		jsArray.put(jsObject);
		
		sut.parseBooks(jsArray.toString());
	}
	
	
	@Test (expected = ParseException.class)
	public void testParseBooks_missingTitle() throws ParseException {
		jsObject.put("id", ids[0]);
		jsObject.put("description", descriptions[0]);
		jsArray.put(jsObject);
		
		sut.parseBooks(jsArray.toString());
	}
	
	
	@Test (expected = ParseException.class)
	public void testParseBooks_catchBookException() throws ParseException {
		jsObject.put("id", "");
		jsObject.put("title", titles[0]);
		jsArray.put(jsObject);
		
		sut.parseBooks(jsArray.toString());
	}
	
	
	@Test (expected = ParseException.class)
	public void testParseBooks_nullArgument() throws ParseException {
		sut.parseBooks(null);
	}
	
	
	
	// Test parseBook(JSON_style_string)
	
	@Test
	public void testParseBook() throws ParseException {
		IBook book = sut.parseBook(validJsonBook(0, true).toString());
		
		Assert.assertNotNull(book);
		
		Assert.assertEquals(book.getId(), ids[0]);
		Assert.assertEquals(book.getTitle(), titles[0]);
		Assert.assertEquals(book.getDescription(), descriptions[0]);
		
		book = sut.parseBook(validJsonBook(1, false).toString());
		
		Assert.assertNotNull(book);
		
		Assert.assertEquals(book.getId(), ids[1]);
		Assert.assertEquals(book.getTitle(), titles[1]);
		Assert.assertEquals(book.getDescription(), "");
	}
	
	
	@Test (expected = ParseException.class)
	public void testParseBook_invalidStringFormat() throws ParseException {
		sut.parseBook("{ error");
	}
	
	
	@Test (expected = ParseException.class)
	public void testParseBook_missingId() throws ParseException {
		jsObject.put("title", titles[0]);
		jsObject.put("description", descriptions[0]);
		
		sut.parseBook(jsObject.toString());
	}
	
	
	@Test (expected = ParseException.class)
	public void testParseBook_missingTitle() throws ParseException {
		jsObject.put("id", ids[0]);
		jsObject.put("description", descriptions[0]);
		
		sut.parseBook(jsObject.toString());
	}
	
	
	@Test (expected = ParseException.class)
	public void testParseBook_catchBookException() throws ParseException {
		jsObject.put("id", ids[0]);
		jsObject.put("title", "");
		
		sut.parseBook(jsObject.toString());
	}
	
	
	@Test (expected = ParseException.class)
	public void testParseBook_nullArgument() throws ParseException {
		sut.parseBook(null);
	}
	
	
	
	
	
	/**
	 *  Creates a JSONObject containing the data necessary for defining an IBook object  
	 * @param index Used to select id/title/description from string arrays
	 * @param hasDescription Determines whether a "description" key/value is added to the object
	 * @return a JSONObject containing "id", "title", and optional "description" key/value pairs
	 */
	private JSONObject validJsonBook(int index, boolean hasDescription) {
		JSONObject jsonObj = new JSONObject().put("id", ids[index]);
		jsonObj.put("title", titles[index]);
		if (hasDescription)
			jsonObj.put("description", descriptions[index]);
		
		return jsonObj;
	}	
}
