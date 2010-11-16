package models.mock;

import java.util.Date;

import models.*;

/**
 * The Class MockNotification used for testing.
 */
public class MockNotification implements INotification {
	
	/** The question. */
	private IQuestion question;	
	
	/** The originator. */
	private IUser originator;
	
		/**
		 * Instantiates a new mock notification.
		 *
		 * @param inOriginator the in originator
		 * @param inQuestion the in question
		 */
		public MockNotification(IUser inOriginator, IQuestion inQuestion){
		
		question = inQuestion;
		originator = inOriginator;
	}

		
    public Date createdAt() {
	    // TODO Auto-generated method stub
	    return null;
    }

		
    public IUser getOriginator() {
	    // TODO Auto-generated method stub
	    return null;
    }

		
    public IQuestion getQuestion() {
	    // TODO Auto-generated method stub
	    return null;
    }

		
    public Type getType() {
	    // TODO Auto-generated method stub
	    return null;
    }

		
    public boolean isRead() {
	    // TODO Auto-generated method stub
	    return false;
    }

		
    public void markAsRead() {
	    // TODO Auto-generated method stub
	    
    }
		
    public long getId() {
	    // TODO Auto-generated method stub
	    return 0;
    }

}
