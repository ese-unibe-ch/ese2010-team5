package models.mock;

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

}
