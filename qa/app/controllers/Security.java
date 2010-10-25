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
		
		/**
     * This method returns the current connected username
     * @return
     */
    public static String connected() {
        return session.get("username");
    }

    /**
     * Indicate if a user is currently connected
     * @return  true if the user is connected
     */
    public static boolean isConnected() {
        return session.contains("username");
    }

}
