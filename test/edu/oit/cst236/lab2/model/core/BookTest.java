package edu.oit.cst236.lab2.model.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.oit.cst236.lab2.model.core.Book;

public class BookTest {
	private Book sut;
	private final String TEST_ID = "Book_ID";
	private final String TEST_TITLE = "Book_Title";
	private final String TEST_DESCRIPTION = "Book_Description";
	
	
	
	@Before
	public void setup() {
		sut = new Book(TEST_ID, TEST_TITLE);
	}

	
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstructor_NullId() {
		new Book(null, TEST_TITLE);
	}
	
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstrcutor_EmptyId() {
		new Book("", TEST_TITLE);
	}
	
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstructor_NullTitle() {
		new Book(TEST_ID, null);
	}
	
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstrcutor_EmptyTitle() {
		new Book(TEST_ID, "");
	}

	
	@Test
	public void testGetId() {
		Assert.assertEquals(sut.getId(), TEST_ID);
	}

	
	@Test
	public void testGetTitle() {
		Assert.assertEquals(sut.getTitle(), TEST_TITLE);
	}
	
	
	@Test
	public void testDescriptionAccessors() {
		sut.setDescription(TEST_DESCRIPTION);
		Assert.assertEquals(sut.getDescription(), TEST_DESCRIPTION);
	}
	
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetDescription_nullArgument() {
		sut.setDescription(null);
	}
}
