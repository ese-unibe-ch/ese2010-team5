package models;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import play.Logger;

import utils.QaDB;
import utils.QaMarkdown;

/**
 * The Class Question.
 */
public class Question extends Post implements IQuestion {
	
	/** The title. */
	private String title;	
	
	/** The answers. */
	private List<Answer> answers= new LinkedList<Answer>();
	
	/** The subscribers. */
	private List<IUser> subscribers = new LinkedList<IUser>();	
	
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
	public Question(IUser user, String title, String content, String tags) {
		super(user, content);
		this.title = title;
		setContent(content);
		addSubscriber(user);
		tagWith(tags);
	}
	
	
	/**
	 * Instantiates a new question.
	 *
	 * @param user the owner
	 * @param content the content
	 */
	public Question(IUser user, String content) {
		this(user,"",content, "");
	}
	
	/**
	 * Tag with.
	 *
	 * @param tags the tags
	 */
	private void tagWith(String tagString) {
		String delims = ", ";
		
		String[] stringTags = tagString.split(delims);
				
		for(String tagName : stringTags){
			//check DB if tag already exists
			Logger.debug("tagwith: %s",tagName);
			Tag t = QaDB.findTagByName(tagName); 
			//Logger.debug("tagwith: %s",t.getName());
			if( t != null){
				this.tags.add(t);
			}
			else{
				t = QaDB.addTag(new Tag(tagName, this));
				this.tags.add(t);
			}
		}
	}
	
	
	public List<Tag> getTags(){
		return this.tags;
	}
	


	
	/* (non-Javadoc)
	 * @see models.IQuestion#getAnswers()
	 */
	
	public List<Answer> getAnswers() {
		return this.answers;
	}

	/* (non-Javadoc)
	 * @see models.IQuestion#addAnswer(models.Answer)
	 */
	
	public void addAnswer(Answer newAnswer) {		
		this.answers.add(newAnswer);		
	}
	
	/* (non-Javadoc)
	 * @see models.IQuestion#getTitle()
	 */
	
	public String getTitle(){
		return title;
	}
	
	/* (non-Javadoc)
	 * @see models.IQuestion#addSubscriber(models.User)
	 */
	
	public void addSubscriber(IUser inUser){
		subscribers.add(inUser);
	}
	
	/* (non-Javadoc)
	 * @see models.IQuestion#getSubscribers()
	 */
	
	public List<IUser> getSubscribers(){
		return subscribers;
	}
	

	/* (non-Javadoc)
	 * @see models.IQuestion#setTitle(java.lang.String)
	 */
	
	public void setTitle(String title) {
		this.title = title;		
	}
	
	/* (non-Javadoc)
	 * @see models.IQuestion#setContent(java.lang.String)
	 */	
	@Override
	public void setContent(String editedContent){
		super.setContent(editedContent);
		
		/*render markdown*/
		/*also update the rendered one*/
		renderedContent = QaMarkdown.toHtml(editedContent);
		
	}
	
	/* (non-Javadoc)
	 * @see models.IQuestion#getContent()
	 */
	@Override
	public String getContent(){
		return renderedContent;
	}
	
	/* (non-Javadoc)
	 * @see models.IQuestion#getMarkdown()
	 */
	
	public String getMarkdown(){
		return content;
	}
	
	protected void doDelete() {
		/* do somthing when im getting deleted*/	  	  
	}

	/* (non-Javadoc)
	 * @see models.IQuestion#setOwner(models.User)
	 */
	
	public void setOwner(IUser user) {
		this.owner = user;
		
	}

	/* (non-Javadoc)
	 * @see models.IQuestion#isTaggedWith(models.Tag)
	 */
	
	public boolean isTaggedWith(Tag tag) {
		return this.tags.contains(tag);
	}
	
	public boolean isTaggedWith(String tagString) {
		for (Tag tag : tags){
			if (tag.getName().equals(tagString))
				return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see models.IQuestion#isTaggedWith(models.Tag[])
	 */
	
	public boolean isTaggedWith(Tag[] tags) {
		for (Tag tag : tags){
			if (!this.tags.contains(tag))
				return false;
		}
		return true;
	}


	

}
