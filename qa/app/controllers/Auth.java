package controllers;

import models.User;
import play.mvc.Controller;

import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;
import utils.QaDB;

/**
 * The Class Auth.
 */
public class Auth extends Controller{

	/** The user. */
	static User user;

	/**
	 * Sets the logged in user.
	 */
	@Before
	static void setUser() {
		if (Security.isConnected()) {
			user = QaDB.findUserByName(Security.connected());
			renderArgs.put("username", user.getName());
			renderArgs.put("user", user);
		}
	}	
	
	
}
