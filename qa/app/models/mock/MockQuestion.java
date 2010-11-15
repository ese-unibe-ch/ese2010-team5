package models.mock;

import models.*;
import java.util.*;

/**
 * The Class MockQuestion used for testing.
 */
public class MockQuestion extends Post implements IQuestion{

	private IUser owner;
	private String content;

	/**
	 * Instantiates a new mock question.
	 *
	 * @param user the user
	 * @param content the content
	 */
	public MockQuestion(IUser user, String content) {
			super(user,content);
			this.owner = user;
			this.content = content;
		}

	
	public List<Answer> getAnswers() {
		return null;
	}

	
	public void addAnswer(Answer newAnswer) {
		
	}
	public boolean delAnswer(Answer newAnswer) {
		return true;
	}

	
	public String getTitle() {
		return null;
	}

	
	public void addSubscriber(IUser inUser) {
		
	}

	
	public List<IUser> getSubscribers() {
		return null;
	}

	
	public void setTitle(String title) {
		
	}

	
	public String getMarkdown() {
		return null;
	}

	
	public void setOwner(IUser user) {
		
	}

	
	public boolean isTaggedWith(Tag tag) {
		return false;
	}

	
	public boolean isTaggedWith(Tag[] tags) {
		return false;
	}

	@Override
	protected void doDelete() {
		
	}

	
	public List<Tag> getTags() {
		return null;
	}

	public boolean isTaggedWith(String tagString) {
		return false;
	}
	

}
