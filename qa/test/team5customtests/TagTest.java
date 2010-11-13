package team5customtests;

import static org.junit.Assert.*;
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import models.*;

import org.junit.Before;
import org.junit.*;

public class TagTest extends UnitTest{
	Tag tag;
	IUser use;
	IQuestion quest;

	@Before
	public void setUp() throws Exception {
		use = new MockUser("Hans");
		quest = new MockQuestion(use,"JUnitTestQuestion");
		tag = new Tag("junit",quest);		
	}
	
	@Test
	public void shouldReturnRightNameandToString(){
		assertEquals("junit",tag.getName());
		assertEquals("junit",tag.toString());
	}
	
	@Test
	public void shouldReturnIdGreaterThanOne(){
		assertTrue(tag.getId()>1);
	}

}
