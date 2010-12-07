package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.hibernate.annotations.OptimisticLock;

import play.*;
import play.data.validation.Email;
import play.data.validation.Required;
import play.mvc.Controller;
import play.mvc.Scope.Session;
import play.mvc.results.Redirect;

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
	 * @throws Throwable 
	 */
	public static void edit(String username) {
		if (!Security.connected().equals(username))
			redirect("/");
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
		validation.email(email);
		validation.match(homepage, "^http\\://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(/\\S*)?$");
		
		if (validation.hasErrors()){
			 params.flash(); // add http parameters to the flash scope
	         validation.keep(); // keep the errors for the next request
	         edit(user.getName());
		}
		
		User user = QaDB.findUserById(id);
		if (user == null)
			flash("error", "could not find user with id: "+id);
		
		user.update(email, birthDate, homepage);
		
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
			try {
				edit(u.getName());
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
	public static void create(@Required String username, @Required @Email String email, 
			String birthDate, String homepage){
		
		if(validation.hasErrors()){
			params.flash();
			validation.keep();
			signup();
		}
		 
		User u = QaDB.addUser(new User(username));
		u.update(email, birthDate, homepage);
		String password = "test";		
		
		try {			
			/** be careful! this generate a http-redirect response, 
			 *  which results in a GET /login */			
	    Secure.authenticate(username, password, false);
	    if(Security.isConnected()){
	    	Logger.debug("connected");
	    	flash.put("Welcome %s", username);
	    	
	    }
    } catch (Throwable e) {    	
    	flash.error("strange error %s",e.getMessage());
    	redirect("/");
    }		
		
		
	}
	
	
	public static void signup(){
		
		render();
		
	}
	
}
