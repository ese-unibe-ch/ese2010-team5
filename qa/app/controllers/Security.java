package controllers;

import models.User;

public class Security extends Secure.Security{

		static boolean authenticate(String username, String password) {
        return User.findByName(username) != null;
    }
	
		static void onDisconnected() {
			
			//return to home
			redirect("/");
			
		}

}
