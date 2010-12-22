package controllers;

import java.util.*;

import models.*;
import models.impl.Answer;
import models.impl.Comment;
import play.Logger;
import play.data.validation.Required;
import utils.NoLogin;
import utils.QaDB;

/**
 * The Class Answers.
 */
public class Answers extends Posts {
	
	/**
	 * Edits the answer.
	 *
	 * @param id the id
	 */
	public static void edit(long id){
				
		Answer a = QaDB.findAnswerById(id);		
		if(a == null){
			flash("error", "could not find answer with id "+id);
			redirect("/");
		}
		render(a);
	}
	
	/**
	 * Sets the content.
	 *
	 * @param id the id
	 * @param content the content
	 */
	public static void setContent(long id, String content){
		Logger.debug("Setting new content: \""+content+"\"");
		
		Answer a = QaDB.findAnswerById(id);		
		if(a == null){
			flash("error", "could not find answer with id "+id);
			redirect("/");
		}
		a.setContent(content);
		flash.put("info","Content of answer "+id+" changed");
		
		IQuestion q = a.getQuestion();
		//sends a redirect
		Questions.view(q.getId());
		
	}
	
	/**
	 * Adds a comment.
	 *
	 * @param comment the comment
	 * @param aId the id
	 */
	public static void addComment(String comment, long aId){
		
		Answer a = QaDB.findAnswerById(aId);
		
		
		if(a == null){
			flash.error("could not find answer a: "+aId);
			redirect("/");
			return;
		}
		
		IQuestion q = a.getQuestion();
		
		Comment newComment = QaDB.addComment(new Comment(user,comment,a));
		
		flash.put("info", "new Comment created");
		
		long qId = q.getId();
		Questions.view(qId);
	}	
	
	/**
	 * View the answer.
	 *
	 * @param id the id
	 */
	@NoLogin
	public static void view(long id){
		Logger.debug("Show Answer: " +id);
		
		Answer a = QaDB.findAnswerById(id);
		if(a == null){
			flash("error", "could not find answer with id: " + id);
			redirect("/");
		}
	}
	
}
