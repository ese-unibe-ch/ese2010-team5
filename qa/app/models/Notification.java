package models;

import java.security.InvalidParameterException;
import java.util.Date;
import java.util.List;



public class Notification {	
	
	
	private Question question;	
	private boolean read = false;
	private long createdAt;
	private Type type;
	private User originator;
	
	
	/* the text of the notification will depend on the type*/
	/* we get the text from conf/messages */
	public enum Type{
		NEW_ANSWER
	}
	
	
	public Notification(User inOriginator, Question inQuestion, Type inType){
		
		if(inOriginator == null || inType == null || inQuestion == null)
			throw new InvalidParameterException("all params required");
		
		question = inQuestion;
		createdAt = System.currentTimeMillis();
		type = inType;
		originator = inOriginator;
	}
	
	public void markAsRead(){
		read = true;
	}
	
	public boolean isRead(){
		return read;
	}
	
	public Question getQuestion(){
		return question;
	}
	
	public User getOriginator(){
		return originator;
	}
	
	public Type getType(){
		return type;
	}
	
	public Date createdAt(){
		return new Date(createdAt);
	}
	
	
}
