package utils;

import controllers.Security;
import models.*;
import models.impl.Post;
import play.Logger;
import play.templates.JavaExtensions;

/**
 * The Class QaExtensions.
 */
public class QaExtensions extends JavaExtensions {

	/**
	 * Checks if the post belongs to logged in user or admin.
	 *
	 * @param inPost
	 * @return true, if successful
	 */
	public static boolean ownPost(Post inPost){
		
		if(inPost == null) return false;
		
		if(Security.isConnected()){
			IUser connectedUser = QaDB.findUserByName(Security.connected());			
			if(connectedUser != null)				
				return connectedUser.equals(inPost.getOwner()) || connectedUser.isAdmin();			
			
		}
		
		return false;
	
		
	}
	
}
