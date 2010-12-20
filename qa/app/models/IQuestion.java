package models;

import java.util.List;

import models.impl.Answer;
import models.impl.Question;
import models.impl.Tag;

/**
 * The Interface IQuestion.
 */
public interface IQuestion extends IPost{

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
	 * Remove a  answer.
	 *
	 * @param newAnswer the new answer
	 */
	public boolean delAnswer(Answer newAnswer);

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
	
	public void remSubscriber(IUser inUser);
	public boolean isSubscriber(IUser inUser);

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
	 * override content of the question.
	 *
	 * @param editedContent the new content
	 */
	public abstract void setContent(String editedContent);

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public abstract String getContent();

	/**
	 * Gets the markdown parsed content.
	 *
	 * @return the markdown string
	 */
	public abstract String getMarkdown();

	/**
	 * Sets the owner.
	 *
	 * @param user the new owner
	 */
	public abstract void setOwner(IUser user);

	/**
	 * Tag question with tags specified in a String of comma and whitespace separated tag names.
	 *
	 * @param tagString the String of tag names
	 */
	
	
	/**
	 * Checks if is tagged with.
	 *
	 * @param tag the tag
	 * @return true, if is tagged with the tag
	 */
	public abstract boolean isTaggedWith(Tag tag);
	
	/**
	 * Checks if is tagged with.
	 *
	 * @param tagString the tag string
	 * @return true, if is tagged with
	 */
	public abstract boolean isTaggedWith(String tagString);

	/**
	 * Checks if is tagged with.
	 *
	 * @param tags a list of tags
	 * @return true, if is tagged with all the tags in the list
	 */
	public abstract boolean isTaggedWith(Tag[] tags);

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public abstract long getId();
 
	public abstract void close();

	public abstract boolean isClosed();

	public abstract void reopen();
}