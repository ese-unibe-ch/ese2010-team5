package team5customtests;


import static org.junit.Assert.*;
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import models.*;
import org.junit.*;
import org.junit.Before;

public class QuestionTest extends UnitTest{
	public Question quest;
	
	@Before
	public void SetUp(){
		quest = new Question(null,"Testquestion for JUnit");
	}
	
	@Test
	public void shouldReturnCorrectTimeStamp(){
		//Testing Timestamp with accuracy of seconds
		//because there has some time passed since construction
		assertEquals(System.currentTimeMillis()/1000,quest.getTimestamp().getTime()/1000);
	}
	
	@Test
	public void shouldSetProperNewContent(){
		assertEquals("Testquestion for JUnit",quest.getContent());
		quest.setContent("Changed testquestion for JUnit");
		assertEquals("Changed testquestion for JUnit",quest.getContent());
	}
	
	@Test
	public void shouldNotAcceptVoteFromNullUser(){
		assertEquals(0,quest.getVotation());
		
		quest.rateUp(null);
		
		assertEquals(0,quest.getVotation());
	}

}
