package team5customtests;

import java.util.LinkedList;
import java.util.List;

import models.*;
import models.impl.Question;
import models.mock.MockComment;
import models.mock.MockUser;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;
import utils.QaMarkdown;

public class QuestionTest extends UnitTest {
	public Question quest;
	public IUser use1,use2;
	public IComment com1,com2;

	@Before
	public void SetUp() {
		use1 = new MockUser("Hans");
		use2 = new MockUser("Peter");
		quest = new Question(use1, "Testquestion for JUnit");
		com1 = new MockComment(use1,"testcomment1",quest);
		com2 = new MockComment(use2,"testcomment2",quest);
	}
	
	@Test
	public void shouldReturnCommentList(){
		List<IComment> comments = new LinkedList<IComment>();
		comments.add(com1);
		comments.add(com2);
		assertEquals(comments,quest.getComments());
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
		assertEquals("Changed testquestion for JUnit", quest.getMarkdown());
	}
	
	@Test
	public void shouldSetTitle(){
		assertEquals("",quest.getTitle());
		quest.setTitle("testtitle");
		assertEquals("testtitle",quest.getTitle());
	}
	
	@Test
	public void shouldReassignQuestionToNewUser(){
		assertEquals(use1,quest.getOwner());
		quest.setOwner(use2);
		assertEquals(use2,quest.getOwner());
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
	
	
	@Test
	public void shouldReturnSubscriberslist(){
		List<IUser> subscribers = new LinkedList<IUser>();
		subscribers.add(use1);
		subscribers.add(use2);
		//Since use1 is already in the subscriberslist, only use2 has to be added manually
		quest.addSubscriber(use2);
		assertEquals(subscribers,quest.getSubscribers());
	}
}