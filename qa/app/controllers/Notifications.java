package controllers;

import java.util.List;

import models.INotification;
import models.Notification;

import utils.QaDB;

public class Notifications extends Auth{

	
	public static void list(){
		
		List<INotification> notifications = user.getNotifications();
		
		render(notifications);
		
		
	}
	
	
}
