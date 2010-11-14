package models;

import java.util.List;

public interface IQuestion {

	/**
	 * Gets the tags.
	 *
	 * @return the tags
	 */
	public abstract List<Tag> getTags();

	/**
	 * Gets the answers posted to this question.
	 *
	 * @return the answers
	 */
	public abstract List<Answer> getAnswers();

	/**
	 * Adds a new answer.
	 *
	 * @param newAnswer the new answer
	 */
	public abstract void addAnswer(Answer newAnswer);

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public abstract String getTitle();

	/**
	 * Adds the subscriber.
	 *
	 * @param inUser the subscriber
	 */
	public abstract void addSubscriber(IUser inUser);

	/**
	 * Gets the subscribers.
	 *
	 * @return the subscribers
	 */
	public abstract List<IUser> getSubscribers();

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public abstract void setTitle(String title);

	/**
	 * override set content from post
	 */
	public abstract void setContent(String editedContent);

	public abstract String getContent();

	public abstract String getMarkdown();

	public abstract void setOwner(IUser user);

	public abstract boolean isTaggedWith(Tag tag);
	
	public abstract boolean isTaggedWith(String tagString);

	public abstract boolean isTaggedWith(Tag[] tags);

	public abstract long getId();

}