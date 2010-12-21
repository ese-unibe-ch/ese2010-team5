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
	 * @param username the username
	 * @param password the password
	 * @return true, if successful
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
		if (Security.connected() != null)
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
	 * {@link #check(String)}
	 * @param profile
	 * @return
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
	 * @return the string
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
