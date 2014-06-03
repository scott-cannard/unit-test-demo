package edu.oit.cst236.lab2.parser.json;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.oit.cst236.lab2.model.ILibrary;
import edu.oit.cst236.lab2.parser.ParseException;
import edu.oit.cst236.lab2.parser.json.LibraryJsonParser;

public class LibraryJsonParserTest {
	private LibraryJsonParser sut;
	private JSONArray jsArray;
	private JSONObject jsObject;
	
	private final String[] ids = { "a1", "b2" };
	private final String[] names = { "Portland Public", "Washington County" };
	private final int[] nAvailable = { 16, 27 };
	private final int[] nUnavailable = { 9, 11 };
	
	
	
	@Before
	public void setup() {
		sut = new LibraryJsonParser();
		jsArray = new JSONArray();
		jsObject = new JSONObject();
	}
	
	
	
	// Test parseLibrary(JSON_style_string)
	@Test
	public void testParseLibrary() throws ParseException {
		ILibrary library = sut.parseLibrary(validJsonLibrary(0).toString());
		
		Assert.assertNotNull(library);
		Assert.assertEquals(library.getId(), ids[0]);
		Assert.assertEquals(library.getName(), names[0]);
		Assert.assertEquals(library.getAvailableBooks(), nAvailable[0]);
		Assert.assertEquals(library.getUnavailableBooks(), nUnavailable[0]);
	}
	
	
	@Test (expected = ParseException.class)
	public void testParseLibrary_invalidStringFormat() throws ParseException {
		sut.parseLibrary("{ error");
	}
	
	
	@Test (expected = ParseException.class)
	public void testParseLibrary_missingId() throws ParseException {
		jsObject.put("name", names[0]);
		jsObject.put("availableBooks", nAvailable[0]);
		jsObject.put("unavailableBooks", nUnavailable[0]);
		
		sut.parseLibrary(jsObject.toString());
	}
	
	
	@Test (expected = ParseException.class)
	public void testParseLibrary_missingName() throws ParseException {
		jsObject.put("id", ids[0]);
		jsObject.put("availableBooks", nAvailable[0]);
		jsObject.put("unavailableBooks", nUnavailable[0]);
		
		sut.parseLibrary(jsObject.toString());
	}
	
	
	@Test (expected = ParseException.class)
	public void testParseLibrary_missingAvailableBooks() throws ParseException {
		jsObject.put("id", ids[0]);
		jsObject.put("name", names[0]);
		jsObject.put("unavailableBooks", nUnavailable[0]);
		
		sut.parseLibrary(jsObject.toString());
	}
	
	
	@Test (expected = ParseException.class)
	public void testParseLibrary_missingUnavailableBooks() throws ParseException {
		jsObject.put("id", ids[0]);
		jsObject.put("name", names[0]);
		jsObject.put("availableBooks", nAvailable[0]);
		
		sut.parseLibrary(jsObject.toString());
	}
	
	
	@Test (expected = ParseException.class)
	public void testParseLibrary_libraryThrowsException() throws ParseException {
		jsObject.put("id", "");
		jsObject.put("name", "");
		jsObject.put("availableBooks", -1);
		jsObject.put("unavailableBooks", -1);
		
		sut.parseLibrary(jsObject.toString());
	}
	
	
	@Test (expected = ParseException.class)
	public void testParseLibrary_nullArgument() throws ParseException {
		sut.parseLibrary(null);
	}
	
	
	
	// Test parseLibraries(JSON_style_string)
	
	@Test
	public void testParseLibraries() throws ParseException {
		jsArray.put(validJsonLibrary(0));
		jsArray.put(validJsonLibrary(1));
		List<ILibrary> libraries = sut.parseLibraries(jsArray.toString());
		
		Assert.assertNotNull(libraries);
		
		Assert.assertEquals(libraries.get(0).getId(), ids[0]);
		Assert.assertEquals(libraries.get(0).getName(), names[0]);
		Assert.assertEquals(libraries.get(0).getAvailableBooks(), nAvailable[0]);
		Assert.assertEquals(libraries.get(0).getUnavailableBooks(), nUnavailable[0]);
		
		Assert.assertEquals(libraries.get(1).getId(), ids[1]);
		Assert.assertEquals(libraries.get(1).getName(), names[1]);
		Assert.assertEquals(libraries.get(1).getAvailableBooks(), nAvailable[1]);
		Assert.assertEquals(libraries.get(1).getUnavailableBooks(), nUnavailable[1]);
	}
	
	
	@Test (expected = ParseException.class)
	public void testParseLibraries_invalidStringFormat() throws ParseException {
		sut.parseLibraries("{ error");
	}
	
	
	@Test (expected = ParseException.class)
	public void testParseLibraries_missingId() throws ParseException {
		jsObject.put("name", names[1]);
		jsObject.put("availableBooks", nAvailable[1]);
		jsObject.put("unavailableBooks", nUnavailable[1]);
		jsArray.put(jsObject);
		
		sut.parseLibraries(jsArray.toString());
	}
	
	
	@Test (expected = ParseException.class)
	public void testParseLibraries_missingName() throws ParseException {
		jsObject.put("id", ids[1]);
		jsObject.put("availableBooks", nAvailable[1]);
		jsObject.put("unavailableBooks", nUnavailable[1]);
		jsArray.put(jsObject);
		
		sut.parseLibraries(jsArray.toString());
	}
	
	
	@Test (expected = ParseException.class)
	public void testParseLibraries_missingAvailableBooks() throws ParseException {
		jsObject.put("id", ids[1]);
		jsObject.put("name", names[1]);
		jsObject.put("unavailableBooks", nUnavailable[1]);
		jsArray.put(jsObject);
		
		sut.parseLibraries(jsArray.toString());
	}
	
	
	@Test (expected = ParseException.class)
	public void testParseLibraries_missingUnavailableBooks() throws ParseException {
		jsObject.put("id", ids[1]);
		jsObject.put("name", names[1]);
		jsObject.put("availableBooks", nAvailable[1]);
		jsArray.put(jsObject);
		
		sut.parseLibraries(jsArray.toString());
	}
	
	
	@Test (expected = ParseException.class)
	public void testParseLibraries_libraryThrowsException() throws ParseException {
		jsObject.put("id", "");
		jsObject.put("name", "");
		jsObject.put("availableBooks", -1);
		jsObject.put("unavailableBooks", -1);
		jsArray.put(jsObject);
		
		sut.parseLibraries(jsArray.toString());
	}

	
	@Test (expected = ParseException.class)
	public void testParseLibraries_nullArgument() throws ParseException {
		sut.parseLibraries(null);
	}

	
	
	
	
	/**
	 *  Creates a JSONObject containing the data necessary for defining an ILibrary object  
	 * @param index Used to select id/name/#available books/#unavailable books from string arrays
	 * @return a JSONObject containing "id", "name", "availableBooks", and "unavailableBooks" key/value pairs
	 */
	private JSONObject validJsonLibrary(int index) {
		JSONObject jsonObj = new JSONObject().put("id", ids[index]);
		jsonObj.put("name", names[index]);
		jsonObj.put("availableBooks", nAvailable[index]);
		jsonObj.put("unavailableBooks", nUnavailable[index]);
		
		return jsonObj;
	}	
}
