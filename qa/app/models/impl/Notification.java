package models.impl;

import java.security.InvalidParameterException;
import java.util.Date;
import java.util.List;

import models.INotification;
import models.IQuestion;
import models.IUser;
import models.INotification.Type;

/**
 * The Class Notification.
 */
public class Notification implements INotification {	
	
	
	/** The question. */
	private IQuestion question;	
	
	/** The is read variable. */
	private boolean read = false;
	
	/** The created at timestamp. */
	private long createdAt;
	
	/** The notificationstype. */
	private Type type;
	
	/** The originator. */
	private IUser originator;
	
	private IUser subscriber;
	
	private long id;
	
	private static long idCounter = 1;

	
	/**
	 * Instantiates a new notification.
	 *
	 * @param inOriginator the originator
	 * @param inQuestion the question
	 * @param inType the type
	 */
	public Notification(IUser inSubscriber, IUser inOriginator,
											IQuestion inQuestion, Type inType){
		
		if(inOriginator == null || inType == null || inQuestion == null)
			throw new InvalidParameterException("all params required");
		
		question = inQuestion;
		createdAt = System.currentTimeMillis();
		type = inType;
		originator = inOriginator;
		subscriber = inSubscriber;
		id = idCounter++;
	}
	
	/**
	 * Mark as read.
	 */
	public void markAsRead(){
		read = true;
	}
	
	/**
	 * Checks if is read.
	 *
	 * @return true, if is read
	 */
	public boolean isRead(){
		return read;
	}
	
	/**
	 * Gets the question.
	 *
	 * @return the question
	 */
	public IQuestion getQuestion(){
		return question;
	}
	
	/**
	 * Gets the originator.
	 *
	 * @return the originator
	 */
	public IUser getOriginator(){
		return originator;
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public Type getType(){
		return type;
	}
	
	/**
	 * Gets the timestamp.
	 *
	 * @return the date
	 */
	public Date createdAt(){
		return new Date(createdAt);
	}
	
  public long getId() {	  
	  return id;
  }
	
  public void delete() {
	  subscriber.delNotification(this);
  }	
  public IUser getSubscriber() {	 
	  return subscriber;
  }
	
	
}
