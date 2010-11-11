package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import play.*;
import play.mvc.Controller;
import play.mvc.Scope.Session;

import models.IUser;
import models.User;
import utils.QaDB;

public class Users extends Auth{

	public static void edit(String username) {
		Logger.debug("Edit user profile: " + username);
		IUser u = QaDB.findUserByName(username);
		if(u == null){
			error("could not find user: \""+username+"\"");
			return;
		}	
		render(u);
	}
	
	public static void updateProfile(long id, String name, String email, String birthDate, String homepage){
		User user = QaDB.findUserById(id);
		if (user == null)
			flash("error", "could not find user with id: "+id);
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
		Date date = new Date();
		try {
			date = sdf.parse(birthDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (date != null){
			user.setBirthDate(date);
		}
		user.setEmail(email);
		user.setHomepage(homepage);
		Logger.debug("updated user profile of: " + user.getName());
		flash.put("info", "Profile information of " + user.getName() + " changed");
		redirect("/users/edit/"+user.getName());
	}
	
	public static void view(String username) {
		Logger.debug("Show user profile: " + username);
		User u = QaDB.findUserByName(username);
		if(u == null){
			error("could not find user: \""+username+"\"");
			return;
		}
		if (session.get("username").equals(username))
			edit(u.getName());
		else
			render(u);
	}
	
	public static void delete(User user){
		user.delete();
		redirect("/");
	}
}
