package utils;

import controllers.Security;
import models.*;
import models.impl.Post;
import play.Logger;
import play.templates.JavaExtensions;

public class QaExtensions extends JavaExtensions {

	public static boolean ownPost(Post inPost){
		
		if(inPost == null) return false;
		
		if(Security.isConnected()){
			IUser connectedUser = QaDB.findUserByName(Security.connected());			
			if(connectedUser != null)				
				return connectedUser.equals(inPost.getOwner());			
			
		}
		
		return false;
	
		
	}
	
}
