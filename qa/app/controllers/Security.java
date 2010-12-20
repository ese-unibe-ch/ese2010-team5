package controllers;

import play.Logger;
import sun.util.logging.resources.logging;
import utils.QaDB;
import models.impl.User;

/**
 * The Class Security.
 */
public class Security extends Secure.Security{

	/**
	 * Authenticate.
	 *
	 * @param username 
	 * @param password 
	 * @return true, if successfully authenticated
	 */
	static boolean authenticate(String username, String password) {
	  User u = QaDB.findUserByName(username);
	  if(u != null) {	    
	    return u.getPassword().equals(password);
	  }
	  
		return false; 
  }
	
	static void onAuthenticated() {
		/*little welcom message*/
		flash.success("Welcome %s", connected());
  }
	
	/**
	 * What to do when getting disconnected.
	 */
	static void onDisconnected() {
			//return to home			
			redirect("/");
	}
	/**
	 * checks the profile of a user
	 * @param profile
	 * @return true, if profile exists
	 */	
	static boolean check(String profile) {
		
		Logger.debug("profile: %s", profile);
		if(profile == null) return false;
		
		if(profile.equalsIgnoreCase("anonymous"))
			return true;
		
		if(isConnected()){
			Logger.debug("params: %s", params);
			return true;			
		}
		
		return false;
		    
	}
		
	/**
	 * This method returns the current connected username.
	 *
	 * @return the username
	 */
    public static String connected() {
        return session.get("username");
    }

    /**
     * Indicate if a user is currently connected.
     *
     * @return  true if the user is connected
     */
    public static boolean isConnected() {
        return session.contains("username");
    }

}
