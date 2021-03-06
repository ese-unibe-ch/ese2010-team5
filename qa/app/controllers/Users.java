package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import play.*;
import play.mvc.Controller;
import play.mvc.Scope.Session;

import models.IQuestion;
import models.IUser;
import models.impl.Answer;
import models.impl.Question;
import models.impl.User;
import utils.QaDB;

/**
 * The Class Users.
 */
public class Users extends Auth{

	/**
	 * Edits the user.
	 *
	 * @param username the username
	 */
	public static void edit(String username) {
		Logger.debug("Edit user profile: " + username);
		IUser u = QaDB.findUserByName(username);
		if(u == null){
			error("could not find user: \""+username+"\"");
			return;
		}
		List<IQuestion> questions = QaDB.findAllQuestionsOfUser(u);
		List<Answer> answers = QaDB.findAllAnswersOfUser(u);
		
		render(u, questions, answers);
	}
	
	/**
	 * Update profile.
	 *
	 * @param id the id
	 * @param name the name
	 * @param email the email
	 * @param birthDate the birth date
	 * @param homepage the homepage
	 */
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
	
	/**
	 * View the user.
	 *
	 * @param username the username
	 */
	public static void view(String username) {
		Logger.debug("Show user profile: " + username);
		User u = QaDB.findUserByName(username);
		if(u == null){
			error("could not find user: \""+username+"\"");
			return;
		}
		
		List<IQuestion> questions = QaDB.findAllQuestionsOfUser(u);
		List<Answer> answers = QaDB.findAllAnswersOfUser(u);
		
		if (Security.isConnected() && Security.connected().equals(username))
			edit(u.getName());
		else
			render(u, questions, answers);
	}
	
	/**
	 * Delete the user.
	 *
	 * @param username the username
	 * @throws Throwable 
	 */
	public static void delete(String username) throws Throwable{
		User user = QaDB.findUserByName(username);
		if(user == null){
			error("could not find user: \""+username+"\"");
			return;
		}
		user.delete();
		Secure.logout();
		//redirect("/");

	}
}
