package models;

import java.util.Date;

/**
 * The Interface INotification.
 */
public interface INotification {
	
	/* the text of the notification will depend on the type*/
	/* we get the text from conf/messages */
	/**
	 * The Enum Type.
	 */
	public enum Type{
		NEW_ANSWER
	}

	public Date  			createdAt();
	public IUser 			getOriginator();
	public IUser			getSubscriber();
	public IQuestion 	getQuestion();
	public Type				getType();
	public boolean 		isRead();
	public void 			markAsRead();
	public long 			getId();
	public void				delete();
	
	
}