package team5customtests;

import static org.junit.Assert.*;
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import models.*;
import org.junit.Before;
import org.junit.*;

public class UserTest extends UnitTest{
	User use;

	@Before
	public void setUp() throws Exception {
		use = new User("Hans");
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
	public void shouldProperlySetAndGetDateOfBirth(){
		use.setDateOfBirth("01.01.1901");
		assertEquals("01.01.1901",use.getDateOfBirth());
	}

}
