package models;

import java.security.InvalidParameterException;

import models.Notification.Type;

public class MockNotification implements INotification {
	private IQuestion question;	
	private IUser originator;
	
	
	
	public MockNotification(IUser inOriginator, IQuestion inQuestion){
		
		question = inQuestion;
		originator = inOriginator;
	}

}
