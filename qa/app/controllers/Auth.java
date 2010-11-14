package controllers;

import models.User;
import play.mvc.Controller;

import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;
import utils.QaDB;

//@With(Secure.class)
public class Auth extends Controller{

	static User user;

	@Before
	static void setUser() {
		if (Security.isConnected()) {
			user = QaDB.findUserByName(Security.connected());
			renderArgs.put("username", user.getName());
			renderArgs.put("user", user);
		}
	}	
	
	
}
