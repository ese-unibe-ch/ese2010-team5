package models;

import java.security.InvalidParameterException;
import java.util.Date;
import java.util.List;

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
	
	
	/* the text of the notification will depend on the type*/
	/* we get the text from conf/messages */
	/**
	 * The Enum Type.
	 */
	public enum Type{
		NEW_ANSWER
	}

	
	/**
	 * Instantiates a new notification.
	 *
	 * @param inOriginator the originator
	 * @param inQuestion the question
	 * @param inType the type
	 */
	public Notification(IUser inOriginator, IQuestion inQuestion, Type inType){
		
		if(inOriginator == null || inType == null || inQuestion == null)
			throw new InvalidParameterException("all params required");
		
		question = inQuestion;
		createdAt = System.currentTimeMillis();
		type = inType;
		originator = inOriginator;
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
	
	
}
