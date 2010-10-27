package team5customtests;

import static org.junit.Assert.*;
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import models.*;
import org.junit.Before;
import org.junit.*;


public class CommentTest extends UnitTest{
	IUser use;
	Post quest;
	Comment comm;

	@Before
	public void setUp() throws Exception {
		use = new MockUser("Hans");
		quest = new MockQuestion(use,"Will the comments work?");
		comm = new Comment(use,"I totally think that the comments work",quest);
	}
	
	@Test
	public void shouldReturnPostOfComment(){
		assertEquals(quest,comm.getPost());
	}

}
