package models;

import java.util.*;

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
	
	/** Rendered content in html. */
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
	 * @param tagString the tag string
	 */
	private void tagWith(String tagString) {
		String delims = ", ";
		
		String[] stringTags = tagString.split(delims);
				
		for(String tagName : stringTags){
			//check DB if tag already exists			
			Tag t = QaDB.findTagByName(tagName);
			
			if( t != null){
				this.tags.add(t);
				t.registerQuestion(this);
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
	
	public List<Answer> getAnswers() {
		return this.answers;
	}

	public void addAnswer(Answer newAnswer) {		
		this.answers.add(newAnswer);		
	}
	
	public String getTitle(){
		return title;
	}
	
	public void addSubscriber(IUser inUser){
		subscribers.add(inUser);
	}
	
	public List<IUser> getSubscribers(){
		return subscribers;
	}
	
	public void setTitle(String title) {
		this.title = title;		
	}
	
	@Override
	public void setContent(String editedContent){
		super.setContent(editedContent);
		
		/*render markdown*/
		/*also update the rendered one*/
		renderedContent = QaMarkdown.toHtml(editedContent);
		
	}
	
	@Override
	public String getContent(){
		return renderedContent;
	}
	
	public String getMarkdown(){
		return content;
	}
	
	protected void doDelete() {
		/* do somthing when im getting deleted*/	  	  
	}

	public void setOwner(IUser user) {
		this.owner = user;
		
	}

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

	public boolean isTaggedWith(Tag[] tags) {
		for (Tag tag : tags){
			if (!this.tags.contains(tag))
				return false;
		}
		return true;
	}


	

}
