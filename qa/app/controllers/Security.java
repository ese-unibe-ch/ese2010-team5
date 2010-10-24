package controllers;

import utils.QaDB;
import models.User;

public class Security extends Secure.Security{

		static boolean authenticate(String username, String password) {
        return QaDB.findUserByName(username) != null;
    }
	
		static void onDisconnected() {
			
			//return to home
			redirect("/");
			
		}

}
