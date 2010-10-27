package team5customtests;

import static org.junit.Assert.*;
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import models.*;
import org.junit.Before;
import org.junit.*;

public class AnswerTest extends UnitTest{
	Answer ans;

	@Before
	public void setUp() throws Exception {
		//Noch Mockclasses erstellen für User,Question,Comment
		Answer ans = new Answer(null,"This UnitTest will work sometime",null);
	}
	
	@Test
	public void shouldNotBeBestAfterConstruction(){
		assertFalse(ans.isBest());
	}
	
	@Test
	public void shouldSetProperlyToBest(){
		assertFalse(ans.isBest());
		ans.setIsBest(true);
		assertTrue(ans.isBest());
		
	}

}
