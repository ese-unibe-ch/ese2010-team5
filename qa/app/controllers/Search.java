package controllers;

import java.util.List;

import models.IQuestion;
import models.IUser;

import play.data.validation.Required;
import utils.NoLogin;
import utils.QaDB;

/**
 * The Class Search.
 */
public class Search extends Auth {

	/**
	 * Search in Questions.
	 *
	 * @param q the searchstring
	 */
	@NoLogin
	public static void questions(@Required String q){
		if(validation.hasErrors()){
			flash.error("empty query");
			redirect("/");
		}
		
		List<IQuestion> result = QaDB.findAllQuestionsByQuery(q);
		render(q,result);
		
	}
	
	/**
	 * Search in Users.
	 *
	 * @param q the searchstring
	 */
	@NoLogin
	public static void users(@Required String q){
		if(validation.hasErrors()){
			flash.error("empty query");
			redirect("/");
		}
		
		List<IUser> result  = QaDB.findAllUsersByQuery(q);
		render(q,result);			
		
	}
	
}
