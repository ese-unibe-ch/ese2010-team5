package team5customtests;

import static org.junit.Assert.*;
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import models.*;
import models.mock.MockUser;

import org.junit.Before;
import org.junit.*;

public class TagTest extends UnitTest{
	Tag tag;
	//Sorry had to do it without the interface (would have to change the whole application)
	Question quest,quest1;
	IUser use,use1;

	@Before
	public void setUp() {
		use = new MockUser("Hans");
		use1 = new MockUser("Joggeli");
		quest = new Question(use,"JUnitTestQuestion");
		quest = new Question(use1,"JUnitTestQuestion1");
		tag = new Tag("junit",quest);	
	}
	
	@Test
	public void shouldReturnRelatedQuestionsCount(){
		tag.registerQuestion(quest1);
		assertEquals(2,tag.count());	
	}
	
	@Test
	public void shouldHaveOneQuestionLessAfterUnregister(){
		tag.registerQuestion(quest1);
		assertEquals(2,tag.count());
		tag.unregisterQuestion(quest1);
		assertEquals(1,tag.count());
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
