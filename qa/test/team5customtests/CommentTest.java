package team5customtests;

import models.IQuestion;
import models.IUser;
import models.impl.Comment;
import models.impl.Post;
import models.mock.MockQuestion;
import models.mock.MockUser;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;

public class CommentTest extends UnitTest {
	IUser use;
	IQuestion quest;
	Comment comm;

	@Before
	public void setUp() throws Exception {
		use = new MockUser("Hans");
		quest = new MockQuestion(use, "Will the comments work?");
		comm = new Comment(use, "I totally think that the comments work",
				(Post) quest);
	}

	@Test
	public void shouldReturnPostOfComment() {
		assertEquals(quest, comm.getPost());
	}

	/**
	 * @author simon and marius
	 */
	@Test
	public void shouldReturnLikes() {

		IUser sebastian = new MockUser("Sebastian");
		comm.like(use);

		// author of comment can't like/dislike
		assertFalse(comm.isInLikeList(use));
		assertEquals(comm.getLikes(), "0 users like this comment.");

		comm.like(sebastian);
		assertTrue(comm.isInLikeList(sebastian));
		assertEquals(comm.getLikes(), "1 user likes this comment.");

		// user can like a comment once until he unlikes it
		comm.like(sebastian);
		assertTrue(comm.isInLikeList(sebastian));
		assertEquals(comm.getLikes(), "1 user likes this comment.");

		comm.dislike(sebastian);
		assertFalse(comm.isInLikeList(sebastian));
		assertEquals(comm.getLikes(), "0 users like this comment.");

		// user can dislike a comment once until he likes it
		comm.dislike(sebastian);
		assertFalse(comm.isInLikeList(sebastian));
		assertEquals(comm.getLikes(), "0 users like this comment.");

	}

}
