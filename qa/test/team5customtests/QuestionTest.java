package team5customtests;

import models.*;
import models.mock.MockUser;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;
import utils.QaMarkdown;

public class QuestionTest extends UnitTest {
	public Question quest;
	public IUser use1,use2;

	@Before
	public void SetUp() {
		use1 = new MockUser("Hans");
		use2 = new MockUser("Peter");
		quest = new Question(use1, "Testquestion for JUnit");
	}

	@Test
	public void shouldReturnCorrectTimeStamp() {
		// Testing Timestamp with accuracy of seconds
		// because there has some time passed since construction
		assertEquals(System.currentTimeMillis() / 1000, quest.getTimestamp().getTime() / 1000);
	}

	@Test
	public void shouldSetProperNewContent() {
		String firstcontent = new String(QaMarkdown.toHtml("Testquestion for JUnit"));
		String latercontent = new String(QaMarkdown.toHtml("Changed testquestion for JUnit"));
		assertEquals(firstcontent, quest.getContent());
		quest.setContent("Changed testquestion for JUnit");
		assertEquals(latercontent, quest.getContent());
	}

	@Test
	public void shouldReturnOwner(){
		assertEquals(use1,quest.getOwner());
	}

	@Test
	public void shouldNotAcceptVoteFromNullUser() {
		assertEquals(0, quest.getVotation());

		quest.rateUp(null);

		assertEquals(0, quest.getVotation());
	}
	
	@Test
	public void shouldNotAcceptDoubleVote(){
		assertEquals(0,quest.getVotation());
		quest.rateUp(use2);
		assertEquals(1,quest.getVotation());
		quest.rateUp(use2);
		assertEquals(1,quest.getVotation());
	}
	
	@Test
	public void shouldNotAcceptVoteFromOwner(){
		assertEquals(0,quest.getVotation());
		quest.rateUp(use1);
		assertEquals(0,quest.getVotation());
	}

}
