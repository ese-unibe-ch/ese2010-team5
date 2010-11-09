package models;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import utils.QaDB;
import utils.QaMarkdown;

/**
 * The Class Question.
 */
public class Question extends Post {
	
	/** The title. */
	private String title;	
	
	/** The answers. */
	private List<Answer> answers= new LinkedList<Answer>();
	
	/** The subscribers. */
	private List<User> subscribers = new LinkedList<User>();	
	
	/** The tags. */
	private List<Tag> tags = new LinkedList<Tag>();
	
	/** Rendered content in html */
	private String renderedContent;
	

	/**
	 * Instantiates a new question.
	 *
	 * @param user the user who created the question
	 * @param title the title
	 * @param content the content
	 * @param tags the tags
	 */
	public Question(IUser user, String title, String content, String...tags) {
		super(user, content);
		this.title = title;
		setContent(content);
		addSubscriber((User) user);
		tagWith(tags);
	}

	/**
	 * Tag with.
	 *
	 * @param tags the tags
	 */
	private void tagWith(String... tags) {
		for(String tagName : tags){
			//check DB if tag already exists
			if(QaDB.findTagByName(tagName) != null){
				this.tags.add(QaDB.findTagByName(tagName));
			}
			else{
				Tag tag = new Tag(tagName, this);
				QaDB.addTag(tag);
				this.tags.add(tag);
			}
		}
	}
	
	/**
	 * Gets the tags.
	 *
	 * @return the tags
	 */
	public List<Tag> getTags(){
		return this.tags;
	}
	
	/**
	 * Instantiates a new question.
	 *
	 * @param user the owner
	 * @param content the content
	 */
	public Question(IUser user, String content) {
		this(user,"",content);
	}

	
	/**
	 * Gets the answers posted to this question.
	 *
	 * @return the answers
	 */
	public List<Answer> getAnswers() {
		return this.answers;
	}

	/**
	 * Adds a new answer.
	 *
	 * @param newAnswer the new answer
	 */
	public void addAnswer(Answer newAnswer) {		
		this.answers.add(newAnswer);		
	}
	
	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle(){
		return title;
	}
	
	/**
	 * Adds the subscriber.
	 *
	 * @param inUser the subscriber
	 */
	public void addSubscriber(User inUser){
		subscribers.add(inUser);
	}
	
	/**
	 * Gets the subscribers.
	 *
	 * @return the subscribers
	 */
	public List<User> getSubscribers(){
		return subscribers;
	}
	

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;		
	}
	
	/**
	 * override set content from post
	 */	
	public void setContent(String editedContent){
		super.setContent(editedContent);
		
		/*render markdown*/
		/*also update the rendered one*/
		renderedContent = QaMarkdown.toHtml(editedContent);
		
	}
	
	public String getContent(){
		return renderedContent;
	}
	
	public String getMarkdown(){
		return content;
	}
	
	
	protected void doDelete() {
		/* do somthing when im getting deleted*/	  	  
	}

}
