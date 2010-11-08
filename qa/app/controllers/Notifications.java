package controllers;

import java.util.List;

import models.Notification;

import utils.QaDB;

public class Notifications extends Auth{

	
	public static void list(){
		
		List<Notification> notifications = user.getNotifications();
		
		render(notifications);
		
		
	}
	
	
}
