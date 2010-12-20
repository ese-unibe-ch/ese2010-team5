package models.impl;

import java.util.*;

import models.INotification;
import models.IQuestion;
import models.IUser;
import models.INotification.Type;

import play.Logger;

import utils.QaDB;
import utils.QaMarkdown;
import utils.QaSorter;
import utils.QaDB.OrderBy;

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

	private boolean closed;
	

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
		tagWith(tags.toLowerCase());
		
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
	 * Tag with a String of tag names.
	 *
	 * @param tagString the string containing the tag names
	 */
	public void tagWith(String tagString) {
		String delims = ", ";
		String[] stringTags = tagString.split(delims);		
		for(String tagName : stringTags){
			if (!tagName.isEmpty()){
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
	}
	
	public List<Tag> getTags(){
		return this.tags;
	}
	
	public List<Answer> getAnswers() {
		
		/*sort them by date*/		
		Collections.sort(answers, new QaSorter(OrderBy.DATE));		
		
		if(! answers.isEmpty()){			
			/* find best answer*/
			int idx = -1;
			for(Answer a: answers){
				idx++;
				if(a.isBest()){					
					break;
				}
			}
			/* move to the head if found*/
			if(idx >= 0){
				Answer bestAnswer = answers.remove(idx);
				answers.add(0, bestAnswer);
			}
		}	
		return answers;
	}

	public void addAnswer(Answer newAnswer) {
		
		if(newAnswer == null)
			return;
		
		answers.add(newAnswer);
		
		/*publish notifications for subscribers*/
		List<IUser> subscribers = getSubscribers();
		for(IUser subscriber : subscribers){
			
			subscriber.addNotification(
					QaDB.addNotification(new Notification(subscriber,newAnswer.getOwner(),this, Notification.Type.NEW_ANSWER))
			);
		}	
	}
	
	public boolean delAnswer(Answer newAnswer) {
		if(answers != null){
			return answers.remove(newAnswer);
		}	
		return false;		
	}
	
	public String getTitle(){
		return title;
	}
	
	public void addSubscriber(IUser inUser){
		subscribers.add(inUser);
		inUser.addSubscription(this);
	}
	
	public List<IUser> getSubscribers(){
		return subscribers;
	}
	
	public void setTitle(String title) {
		this.title = title;		
	}
	
	public void setContent(String editedContent, boolean rendered){
		if(rendered){
			super.setContent(editedContent);
			renderedContent = editedContent;
		}else{
			super.setContent(editedContent);
			//mark renderedContent as dirty, so that it will be re-rendered
			//when needed
			renderedContent = null;
		}
		
	}
	
	public void setContent(String editedContent){
		setContent(editedContent,false);		
	}
	
	
	public String getContent(){
		
		if(renderedContent == null){
			/*render markdown*/			
			renderedContent = QaMarkdown.toHtml(content);	
		}
		
		return renderedContent;
	}
	
	public String getMarkdown(){
		return content;
	}
	
	protected void doDelete() {
		/* do somthing when im getting deleted*/
		/* maybe remove the answers*/
		deleteTags();
		remSubscribers();
		//deleteAnswers();
		//deleteComments();
		//QaDB.removeQuestion(this);
	}

	private void deleteAnswers() {
		if (answers != null){
			for (Answer answer : answers){
				answer.doDelete();
			}
		}
	}

	private void deleteTags() {
		for(Tag tag : tags){
			// ensure that a tag is deleted from DB when there is no question tagged with it
			tag.unregisterQuestion(this);
			if (tag.count() == 0)
				QaDB.removeTag(tag);
		}
	}
	
	private void remSubscribers(){
		for(IUser u : subscribers){
			u.remSubscription(this);
		}
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
	
  public boolean isSubscriber(IUser inUser) {
	  if(inUser == null)return false;
  	
	  return subscribers.contains(inUser);
  }
	
  public void remSubscriber(IUser inUser) {
	  if(inUser == null) return;	  
	  subscribers.remove(inUser);
	  inUser.remSubscription(this);
  }

	public void addTag(String tagName) {
		Tag tag = Tag.findOrCreateTagByName(tagName, this);
		tags.add(tag);
	}
	
	public static void createQuestion(User user, String title, String content,
			String tag) {
		
		QaDB.addQuestion(new Question(user, title, content, tag));
	
	}
	
	public void addAnswer(User user, String answer) {
		QaDB.addAnswer(new Answer(user, answer, this));
	}

	public Comment addComment(User user, String comment) {
		Comment newComment = QaDB.addComment(new Comment(user, comment, this));
		return newComment;
	}
	
	public boolean save(){
		QaDB.addQuestion(this);
		return true;
	}

	public void close() {
		closed = true;
		
	}

	public boolean isClosed() {
		return closed;
	}

	public void reopen() {
		closed = false;
	}

}
