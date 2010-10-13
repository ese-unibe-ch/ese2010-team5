package controllers;

import models.User;
import play.mvc.Controller;

import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Auth extends Controller{

	static User user;

	@Before
	static void setUser() {
		if (Security.isConnected()) {
			user = User.findByName(Security.connected());
			renderArgs.put("user", user.getName());
		}
	}	
	
	
}
