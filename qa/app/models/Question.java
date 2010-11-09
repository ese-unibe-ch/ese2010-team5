package models;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import utils.QaDB;

public class Question extends Post {
	
	private String title;	
	private List<Answer> 				answers= new LinkedList<Answer>();
	private List<User>	 				subscribers = new LinkedList<User>();	
	private List<Tag> tags = new LinkedList<Tag>();
	

	public Question(IUser user, String title, String content, String...tags) {
		super(user, content);
		this.title = title;

		addSubscriber((User) user);
		tagWith(tags);
	}

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
	
	public List<Tag> getTags(){
		return this.tags;
	}
	
	public Question(IUser user, String content) {
		this(user,"",content);
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
	
	public void addSubscriber(User inUser){
		subscribers.add(inUser);
	}
	
	public List<User> getSubscribers(){
		return subscribers;
	}
	

	public void setTitle(String title) {
		this.title = title;		
	}
	
	
	protected void doDelete() {
		/* do somthing when im getting deleted*/	  	  
	}

	public void setOwner(User user) {
		this.owner = user;
		
	}

	public boolean isTaggedWith(Tag tag) {
		return tags.contains(tag);
	}

	public boolean isTaggedWith(Tag[] tags) {
		for (Tag tag : tags){
			if (!this.tags.contains(tag))
				return false;
		}
		return true;
	}

}
