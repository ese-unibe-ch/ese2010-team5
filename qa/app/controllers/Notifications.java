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
	
	public static void delete(long id){
		INotification n = QaDB.findNotificationById(id);
		
		if(n == null){
			flash.error("could not find notification %d", id);
			return;
		}
		
		QaDB.delNotification(n);
		
		renderText("success");
	}
	
	public static void markAsRead(long id){
		INotification n = QaDB.findNotificationById(id);
		
		if(n == null){
			flash.error("could not find notification %d", id);
			return;
		}
		
		n.markAsRead();
		
		renderText("success");
		
	}
	
	
}
