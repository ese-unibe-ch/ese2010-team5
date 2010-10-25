package controllers;

import play.*;
import play.mvc.Controller;

import models.User;
import utils.QaDB;

public class Users extends Auth{

	public static void edit(long id) {
		Logger.debug("Edit user: " + id);

		User u = QaDB.findUserById(id);
		System.out.println(id);

		if(u == null){
			error("could not find user with id: "+id);
			return;
		}	
		render(u);
	}
}
