package controllers;

import java.util.*;

import models.*;

import play.Logger;
import play.data.validation.Required;
import utils.QaDB;

/**
 * The Class Comments.
 */
public class Comments extends Posts {
	
	
	/**
	 * Edits the comment.
	 *
	 * @param id the id
	 */
	public static void edit(long id){
				
		IComment c = QaDB.findCommentById(id);
		
		if(c == null){
			flash("error", "could not find Comment with id "+id);
			redirect("/");
		}
		
		render(c);
		
	}
	
	/**
	 * Sets the content.
	 *
	 * @param id the id
	 * @param content the content
	 */
	public static void setContent(long id, String content){
		Logger.debug("Setting new content: \""+content+"\"");
		
		Comment cA = QaDB.findCommentById(id);		
		
		
		if(cA == null){
			flash("error", "could not find answer with id "+id);
			redirect("/");
		}
		cA.setContent(content);
		flash.put("info","Content of answer "+id+" changed");
		
		Post a = cA.getPost();
		
		if(a instanceof Question)
			Questions.view(a.getId());
		else if (a instanceof Answer){
			Answer b = (Answer) a;
			IQuestion q = b.getQuestion();
			Questions.view(q.getId());
		}
		
		
	}
	
	
}
