package models;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Question extends Post {
	
	private String title;	
	private List<Answer> 				answers= new LinkedList<Answer>();
	private List<User>	 				subscribers = new LinkedList<User>();	
	

	public Question(IUser user, String title, String content) {
		super(user, content);
		this.title = title;
		addSubscriber((User) user);
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

}
