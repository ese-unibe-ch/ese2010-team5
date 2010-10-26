package controllers;

import play.*;
import play.mvc.Controller;

import models.User;
import utils.QaDB;

public class Users extends Auth{

	public static void edit(String username) {
		Logger.debug("Edit user: " + username);
		User u = QaDB.findUserByName(username);
		if(u == null){
			error("could not find user: \""+username+"\"");
			return;
		}	
		render(u);
	}
	
	public static void updateProfile(long id, String name, String email){
		User user = QaDB.findUserById(id);
		if (user == null)
			flash("error", "could not find user with id: "+id);
		user.setUsername(name);
		user.setEmail(email);
		redirect("/users/edit/"+user.getName());
	}
}
