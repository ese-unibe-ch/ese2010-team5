package controllers;

import java.util.List;

import models.IQuestion;
import models.IUser;

import play.data.validation.Required;
import utils.NoLogin;
import utils.QaDB;

public class Search extends Auth {

	@NoLogin
	public static void questions(@Required String q){
		if(validation.hasErrors()){
			flash.error("empty query");
			redirect("/");
		}
		
		List<IQuestion> result = QaDB.findAllQuestionsByQuery(q);
		render(q,result);
		
	}
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
