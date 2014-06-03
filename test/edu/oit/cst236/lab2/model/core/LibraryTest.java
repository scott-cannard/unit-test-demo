package edu.oit.cst236.lab2.model.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.oit.cst236.lab2.model.core.Library;

public class LibraryTest {
	private Library sut;
	private final String TEST_ID = "Library_ID";
	private final String TEST_NAME1 = "Library_Name";
	private final String TEST_NAME2 = "Another_Name";

	
	
	@Before
	public void setup() {
		sut = new Library(TEST_ID, TEST_NAME1);
	}
	
	
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstrcutor_nullId() {
		new Library(null, TEST_NAME1);
	}

	
	@Test (expected = IllegalArgumentException.class)
	public void testConstrcutor_emptyId() {
		new Library("", TEST_NAME1);
	}
	
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstrcutor_nullName() {
		new Library(TEST_ID, null);
	}
	
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstrcutor_emptyName() {
		new Library(TEST_ID, "");
	}
	
	
	@Test
	public void testGetId() {
		Assert.assertEquals(sut.getId(), TEST_ID);
	}
	
	
	@Test
	public void testNameAccessors() {
		Assert.assertEquals(sut.getName(), TEST_NAME1);
		
		sut.setName(TEST_NAME2);
		Assert.assertEquals(sut.getName(), TEST_NAME2);
	}
	
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetName_nullArgument() {
		sut.setName(null);
	}
	
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetName_emptyArgument() {
		sut.setName("");
	}
	
	
	@Test
	public void testAvailableBooks() {
		Assert.assertTrue(sut.getAvailableBooks() == 0);
		
		sut.setAvailableBooks(1);
		Assert.assertTrue(sut.getAvailableBooks() == 1);
		
		sut.setAvailableBooks(Integer.MAX_VALUE);
		Assert.assertTrue(sut.getAvailableBooks() == Integer.MAX_VALUE);
	}
	
	
	@Test (expected = IllegalArgumentException.class)
	public void testAvailableBooks_negativeArgument() {
		sut.setAvailableBooks(-1);
	}
	
	
	@Test
	public void testUnavailableBooks() {
		Assert.assertTrue(sut.getUnavailableBooks() == 0);
		
		sut.setUnavailableBooks(1);
		Assert.assertTrue(sut.getUnavailableBooks() == 1);
		
		sut.setUnavailableBooks(Integer.MAX_VALUE);
		Assert.assertTrue(sut.getUnavailableBooks() == Integer.MAX_VALUE);
	}
	
	
	@Test (expected = IllegalArgumentException.class)
	public void testUnavailableBooks_negativeArgument() {
		sut.setUnavailableBooks(-1);
	}
}
