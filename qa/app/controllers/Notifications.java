package controllers;

import java.util.List;

import models.INotification;
import models.impl.Notification;

import utils.QaDB;

/**
 * The Class Notifications.
 */
public class Notifications extends Auth{

	
	/**
	 * List the notifications.
	 */
	public static void list(){
		
		List<INotification> notifications = user.getNotifications();
		
		render(notifications);
		
		
	}
	
	
}
