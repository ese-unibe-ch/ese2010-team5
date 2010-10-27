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

}
