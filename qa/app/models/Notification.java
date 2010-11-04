package models;

import java.security.InvalidParameterException;
import java.util.Date;
import java.util.List;

import play.ns.com.jhlabs.image.CraterFilter;



public class Notification {	
	
	
	private Question question;	
	private boolean read = false;
	private long createdAt;
	private Type type;
	
	
	/* the text of the notification will depend on the type*/
	/* we get the text from conf/messages */
	public enum Type{
		NEW_ANSWER
	}
	
	
	public Notification(Question inQuestion, Type inType){
		
		if(inType == null || inQuestion == null)
			throw new InvalidParameterException("inUser & inPost are required");
		
		question = inQuestion;
		createdAt = System.currentTimeMillis();
		type = inType;
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
	
	
	public Type getType(){
		return type;
	}
	
	public Date createdAt(){
		return new Date(createdAt);
	}
	
	
}
