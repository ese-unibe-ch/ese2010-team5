package team5customtests;

import static org.junit.Assert.*;

import java.util.Date;

import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import models.*;
import org.junit.Before;
import org.junit.*;

public class UserTest extends UnitTest{
	Date curDate;
	User use;

	@Before
	public void setUp() throws Exception {
		use = new User("Hans");
		curDate = new Date(System.currentTimeMillis());
	}
	
	@Test
	public void shouldReturnUserName(){
		assertEquals("Hans",use.getUsername());
		assertEquals("Hans",use.getName());
	}
	
	@Test
	public void shouldSetUserName(){
		assertEquals("Hans",use.getName());
		use.setUsername("Peter");
		assertEquals("Peter",use.getName());
	}
	
	@Test
	public void shouldProperlySetAndGetEmail(){
		use.setEmail("myaddress@myprovider.mydomain");
		assertEquals("myaddress@myprovider.mydomain",use.getEmail());
	}
	
	@Test
	public void shouldReturnUserID(){
		assertTrue(use.getId()>0);
	}
	
	@Test 
	public void shouldProperlySetAndGetHomePage(){
		use.setHomepage("www.internet.ch");
		assertEquals("www.internet.ch",use.getHomepage());
	}
	
	@Test
	public void shouldProperlySetAndGetDateOfBirth(){
		use.setBirthDate(curDate);
		assertEquals(curDate,use.getBirthDate());
	}

}
