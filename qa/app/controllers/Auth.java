package controllers;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import models.INotification;
import models.impl.Notification;
import models.impl.User;
import play.Logger;
import play.mvc.Controller;

import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;
import utils.NoLogin;
import utils.QaDB;

/**
 * The Class Auth.
 */

public class Auth extends Controller{

	/** The user. */
	static User user = null;

	/**
	 * Sets the logged in user.
	 */
	@Before
	static void setUser() {		
		if (Security.isConnected()) {
			user = QaDB.findUserByName(Security.connected());			
			renderArgs.put("username", user.getName());
			renderArgs.put("user", user);
		}else{
			user = null;			
			renderArgs.put("username", "");
			renderArgs.put("user", "");
		}		
		Collection<INotification> notifications = 
			user != null ? 
					user.getNotifications():
						new LinkedList();
					
		renderArgs.put("notifications", notifications);
	}
	
	@Before
	static void checkNoLogin() {
		/* check if requested action method is annotated with NoLogin
		 * of course only if we are not logged in
		 */
		if(! Security.isConnected()) {
			Annotation nologin = getActionAnnotation(NoLogin.class);
			if(nologin == null){
				/*oops*/
				flash.error("must be logged in");
				redirect("/");
			}					
		}
	}
	
}
