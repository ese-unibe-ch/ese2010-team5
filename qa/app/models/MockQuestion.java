package models;

import java.util.Date;
import java.util.LinkedList;

/**
 * The Class MockQuestion used for testing.
 */
public class MockQuestion implements IPost{

	private IUser owner;
	private String content;

	/**
	 * Instantiates a new mock question.
	 *
	 * @param user the user
	 * @param content the content
	 */
	public MockQuestion(IUser user, String content) {
			this.owner = user;
			this.content = content;
		}
	

}
